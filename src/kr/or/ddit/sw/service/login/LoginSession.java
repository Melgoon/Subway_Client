package kr.or.ddit.sw.service.login;

import javafx.stage.Stage;
import kr.or.ddit.sw.view.mainadmin.MainAdminController;
import kr.or.ddit.sw.view.mainview.MainViewController;
import kr.or.ddit.sw.view.owner.OwnerController;
import kr.or.ddit.sw.view.registFoodMtr.ViewFoodMtrController;
import kr.or.ddit.sw.view.registProd.ViewProdController;
import kr.or.ddit.sw.vo.member.MemberVO;
import kr.or.ddit.sw.vo.owner.OwnerVO;

public class LoginSession {

  //사업자
  public static OwnerVO ownerSession;
  //개인사용자
  public static MemberVO memberSession;




  //TODO 로그아웃 세션 만들기

  //가장 많이 쓰는 메인화면을 static으로 선언해준다.
  public static MainViewController mainViewController;

  //사업자 메인화면을 static으로 선언해준다.
  public static MainAdminController mainAdminController;

  //uuid를 static으로 설정해주기위해서 이쪽에서 선언해준다.
  public static String uuid;

  //openDialog에서의 인사말을 저장해주는 메소드
  public static String introString;

  //ViewProdController 객체 공유를 위한 객체 생성
  public static ViewProdController viewProdController;

  //가격제공
  public static String price;

  public static ViewFoodMtrController viewFoodMtrController;
}

