package controller;

import java.util.HashMap;
import java.util.Map;

import auth.AuthenticationManager;
import dbConnect.MyDBConnection;
import myUtil.MyUtil;
import reader.Reader;
import service.CompanyService;
import service.EmployeeService;
import service.resultData.PasswordChangeResult;



public class MainController {

	private CompanyService companyService;
	private EmployeeService employeeService;
	private EmployeeController employeeController;
	private CompanyController companyController;
	private Reader reader;
	private AuthenticationManager authenticationManager;


	public MainController(CompanyService companyService, EmployeeService employeeService
			, EmployeeController employeeController, CompanyController companyController, Reader reader, AuthenticationManager authenticationManager) {
		this.companyService = companyService;
		this.employeeService = employeeService;
		this.employeeController = employeeController;
		this.companyController = companyController;
		this.reader = reader;
		this.authenticationManager = authenticationManager;
	}

	public void mainMenu() {


		String menuNo = "";

		boolean isRunning = true;
		while(isRunning) {

			System.out.println(">>-----구인사이트 시작메뉴-----<<");
			System.out.println("1. 구직자 회원가입      2. 구인회사 회원가입      3.구직자 로그인      4. 구인회사 로그인   5. 구직자 아이디, 비밀번호 찾기   6.구인회사 아이디, 비밀번호 찾기     7.프로그램종료");
			System.out.println("-----------------------------------------------------------------------------------------");

			menuNo = reader.getInputWithPrompt("메뉴번호 선택 : ");

			switch (menuNo) {
				case "1":
					
					employeeRegister();

					break;

				case "2":

					companyRegister();
					break;


				case "3":
//					employeeController.login(sc);
					
					employeeLogin();
					break;


				case "4":

//					companyController.login(sc);
					companyLogin();
					break;


				case "5"://구직자
//					HssEmployeeCtrl.empIdPasswdSearch(sc);
					empIdPasswdSearch();
					break;

				case "6": //구인회사 아이디/비번찾기 메뉴
//					HssComanyCtrl.compIdPasswdSearch(sc);
					compIdPasswdSearch();
					break;

				case "7"://program shutdown
					isRunning = false;
					MyDBConnection.closeConnection();
					break;



				default:
					System.out.println("-----------!!올바른 메뉴번호를 입력해 주세요!!!-----------");

					break;
			}


		}

		System.out.println("-----프로그램 종료-----");


	}


	//todo: 중복 메소드 추상화!!!
	private void employeeRegister() {

		System.out.println("---------------구직자 회원가입-----------------");

		Map<String, String> userInput = new HashMap<>();


		String emp_id = reader.getValidatedInput("사용하실 아이디 입력 : ", string -> !string.trim().isBlank(),   "공백을 사용할  수 없습니다");
		userInput.put("emp_id", emp_id);

		String emp_password = reader.getValidatedInput("사용하실 비밀번호 입력 : ", MyUtil::isCheckPasswd
				, "비밀번호 규칙은 비밀번호의 길이는 8글자 이상 15글자 영문대문자, 영문소문자, 숫자, 특수기호가 혼합되어야만 합니다.");
		userInput.put("emp_password", emp_password);



		String emp_name = reader.getValidatedInput("이름 입력: ", string -> string.trim().isBlank()
				,"이름을 입력하시오" );
		userInput.put("emp_name", emp_name);

		String emp_email = reader.getValidatedInput("사용하실 이메일 주소 입력: ", string -> MyUtil.isTrueEmail(string)
				,"!!올바른 이메일 형식을 입력하시오!!" );
		userInput.put("emp_email", emp_email);


		String emp_addr = reader.getValidatedInput("거주지 입력: ", string -> string.trim().isBlank()
				, "거주지를 입력하시오");
		userInput.put("emp_addr", emp_addr);


		String emp_jubun = reader.getValidatedInput("주민번호 7자리 입력 : ", string->string.trim().isBlank()
				, "올바른 주민등록번호 7자리를 입력하시오!!");
		userInput.put("emp_jubun", emp_jubun);



		String emp_tel = reader.getValidatedInput("전화번호 입력 : ", MyUtil::isTrueTel
				, "올바른 전화번호 형식을 입력하시오 010-1111-1111");
		userInput.put("emp_tel", emp_tel);


		boolean isConfirm = false;

		while(!isConfirm) {
			String choice = reader.getInputWithPrompt("이 정보로 회원가입 하시겠습니까? Y/N : ");

			if(choice.equalsIgnoreCase("y")) {
				isConfirm = true;
				int isSuccess = employeeService.register(userInput);

				if(isSuccess == 1) {

					System.out.println("-----회원가입 성공!!-----");
				}else {

					System.out.println("-----회원가압 실패!!-----");
				}


			}else if(choice.equalsIgnoreCase("n")) {
				isConfirm = true;
				System.out.println("!!회원가입 취소!!");
			}else {

				System.out.println("!!!똑바로 입력하시오!!!");
			}

		};

	}

	private void companyRegister() {
		System.out.println("\n >>> ---- 기업 회원가입 ---- <<<");

		Map<String, String> userInput = new HashMap<>();



		String comp_id = reader.getValidatedInput("사용하실 아이디 입력 : ",
				string -> string.trim().isEmpty(),
				"공백을 사용할  수 없습니다");
		userInput.put("comp_id", comp_id);

		String comp_password = reader.getValidatedInput("사용하실 비밀번호 입력 : ",
				string -> MyUtil.isCheckPasswd(string),
				"비밀번호 규칙은 비밀번호의 길이는 8글자 이상 15글자 영문대문자, 영문소문자, 숫자, 특수기호가 혼합되어야만 합니다.");
		userInput.put("comp_password", comp_password);



		String comp_name = reader.getValidatedInput(" 회사명 입력 : ",
				string -> string.trim().isEmpty(),
				"공백을 사용할  수 없습니다");
		userInput.put("comp_name", comp_name);


		String comp_email = reader.getValidatedInput("사용하실 이메일 주소 입력: ",
				string -> MyUtil.isTrueEmail(string),
				"!!올바른 이메일 형식을 입력하시오!!");
		userInput.put("comp_email", comp_email);



		String comp_scale = reader.getValidatedInput("5. 기업규모(중,소,대) : ",
				string -> string.equals("대") || string.equals("중") || string.equals("소"),
				"!!올바른 값을 입력하시오!!");
		userInput.put("comp_scale", comp_scale);



		String comp_est_date = reader.getValidatedInput("6. 설립일 yyyy-mm-dd: ",
				string -> MyUtil.isTrueDate(string),
						"!!올바른 설립일을 입력하시오!!");
		userInput.put("comp_est_date", comp_est_date);


		String comp_addr = reader.getValidatedInput("7. 회사주소 : ",
				string -> string.trim().isEmpty(),
				"공백을 사용할  수 없습니다");
		userInput.put("comp_addr", comp_addr);


		String comp_ceo = reader.getValidatedInput("8. 대표자 : ",
				string -> string.trim().isEmpty(),
				"공백을 사용할  수 없습니다");
		userInput.put("comp_ceo", comp_ceo);


		String comp_emp_cnt = reader.getValidatedInput("9. 사원수 : ",
				string -> {
					try {
						Integer.parseInt(string);
					}catch (NumberFormatException e) {
						return false;
					}

					return true;
				}, "!!숫자만 입력하시오 !!");

		userInput.put("comp_emp_cnt", String.valueOf(comp_emp_cnt));



		String comp_capital = reader.getValidatedInput("10. 자본금 : ",
				string -> {
					try {
						Integer.parseInt(string);
					}catch (NumberFormatException e) {
						return false;
					}

					return true;
				}, "!!숫자만 입력하시오 !!");

		userInput.put("comp_capital", String.valueOf(comp_capital));



		String comp_sales = reader.getValidatedInput("10. 매출 : ",
				string -> {
					try {
						Integer.parseInt(string);
					}catch (NumberFormatException e) {
						return false;
					}

					return true;
				}, "!!숫자만 입력하시오 !!");
		userInput.put("comp_sales", String.valueOf(comp_sales));





		String comp_insurance = reader.getValidatedInput("12. 사대보험여부 : ",
				string -> string.trim().isEmpty(),
				"공백을 사용할  수 없습니다");

		userInput.put("comp_insurance", comp_insurance);

		String indust_name = reader.getValidatedInput("13. 산업 : ",
				string -> string.trim().isEmpty(),
				"공백을 사용할  수 없습니다");
		userInput.put("indust_name", indust_name);



		// DTO 에 보낸 값을 실제로 DB 에 보내줘야한다. DAO(Database Access Object) 데이터베이스에 접근해서 어떠한
		// 일처리를 하는(DDL, DML, procedure 등등의 일처리를 해주는) 객체
		// 전개도를 보면 된다.

		int n = companyService.register(userInput);

		// memberRegister 에 값을 성공적으로 받았을 경우 1 못받았을 경우 0(else)
		if (n == 1) {
			System.out.println("\n >>> 회원가입을 축하드립니다. <<<");
		} else {
			System.out.println(">>> 회원가입이 실패되었습니다. <<<");
		}

	}

	private Map<String, String> inputLoginInfo() {

		String userid = reader.getInputWithPrompt("▷ 아이디 : ");

		String passwd = reader.getInputWithPrompt("▷ 비밀번호 : ");

		// userid, passwd, email, ~~~ 등을 map에 담는다.

		Map<String, String> paraMap = new HashMap<>(); //맵은 넣을때 제한이 없다
		paraMap.put("userid", userid); //맵에 넣는 것 key , value - userid가 pk므로 1개 행 나옴 - 없으면 null
		paraMap.put("passwd", passwd);

		return paraMap;

	}


	//todo : LoginDto 의존성 어떻게 들어내지??
	private void companyLogin() {
		System.out.println("\n >>> --- 로그인 --- <<<");

		String userid = reader.getInputWithPrompt("아이디 ");
		String passwd = reader.getInputWithPrompt("비밀번호: ");

		boolean isLoggedIn = authenticationManager.login("company".toLowerCase(), userid, passwd);

		if(isLoggedIn) {
			companyController.companyMenu();
		} else {
			System.out.println("\n >>> [경고] 아이디나 비밀번호 오류입니다. 다시 입력해주세요.<<< ");
		}



	}

	private void employeeLogin() {

		System.out.println("\n >>> --- 로그인 --- <<<");


		String userid = reader.getInputWithPrompt("아이디 ");
		String passwd = reader.getInputWithPrompt("비밀번호: ");

		boolean isLoggedIn = authenticationManager.login("employee".toLowerCase(), userid, passwd);

		if(isLoggedIn) {
			employeeController.employeeMenu();
		} else {
			System.out.println("\n >>> [경고] 아이디나 비밀번호 오류입니다. 다시 입력해주세요.<<< ");
		}



	}

	private void empIdPasswdSearch() {
		String menuNo = "";
		do {
			System.out.println("----------------------------------------------");
			System.out.println("   1. 아이디 찾기    2. 비밀번호 찾기    3. 나가기");
			System.out.println("----------------------------------------------");

			menuNo = reader.getInputWithPrompt("메뉴번호 선택 : ");
			switch(menuNo) {
				case "1":
					findEmpId();
					break;
				case "2":
					findEmpPassword();
					break;
				case "3":
					break;
				default:
					System.out.println("-----------!!올바른 메뉴번호를 입력해 주세요!!!-----------");
					break;
			}
		}while(!"3".equals(menuNo));
	}


	private void findEmpId() {


		String emp_name = reader.getInputWithPrompt("이름을 입력하세요. : ");
		String emp_email = reader.getInputWithPrompt("이메일을 입력하세요. : ");


		String id = employeeService.findId(emp_name, emp_email);
		if(id == null) {
			System.out.println("\n >>> 가입하지 않은 회원입니다. <<< ");
			return;
		}
		System.out.println("고객님의 아이디는 " + id + "입니다.");

	}



	private void findEmpPassword() {

		String emp_name = reader.getInputWithPrompt("이름을 입력하세요. : ");
		String emp_id =  reader.getInputWithPrompt("아이디를 입력하세요. : ");

		PasswordChangeResult result = employeeService.changeEmpPassword(reader, emp_id);

		switch (result){
			case PASSWORD_RECENTLY_USED:
				System.out.println("\n !!! 최근에 사용한 비밀번호입니다 !!!");
			case FAILURE:
				System.out.println("\n !!! 서버오류로 변경에 실패하였습니다 !!!");
			case SUCCESS:
				System.out.println("\n >>> 변경 성공 <<< ");
			case MEMBER_NOT_FOUND:
				System.out.println("\n !!! 존재하지 않는 아이디입니다 !!!");
		}

	}



	//todo: emp와 company메뉴가 중복된다. 추상화?
	private void compIdPasswdSearch() {

		String menuNo = "";

		do {

			System.out.println("----------------------------------------------");
			System.out.println("   1. 아이디 찾기    2. 비밀번호 찾기    3. 나가기");
			System.out.println("----------------------------------------------");

			menuNo = reader.getInputWithPrompt("메뉴번호 선택 : ");
			switch(menuNo) {
				case "1":
					findCompId();
					break;
				case "2":
					findCompPassword();
					break;
				case "3":
					break;
				default:
					System.out.println("-----------!!올바른 메뉴번호를 입력해 주세요!!!-----------");
					break;
			}
		}while(!"3".equals(menuNo));
	}


	private void findCompId() {

		String comp_name = reader.getInputWithPrompt("회사 이름을 입력하세요. : ");
		String comp_email = reader.getInputWithPrompt("회사 이메일을 입력하세요. : ");


		String id = companyService.findId(comp_name, comp_email);
		if(id == null) {
			System.out.println("\n >>> 가입하지 않은 회원입니다. <<< ");
			return;
		}
		System.out.println("고객님의 아이디는 " + id + "입니다.");

	}

	private void findCompPassword() {
		String comp_name = reader.getInputWithPrompt("이름을 입력하세요. : ");
		String comp_id =  reader.getInputWithPrompt("아이디를 입력하세요. : ");

		PasswordChangeResult result = companyService.changeCompPassword(reader, comp_id);


		switch (result){
			case PASSWORD_RECENTLY_USED:
				System.out.println("\n !!! 최근에 사용한 비밀번호입니다 !!!");
			case FAILURE:
				System.out.println("\n !!! 서버오류로 변경에 실패하였습니다 !!!");
			case SUCCESS:
				System.out.println("\n >>> 변경 성공 <<< ");
			case MEMBER_NOT_FOUND:
				System.out.println("\n !!! 존재하지 않는 아이디입니다 !!!");
		}


	}




}