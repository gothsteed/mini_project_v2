package controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import controller.hss.HssComanyCtrl;
import controller.hss.HssSearchComCtrl;
import controller.hss.HssSearchEmpCtrl;
import controller.jzee.Zee_CompanyController;
import controller.jzee.Zee_EmployeeController;
import controller.ksj.CompanyController_ksj;
import controller.leeJungYeon.LeeJungYeonController;
import domain.CompanyDto;
import domain.CompanyLoginDto;
import model.CompanyDao;
import model.CompanyDaoImple;
import myUtil.MyUtil;



public class CompanyController {
	
	private CompanyDao companyDao = new CompanyDaoImple();
	private LeeJungYeonController leeJungYeonController = new LeeJungYeonController();
	private HssComanyCtrl hssComanyCtrl = new HssComanyCtrl();
	private HssSearchComCtrl hssSearchComCtrl = new HssSearchComCtrl();
	private HssSearchEmpCtrl hssSearchEmpCtrl = new HssSearchEmpCtrl();
	private CompanyController_ksj companyController_ksj = new CompanyController_ksj();
	
	private Zee_CompanyController zee_CompanyController = new Zee_CompanyController();
	private Zee_EmployeeController zee_EmployeeController = new Zee_EmployeeController();
	
	private void companyMenu(Scanner sc, CompanyLoginDto loggedInComp) {
		

		
		
		
		CompanyLoginDto member = loggedInComp;

		String s_Choice = "";

		while(loggedInComp != null) {
			
	
				
			System.out.println("-".repeat(70));
			System.out.println("============================== ◆ 주식회사 "+ member.getComp_name() +" 님 전용메뉴 ◆ ==============================");
			System.out.println("1. 회사정보 보기   2. 회사정보 수정   3. 모든구직자 조회   4. 구직자 검색   5. 채용공고입력하기\n"
					+ "6. 우리회사 채용공고 조회    7.로그아웃   8. 회사 탈퇴하기");
			System.out.println("-".repeat(70));
			System.out.println("▷메뉴번호 선택 : ");
			s_Choice = sc.nextLine();
			
			switch (s_Choice) {
				case "1": // 1. 회사정보 보기
					zee_CompanyController.showmyCompInfo(loggedInComp.getComp_seq());

					break;
					
				case "2": // 2. 회사정보 수정
					
					companyController_ksj.update_comp_info(sc, loggedInComp.getComp_seq());
					break;
					
				case "3": // 3.  모든구직자 조회
					zee_CompanyController.showAllEmployee();
					
					break;
					
				case "4": // 4. 구직자 검색
					
					employeeSearch(sc, member);
					
					break;
					
				case "5": // 5. 채용공고입력하기
					
					hssComanyCtrl.writeRecruit(sc, member);
					

					break;
					
				case "6": // 6. 우리회사 채용공고 조회
					leeJungYeonController.viewCompanyRecruits(sc, member);
					break;
					

//				case "7": // 7. 채용공고지원자 조회
//					companyController_ksj.searchApplicantsByJobPosting(sc, member.getComp_seq());
//					break;
//				
				case "7": // 7.로그아웃
					
					String affirm = "";
					do {
						
						System.out.println("정말 로그아웃 하시겠습니까?[y/n]");
						affirm = sc.nextLine();
						
						if("y".equalsIgnoreCase(affirm)) {
							loggedInComp = null;						
							System.out.println("로그아웃 성공!!");
							
						} else if("n".equalsIgnoreCase(affirm)) {
							System.out.println("로그아웃 취소");
							
						}
					}while(!("y".equalsIgnoreCase(affirm) || "n".equalsIgnoreCase(affirm)));
					
					break;
				case "8": // 8. 회사 탈퇴하기
					
					int n = companyController_ksj.drop_Member(sc, loggedInComp.getComp_seq());
					if (n == 1) {
						loggedInComp = null;
						break;
					}
					
					
				default:
					System.out.println("!!올바른 숫자를 입력하시오!!");
					break;
				}

		}
		
		
	}
	
	private void employeeSearch(Scanner sc, CompanyLoginDto member) {
		String s_menuNo = "";
		
		do {
			////////////////////
			System.out.println("\n ------------- 구직자 검색메뉴 -------------- \n"
							+ "1. 성별검색    2.연령대검색  3.회사메뉴로 돌아가기 \n");
			System.out.print("▷ 메뉴번호 선택 : ");
			s_menuNo = sc.nextLine();
			
			switch (s_menuNo) {
				case "1":
					hssSearchEmpCtrl.genderSearch(sc);
					break;
				case "2": // 2.자본금검색
					leeJungYeonController.searchEmployeeByAgeGroup(sc);
					break;
					
					
				case "3": 
					break;
					
				default:
					System.out.println(">> 메뉴에 없는 번호입니다. <<");
					break;
			} // end of switch (s_menuNo)-------------
			////////////////////////////////////////////////////////////
		} while(!("3".equals(s_menuNo)));
		
	}


	public void register(Scanner sc) {
		
		System.out.println("\n >>> ---- 기업 회원가입 ---- <<<");
		
		
		CompanyLoginDto memberLogin = new CompanyLoginDto();
		CompanyDto member = new CompanyDto();
		

		
		String comp_id = "";
		while(member.getComp_id() == null) {
			System.out.print("사용하실 아이디 입력 : ");
			comp_id = sc.nextLine();
			
			if(comp_id.trim().equals("")) {
				
				System.out.println("공백을 사용할  수 없습니다");
			}
			else {
				member.setComp_id(comp_id);
			}
		}
		memberLogin.setComp_id(comp_id);
		

		String comp_password = "";
		while(memberLogin.getComp_password() == null) {
			System.out.print("사용하실 비밀번호 입력 : ");
			comp_password = sc.nextLine();
			
			if(MyUtil.isCheckPasswd(comp_password)) {
				
				memberLogin.setComp_password(comp_password);
			}else {
				System.out.println("비밀번호 규칙은 비밀번호의 길이는 8글자 이상 15글자 영문대문자, 영문소문자, 숫자, 특수기호가 혼합되어야만 합니다.");
			}
			
		}

		System.out.print("3. 회사명 : ");
		String comp_name = sc.nextLine();
		member.setComp_name(comp_name);
		memberLogin.setComp_name(comp_name);

		String comp_email = "";
		while(member.getComp_email() == null) {
			System.out.print("사용하실 이메일 주소 입력: ");
			comp_email = sc.nextLine();
			
			if(MyUtil.isTrueEmail(comp_email)) {
				
				member.setComp_email(comp_email);
			}else {
				System.out.println("!!올바른 이메일 형식을 입력하시오!!");
			}
			
		}
		
		String comp_scale = "";
		while(true) {
			
			System.out.print("5. 기업규모(중,소,대) : ");
			comp_scale = sc.nextLine();
			if(comp_scale.equals("대") || comp_scale.equals("중") || comp_scale.equals("중")) {
				
				member.setComp_scale(comp_scale);
				break;
			}else {
				System.out.println("!!올바른 값을 입력하시오!!");
			}
		}

		
		String comp_est_date = "";
		while(member.getComp_est_date() == null) {
			
			System.out.print("6. 설립일 yyyy-mm-dd: ");
			comp_est_date = sc.nextLine();
			
			if(MyUtil.isTrueDate(comp_est_date)) {
				
				member.setComp_est_date(comp_est_date);
			}else {
				System.out.println("!!올바른 설립일을 입력하시오!!");
			}
		}

		
		System.out.print("7. 회사주소 : ");
		String comp_addr = sc.nextLine();
		member.setComp_addr(comp_addr);
		
		System.out.print("8. 대표자 : ");
		String comp_ceo = sc.nextLine();
		member.setComp_ceo(comp_ceo);
		
		
		
		int comp_emp_cnt = 0;
		while(true) {
			System.out.print("9. 사원수 : ");
			
			try {
				comp_emp_cnt = Integer.parseInt(sc.nextLine()) ;
				member.setComp_emp_cnt(comp_emp_cnt);
				
				break;
			}catch (NumberFormatException e) {
				System.out.println("!!숫자만 입력하시오 !!");
			}
		
			
		}
		
		
		int comp_capital = 0;
		while(true) {
			System.out.print("9. 자본금 : ");
			
			try {
				comp_capital = Integer.parseInt(sc.nextLine()) ;
				member.setComp_capital(comp_capital);
				
				break;
			}catch (NumberFormatException e) {
				System.out.println("!!숫자만 입력하시오 !!");
			}
		
			
		}
		
		
		int comp_sales = 0;
		while(true) {
			System.out.print("9. 매출 : ");
			
			try {
				comp_sales = Integer.parseInt(sc.nextLine()) ;
				member.setComp_sales(comp_sales);
				
				break;
			}catch (NumberFormatException e) {
				System.out.println("!!숫자만 입력하시오 !!");
			}
		
			
		}

		
		System.out.print("12. 사대보험여부 : ");
		String comp_insurance = sc.nextLine();
		member.setComp_insurance(comp_insurance);
		
		System.out.print("13. 산업 : ");
		String indust_name = sc.nextLine();
		member.setIndust_name(indust_name);
		
		

		// DTO 에 보낸 값을 실제로 DB 에 보내줘야한다. DAO(Database Access Object) 데이터베이스에 접근해서 어떠한
		// 일처리를 하는(DDL, DML, procedure 등등의 일처리를 해주는) 객체
		// 전개도를 보면 된다.

		int n = companyDao.memberRegister(member, memberLogin);

		// memberRegister 에 값을 성공적으로 받았을 경우 1 못받았을 경우 0(else)
		if (n == 1) {
			System.out.println("\n >>> 회원가입을 축하드립니다. <<<");
		} else {
			System.out.println(">>> 회원가입이 실패되었습니다. <<<");
		}
		
	}

	public void login(Scanner sc) {
		
		CompanyLoginDto comLoginMem = null;
		System.out.println("\n >>> --- 로그인 --- <<<");
		
		System.out.println("▷ 아이디 : ");
		String userid = sc.nextLine();
		
		System.out.println("▷ 비밀번호 : ");
		String passwd = sc.nextLine();
		
		// userid, passwd, email, ~~~ 등을 map에 담는다. 
		
		Map<String, String> paraMap = new HashMap<>(); //맵은 넣을때 제한이 없다
		paraMap.put("userid", userid); //맵에 넣는 것 key , value - userid가 pk므로 1개 행 나옴 - 없으면 null
		paraMap.put("passwd", passwd);
		
		
		comLoginMem = companyDao.login(paraMap); //MemberDAO_imple 실행
		
		if(comLoginMem != null) {
			this.companyMenu(sc, comLoginMem);
		} else {
			System.out.println("\n >>> [경고] 아이디나 비밀번호 오류입니다. 다시 입력해주세요.<<< ");
		}
		
		
	}
	
	
	

}
