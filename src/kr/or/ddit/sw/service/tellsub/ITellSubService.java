package kr.or.ddit.sw.service.tellsub;

import javafx.scene.control.TreeItem;
import kr.or.ddit.sw.vo.tellsub.TellSubMemVO;
import kr.or.ddit.sw.vo.tellsub.TellSubVO;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface ITellSubService extends Remote {
    
    //텔서브웨이 작성
    public Object insertTell(TellSubVO tv) throws RemoteException;
    //텔서브웨이 삭제
    public int deleteTell(TellSubVO tv) throws  RemoteException;

    //텔서브웨이 수정
    public int updateTell(TellSubVO tv) throws RemoteException;
    //내가 쓴 텔서브웨이 전체 조회
    public List<TellSubVO> selectAllTell(String get_tellsub_no) throws RemoteException;
    //텔서브웨이 상세조회
    public List<TellSubVO> choisselectTell(TellSubVO Tellsub_no) throws RemoteException;

}
