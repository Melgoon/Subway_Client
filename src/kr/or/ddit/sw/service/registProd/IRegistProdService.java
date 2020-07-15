package kr.or.ddit.sw.service.registProd;

import kr.or.ddit.sw.vo.foodmtr.FoodMtrVO;
import kr.or.ddit.sw.vo.prod.CombiVO;
import kr.or.ddit.sw.vo.prod.KategoriVO;
import kr.or.ddit.sw.vo.prod.ProdVO;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface IRegistProdService extends Remote {

    public List<ProdVO> selectProd() throws RemoteException;

    public List<KategoriVO> selectKategori() throws RemoteException;

    public List<FoodMtrVO> selectMtr(Integer mtr_div) throws RemoteException;

    public int insertProd(ProdVO vo) throws  RemoteException;

    public int insertCombi(CombiVO vo) throws  RemoteException;

    public int deleteProd(ProdVO vo) throws RemoteException;

    public int deleteCombi(Integer prod_no) throws RemoteException;

    public List<CombiVO> selectCombi() throws RemoteException;



}
