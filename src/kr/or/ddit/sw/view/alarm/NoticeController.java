package kr.or.ddit.sw.view.alarm;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Callback;
import kr.or.ddit.sw.service.alarm.IAlarmService;
import kr.or.ddit.sw.service.login.LoginSession;
import kr.or.ddit.sw.service.notice.INoticeService;
import kr.or.ddit.sw.view.basic.BasicSlidePaneController;
import kr.or.ddit.sw.vo.notice.NoticeMemChkVO;
import kr.or.ddit.sw.vo.notice.NoticeVO;

import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import static kr.or.ddit.sw.view.basic.BasicSlidePaneController.con1;
import static kr.or.ddit.sw.view.basic.BasicSlidePaneController.countAlarm;

public class NoticeController implements Initializable {



    public TableColumn no;
    public TableColumn title;
    public TableColumn content;
    public TableColumn check;
    public Pagination pn;
    public TableView tv;
    public Label label;
    public Button closeBtn;
    public ImageView NoView;

    Registry reg;
    INoticeService regNotice;
    IAlarmService regAlarm;
    BasicSlidePaneController basicSlidePaneController;
    List<NoticeVO> noticeInfo;
    private NoticeVO nv;
    private NoticeMemChkVO chkvo;
    private int count;
    private int from, to, itemsForPage;
    private ObservableList<NoticeVO> allTableData, currentPageData,data;
    String user_id = LoginSession.memberSession.getMEM_ID();



    List<Integer> disListNo;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            reg= LocateRegistry.getRegistry("localhost",7774);
            regNotice = (INoticeService) reg.lookup("notice");
            regAlarm = (IAlarmService) reg.lookup("alarm");
        } catch (RemoteException | NotBoundException e) {
            e.printStackTrace();
        }

        Image noticeView = new Image("file:src/images/NoticeView.png");
        NoView.setImage(noticeView);


        no.setCellValueFactory(new PropertyValueFactory<>("NOTICE_NO"));
        title.setCellValueFactory(new PropertyValueFactory<>("NOTICE_NAME"));
        content.setCellValueFactory(new PropertyValueFactory<>("NOTICE_CONTENT"));
        check.setCellValueFactory(new PropertyValueFactory<>("NOTICE_CHK"));
        //

//        try {
//            disListNo = regAlarm.disSelectNO(user_id);
//            chkvo= new NoticeMemChkVO();
//            chkvo.setMEM_ID(user_id);
//
//            //regAlarm.insertChk(vo 넣어줘야함 for문);
//            for (int i = 0; i < disListNo.size(); i++) { //intValue()
//                chkvo.setNOTICE_NO(disListNo.get(i));
//               count += regAlarm.insertChk(chkvo);
//            }
//            System.out.println(count);
//
//        } catch (RemoteException e) {
//            e.printStackTrace();
//        }


        loadData();
/////////////////////////////////////////

        try {
            //TODO
            //select * from notice,notice_mem_chk where notice.notice_no
            // = notice_mem_chk.notice_no and notice_mem_chk.mem_check='1'; 수정
            //list = regNotice.selectAllNotice();
            noticeInfo= new ArrayList<>();
            noticeInfo = regAlarm.ReadNoSelect(user_id);
        } catch (RemoteException e) {
            e.printStackTrace();
        }

         data = FXCollections.observableArrayList();
        for (int i = 0; i < noticeInfo.size(); i++) {
            //System.out.println(list.get(i+1).getNOTICE_CHK());
            if (noticeInfo.get(i).getNOTICE_CHK()==null){
                continue;
            }
            if (noticeInfo.get(i).getNOTICE_CHK().equals("1")){
                System.out.println(noticeInfo.get(i).getNOTICE_CONTENT());
                data.add(noticeInfo.get(i));
            }
        }
      //tv.setItems(data);
        nv= new NoticeVO();
        ////////////////////////
        tv.setOnMouseClicked(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event) {
                if(event.getClickCount()>1) {

                    nv = (NoticeVO) tv.getSelectionModel().getSelectedItem();
            try {
                //TODO 수정
                //업데이트 할때 유저 아이디랑  게시판 번호
                chkvo = new NoticeMemChkVO();
                chkvo.setMEM_ID(user_id);
                chkvo.setNOTICE_NO(nv.getNOTICE_NO());

                //업데이트 문엔 VO 넣어주자.
                regAlarm.updateNo(chkvo);
                countAlarm-=1;
                con1.countAlarmView();
                loadData();

            } catch (RemoteException remoteException) {
                remoteException.printStackTrace();
                }
            }
            }

        });

//        tv.setOnMouseClicked(e->{
//            nv = (NoticeVO) tv.getSelectionModel().getSelectedItem();
//            try {
//                regAlarm.updateNo(nv.getNOTICE_NO());
//                countAlarm-=1;
//                con1.changebell();
//                loadData();
//
//            } catch (RemoteException remoteException) {
//                remoteException.printStackTrace();
//            }
//        });

        closeBtn.setOnAction(e->{
            //con1.changebell();
            Stage stage = (Stage) closeBtn.getScene().getWindow();
            stage.close();
        });

        }

    public void loadData(){
        try {
            //TODO 수정해야함
            //select * from notice,notice_mem_chk where notice.notice_no
            // = notice_mem_chk.notice_no and notice_mem_chk.mem_check='1';
            noticeInfo=null;
            noticeInfo = regAlarm.ReadNoSelect(user_id);

        } catch (RemoteException e) {
            e.printStackTrace();
        }

        data = FXCollections.observableArrayList();
        for (int i = 0; i < noticeInfo.size(); i++) {
            //System.out.println(list.get(i+1).getNOTICE_CHK());
            if (noticeInfo.get(i).getNOTICE_CHK()==null){
                continue;
            }
            if (noticeInfo.get(i).getNOTICE_CHK().equals("1")){
                System.out.println(noticeInfo.get(i).getNOTICE_CONTENT());
                data.add(noticeInfo.get(i));
            }
        }

        allTableData = FXCollections.observableArrayList(data);
         // tv.setItems(allTableData);
        itemsForPage = 13; // 한페이지에 보여줄 항목 수 설정
        int totalDataCnt = allTableData.size();
        int totalPageCnt = totalDataCnt % itemsForPage == 0 ?
                totalDataCnt / itemsForPage : totalDataCnt / itemsForPage + 1;

        pn.setPageCount(totalPageCnt); //페이지 밑의 숫자

        //방법1 Callback타입의 익명객체 생성
        pn.setPageFactory(new Callback<Integer, Node>() {

            @Override //fx에서 Node는 모든 컨트롤러가 다 포함된다.
            public Node call(Integer pageIndex) {
                from = pageIndex * itemsForPage;
                to = from + itemsForPage - 1;
                tv.setItems(getTableViewData(from, to));

                return tv;
            }

            //전체 데이터중에 해당하는 데이터 대려오는것
            private ObservableList<NoticeVO> getTableViewData(int from, int to) {
                currentPageData = FXCollections.observableArrayList();
                int totaSize = allTableData.size();

                for (int i = from; i <= to && i < totaSize; i++) {
                    currentPageData.add(allTableData.get(i));
                }
                return currentPageData;
            }
        });
    }
}

