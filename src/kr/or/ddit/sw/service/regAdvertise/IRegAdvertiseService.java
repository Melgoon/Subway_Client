package kr.or.ddit.sw.service.regAdvertise;

import kr.or.ddit.sw.vo.advertise.AdvertiseVO;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IRegAdvertiseService extends Remote {
    public int insertAdvertise(AdvertiseVO vo) throws RemoteException;

}
