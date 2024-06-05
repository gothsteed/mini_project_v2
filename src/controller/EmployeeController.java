package controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import auth.AuthenticationManager;
import domain.Career;
import domain.Education;
import dto.*;
import myUtil.MyUtil;
import reader.Reader;
import service.CompanyService;
import service.EmployeeService;
import service.resultData.ApplyResult;

public class EmployeeController {

	private CompanyService companyService;
	private EmployeeService employeeService;
	private Reader reader;
	private AuthenticationManager authenticationManager;

	public EmployeeController(CompanyService companyService, EmployeeService employeeService, Reader reader, AuthenticationManager authenticationManager) {
		this.companyService = companyService;
		this.employeeService = employeeService;
		this.reader = reader;
		this.authenticationManager = authenticationManager;

	}


	public void employeeMenu() {


		LoginObjectDto loginObjectDto = authenticationManager.getLoginDto();
		
		String s_Choice = "";
		//매개변수 loggedInEmp가 null이 아닐때 while로 메뉴 출력 무한 반복
		// login 메소드에서 empLoginMem 변수가 loggedInEmp로 들어옴(타입일치)
		while(authenticationManager.isLoggedIn()) {

			
			System.out.println("-".repeat(70));
			System.out.println("============================== ◆ "+ loginObjectDto.getName() +" 님 전용메뉴 ◆ ==============================");
			System.out.println("1. 내정보 보기   2. 내정보 수정   3. 이력서 메뉴   4.모든구인회사 조회   5. 구인회사 검색하기 \n"
					+ "6. 모든채용공고 조회   7. 채용응모하기    8. 채용응모한것조회    9.로그아웃   10. 회원탈퇴  ");
			System.out.println("-".repeat(70));

			s_Choice = reader.getInputWithPrompt("▷메뉴번호 선택 : ");


			switch (s_Choice) {
				case "1": // 1. 내정보 보기
					
//					leeJungYeonController.viewMyInfo(member);
					viewMyInfo(loginObjectDto.getSeq());
					break;
					
				case "2": // 2. 내정보 수정

					update_emp_info();
					
					break;
					
				case "3": // 이력서 메뉴
					applicationMenu();
					
					break;
					
				case "4": // 4.모든구인회사 조회

					showAllComp();
					
					break;
					
				case "5": // 5. 구인회사 검색하기
//					companySearch(sc);
					companySearch();
					
					break;
					
				case "6": // 6. 모든채용공고 조회
//					controllerkdh_EMP.viewCompanyRecruits(sc);
					viewAllRecruits();

					break;
					
				case "7": // 7. 채용응모하기
//					leeJungYeonController.apply(sc, member);
					apply();
					break;
					
				case "8": // 8. 채용응모한것조회
//					employeeController_ksj.JobApplicationInquiry(sc, loggedInEmp.getEmp_seq());
					viewAppliedRecruit();
					break;
				
				case "9": // 9.로그아웃


					String isConfirm = reader.getValidatedInput("정말 로그아웃 하시겠습니까?[y/n]",
							string -> string.equalsIgnoreCase("y") || string.equalsIgnoreCase("n"),
							"!!똑바로 입력하시오!!");

					if("y".equalsIgnoreCase(isConfirm)) {

						authenticationManager.logout();

						System.out.println("로그아웃 되었습니다");
						return;
					} else if("n".equalsIgnoreCase(isConfirm)) {
						System.out.println("로그아웃 취소");

					}

					break;
				case "10": // 10. 회원탈퇴
					dropMember();

					break;
				default:
					break;
			}
		}
	}


	public void viewMyInfo(int seq) {
		System.out.println("------------내 정보------------");

//		String infoString = member.getInfo(employeeService);

		EmployeeDto employeeDto = employeeService.getEmployeeDto(seq);
		StringBuilder sb = new StringBuilder();

		sb.append("seq : " + employeeDto.getEmp_seq() + "\n");
		sb.append("아이디 : " + employeeDto.getEmp_id() + "\n");
		sb.append("이름 : " +  employeeDto.getEmp_name() + "\n");
		sb.append("이메일 : " +employeeDto.getEmp_email() + "\n");
		sb.append("전번 : " + employeeDto.getEmp_tel() + "\n");

		System.out.println(sb);
	}

	public void update_emp_info() {
		int result = 0;

		System.out.println("\n>>> 구직자정보 수정하기 <<<");

		LoginObjectDto loginObjectDto = authenticationManager.getLoginDto();
		EmployeeDto employeeDto = employeeService.getEmployeeDto(loginObjectDto.getSeq());


		// 회사 로그인 한 정보의 시퀀스를 가져와서 비밀번호를 가져오고

		// 위의 시퀀스를 이용해 회사에 있는 자료들을 가져온다.
		System.out.println("------------------수정 전--------------------");
		System.out.println("[수정전 구직자아이디] : " + employeeDto.getEmp_id());
		System.out.println("[수정전 구직자이름] : " + employeeDto.getEmp_name());
		System.out.println("[수정전 구직자이메일] : " + employeeDto.getEmp_email());
		System.out.println("[수정전 구직자주민번호] : " + employeeDto.getJubun());
		System.out.println("[수정전 구직자주소] : " + employeeDto.getEmp_address());
		System.out.println("[수정전 구직자전화번호] : " + employeeDto.getEmp_tel());
		System.out.println("--------------------------------------");

		System.out.println("---------------구직자 정보수정-----------------");


		//todo :로그인 정보 따로 변경
//		System.out.print("사용하실 비밀번호 입력 [변경하지 않으려면 그냥 엔터] : ");
//		String emp_password = reader.getInputDefaultWhenFalse("사용하실 비밀번호 입력 [변경하지 않으려면 그냥 엔터] : ", string -> !string.isBlank(), )
//		if (employeeLoginDto.getEmp_password() != null && emp_password.isBlank()) {
//			emp_password = employeeLoginDto.getEmp_password();
//		}
//		employeeLoginDto.setEmp_password(emp_password);

		String emp_name = reader.getInputDefaultWhenTrue("이름 입력 [변경하지 않으려면 그냥 엔터] : ", String::isBlank, employeeDto.getEmp_name());
		employeeDto.setEmp_name(emp_name);


		String emp_email = reader.getInputDefaultWhenTrue("사용하실 이메일 주소 입력 [변경하지 않으려면 그냥 엔터] : ", String::isBlank, employeeDto.getEmp_email());
		employeeDto.setEmp_email(emp_email);

		System.out.print("거주지 입력 [변경하지 않으려면 그냥 엔터] : ");
		String emp_addr = reader.getInputDefaultWhenTrue("거주지 입력 [변경하지 않으려면 그냥 엔터] : ", String::isBlank, employeeDto.getEmp_address());
		employeeDto.setEmp_address(emp_addr);

		System.out.print("전화번호 입력 [변경하지 않으려면 그냥 엔터] : ");
		String emp_tel = reader.getInputDefaultWhenTrue("전화번호 입력 [변경하지 않으려면 그냥 엔터] : ", String::isBlank, employeeDto.getEmp_tel());
		employeeDto.setEmp_tel(emp_tel);



			////////////////////////////////////////////////////////////
		System.out.print("▷ 이 정보로 수정 하시겠습니까? [Y/N] : ");
		String yn = reader.getValidatedInput("▷ 이 정보로 수정 하시겠습니까? [Y/N] : ",
				string -> string.equalsIgnoreCase("y") || string.equalsIgnoreCase("n"),
				">> Y 또는 N 만 입력하세요!! << \n");

		// 게시판 글쓰기
		if ("y".equalsIgnoreCase(yn)) {
//			result = edao_ksj.update_emp_info(employeeLoginDto);

			result = employeeService.updateInfo(employeeDto);

			if (result == 1) {
				System.out.println(">>> 구직자 정보 수정 성공!! <<<");
			} else if (result == -1) {
				System.out.println(">>> 데이터를 정확하게 입력해주세요!! <<<");
				System.out.println(">> 구직자 정보 수정 실패!! <<");
			}


		}
		else if ("n".equalsIgnoreCase(yn)) {
			System.out.println(">> 구직자 정보 수정 취소!! <<");

		}

	}



	public void showAllComp() {


		List<CompanyDto> companyList = companyService.getAllComp();

		if (companyList.size() > 0) {
			// Print table headers with fixed column widths
			System.out.println("-".repeat(110));
			System.out.printf("%-15s %-15s %-20s %-25s %-15s %-10s %-15s %-15s %-15s %-15s %-15s%n",
					"회사이름", "회사규모", "회사주소", "회사이메일", "대표자", "사원수",
					"매출액", "자본금", "설립일", "사대보험여부", "산업명");
			System.out.println("-".repeat(110));

			StringBuilder sb = new StringBuilder();

			for (CompanyDto comp : companyList) {
				// Append each row with formatted output
				sb.append(String.format("%-15s %-15s %-20s %-25s %-15s %-10d %-15d %-15d %-15s %-15s %-15s%n",
						comp.getComp_name(),
						comp.getComp_scale(),
						comp.getComp_addr(),
						comp.getComp_email(),
						comp.getComp_ceo(),
						comp.getComp_emp_cnt(),
						comp.getComp_sales(),
						comp.getComp_capital(),
						comp.getComp_est_date().toString(),
						comp.getComp_insurance(),
						comp.getIndust_name()));
			}

			// Print the entire content of the StringBuilder
			System.out.println(sb.toString());

		} else {
			System.out.println(">>>등록된 회사가 아직 없습니다.<<<");
		}

	}

	
	public void applicationMenu() {
		// 검색 메뉴 

			String s_menuNo = "";
			
			do {
				////////////////////
				System.out.println("\n ------------- 이력서 검색메뉴 -------------- \n"
								+ "1. 작성    2.수정   3.삭제   4.이력서 보기  5.구직자메뉴로 돌아가기 \n");
				s_menuNo = reader.getInputWithPrompt("▷ 메뉴번호 선택 : ");
				
				switch (s_menuNo) {
					case "1": 
						//hssEmployeeCtrl.writeApplication(sc, employee);
						writeApplication();
						break;
					case "2": 
						//leeJungYeonController.edit_application(sc, employee);
						editApplication();
						break;
						
					case "3": 
						//leeJungYeonController.deleteApplication(sc, employee);
						deleteApplication();
						break;
					
					case "4":
						showApplication();
						//employeeController_ksj.showApplicationCareer(sc, employee);
						break;
					case "5": 

						break;
						
					default:
						System.out.println(">> 메뉴에 없는 번호입니다. <<");
						break;
				} // end of switch (s_menuNo)-------------
				////////////////////////////////////////////////////////////
			} while(!("5".equals(s_menuNo)));
			
		
	}

	private void showApplication() {

		System.out.println("------ 이력서 조회 -------");
//		StringBuilder sb1 = new StringBuilder();

		List<ApplicationDto> applicationDtoList = employeeService.getApplicationDtoList(authenticationManager.getLoginDto().getSeq());
		EmployeeDto employeeDto = employeeService.getEmployeeDto(authenticationManager.getLoginDto().getSeq());
		if(applicationDtoList.size() == 0) {
			System.out.println("작성하신 이력서가 없습니다.");
			return;
		}

		System.out.println("\n인덱스번호\t이력서 번호\t이름\t이메일\t전화번호\t사는지역\t희망고용형태\t병역여부\t희망연봉\t지원동기");
		System.out.println("-".repeat(250));

		StringBuilder sb = new StringBuilder();

		int index = 0;
		for (ApplicationDto applicationDto : applicationDtoList) {

			//todo: authenticationManager의 loginDto의 setter 제거하기
			//todo: 각종 seq값을 판단하는 domain model의 비즈니스 로직 추가
			sb.append(index).append("\t").
					append(applicationDto.getApplicationSeq()).
					append("\t").append(employeeDto.getEmp_name()).
					append("\t").append(employeeDto.getEmp_email()).append("\t").
					append(employeeDto.getEmp_tel()).append("\t").
					append(applicationDto.getLocationSeq() == 1 ? "서울" : "경기").append("\t").
					append(applicationDto.getEmployTypeSeq() == 1 ? "정규" : "계약").append("\t").
					append(applicationDto.getMilitarySeq() == 1 ? "군필" : "미필").append("\t").
					append(applicationDto.getLicense()).append("\t").
					append(applicationDto.getHopeSal()).append("\t").
					append(applicationDto.getMotive()).append("\n");
			index++;

		} // end of for-----------------------------

		System.out.println(sb); // 찍음

		String appNum = reader.getValidatedInput("> 학력을 조회하고 싶은 이력서 번호를 입력하세요(학력조회를 하고 싶지 않을 경우 엔터): ",
				string -> {
					try {
						if(string.trim().isEmpty()) {
							return true;
						}
						Integer.parseInt(string);
						applicationDtoList.get(Integer.parseInt(string));
						return true;
					}catch (Exception e) {

						return false;
					}

				},
				"!!!!올바른 인덱스 번호를 입력하시오!!!!");

		if(!appNum.isBlank()) {
			List<EducationDto> educationDtoList = applicationDtoList.get(Integer.parseInt(appNum)).getEducationDtoList();
			if(educationDtoList.size() > 0) {
				System.out.println("\n학교이름\t학과이름\n");
				System.out.println("-".repeat(250));

				sb = new StringBuilder();


				for (EducationDto dto : educationDtoList) {
					sb.append("\n" + dto.getSchoolName() + "\t" + dto.getDepartmentName() + "\n");
				}
				System.out.println(sb);

			} else {
				System.out.println("해당 이력서에는 작성된 학력이 없습니다.");
			}
		}

		appNum = reader.getValidatedInput("> 경력을 조회하고 싶은 이력서 번호를 입력하세요(경력조회를 하고 싶지 않을 경우 엔터): ",
				string -> {
					try {
						if(string.trim().isEmpty()) {
							return true;
						}
						Integer.parseInt(string);
						applicationDtoList.get(Integer.parseInt(string));
						return true;
					}catch (Exception e) {

						return false;
					}

				},
				"!!!!올바른 인덱스 번호를 입력하시오!!!!");

		//경력조회 시작~~~
		if(!appNum.isBlank()) {
			List< CareerDto> careerDtoList = applicationDtoList.get(Integer.parseInt(appNum)).getCareerDtoList();
			if(careerDtoList.size() > 0) {
				System.out.println("\nt직무명\t경력시작\t경력끝");
				System.out.println("-".repeat(250));

				sb = new StringBuilder();


				for (CareerDto dto : careerDtoList) {
					sb.append("\n").append(dto.getPosition()).
							append("\t").append(dto.getCareerStartDate()).
							append("\t").append(dto.getCareerEndDate()).append("\n");

				}
				System.out.println(sb.toString());

			} else {
				System.out.println("해당 이력서에는 작성된 경력이 없습니다.");
			}

		}
		// 이력서의 경력 자세히 보기 끝 //


	}

	private void deleteApplication() {
		System.out.println("------ 이력서 삭제 -------");

		int result = 0;

		System.out.println("삭제할 이력서 번호를 선택하시오 : ");
		int application_seq = Integer.parseInt(reader.getValidatedInput("삭제할 이력서 번호를 선택하시오 : ",
				string -> {
					try{
						Integer.parseInt(string);
						return true;
					}catch (NumberFormatException e) {
						return  false;
					}

				},
				"!!숫자만 입력하시오!!"
				));

		result = employeeService.deleteApplication(authenticationManager.getLoginDto().getSeq() ,application_seq);

		if(result == 1) {

			System.out.println("이력서 삭제 성공!!");
		}else if(result == 0) {
			System.out.println("해당 이력서 번호의 정보를 찾을 수 없거나 권한이 없습니다.");
		}
		else {
			System.out.println("!!이력서 삭제 실패!!");
		}


	}

	private void editApplication() {
		System.out.println("------------ 이력서 수정 -------------");

		String application_seq = reader.getValidatedInput("수정할 이력서 번호를 입력하시오: ",
				string -> {
					try{
						Integer.parseInt(string);
						return true;
					}catch (NumberFormatException e) {
						return false;
					}
				},
				"!!숫자를 입력하시오!!");

		ApplicationDto myApplicationDto = employeeService.getApplicationDto(Integer.parseInt(application_seq));
		System.out.println("controller : " + myApplicationDto.getMotive());

		if(myApplicationDto == null) {
			System.out.println("!!!존재하지 않는 이력서 입니다!!!");
			return;
		}

		String loc =  reader.getValidatedInput("현재 희망 근무 지역: " + myApplicationDto.getLocationSeq() + "\n" +
				"수정할 희망 근무 지역 입력(변경 안할시 엔터) : \n" +
				"1. 서울\t 2.경기도",
				string -> string.trim().equals("1") || string.trim().equals("2") || string.trim().isEmpty(),
				"똑바로 입력하시오");

		myApplicationDto.setLocationSeq(loc.trim().isEmpty()? myApplicationDto.getLocationSeq(): Integer.parseInt(loc));


		String empType =  reader.getValidatedInput("현재 희망 근무 타입: " + myApplicationDto.getEmployTypeSeq() + "\n" +
						"수정할 희망 근무 타입(변경 안할시 엔터) :  \n" +
						"0. 계약직\t 1.정규직",
				string -> string.trim().equals("1") || string.trim().equals("2") || string.trim().isEmpty(),
				"똑바로 입력하시오");

		myApplicationDto.setEmployTypeSeq(empType.trim().isEmpty()? myApplicationDto.getEmployTypeSeq(): Integer.parseInt(empType));




		String mil =  reader.getValidatedInput("현재 병역 상태: " + myApplicationDto.getMilitarySeq()+ "\n" +
						"수정할 병역상태(변경 안할시 엔터) :   \n" +
						"0. 미필\t 1.군필 \t 2.면제 \t 3.복무중" ,
				string -> string.trim().equals("0")||string.trim().equals("1") || string.trim().equals("2") || string.trim().equals("3") || string.trim().isEmpty(),
				"똑바로 입력하시오");

		myApplicationDto.setMilitarySeq(mil.trim().isEmpty()? myApplicationDto.getMilitarySeq(): Integer.parseInt(mil));




		String license = reader.getInputWithPrompt("현재 자격증: " + myApplicationDto.getLicense() + "\n" +
				"수정할 자격증(변경 안할시 엔터) : ");
		if(!license.trim().isEmpty()) {
			myApplicationDto.setLicense(license);
		}


		String motive = reader.getInputWithPrompt("현재 자기소개: " + myApplicationDto.getMotive() + "\n" +
				"수정할 자기소개(변경 안할시 엔터) : ");
		if(!motive.trim().isEmpty()) {
			myApplicationDto.setMotive(motive);
		}


		for(int i = 0; i < myApplicationDto.getCareerDtoList().size(); i++) {
			CareerDto career = myApplicationDto.getCareerDtoList().get(i);
			System.out.println(career);
			String affirm = reader.getValidatedInput("수정하세겠습니까? (Y/N): ",
					string -> string.equalsIgnoreCase("Y") || string.equalsIgnoreCase("N"),
					"잘못된 입력입니다. Y 또는 N을 입력해주세요.");

			if(affirm.trim().equalsIgnoreCase("N")) {
				continue;
			}

			String position = reader.getInputWithPrompt("Enter new position (press enter to pass): ");
			if(!position.trim().isEmpty()) {
				career.setPosition(position);
			}



			//todo: 비즈니스 로직을 Domain 오브젝트 내에서 수행 할 수 있도록!!!
			String startDate = reader.getValidatedInput("Enter new career start date (YYYY/MM/DD)(press enter to pass): ",
					string -> {
						try {
							SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
							sdf.parse(string);
							return true;
						}catch (ParseException e ) {
							return false;
						}
					},
					"!!enter correct format!!");


			String endDate = reader.getValidatedInput("Enter new career end date (YYYY/MM/DD)(press enter to pass): ",
					string -> {
						try {
							SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
							sdf.parse(string);
							return true;
						}catch (ParseException e ) {
							return false;
						}
					},
					"!!enter correct format!!");



			try {
				SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");

				career.setCareerStartDate(simpleDateFormat.parse(startDate));
				career.setCareerEndDate(simpleDateFormat.parse(endDate));
			}catch (ParseException e) {
				System.out.println("!!!! 잘못된 형식 !!!!");
				return;
			}

		}


		for(int i = 0; i < myApplicationDto.getEducationDtoList().size(); i++) {
			EducationDto education = myApplicationDto.getEducationDtoList().get(i);
			System.out.println("현재 학력 정보: ");
			System.out.println("학교 이름: " + education.getSchoolName());
			System.out.println("학과 이름: " + education.getDepartmentName());
			System.out.print("수정하세겠습니까? (Y/N): ");

			String affirm = reader.getValidatedInput("현재 학력 정보: \n" +
					"학교 이름: " + education.getSchoolName() +"\n" +
					"학과 이름: " + education.getDepartmentName() + "\n" +
					"수정하세겠습니까? (Y/N): \n",
					string -> string.equalsIgnoreCase("y") || string.equalsIgnoreCase("n"),
					"잘못된 입력입니다. Y 또는 N을 입력해주세요.");



			if(affirm.equalsIgnoreCase("y")) {
				String newSchoolName = reader.getInputWithPrompt("새 학교 이름을 입력하세요 (press enter to pass): ");
				if(!newSchoolName.trim().isEmpty()) {
					education.setSchoolName(newSchoolName);
				}

				String newDepartmentName = reader.getInputWithPrompt("새 학과 이름을 입력하세요 (press enter to pass): ");
				if(!newDepartmentName.trim().isEmpty()) {
					education.setDepartmentName(newDepartmentName);
				}



			} else if(affirm.equalsIgnoreCase("n")) {
				continue; // 다음 항목으로 넘어갑니다.
			}


		}


		int result = employeeService.updateApplication(myApplicationDto);


		if(result == 1){
			System.out.println("-----수정 성공-------");
		}else {
			System.out.println("!!!! 수정 실패 !!!!");
		}

	}

	private void writeApplication() {

		ApplicationDto applicationDto = new ApplicationDto();

		applicationDto.setEmpSeq(authenticationManager.getLoginDto().getSeq());

		System.out.println("이력서를 작성 시작합니다.");

		// 자격증 LICENSE
		String license = reader.getInputWithPrompt("1. 소유 자격증을 입력하세요(없을시 엔터):");
		applicationDto.setLicense(license);

		// 희망연봉 HOPE_SAL
		String hopeSal = reader.getValidatedInput("2. 희망연봉을 입력하세요(없을시 엔터):",
					string -> {
						if(string.isEmpty()) {
							string = "0";
						}

						try{
							Integer.parseInt(string);
							return true;
						}catch (NumberFormatException e) {
							return false;
						}
					},
					"올바른 숫자 형식이 아닙니다. 다시 입력하세요.");
		applicationDto.setHopeSal(Integer.parseInt(hopeSal));


		// 병역 MILITARY_SEQ Not Null
		String mil = reader.getValidatedInput("3. 병역 여부를 숫자로 입력하세요(0.미필 1.군필 2.면제 3.복무중):",
					string -> {
						return string.trim().equals("0") || string.trim().equals("1") || string.trim().equals("2") || string.trim().equals("3");
					},
					"0, 1, 2, 3만 입력 가능합니다.");
		applicationDto.setMilitarySeq(Integer.parseInt(mil));


		// 근무지역 LOCATION_SEQ Not Null
		String loc = reader.getValidatedInput("4. 희망근무지역을 숫자로 입력하세요(1.서울 2.경기도) :",
					string -> string.trim().equals("1") || string.trim().equals("2"),
					"1, 2만 입력 가능합니다.");
		applicationDto.setLocationSeq(Integer.parseInt(loc));

		// 희망 근무 전형 EMPLOY_TYPE_SEQ Not Null
		String empType = reader.getValidatedInput("5. 희망 근무 전형을 숫자로 입력하세요(0. 계약직  1.정규직): ",
					string -> {
						return string.trim().equals("1") || string.trim().equals("2");
					},
					"1, 2만 입력 가능합니다.");
		applicationDto.setEmployTypeSeq(Integer.parseInt(empType));



		// 경력 입력받기
		List<CareerDto> careerList = new ArrayList<>();
		String careerIn = "";
		while (true) {
			careerIn = reader.getValidatedInput("6.경력을 입력하시겠습니까? (Y/N): ",
					string -> string.trim().equalsIgnoreCase("y") || string.trim().equalsIgnoreCase("n"),
					"잘못된 입력입니다. Y 또는 N을 입력해주세요.");

			if (careerIn.equalsIgnoreCase("y")) {
				CareerDto career = new CareerDto();

				String position = reader.getValidatedInput("직무명 : ",
						string -> !string.isEmpty(),
						"직무명을 입력하지 않았습니다. 다시 입력하세요.");

				career.setPosition(position);
				String startDate = reader.getValidatedInput("경력시작일(yyyy-MM-dd 형식으로 입력하세요) : ",
						string -> !string.isBlank() && MyUtil.isTrueDate(string),
						"경력시작일을 입력하지 않았거나 유효하지 않은 날짜입니다. 다시 입력하세요.");


				String endDate = reader.getValidatedInput("경력종료일(yyyy-MM-dd 형식으로 입력하세요) : ",
						string -> !string.isBlank() && MyUtil.isTrueDate(string),
						"경력시작일을 입력하지 않았거나 유효하지 않은 날짜입니다. 다시 입력하세요.");

				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				try {
					career.setCareerStartDate(sdf.parse(startDate));
					career.setCareerEndDate(sdf.parse(endDate));

				}catch (ParseException e) {
					System.out.println("!!!! 잘못된 날짜 형식입나다 !!!!");
				}



				careerList.add(career); // 경력 정보를 리스트에 추가
			} else if (careerIn.equalsIgnoreCase("n")) {
				break; // 경력 입력 종료
			} else {
				System.out.println("잘못된 입력입니다. Y 또는 N을 입력해주세요.");
			}
		}

		applicationDto.setCareerDtoList(careerList);


		// 학력 입력받기

		String educationIn = "";
		List<EducationDto> educationList = new ArrayList<>(); //리스트 생성

		while(true) {

			educationIn = reader.getValidatedInput("7.학력을 입력하시겠습니까? (Y/N): ",
					string -> string.trim().equalsIgnoreCase("y") || string.trim().equalsIgnoreCase("n"),
					"잘못된 입력입니다. Y 또는 N을 입력해주세요.");

			if(educationIn.equalsIgnoreCase("y")) {
				EducationDto education = new EducationDto(); //객체 생성


				String schoolName = reader.getValidatedInput("학교명 : ",
						string -> !string.isEmpty(),
						"학교명을 입력하지 않았습니다. 다시 입력하세요.");

				education.setSchoolName(schoolName);


				String departmentName = reader.getValidatedInput("학과명 : ",
						string -> !string.isEmpty(),
						"학과명을 입력하지 않았습니다. 다시 입력하세요.");
				education.setDepartmentName(departmentName);

				educationList.add(education); // 리스트 추가

			} else if(educationIn.equalsIgnoreCase("n")) {
				break; //학력 입력 종료
			} else {
				System.out.println("잘못된 입력입니다. Y 또는 N을 입력해주세요.");
			}
		}
		applicationDto.setEducationDtoList(educationList);



		String motive = reader.getValidatedInput("지원동기를 입력하세요. : ",
				string -> !string.isEmpty(),
				"지원동기를 입력하지 않았습니다. 다시 입력하세요.");

		applicationDto.setMotive(motive);


		//DAO 메서드 호출 1. 이력서 작성 메소드 2. 경력 작성 메소드 3. 학력 작성 메소드


		boolean isSuccess =  employeeService.insertApplication(applicationDto);

		if(isSuccess) {

			System.out.println("!!이력서 작성 성공!!");

		} else {
			System.out.println("!!이력서 작성 실패!!");
		}
	}


	public void companySearch() {
		// 검색 메뉴 

			String s_menuNo = "";
			
			do {
				////////////////////
				System.out.println("\n ------------- 회사 검색메뉴 -------------- \n"
								+ "1. 회사이름검색    2.자본금검색   3.직원 수로 검색   4.매출로 검색  5.공고제목으로 검색 6.구직자메뉴로 돌아가기 \n");
				s_menuNo = reader.getInputWithPrompt("▷ 메뉴번호 선택 : ");
				
				switch (s_menuNo) {
					case "1": //1. 회사이름검색
						compSearchByName();
						break;
					case "2": // 2.자본금검색
						//hssSearchComCtrl.compCapital(sc);
						compSearchByCapital();
						break;
						
					case "3": // 3.직원수로 검색
						//leeJungYeonController.searchCompanyByEmployeeCount(sc);
						compSearchByEmployeeCount();
						break;
					
					case "4": // 4.매출로 검색
						//leeJungYeonController.searchCompanyBySales(sc);
						compSearchBySales();
						break;
						
					case "5": // 5.공고제목으로 검색
						//hssSearchComCtrl.titleSearch(sc);
						searchRecruit();
						break;

					case "6":
						break;
						
					default:
						System.out.println(">> 메뉴에 없는 번호입니다. <<");
						break;
				} // end of switch (s_menuNo)-------------
				////////////////////////////////////////////////////////////
			} while(!("6".equals(s_menuNo)));
			
		
	}


	private void compSearchByName() {

		String compName = reader.getInputWithPrompt("▷ 검색하실 회사명을 입력하세요 : ");

		List<CompanyDto> compList = companyService.getCompByName(compName);

		if(compList.size() > 0) {
			System.out.println("-".repeat(50));
			System.out.println("회사명\t기업규모\t설립일\t회사주소\t"
					+ "대표자이름\t사원수\t자본금\t매출액\t");
			System.out.println("-".repeat(50));

			StringBuilder sb = new StringBuilder();

			for(CompanyDto cpdto : compList) {
				sb.append(cpdto.getComp_name() + "\t" + cpdto.getComp_scale() + "\t" + cpdto.getComp_est_date() + "\t"
						+ cpdto.getComp_addr() + "\t" + cpdto.getComp_ceo() + "\t" + cpdto.getComp_emp_cnt() + "\t"
						+ cpdto.getComp_capital() + "\t" + cpdto.getComp_sales() + "\n"
				);
			}
			System.out.println(sb.toString());


		} else {
			System.out.println("검색결과 회사명이 " + compName + "인 회사는 없습니다.");
		}

	}

	private void compSearchByCapital() {

		while(true) {
			try {

				String capital = reader.getValidatedInput("▷ 검색시 최소 자본금을 입력하세요 : ",
						string -> {
							try {
								Integer.parseInt(string);
								return true;
							}catch (NumberFormatException e) {
								return false;
							}

						},
						"!!숫자만 입력하시오!!");

				//List<CompanyDto> compList = HssMemberDao.compCapitalSearch(Integer.parseInt(capital));
				List<CompanyDto> compList = companyService.getCompCapitalBiggerThen(Integer.parseInt(capital));
				if(compList.size() > 0) {
					System.out.println("-".repeat(50));
					System.out.println("회사명\t기업규모\t설립일\t회사주소\t"
							+ "대표자이름\t사원수\t자본금\t매출액\t");
					System.out.println("-".repeat(50));

					StringBuilder sb = new StringBuilder();

					for(CompanyDto cpdto : compList) {
						sb.append(cpdto.getComp_name() + "\t" + cpdto.getComp_scale() + "\t" + cpdto.getComp_est_date() + "\t"
								+ cpdto.getComp_addr() + "\t" + cpdto.getComp_ceo() + "\t" + cpdto.getComp_emp_cnt() + "\t"
								+ cpdto.getComp_capital() + "\t" + cpdto.getComp_sales() + "\n"
						);
					}
					System.out.println(sb.toString());
					break;
				} else {
					System.out.println("검색결과 자본금이 " + capital + "이상인 회사는 없습니다.");
					break;
				}
			} catch (NumberFormatException e) {
				System.out.println("[경고] 숫자만 입력하세요.");
			}
		}
	}

	private void compSearchByEmployeeCount() {

		System.out.println("------- 직원 수로 회사 검색 ------");



		String temp = reader.getValidatedInput("▷ 검색할 최소 직원 수 입력 :  ",
				string -> {
					try {
						Integer.parseInt(string);
						return true;
					}catch (NumberFormatException e) {
						return false;
					}
				},
				"!!숫자만 입력하시오!!");
		int min  = Integer.parseInt(temp);

		temp = reader.getValidatedInput("▷ 검색할 최대 직원 수 입력 :  ",
				string -> {
					try {
						Integer.parseInt(string);
						return true;
					}catch (NumberFormatException e) {
						return false;
					}
				},
				"!!숫자만 입력하시오!!");
		int max  = Integer.parseInt(temp);



		///List<CompanyDto> searchList = myDao.searchCompanyByEmployeeCount(min, max);
		List<CompanyDto> searchList = companyService.searchCompanyByEmployeeCount(min, max);

		System.out.println("seq \t 이름 \t 산업 \t 이메일 \t규모 \t 설립일 \t 주소 \t 대표 \t 직원수 \t 자본금 \t 매출 \t 보험여부");
		if (!searchList.isEmpty()) {
			for (CompanyDto company : searchList) {
				System.out.print(company.getComp_seq() + "\t");
				System.out.print(company.getComp_name() + "\t");

				System.out.print(company.getIndust_name() + "\t");
				System.out.print(company.getComp_email() + "\t");

				System.out.print(company.getComp_scale() + "\t");
				System.out.print(company.getComp_est_date() + "\t");
				System.out.print(company.getComp_addr() + "\t");
				System.out.print(company.getComp_ceo() + "\t");
				System.out.print(company.getComp_emp_cnt() + "\t");
				System.out.print(company.getComp_capital() + "\t");
				System.out.print(company.getComp_sales() + "\t");
				System.out.print(company.getComp_insurance()+ "\n");
			}
		} else {
			System.out.println("해당 범위의 회사를 찾을 수 없음");
		}
	}

	private void compSearchBySales() {

		System.out.println("------- 매출로 회사 검색 ------");



		String temp = reader.getValidatedInput("▷ 검색할 최소 매출 입력 :  ",
				string -> {
					try {
						Integer.parseInt(string);
						return true;
					}catch (NumberFormatException e) {
						return false;
					}
				},
				"!!숫자만 입력하시오!!");
		int min  = Integer.parseInt(temp);

		temp = reader.getValidatedInput("▷ 검색할 최대 매출 입력 :  ",
				string -> {
					try {
						Integer.parseInt(string);
						return true;
					}catch (NumberFormatException e) {
						return false;
					}
				},
				"!!숫자만 입력하시오!!");
		int max  = Integer.parseInt(temp);



		List<CompanyDto> searchList = companyService.searchCompanyBySales(min, max);

		System.out.println("seq \t 이름 \t 산업 \t 이메일 \t규모 \t 설립일 \t 주소 \t 대표 \t 직원수 \t 자본금 \t 매출 \t 보험여부");
		if (!searchList.isEmpty()) {
			for (CompanyDto company : searchList) {
				System.out.print(company.getComp_seq() + "\t");
				System.out.print(company.getComp_name() + "\t");

				System.out.print(company.getIndust_name() + "\t");
				System.out.print(company.getComp_email() + "\t");

				System.out.print(company.getComp_scale() + "\t");
				System.out.print(company.getComp_est_date() + "\t");
				System.out.print(company.getComp_addr() + "\t");
				System.out.print(company.getComp_ceo() + "\t");
				System.out.print(company.getComp_emp_cnt() + "\t");
				System.out.print(company.getComp_capital() + "\t");
				System.out.print(company.getComp_sales() + "\t");
				System.out.print(company.getComp_insurance()+ "\n");

			}
		} else {
			System.out.println("해당 범위의 회사를 찾을 수 없음");
		}


	}

	private void searchRecruit() {

		System.out.print("▷ 검색하실 공고명을 입력하세요 : ");
		String title = reader.getInputWithPrompt("▷ 검색하실 공고명을 입력하세요 : ");

		List<RecruitDto> rcList = employeeService.titleSearch(title);
		if(rcList.size() > 0) {
			System.out.println("-".repeat(50));
			System.out.println("회사명\t공고제목\t요구자격증\t전형\t"
					+ "직무\t채용인원\t요구학력\t요구경력\t급여\t모집시작일자\t모집마감일자\t합격자발표일\t"
					+ "채용담당자이름\t채용담당자이메일\t고용기간");
			System.out.println("-".repeat(50));

			StringBuilder sb = new StringBuilder();

			for(RecruitDto rdto : rcList) {
				sb.append(rdto.getComp_name() + "\t" + rdto.getRecruit_title() + "\t" +
						rdto.getLicense() + "\t" + rdto.getScreening() + "\t" + rdto.getPosition() +
						rdto.getEdu_level() + "\t" + rdto.getExp_level() + "\t" + rdto.getSalary() +
						rdto.getRecruit_start_date() + "\t" + rdto.getRecruit_end_date() + "\t" +
						rdto.getResult_date() + "\t" + rdto.getRecruit_manager_name() + "\t" +
						rdto.getRecruit_manager_email() + "\t" + rdto.getHire_period() + "\n"

				);
			}
			System.out.println(sb.toString());


		} else {
			System.out.println("검색결과 " + title + "을(를) 포함하는 공고는 없습니다.");
		}

	}

	private void viewAllRecruits() {

		System.out.println("-------- 전체 회사 공고 현황 --------");

		List<RecruitDto> recruitList = employeeService.getAllRecruit();

		System.out.println("번호 \t 공고일련번호 \t\t 공고 \t\t 제목 \t\t 시작일자 \t\t 마감날짜 \t\t 마감여부");

		StringBuilder sb = new StringBuilder();
		int index = 0;
		for (RecruitDto r : recruitList) {
			sb.append(index + "\t\t");
			sb.append(r.getRecruit_seq() + "\t\t");
			//sb.append(r.getRecruit_title() + "[" + r.getCount() + "]" + "\t\t");
			sb.append(r.getRecruit_title() + "\t\t");
			sb.append(r.getRecruit_start_date() + "\t\t");
			sb.append(r.getRecruit_end_date() + "\t\t");
			sb.append(r.getStatus() + "\n");
			index ++;
		}

		System.out.println(sb);

		String no;
		no = reader.getValidatedInput("상세보기 번호: " ,
				string -> {
					try {
						Integer.parseInt(string);

					}catch (NumberFormatException e) {
						return false;
					}
					return true;
				},
				"!!숫자만 입력하시오!!");

		RecruitDto rcdto;
		try {
			rcdto = recruitList.get(Integer.parseInt(no));
		}
		catch (IndexOutOfBoundsException e) {
			System.out.println("해당 공고가 없습니다.");
			return;
		}

		sb = new StringBuilder();

		sb.append("공고번호 : " + rcdto.getRecruit_seq()+"\n");
		sb.append("공고제목 : " + rcdto.getRecruit_title()+"\n");
		sb.append("채용인원 : " + rcdto.getRecruit_num()+"\n");
		sb.append("회사이름 : " + rcdto.getComp_name()+"\n");
		sb.append("우대자격증 : " + rcdto.getLicense()+"\n");
		sb.append("전형 : " + rcdto.getScreening()+"\n");
		sb.append("직무 : " + rcdto.getPosition()+"\n");
		sb.append("요구학력 : " + rcdto.getEdu_level()+"\n");
		sb.append("요구경력 : " + rcdto.getExp_level()+"\n");
		sb.append("급여 : " + rcdto.getSalary()+"\n");
		sb.append("모집시작일자 : " + rcdto.getRecruit_start_date()+"\n");
		sb.append("모집마감일자 : " + rcdto.getRecruit_end_date()+"\n");
		sb.append("합격자 발표일 : " + rcdto.getResult_date()+"\n");
		sb.append("모집공고담당자이름 : " + rcdto.getRecruit_manager_name()+"\n");
		sb.append("모집공고담당자이메일 : " + rcdto.getRecruit_manager_email()+"\n");
		sb.append("고용기간 : " + rcdto.getHire_period()+"\n");
		sb.append("채용공고 유지상태 : " + rcdto.getStatus()+"\n"+"-".repeat(60));

		System.out.println(sb);

		// 통계
		viewRecruitStats(rcdto.getRecruit_seq());
	}

	private void viewRecruitStats(int seq) {
		//myDao.getRecruitStats(no);
		EmployeeStatsDto employeeStatsDto = employeeService.getRecruitStats(seq);

		employeeStatsDto.printStats();
	}

	private void apply() {
		System.out.println("------- 원서 넣기 -------");

		String recruitSeq = reader.getValidatedInput("지원할 공고 번호 입력: ",
				string -> {
					try {
						Integer.parseInt(string);
						return true;
					}
					catch (NumberFormatException e) {

						return false;
					}
				},
				"!!숫자를 입력하시오!!");


		String application_seq = reader.getValidatedInput("지원할 내 이력서 번호 입력: ",
				string -> {
					try {
						Integer.parseInt(string);
						return true;
					}
					catch (NumberFormatException e) {

						return false;
					}
				},
				"!!숫자를 입력하시오!!");

		ApplyResult result = employeeService.apply(authenticationManager.getLoginDto().getSeq(), Integer.parseInt(application_seq), Integer.parseInt(recruitSeq));

//		RecruitEntryDto recruitEntryDto = new RecruitEntryDto();
//		recruitEntryDto.setRecruit_seq(recruitSeq);
//		recruitEntryDto.setApplication_seq(application_seq);
//
//		int result =  myDao.apply(recruitEntryDto, employee.getEmp_seq());


		if(result == ApplyResult.SUCCESS) {

			System.out.println("지원 성공!!");
		}else if(result == ApplyResult.ALREADY_APPLIED) {

			System.out.println("지원 실패!! : 이미 지원한 공고 입니다!!");
		}else if(result == ApplyResult.APPLICATION_NOT_EXIST){

			System.out.println("지원 실패!! : 이력서가 존재하지 않습니다.");
		}else if(result == ApplyResult.RECRUIT_NOT_EXIST) {
			System.out.println("지원 실패!! : 공고가 존재하지 않습니다.");
		}else {
			System.out.println("지원 실패!! : 서버오류");
		}
	}

	private void viewAppliedRecruit() {
		List<RecruitEntryDto> recruitEntryList = employeeService.getAppliedRecruit(authenticationManager.getLoginDto().getSeq());

		System.out.println(recruitEntryList.size());

		if (recruitEntryList.size() > 0) {
			// Print table headers with specified column widths
			System.out.printf("%-20s %-30s %-15s %-15s %-15s %-10s %-10s %-10s %-10s %-20s %-20s %-20s %-20s %-30s %-15s %-20s %-10s%n",
					"회사이름", "공고제목", "전형", "직무", "우대자격증", "채용인원", "요구학력", "요구경력", "급여", "모집시작일자",
					"모집마감일자", "합격자 발표일", "채용담당자이름", "채용담당자이메일", "고용기간", "응모날짜", "합격여부");
			System.out.println("-".repeat(230));  // Adjust total characters to match the width above

			for (RecruitEntryDto recruitEntryDto : recruitEntryList) {
				// Print each row with formatted columns
				System.out.printf("%-20s %-30s %-15s %-15s %-15s %-10s %-10s %-10s %-10s %-20s %-20s %-20s %-20s %-30s %-15s %-20s %-10s%n",
						recruitEntryDto.getRecruitDto().getComp_name(),
						recruitEntryDto.getRecruitDto().getRecruit_title(),
						recruitEntryDto.getRecruitDto().getScreening(),
						recruitEntryDto.getRecruitDto().getPosition(),
						recruitEntryDto.getRecruitDto().getLicense(),
						recruitEntryDto.getRecruitDto().getRecruit_num(),
						recruitEntryDto.getRecruitDto().getEdu_level(),
						recruitEntryDto.getRecruitDto().getExp_level(),
						recruitEntryDto.getRecruitDto().getSalary(),
						recruitEntryDto.getRecruitDto().getRecruit_start_date(),
						recruitEntryDto.getRecruitDto().getRecruit_end_date(),
						recruitEntryDto.getRecruitDto().getResult_date(),
						recruitEntryDto.getRecruitDto().getRecruit_manager_name(),
						recruitEntryDto.getRecruitDto().getRecruit_manager_email(),
						recruitEntryDto.getRecruitDto().getHire_period(),
						recruitEntryDto.getEntry_date(),
						recruitEntryDto.getPass_status());
			}
		} else {
			System.out.println("응모하신 회사가 없습니다.");
		}
	}



	public void dropMember() {

		////////////////////////////////////////////////////////////
		System.out.print("▷ 정말로 회원탈퇴 하시겠습니까? [Y/N] : ");
		String yn = reader.getValidatedInput("▷ 정말로 회원탈퇴 하시겠습니까? [Y/N] : ",
				string -> string.equalsIgnoreCase("y") || string.equalsIgnoreCase("n"),
				"!!똑바로 입력하시오!!" );
		// 게시판 글쓰기
		if ("y".equalsIgnoreCase(yn)) {
			int result = employeeService.dropMember(authenticationManager.getLoginDto().getSeq());
			if (result == 1) {
				authenticationManager.logout();
				System.out.println(">>> 구직자 회원탈퇴 성공!! <<<");
			} else {
				System.out.println(">>> 데이터를 정확하게 입력해주세요!! <<<");
				System.out.println(">> 구직자 회원탈퇴 실패!! <<");
			}

		} else if ("n".equalsIgnoreCase(yn)) {
			System.out.println(">> 구직자 회원탈퇴 취소!! <<");
		}

	}


	
	

}
