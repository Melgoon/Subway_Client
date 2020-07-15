package kr.or.ddit.sw.service.alarm;

import kr.or.ddit.sw.vo.notice.NoticeMemChkVO;
import kr.or.ddit.sw.vo.notice.NoticeVO;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface IAlarmService extends Remote {

    /**
     * 일반게시판 맴체크랑 , 맴버랑 조인해서 현재 맴체크전부 풀력

     */
    //읽어야 할 게시판 번호 추출
    public List<NoticeVO> ReadNoSelect(String cv) throws RemoteException;
    // 읽어야할 게시판 수
    public int noticeNumber(String cv) throws RemoteException ;

    public int updateNo(NoticeMemChkVO vo) throws RemoteException;

    // 추가 할것들 회원별 읽어야할 게시판 번호 추출
    public List<Integer> disSelectNO(String user_id) throws RemoteException;

    public int deleteChk(int no) throws RemoteException;
    public int insertChk(NoticeMemChkVO vo) throws RemoteException;

    public String newUserChk(String user_id) throws RemoteException;

    public List<Integer> noticeNo() throws RemoteException;

    public List<Integer> noticeChkNo(String user_id) throws RemoteException;
}
