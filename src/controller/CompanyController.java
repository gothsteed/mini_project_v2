package controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import auth.AuthenticationManager;
import dto.ApplicationDto;
import dto.CompanyDto;
import dto.EmployeeDto;
import dto.RecruitDto;
import myUtil.MyUtil;
import reader.Reader;
import service.CompanyService;
import service.EmployeeService;


public class CompanyController {


	private CompanyService companyService;
	private EmployeeService employeeService;
	private Reader reader;
	private AuthenticationManager authenticationManager;

	public CompanyController(CompanyService companyService, EmployeeService employeeService, Reader reader, AuthenticationManager authenticationManager) {
		this.companyService = companyService;
		this.employeeService = employeeService;
		this.reader = reader;
		this.authenticationManager = authenticationManager;
	}


	public void companyMenu() {


		String s_Choice = "";

		while(authenticationManager.isLoggedIn()) {
			
	
				
			System.out.println("-".repeat(70));
			System.out.println("============================== ◆ 주식회사 "+ authenticationManager.getLoginDto().getName() +" 님 전용메뉴 ◆ ==============================");
			System.out.println("1. 회사정보 보기   2. 회사정보 수정   3. 모든구직자 조회   4. 구직자 검색   5. 채용공고입력하기\n"
					+ "6. 우리회사 채용공고 조회    7.로그아웃   8. 회사 탈퇴하기");
			System.out.println("-".repeat(70));
			s_Choice = reader.getInputWithPrompt("▷메뉴번호 선택 : ");
			
			switch (s_Choice) {
				case "1": // 1. 회사정보 보기
					//zee_CompanyController.showmyCompInfo(loggedInComp.getComp_seq());
					showCompanyInfo();
					break;
					
				case "2": // 2. 회사정보 수정
					
					//companyController_ksj.update_comp_info(sc, loggedInComp.getComp_seq());
					updateCompInfo();

					break;
					
				case "3": // 3.  모든구직자 조회
					//zee_CompanyController.showAllEmployee();
					showAllEmployee();
					break;
					
				case "4": // 4. 구직자 검색
					
					employeeSearch();
					
					break;
					
				case "5": // 5. 채용공고입력하기
					
					//hssComanyCtrl.writeRecruit(sc, member);
					writeRecruit();

					break;
					
				case "6": // 6. 우리회사 채용공고 조회
					//leeJungYeonController.viewCompanyRecruits(sc, member);
					viewCompanyRecruits();

					break;
					

//				case "7": // 7. 채용공고지원자 조회
//					companyController_ksj.searchApplicantsByJobPosting(sc, member.getComp_seq());
//					break;
//				
				case "7": // 7.로그아웃
					
					String affirm = reader.getValidatedInput("정말 로그아웃 하시겠습니까?[y/n]",
							string -> string.equalsIgnoreCase("y") || string.equalsIgnoreCase("n"),
							"!!!! 올바른 값 입력하시오 !!!!");

					if("y".equalsIgnoreCase(affirm)) {
						authenticationManager.logout();
						System.out.println("로그아웃 성공!!");

					} else if("n".equalsIgnoreCase(affirm)) {
						System.out.println("로그아웃 취소");

					}
					
					break;
				case "8": // 8. 회사 탈퇴하기

					dropMember();

				default:
					System.out.println("!!올바른 숫자를 입력하시오!!");
					break;
				}

		}
		
		
	}

	private void dropMember() {
		int result = 0;

		String yn = reader.getValidatedInput("▷ 정말로 회사 회원탈퇴 하시겠습니까? [Y/N] : ",
				string -> string.equalsIgnoreCase("y") || string.equalsIgnoreCase("n"),
				">> Y 또는 N 만 입력하세요!! << \n");
		// 게시판 글쓰기
		if ("y".equalsIgnoreCase(yn)) {
			result = companyService.dropMember(authenticationManager.getLoginDto().getSeq());
			if (result == 1) {
				System.out.println(">>> 회사 회원탈퇴 성공!! <<<");
				authenticationManager.logout();
			} else {
				System.out.println(">>> 데이터를 정확하게 입력해주세요!! <<<");
				System.out.println(">> 회사 회원탈퇴 실패!! <<");
			}

		} else if ("n".equalsIgnoreCase(yn)) {
			System.out.println(">> 회사 회원탈퇴 취소!! <<");
		}

	}

	private void viewCompanyRecruits() {
		System.out.println("-------- 우리 회사 공고 현황 --------");

		List<RecruitDto> recruitList = companyService.getCompanyRecruits(authenticationManager.getLoginDto().getSeq());

		System.out.println("인덱스 \t\t 공고번호 \t\t 제목 \t\t 시작일자 \t\t 마감날자 \t\t 마감여부");
		StringBuilder sb = new StringBuilder();
		int index = 0;
		for(RecruitDto recruit : recruitList) {
			sb.append(index + "\t\t");
			sb.append(recruit.getRecruit_seq() + "\t\t");
			sb.append(recruit.getRecruit_title() + "[" + recruit.getApplicationCount()+ "]" + "\t\t");
			sb.append(recruit.getRecruit_start_date()+ "\t\t");
			sb.append(recruit.getRecruit_end_date()+ "\t");
			sb.append(recruit.getStatus() == 1 ? "모집중 \n" : "마감 \n");
			index++;
		}

		System.out.println(sb);

		String no = reader.getValidatedInput("인덱스 번호 (나가려면 그냥 엔터를 누르시오): ",
				string -> {
					try{
						if(string.isBlank()) {
							return true;
						}

						Integer.parseInt(string);
						return true;
					}catch (NumberFormatException e) {
						return false;
					}
				},
				"올바른 숫자 형식이 아닙니다. 다시 입력하세요.");
		if(no.trim().isBlank()) {
			return;
		}




		//RecruitDto recruitDto = companyDao_kdh.viewRecruitDetailList(company.getComp_seq(), no );
		RecruitDto recruitDto = recruitList.get(Integer.parseInt(no));
		List<ApplicationDto> applicationDtoList = companyService.getAppliedApplications(recruitDto.getRecruit_seq());

		System.out.println("------------공고 "  + recruitDto.getRecruit_seq() + ": "+  recruitDto.getRecruit_title() + "-----------------");
		System.out.println("회사이름 : " + recruitDto.getComp_name());
		System.out.println("채용인원 : " + recruitDto.getRecruit_num());
		System.out.println("직무 : " + recruitDto.getPosition());
		System.out.println("모집 시작일자 : " + recruitDto.getRecruit_start_date());
		System.out.println("모집 마감일자 : " + recruitDto.getRecruit_end_date());

		System.out.println("--------------------------------------------------------");
		System.out.println("인덱스 \t 이력서 번호");

		sb = new StringBuilder();
		index = 0;
		for(ApplicationDto app : applicationDtoList) {
			sb.append(index + "\t");
			sb.append(app.getApplicationSeq() + "  \t  ");
			sb.append(app.getEmpSeq()+  "  \n ");
			index ++;

		}
		System.out.println(sb);


		String application_seq = reader.getValidatedInput("이력서 상세보기 번호 (나가려면 그냥 엔터를 누르시오): ",
				string -> {
					try{
						if(string.isBlank()) {
							return true;
						}

						Integer.parseInt(string);
						return true;
					}catch (NumberFormatException e) {
						return false;
					}
				},
				"올바른 숫자 형식이 아닙니다. 다시 입력하세요.");
		if(application_seq.trim().isEmpty()) {
			return;
		}

		ApplicationDto adto = applicationDtoList.get(Integer.parseInt(application_seq));
		EmployeeDto employeeDto = employeeService.getEmployeeDto(adto.getEmpSeq());

		if(adto == null) {

			System.out.println("!!이력서가 존재 하지 않습니다!!");
			return;


		}

		System.out.println("\n 이력서번호\t\t이름\t\t사는지역\t\t전화번호\t\t이메일\t\t\t고용형태종류\t\t병역상태\t\t소유자격증\t\t희망연봉\t\t지원동기\t\t이력서삭제여부");
		System.out.println("-".repeat(250));

		sb = new StringBuilder();

		sb.append(adto.getApplicationSeq() + "\t\t"
				+employeeDto.getEmp_name() + "\t\t"
				+adto.getLocationSeq() + "\t\t"
				+employeeDto.getEmp_tel() + "\t\t"
				+employeeDto.getEmp_email() + "\t\t"
				+adto.getEmployTypeSeq() + "\t\t"
				+adto.getMilitarySeq()+ "\t\t"
				+adto.getLicense() + "\t\t"
				+adto.getHopeSal()+ "\t\t"
				+adto.getMotive() + "\t\t"
		);

		System.out.println(sb.toString());


	}

	private void writeRecruit() {
		RecruitDto recruitDto = new RecruitDto();

		System.out.println("---------------------채용공고를 작성 시작합니다.----------------------");

		//근무지역 location_seq Not null
		String loc = reader.getValidatedInput("4. 근무지역을 숫자로 입력하세요(1.서울 2.경기도) :",
				string -> string.trim().equals("1") || string.trim().equals("2"),
				"1, 2만 입력 가능합니다.");
		recruitDto.setLocation_seq(Integer.parseInt(loc));

		//고용형태 employ_type_seq Not null
		String empType = reader.getValidatedInput("5. 근무 전형을 숫자로 입력하세요(0. 계약직  1.정규직): ",
				string -> {
					return string.trim().equals("1") || string.trim().equals("2");
				},
				"1, 2만 입력 가능합니다.");
		recruitDto.setEmploy_type_seq(Integer.parseInt(empType));

		//회사이름 comp_name Not null
		recruitDto.setComp_name(authenticationManager.getLoginDto().getName());


		//공고제목 recruit_title Not null
		String title = reader.getValidatedInput("4. 공고 제목을 입력하세요:",
				string -> !string.isEmpty(),
				"공고 제목을 입력하지 않았습니다. 다시 입력하세요.");
		recruitDto.setRecruit_title(title);

		//우대자격증 license null
		String license = reader.getInputWithPrompt("5. 요구 자격증을 입력하세요(없을시 엔터):");
		recruitDto.setLicense(license);

		//전형 screening Not null
		String screening = reader.getValidatedInput("6. 모집 전형을 입력하세요(예: 면접전형, 필기전형):",
				string -> !string.isEmpty(),
				"모집 전형을 입력하지 않았습니다. 다시 입력하세요.");
		recruitDto.setScreening(screening);

		//직무 position Not null
		String position = reader.getValidatedInput("7. 모집 직무를 입력하세요(예: 개발자, 사무직):",
				string -> !string.isEmpty(),
				"모집 직무을 입력하지 않았습니다. 다시 입력하세요.");

		recruitDto.setPosition(position);

		//채용인원(int반환필요) recruit_num Not null
		String recruitNum = reader.getValidatedInput("8. 채용 인원을 입력하세요:",
				string -> {
					try{
						Integer.parseInt(string);
						return true;
					}catch (NumberFormatException e) {
						return false;
					}
				},
				"올바른 숫자 형식이 아닙니다. 다시 입력하세요.");
		recruitDto.setRecruit_num(Integer.parseInt(recruitNum));

		//요구학력 edu_level null
		String eduLevel = reader.getInputWithPrompt("9. 요구학력을 입력하세요(없을시 엔터):");
		recruitDto.setEdu_level(eduLevel);

		//요구경력 exp_level null
		String expLevel =  reader.getInputWithPrompt("10. 요구경력을 입력하세요(없을시 엔터):");
		recruitDto.setExp_level(expLevel);

		//급여(int반환필요) salary null
		String salary = reader.getValidatedInput("11. 급여를 입력하세요(협의시 엔터):",
				string -> {
					try{
						if(string.isBlank()) {
							return true;
						}

						Integer.parseInt(string);
						return true;
					}catch (NumberFormatException e) {
						return false;
					}
				},
				"올바른 숫자 형식이 아닙니다. 다시 입력하세요.");

		recruitDto.setSalary(salary.isBlank()? 0: Integer.parseInt(salary));

		//모집시작일자(date) recruit_start_date Not null
		String startDate = reader.getValidatedInput("12. 모집 시작 일자를 입력하세요.(yyyy-MM-dd 형식으로 입력하세요) :",
				MyUtil::isTrueDate,
				" 모집 시작 일자를 입력하지 않았거나 유효하지 않은 날짜입니다. 다시 입력하세요.");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			recruitDto.setRecruit_start_date(sdf.parse(startDate));
		} catch (ParseException e) {
			System.out.println("!!!! 잘못된 형식 !!!!");
		}

		//모집마감일자(date) recruit_end_date Not null
		String endDate = reader.getValidatedInput("13. 모집 마감 일자를 입력하세요.(yyyy-MM-dd 형식으로 입력하세요) :",
				MyUtil::isTrueDate,
				" 모집 마감 일자를 입력하지 않았거나 유효하지 않은 날짜입니다. 다시 입력하세요.");
		try {
			recruitDto.setRecruit_end_date(sdf.parse(endDate));
		} catch (ParseException e) {
			System.out.println("!!!! 잘못된 형식 !!!!");
		}

		//합격자발표일(date)  result_date not null
		String resultDate = reader.getValidatedInput("14. 합격자 발표일을 입력하세요.(yyyy-MM-dd 형식으로 입력) :",
				MyUtil::isTrueDate,
				" 모집 마감 일자를 입력하지 않았거나 유효하지 않은 날짜입니다. 다시 입력하세요.");
		try {
			recruitDto.setResult_date(sdf.parse(resultDate));
		} catch (ParseException e) {
			System.out.println("!!!! 잘못된 형식 !!!!");
		}

		//채용담당자이름 recruit_manager_name null
		String managerName = reader.getInputWithPrompt("15. 채용담당자 이름을 입력하세요(없을시 엔터를 누르세요) :");
		recruitDto.setRecruit_manager_name(managerName.trim());

		//채용담당자이메일 recruit_manager_email null
		String managerEmail = reader.getInputWithPrompt("16. 채용담당자 이메일을 입력하세요(없을시 엔터를 누르세요) :");
		recruitDto.setRecruit_manager_email(managerEmail.trim());


		//고용기간(nvarchar2) hire_period null
		String hirePeriod = reader.getInputWithPrompt("16. 고용기간을 입력하세요(없을시 엔터를 누르세요) :");
		recruitDto.setHire_period(hirePeriod.trim());

		int isSuccess = companyService.insertRecruit(recruitDto);

		if(isSuccess == 1) {

			System.out.println("!!채용공고 작성 성공!!");

		} else {
			System.out.println("!!채용공고 작성 실패!!");
		}

	}

	private void updateCompInfo() {

		CompanyDto companyDto = companyService.getCompanyDto(authenticationManager.getLoginDto().getSeq());

		System.out.println("\n>>> 회사정보 수정하기 <<<");

		// 회사 로그인 한 정보의 시퀀스를 가져와서 비밀번호를 가져오고

		// 위의 시퀀스를 이용해 회사에 있는 자료들을 가져온다.
		System.out.println("--------------------------------------");
		System.out.println("[수정전 회사아이디] : " + companyDto.getComp_id());
		System.out.println("[수정전 회사이름] : " + companyDto.getComp_name());
		System.out.println("[수정전 회사이메일] : " + companyDto.getComp_email());
		System.out.println("[수정전 회사규모] : " + companyDto.getComp_scale());
		System.out.println("[수정전 회사설립일] : " + companyDto.getComp_est_date());
		System.out.println("[수정전 회사주소] : " + companyDto.getComp_addr());
		System.out.println("[수정전 회사대표자] : " + companyDto.getComp_ceo());
		System.out.println("[수정전 회사사원수] : " + companyDto.getComp_emp_cnt());
		System.out.println("[수정전 회사자본금] : " + companyDto.getComp_capital());
		System.out.println("[수정전 회사매출액] : " + companyDto.getComp_sales());
		System.out.println("[수정전 회사사대보험여부] : " + companyDto.getComp_insurance());
		System.out.println("--------------------------------------");
		System.out.println("---------------구직자 정보수정-----------------");


		String comp_name = reader.getInputWithPrompt("회사명 입력 [변경하지 않으려면 그냥 엔터] : ");
		if (!comp_name.isBlank()) {
			companyDto.setComp_name(comp_name);
		}

		String comp_email = reader.getInputWithPrompt("사용하실 회사이메일 주소 입력 [변경하지 않으려면 그냥 엔터] : ");
		if (!comp_email.isBlank()) {
			companyDto.setComp_email(comp_email);
		}


		String comp_scale =  reader.getValidatedInput("회사 규모 [변경하지 않으려면 그냥 엔터] : ",
				string -> string.equals("대") || string.equals("중") || string.equals("소") || string.isBlank(),
				"!!!! 올바른 값을 입력하시오 !!!!");
		if (!comp_scale.isBlank()) {
			companyDto.setComp_scale(comp_scale);
		}


		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");
		String comp_est_date = reader.getValidatedInput("설립일 입력 (yyyy/MM/dd) [변경하지 않으려면 그냥 엔터] : ",
				string -> {
					try {
						if(string.isBlank()) {
							return true;
						}
						simpleDateFormat.parse(string);
						return true;
					}catch (ParseException e) {
						return false;
					}

				},"!!!! 올바른 형식을 입력하시오 !!!!");

		try {
			if(!comp_est_date.isBlank()) {
				Date estDate = simpleDateFormat.parse(comp_est_date);
				companyDto.setComp_est_date(estDate);
			}


		}catch (ParseException e) {
			System.out.println("!!!! 잘못된 형식 !!!!");
			return;
		}



		String comp_addr = reader.getInputWithPrompt("회사주소 입력 [변경하지 않으려면 그냥 엔터] : ");
		if (!comp_addr.isBlank()) {
			companyDto.setComp_addr(comp_addr);
		}



		String comp_ceo = reader.getInputWithPrompt("회사 대표 입력 [변경하지 않으려면 그냥 엔터] : ");
		if (!comp_ceo.isBlank()) {
			companyDto.setComp_ceo(comp_ceo);
		}

		String comp_emp_cnt = reader.getValidatedInput("사원수 [변경하지 않으려면 그냥 엔터] : ",
				string -> {
					try{
						if(string.isBlank()) {
							return true;
						}

						Integer.parseInt(string);
						return true;

					}catch (NumberFormatException e) {
						return false;
					}

				}, "!!!! 숫자를 입력하시오 !!!!");
		if (!comp_emp_cnt.isBlank()) {
			companyDto.setComp_emp_cnt(Integer.parseInt(comp_emp_cnt));
		}


		System.out.print("자본금 [변경하지 않으려면 그냥 엔터] : ");
		String comp_capital = reader.getValidatedInput("자본금 [변경하지 않으려면 그냥 엔터] : ",
				string -> {
					try{
						if(string.isBlank()) {
							return true;
						}


						Integer.parseInt(string);
						return true;

					}catch (NumberFormatException e) {
						return false;
					}

				}, "!!!! 숫자를 입력하시오 !!!!");
		if (!comp_capital.isBlank()) {
			companyDto.setComp_capital(Integer.parseInt(comp_capital));
		}


		System.out.print("매출액 [변경하지 않으려면 그냥 엔터] : ");
		String comp_sales = reader.getValidatedInput("매출액 [변경하지 않으려면 그냥 엔터] : ",
				string -> {
					try{
						if(string.isBlank()) {
							return true;
						}


						Integer.parseInt(string);
						return true;

					}catch (NumberFormatException e) {
						return false;
					}

				}, "!!!! 숫자를 입력하시오 !!!!");
		if (!comp_sales.isBlank()) {
			companyDto.setComp_sales(Integer.parseInt(comp_sales));
		}

		String comp_insurance =  reader.getInputWithPrompt("사대보험 가입여부 [변경하지 않으려면 그냥 엔터] : ");
		if (!comp_insurance.isBlank()) {
			companyDto.setComp_insurance(comp_insurance);
		}



		////////////////////////////////////////////////////////////
		String yn = reader.getValidatedInput("▷ 이 정보로 수정 하시겠습니까? [Y/N] : ",
				string -> string.equalsIgnoreCase("y") ||string.equalsIgnoreCase("n"),
				">> Y 또는 N 만 입력하세요!! << \n");

		// 게시판 글쓰기
		if ("y".equalsIgnoreCase(yn)) {
			int result = companyService.updateCompany(companyDto);

			if (result == 1) {
				System.out.println(">>> 회사 정보 수정 성공!! <<<");
			} else if (result == -1) {
				System.out.println(">>> 데이터를 정확하게 입력해주세요!! <<<");
				System.out.println(">> 회사 정보 수정 실패!! <<");
			}else {
				System.out.println(">> 회사 정보 수정 실패!! <<");
			}

		} else if ("n".equalsIgnoreCase(yn)) {
			System.out.println(">> 회사 정보 수정 취소!! <<");
		}

	}

	private void showCompanyInfo() {

		CompanyDto comp = companyService.getCompanyDto(authenticationManager.getLoginDto().getSeq());



		System.out.println("-".repeat(40));
		System.out.println("회사ID\t비밀번호\t회사이름\t회사규모 \t회사주소\t회사이메일\t대표자\t사원수\t매출액\t자본금\t설립일\t사대보험여부\t산업명");
		System.out.println("-".repeat(40));

		StringBuilder sb = new StringBuilder();


		sb.append(comp.getComp_id()).append("\t").
				append(comp.getComp_name()).append("\t").
				append(comp.getComp_scale()).append("\t").
				append(comp.getComp_addr()).append("\t").
				append(comp.getComp_email()).append("\t").
				append(comp.getComp_ceo()).append("\t").
				append(comp.getComp_emp_cnt()).append("\t").
				append(comp.getComp_sales()).append("\t").
				append(comp.getComp_capital()).append("\t").
				append(comp.getComp_est_date()).append("\t").
				append(comp.getComp_insurance()).append("\t").
				append(comp.getIndust_name()).append("\n");

		System.out.println(sb);

	}


	public void showAllEmployee() {
		List<EmployeeDto> employeeList = companyService.getAllEmployeeDtoList();

		if (employeeList.size() > 0) {
			// Print header
			System.out.printf("%-100s%n", "------------------------------------------------------------------------------------------------------");
			System.out.printf("%-16s %-20s %-30s %-12s %-12s %-6s %-15s%n",
					"아이디", "성명", "이메일", "생년월일", "나이", "성별", "전화번호");
			System.out.printf("%-100s%n", "------------------------------------------------------------------------------------------------------");

			// Print each employee data row
			for (EmployeeDto emplo : employeeList) {
				System.out.printf("%-16s %-20s %-30s %-12s %-12s %-6s %-15s%n",
						emplo.getEmp_id(),
						emplo.getEmp_name(),
						emplo.getEmp_email(),
						emplo.getJubun().substring(0, 6),  // Assuming 'jubun' is a String that includes birth date
						emplo.getAge(),
						emplo.getGender(),
						emplo.getEmp_tel());
			}
		} else {
			System.out.println(">>>등록된 구직자가 아직 없습니다.<<<");
		}
	}

	
	private void employeeSearch() {
		String s_menuNo = "";
		
		do {
			////////////////////
			System.out.println("\n ------------- 구직자 검색메뉴 -------------- \n"
							+ "1. 성별검색    2.연령대검색  3.회사메뉴로 돌아가기 \n");
			s_menuNo = reader.getInputWithPrompt("▷ 메뉴번호 선택 : ");
			
			switch (s_menuNo) {
				case "1":
					//hssSearchEmpCtrl.genderSearch(sc);
					searchEmployeeByGender();
					break;
				case "2":
					//leeJungYeonController.searchEmployeeByAgeGroup(sc);
					searchEmployeeByAgeRange();
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

	private void searchEmployeeByAgeRange() {
		System.out.println("--------- 연령대로 구직자 검색 -------" );


		String min = reader.getValidatedInput("검색할 최소 연령대 입력 : ",
				string -> {
					try {
						Integer.parseInt(string);
						return true;
					}catch (NumberFormatException e) {
						return false;
					}
				},
				"!!!! 숫자를 입력하시오 !!!!");

		int minNum = Integer.parseInt(min)/10*10;



		String max = reader.getValidatedInput("검색할 최대 연령대 입력 : ",
				string -> {
					try {
						Integer.parseInt(string);
						return true;
					}catch (NumberFormatException e) {
						return false;
					}
				},
				"!!!! 숫자를 입력하시오 !!!!");

		int maxNum = Integer.parseInt(max)/10*10;

		List<EmployeeDto> searchList = companyService.searchEmployeeByAgeRage(minNum, maxNum);

		System.out.println("seq \t 이름 \t생년월일  \t성별  \t나이   \t 이메일    \t전화번호    \t 주소 ");
		if (!searchList.isEmpty()) {
			for (EmployeeDto employee : searchList) {
				System.out.print(employee.getEmp_seq() + "\t");
				System.out.print(employee.getEmp_name() + "\t");
				System.out.print(employee.getAge() + "\t");
				System.out.print(employee.getGender() + "\t" );
				System.out.print(employee.getEmp_email() + "\t");
				System.out.print(employee.getEmp_tel()+ "\t");
				System.out.print(employee.getEmp_address() + "\n");


			}
		} else {
			System.out.println("해당 범위의 구직자를 찾을 수 없음");
		}

	}

	private void searchEmployeeByGender() {

		String gender = reader.getValidatedInput("▷ 검색하실 성별을 입력하세요(남,여로만 입력하세요) : ",
				string -> string.equals("남") || string.equals("여"),
				"!!!! 올바른 값을 입력하시오 !!!!");

		List<EmployeeDto> empGenderlist = companyService.searchEmployeeByGender(gender);


		if(empGenderlist.size() > 0) {
			System.out.println("-".repeat(50));
			System.out.println("성별\t아이디\t이메일\t이름\t"
					+ "거주지역\t전화번호");
			System.out.println("-".repeat(50));

			StringBuilder sb = new StringBuilder();

			for(EmployeeDto empdto : empGenderlist) {
				sb.append( gender + "\t" + empdto.getEmp_id() + "\t" + empdto.getEmp_email() + "\t" +
						empdto.getEmp_name() + "\t" + empdto.getEmp_address() + "\t" +
						empdto.getEmp_tel() + "\n"
				);
			}
			System.out.println(sb);


		} else {
			System.out.println("검색결과 성별이 " + gender + "인 사원은 없습니다.");
		}

	}

}
