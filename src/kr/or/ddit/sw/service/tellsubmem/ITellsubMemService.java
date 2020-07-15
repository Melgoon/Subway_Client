package kr.or.ddit.sw.service.tellsubmem;

import kr.or.ddit.sw.vo.tellsub.TellSubMemVO;
import kr.or.ddit.sw.vo.tellsub.TellSubVO;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface ITellsubMemService extends Remote {

    public int insertTellsubMem(String user_id) throws RemoteException;
    public List<TellSubVO> selectUnionTell(String user_id) throws RemoteException;
    public int updateTellsubMem(TellSubMemVO vo) throws RemoteException;
    public TellSubVO selectDetail(int tellSub_no) throws RemoteException;
    public int selectTellSubNO(String user_id) throws RemoteException;
}
