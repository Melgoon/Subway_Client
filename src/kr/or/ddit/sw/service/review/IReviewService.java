package kr.or.ddit.sw.service.review;

import kr.or.ddit.sw.vo.review.ReviewVO;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface IReviewService extends Remote {

    // 공지사항 작성
    public int insertReview(ReviewVO rv) throws RemoteException;
    // 공지사항 전체조회
    public List<ReviewVO> selectReviewAll() throws RemoteException;
    // 공지사항 삭제
    public int deleteReview(ReviewVO rv) throws RemoteException;
    // 공지사항 수정
    public int updateReview(ReviewVO rv) throws RemoteException;
}
