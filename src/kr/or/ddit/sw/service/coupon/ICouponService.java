package kr.or.ddit.sw.service.coupon;

import kr.or.ddit.sw.vo.coupon.CouponPicVO;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface ICouponService extends Remote {

    public List<CouponPicVO> selectCoupon(String mem_id) throws RemoteException;
}
