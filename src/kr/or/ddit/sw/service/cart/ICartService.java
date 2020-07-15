package kr.or.ddit.sw.service.cart;

import kr.or.ddit.sw.vo.cart.CartJoinProdPicVO;
import kr.or.ddit.sw.vo.cart.CartVO;
import kr.or.ddit.sw.vo.cart.CombiMtrJoinVO;
import kr.or.ddit.sw.vo.ordertable.OrderVO;
import kr.or.ddit.sw.vo.prod.CombiVO;
import kr.or.ddit.sw.vo.prod.ProdPicVO;
import kr.or.ddit.sw.vo.prod.ProdVO;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface ICartService extends Remote {
    public ProdPicVO selectProdPic(int prod_no) throws RemoteException;
    public int insertCart(CartVO vo) throws RemoteException;
    List<CombiMtrJoinVO> selectCombiMtrJoin() throws RemoteException;
    public List<CartVO> selectCart() throws RemoteException;
    public int deleteCombi() throws RemoteException;
    public List<CartJoinProdPicVO> selectCartJoinProdPic(String mem_id) throws RemoteException;
    public ProdVO selectProdName(int prod_no) throws RemoteException;
    public int updateCartChk(int cart_no) throws RemoteException;
    public List<CartJoinProdPicVO> selectOrder(String mem_id) throws RemoteException;
    public int insertOrder(OrderVO vo) throws RemoteException;
    public int deleteCart(int cart_no) throws RemoteException;
    public int updateCartCHkBuy(int cart_no) throws RemoteException;
}
