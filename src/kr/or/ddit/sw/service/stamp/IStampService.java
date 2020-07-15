package kr.or.ddit.sw.service.stamp;

import kr.or.ddit.sw.vo.coupon.CouponHisVO;
import kr.or.ddit.sw.vo.coupon.CouponPicVO;
import kr.or.ddit.sw.vo.coupon.CouponVO;
import kr.or.ddit.sw.vo.member.MemberVO;
import kr.or.ddit.sw.vo.stamp.StampHisVO;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface IStampService extends Remote {
    public StampHisVO findStampNumbers(String mem_id) throws RemoteException;
    public List<CouponPicVO> selectCoupon(String mem_id) throws RemoteException;
    public int insertCoupon(CouponVO vo) throws RemoteException;
    public int insertCouponHis(CouponHisVO vo) throws RemoteException;
    public int updateStamp(StampHisVO vo) throws RemoteException;
    public List<MemberVO> findMemberId() throws RemoteException;
    public int insertCouponB(CouponVO vo) throws RemoteException;
    public int insertStamp(StampHisVO vo) throws RemoteException;

}
