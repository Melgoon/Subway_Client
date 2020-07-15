package kr.or.ddit.sw.view.cart;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXMasonryPane;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import kr.or.ddit.sw.service.cart.ICartService;
import kr.or.ddit.sw.service.login.LoginSession;
import kr.or.ddit.sw.vo.cart.CartJoinProdPicVO;
import kr.or.ddit.sw.vo.cart.CartVO;
import kr.or.ddit.sw.vo.cart.CombiMtrJoinVO;
import kr.or.ddit.sw.vo.ordertable.OrderVO;
import kr.or.ddit.sw.vo.prod.ProdVO;

import java.io.IOException;
import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class CartController implements Initializable {




    public JFXButton send;
    public JFXButton remove;
    public JFXMasonryPane man;

    private Registry reg;
    private ICartService iCartService;



    @Override
    public void initialize(URL location, ResourceBundle resources) {

        try {
            reg = LocateRegistry.getRegistry("localhost", 7774);
            iCartService = (ICartService) reg.lookup("cart");
        } catch (RemoteException | NotBoundException e) {
            e.printStackTrace();
        }

        //combi와 mtr을 조인한 list생성
        List<CombiMtrJoinVO> list = new ArrayList<>();
        try {
            list = iCartService.selectCombiMtrJoin();
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        if(list.size()==0){//--------------------------------------------------------------추가
            //장바구니 -> check가 0인것들만(장바구니 체크여부가 0인것들)가져옴
            List<CartJoinProdPicVO> prodpiclist = new ArrayList<>();
            try {
                prodpiclist = iCartService.selectCartJoinProdPic(LoginSession.memberSession.getMEM_ID());
            } catch (RemoteException e) {
                e.printStackTrace();
            }

            ArrayList<Integer> checkcartno = new ArrayList<>();  //체크된 번호 리스트

            //사진 불러오기
            for (CartJoinProdPicVO vo : prodpiclist) {
                System.out.println("------------------" + prodpiclist.size());
                System.out.println("-----------------" + vo.getProd_no());
                //HBox hbox = new HBox();
                VBox vbox = new VBox();
                VBox labelvbox = new VBox();
                String prodname = null;
                int prodno = vo.getProd_no();

                try {
                    ProdVO pvo;
                    pvo = iCartService.selectProdName(prodno);
                    prodname = pvo.getProd_name();

                } catch (RemoteException e) {
                    e.printStackTrace();
                }

                CheckBox ch = new CheckBox();
                Label l_prod = new Label("상품 이름 : " + prodname);
                Label l_menuinfo = new Label("상세 내용 : " + vo.getCart_cont());
                Label l_price = new Label("상품 가격 : " + Integer.toString(vo.getCart_price()));
                labelvbox.getChildren().addAll(ch, l_prod, l_menuinfo, l_price);
/*          l_prod.setFont(new Font("Arial", 30));
            l_prod.setRotate(270);
            l_prod.setTranslateY(50);

            l_menuinfo.setFont(new Font("Arial", 30));
            l_menuinfo.setRotate(270);
            l_menuinfo.setTranslateY(50);

            l_price.setFont(new Font("Arial", 30));
            l_price.setRotate(270);
            l_price.setTranslateY(50);
*/

                ImageView imageView = new ImageView();
                Image image = new Image(vo.getProd_pic_adr());
                imageView.setImage(image);

                //hbox.getChildren().addAll(imageView,labelvbox);
                //hbox.getChildren().addAll(imageView,l_prod);
                vbox.getChildren().addAll(imageView, labelvbox);

                ch.setOnAction(e -> {
                    if (ch.isSelected() == true) {
                        checkcartno.add(vo.getCart_no()); //체크되었을때 값 리스트에 넣기
                    } else if (ch.isSelected() == false) {
                        checkcartno.remove((Integer) vo.getCart_no());
                    }

                });

                man.getChildren().add(vbox);
            }



            //장바구니삭제버튼 누르기//
            //todo 장바구니 삭제버튼 누른후 삭제는되는데 바로바로 삭제되는모습을어떻게..ㅠ
            remove.setOnAction(e -> {
                for (int i = 0; i < checkcartno.size(); i++) {
                    try {
                        iCartService.deleteCart(checkcartno.get(i));
                    } catch (RemoteException e1) {
                        e1.printStackTrace();
                    }
                }

                try {
                    Parent root = FXMLLoader.load(getClass().getResource("CartLoader.fxml"));
                    LoginSession.mainViewController.bp.setCenter(root);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            });


            //구매 버튼 누르기//
            send.setOnAction(e -> {
                System.out.println(checkcartno.size());
                System.out.println("-------------------");

                //checkprodno가 cart_no와 일치하는 cart테이블의 cart_chk컬럼을 1로 바꾼다
                for (int i = 0; i < checkcartno.size(); i++) {
                    try {
                        iCartService.updateCartChk(checkcartno.get(i));
                        System.out.println(checkcartno.get(i) + "번 cart_chk를 1로 바꾸었습니다");
                    } catch (RemoteException e1) {
                        e1.printStackTrace();
                    }
                }

                //장바구니 -> check가 1이고 buy_chk가 0인것만 가져옴
                List<CartJoinProdPicVO> orderlist = new ArrayList<>();
                try {
                    orderlist = iCartService.selectOrder(LoginSession.memberSession.getMEM_ID());
                } catch (RemoteException e2) {
                    e2.printStackTrace();
                }


                //prod_name을 합쳐서 oder_cont라는 변수에 넣어줌.
                //CartPrice를 합쳐서 oder_price라는 변수에 넣어줌.
                //cart_no를 String으로 여러개의 cart_no를 받는다 ex) 1,2,3,...
                String o_order_cont = " ";
                int o_cart_price = 0;
                String cart_no_string = " ";
                for (int i = 0; i < orderlist.size(); i++) {

                    try {
                        ProdVO pvo;
                        pvo = iCartService.selectProdName(orderlist.get(i).getProd_no());
                        o_order_cont += pvo.getProd_name() + ",";

                    } catch (RemoteException e2) {
                        e2.printStackTrace();
                    }
                    //todo 맨뒤 ,지우기

                    o_cart_price += orderlist.get(i).getCart_price();
                    cart_no_string += orderlist.get(i).getCart_no() + ",";

                    //todo 맨뒤 ,지우기

                }

                //order_qty를 0개로 설정한다
                //order_coupon의 경우 결제창에서 해주어야하기 때문에 우선 0으로 설정해줌
                //order_st의 경우 주문중이기에 0으로 설정해줌
                //prod_no의 경우 0으로 설정
                //cart_no<- 0


                // CART_NO_STRING String으로 수정하고, cart_no 제약조건 삭제하기
                //mem_id추가하여 member_table과 제약조건 연결하기
                //todo session id로 바꾸어야함
                String o_mem_id = LoginSession.memberSession.getMEM_ID();

                //이모든 변수들을 order_table에 insert시켜주는 메서드 입력력
                OrderVO ovo = new OrderVO();
                ovo.setCart_no(0);
                ovo.setOrder_qty(0);
                ovo.setOrder_price(o_cart_price);
                ovo.setOrder_coupon(0);
                ovo.setOrder_st("주문중");
                ovo.setProd_no(0);
                ovo.setOrder_cont(o_order_cont);
                ovo.setCart_no_string(cart_no_string);
                ovo.setMem_id(o_mem_id);

                try {
                    iCartService.insertOrder(ovo);
                } catch (RemoteException remoteException) {
                    remoteException.printStackTrace();
                }

                //checkprodno가 cart_no와 일치하는 cart테이블의 cart_chk_buy컬럼을 1로 바꾼다
                for (int i = 0; i < checkcartno.size(); i++) {
                    try {
                        iCartService.updateCartCHkBuy(checkcartno.get(i));
                        System.out.println(checkcartno.get(i) + "번 cart_chk_buy를 1로 바꾸었습니다");
                    } catch (RemoteException e1) {
                        e1.printStackTrace();
                    }
                }


            });
        }else { //-------------------------------------------------------------------------추가

            int prod_no = list.get(0).getProd_no();//상품번호
            int cart_price = list.get(0).getProd_price(); //가격
            String cart_cont = ""; //카트 내용


            System.out.println(prod_no);
            System.out.println(cart_price);
            System.out.println(cart_cont);

            //세트가격을 위한 부분
            for (int i = 0; i < list.size(); i++) {
                cart_cont += list.get(i).getMtr_name() + ",";
                if (list.get(i).getMtr_div() == 6) {
                    cart_price += 1900;
                }
            }

            //장바구니 담기
            CartVO cv = new CartVO();
            cv.setCart_chk(0);
            cv.setCart_chk_buy(0);
            cv.setCart_cont(cart_cont);
            cv.setCart_price(cart_price);
            cv.setCart_qty(0);
            cv.setCombi_no(list.get(0).getCombi_no());//무의미함
            cv.setMem_id(LoginSession.memberSession.getMEM_ID());
            cv.setMtr_no(list.get(0).getMtr_no());//무의미함
            cv.setProd_no(prod_no);
            try {
                iCartService.insertCart(cv);
            } catch (RemoteException e) {
                e.printStackTrace();
            }

            //combi삭제//
            try {
                int i = iCartService.deleteCombi();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            //combi삭제//


            //장바구니 -> check가 0인것들만(장바구니 체크여부가 0인것들)가져옴
            List<CartJoinProdPicVO> prodpiclist = new ArrayList<>();
            try {
                prodpiclist = iCartService.selectCartJoinProdPic(LoginSession.memberSession.getMEM_ID());
            } catch (RemoteException e) {
                e.printStackTrace();
            }

            ArrayList<Integer> checkcartno = new ArrayList<>();  //체크된 번호 리스트

            //사진 불러오기
            for (CartJoinProdPicVO vo : prodpiclist) {
                System.out.println("------------------" + prodpiclist.size());
                System.out.println("-----------------" + vo.getProd_no());
                //HBox hbox = new HBox();
                VBox vbox = new VBox();
                VBox labelvbox = new VBox();
                String prodname = null;
                int prodno = vo.getProd_no();

                try {
                    ProdVO pvo;
                    pvo = iCartService.selectProdName(prodno);
                    prodname = pvo.getProd_name();

                } catch (RemoteException e) {
                    e.printStackTrace();
                }

                CheckBox ch = new CheckBox();
                Label l_prod = new Label("상품 이름 : " + prodname);
                Label l_menuinfo = new Label("상세 내용 : " + vo.getCart_cont());
                Label l_price = new Label("상품 가격 : " + Integer.toString(vo.getCart_price()));
                labelvbox.getChildren().addAll(ch, l_prod, l_menuinfo, l_price);
/*          l_prod.setFont(new Font("Arial", 30));
            l_prod.setRotate(270);
            l_prod.setTranslateY(50);

            l_menuinfo.setFont(new Font("Arial", 30));
            l_menuinfo.setRotate(270);
            l_menuinfo.setTranslateY(50);

            l_price.setFont(new Font("Arial", 30));
            l_price.setRotate(270);
            l_price.setTranslateY(50);
*/

                ImageView imageView = new ImageView();
                Image image = new Image(vo.getProd_pic_adr());
                imageView.setImage(image);

                //hbox.getChildren().addAll(imageView,labelvbox);
                //hbox.getChildren().addAll(imageView,l_prod);
                vbox.getChildren().addAll(imageView, labelvbox);

                ch.setOnAction(e -> {
                    if (ch.isSelected() == true) {
                        checkcartno.add(vo.getCart_no()); //체크되었을때 값 리스트에 넣기
                    } else if (ch.isSelected() == false) {
                        checkcartno.remove((Integer) vo.getCart_no());
                    }

                });

                man.getChildren().add(vbox);
            }


            //장바구니삭제버튼 누르기//
            remove.setOnAction(e -> {
                for (int i = 0; i < checkcartno.size(); i++) {
                    try {
                        iCartService.deleteCart(checkcartno.get(i));
                    } catch (RemoteException e1) {
                        e1.printStackTrace();
                    }
                }

                try {
                    Parent root = FXMLLoader.load(getClass().getResource("CartLoader.fxml"));
                    LoginSession.mainViewController.bp.setCenter(root);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            });


            //구매 버튼 누르기//
            send.setOnAction(e -> {
                System.out.println(checkcartno.size());
                System.out.println("-------------------");


                Stage stage = (Stage) send.getScene().getWindow();
                try {
                    Parent parent = FXMLLoader.load(getClass().getResource("BR.fxml"));
                    LoginSession.mainViewController.bp.setCenter(parent);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }


                //checkprodno가 cart_no와 일치하는 cart테이블의 cart_chk컬럼을 1로 바꾼다
                for (int i = 0; i < checkcartno.size(); i++) {
                    try {
                        iCartService.updateCartChk(checkcartno.get(i));
                        System.out.println(checkcartno.get(i) + "번 cart_chk를 1로 바꾸었습니다");
                    } catch (RemoteException e1) {
                        e1.printStackTrace();
                    }
                }

                //장바구니 -> check가 1이고 buy_chk가 0인것만 가져옴
                List<CartJoinProdPicVO> orderlist = new ArrayList<>();
                try {
                    orderlist = iCartService.selectOrder(LoginSession.memberSession.getMEM_ID());
                } catch (RemoteException e2) {
                    e2.printStackTrace();
                }




                //prod_name을 합쳐서 oder_cont라는 변수에 넣어줌.
                //CartPrice를 합쳐서 oder_price라는 변수에 넣어줌.
                //cart_no를 String으로 여러개의 cart_no를 받는다 ex) 1,2,3,...
                String o_order_cont = " ";
                int o_cart_price = 0;
                String cart_no_string = " ";
                for (int i = 0; i < orderlist.size(); i++) {

                    try {
                        ProdVO pvo;
                        pvo = iCartService.selectProdName(orderlist.get(i).getProd_no());
                        o_order_cont += pvo.getProd_name() + ",";

                    } catch (RemoteException e2) {
                        e2.printStackTrace();
                    }
                    //todo 맨뒤 ,지우기

                    o_cart_price += orderlist.get(i).getCart_price();
                    cart_no_string += orderlist.get(i).getCart_no() + ",";

                    //todo 맨뒤 ,지우기
                }

                //order_qty를 0개로 설정한다
                //order_coupon의 경우 결제창에서 해주어야하기 때문에 우선 0으로 설정해줌
                //order_st의 경우 주문중이기에 0으로 설정해줌
                //prod_no의 경우 0으로 설정
                //cart_no<- 0


                // CART_NO_STRING String으로 수정하고, cart_no 제약조건 삭제하기
                //mem_id추가하여 member_table과 제약조건 연결하기
                //todo session id로 바꾸어야함
                String o_mem_id = LoginSession.memberSession.getMEM_ID();

                //이모든 변수들을 order_table에 insert시켜주는 메서드 입력력
                OrderVO ovo = new OrderVO();
                ovo.setCart_no(0);
                ovo.setOrder_qty(0);
                ovo.setOrder_price(o_cart_price);
                ovo.setOrder_coupon(0);
                ovo.setOrder_st("주문중");
                ovo.setProd_no(0);
                ovo.setOrder_cont(o_order_cont);
                ovo.setCart_no_string(cart_no_string);
                ovo.setMem_id(o_mem_id);

                try {
                    iCartService.insertOrder(ovo);
                } catch (RemoteException remoteException) {
                    remoteException.printStackTrace();
                }


                //checkprodno가 cart_no와 일치하는 cart테이블의 cart_chk_buy컬럼을 1로 바꾼다
                for (int i = 0; i < checkcartno.size(); i++) {
                    try {
                        iCartService.updateCartCHkBuy(checkcartno.get(i));
                        System.out.println(checkcartno.get(i) + "번 cart_chk_buy를 1로 바꾸었습니다");
                    } catch (RemoteException e1) {
                        e1.printStackTrace();
                    }
                }




            });
        }//-------------------------------------------------------------------------추가







    }

}
