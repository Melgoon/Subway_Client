package kr.or.ddit.sw.service.notice;

import kr.or.ddit.sw.vo.notice.NoticeVO;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface INoticeService extends Remote {

    // 공지사항 작성
    public int insertNotice(NoticeVO nv) throws RemoteException;
    // 공지사항 전체조회
    public List<NoticeVO> selectNoticeAll() throws RemoteException;
    // 공지사항 삭제
    public int deleteNotice(NoticeVO nv) throws RemoteException;
    // 공지사항 수정
    public int updateNotice(NoticeVO nv) throws RemoteException;
}
