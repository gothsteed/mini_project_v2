package controller.ksj;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

import domain.EmployeeDto;
import domain.EmployeeLoginDto;
import domain.RecruitEntryDto;
import model.ksj.EmployeeDaoImple_ksj;
import model.ksj.EmployeeDao_ksj;

public class EmployeeController_ksj {
	EmployeeDao_ksj edao_ksj = new EmployeeDaoImple_ksj();
	EmployeeDto employeeDto = new EmployeeDto(); 
	EmployeeLoginDto employeeLoginDto = new EmployeeLoginDto();

	///////////////////////////////////////////////////////////////////////////
	// 메소드 //

	// 2. 구직자정보 수정 //
	public void update_emp_info(Scanner sc, int emp_seq) {
		int result = 0;

		System.out.println("\n>>> 구직자정보 수정하기 <<<");

		employeeLoginDto = edao_ksj.viewContents(emp_seq);
		// 회사 로그인 한 정보의 시퀀스를 가져와서 비밀번호를 가져오고

		// 위의 시퀀스를 이용해 회사에 있는 자료들을 가져온다.
		System.out.println("--------------------------------------");
		System.out.println("[수정전 구직자아이디] : " + employeeLoginDto.getEmp_id());
		System.out.println("[수정전 구직자비밀번호] : " + employeeLoginDto.getEmp_password());
		System.out.println("[수정전 구직자이름] : " + employeeLoginDto.getEmp_name());
		System.out.println("[수정전 구직자이메일] : " + employeeLoginDto.getEmployeeDto().getEmp_email());
		System.out.println("[수정전 구직자주민번호] : " + employeeLoginDto.getEmployeeDto().getJubun());
		System.out.println("[수정전 구직자주소] : " + employeeLoginDto.getEmployeeDto().getEmp_address());
		System.out.println("[수정전 구직자전화번호] : " + employeeLoginDto.getEmployeeDto().getEmp_tel());
		System.out.println("--------------------------------------");

		System.out.println("---------------구직자 정보수정-----------------");

		employeeDto.setEmp_seq(emp_seq);
		employeeLoginDto.setEmp_seq(emp_seq);


	      System.out.print("사용하실 비밀번호 입력 [변경하지 않으려면 그냥 엔터] : ");
	      String emp_password = sc.nextLine();
	      if (employeeLoginDto.getEmp_password() != null && emp_password.isBlank()) {
	         emp_password = employeeLoginDto.getEmp_password();
	      }
	      employeeLoginDto.setEmp_password(emp_password);

	      System.out.print("이름 입력 [변경하지 않으려면 그냥 엔터] : ");
	      String emp_name = sc.nextLine();
	      if (employeeLoginDto.getEmp_name() != null && emp_name.isBlank()) {
	         emp_name = employeeLoginDto.getEmp_name();
	      }
	      employeeDto.setEmp_name(emp_name);
	      employeeLoginDto.setEmp_name(emp_name);

	      System.out.print("사용하실 이메일 주소 입력 [변경하지 않으려면 그냥 엔터] : ");
	      String emp_email = sc.nextLine();
	      if (employeeLoginDto.getEmployeeDto().getEmp_email() != null && emp_email.isBlank()) {
	         emp_email = employeeLoginDto.getEmployeeDto().getEmp_email();
	      }
	      employeeDto.setEmp_email(emp_email);

	      System.out.print("거주지 입력 [변경하지 않으려면 그냥 엔터] : ");
	      String emp_addr = sc.nextLine();
	      if (employeeLoginDto.getEmployeeDto().getEmp_address() != null && emp_addr.isBlank()) {
	         emp_addr = employeeLoginDto.getEmployeeDto().getEmp_address();
	      }
	      employeeDto.setEmp_address(emp_addr);

	      System.out.print("전화번호 입력 [변경하지 않으려면 그냥 엔터] : ");
	      String emp_tel = sc.nextLine();
	      if (employeeLoginDto.getEmployeeDto().getEmp_tel() != null && emp_tel.isBlank()) {
	         emp_tel = employeeLoginDto.getEmployeeDto().getEmp_tel();
	      }
	      employeeDto.setEmp_tel(emp_tel);

	      employeeLoginDto.setEmployeeDto(employeeDto);
	      

	      do {
	         ////////////////////////////////////////////////////////////
	         System.out.print("▷ 이 정보로 수정 하시겠습니까? [Y/N] : ");
	         String yn = sc.nextLine();

	         // 게시판 글쓰기
	         if ("y".equalsIgnoreCase(yn)) {
	            result = edao_ksj.update_emp_info(employeeLoginDto);
	            if (result == 1) {
	               System.out.println(">>> 구직자 정보 수정 성공!! <<<");
	            } else if (result == -1) {
	               System.out.println(">>> 데이터를 정확하게 입력해주세요!! <<<");
	               System.out.println(">> 구직자 정보 수정 실패!! <<");
	            }
	            break;

	         } else if ("n".equalsIgnoreCase(yn)) {
	            System.out.println(">> 구직자 정보 수정 취소!! <<");
	            break;
	         } else {
	            System.out.println(">> Y 또는 N 만 입력하세요!! << \n");
	         }
	      } while (true);
	   }
	   // 구직자 정보수정 끝 //

	
	
	
	// 구직자 회원탈퇴 //
	public int drop_Member(Scanner sc, int emp_seq) {
		int result = 0;

		do {
			////////////////////////////////////////////////////////////
			System.out.print("▷ 정말로 회원탈퇴 하시겠습니까? [Y/N] : ");
			String yn = sc.nextLine();
			// 게시판 글쓰기
			if ("y".equalsIgnoreCase(yn)) {
				result = edao_ksj.drop_Member(emp_seq);
				if (result == 1) {
					System.out.println(">>> 구직자 회원탈퇴 성공!! <<<");
					break;
				} else if (result == -1) {
					System.out.println(">>> 데이터를 정확하게 입력해주세요!! <<<");
					System.out.println(">> 구직자 회원탈퇴 실패!! <<");
				}
				break;

			} else if ("n".equalsIgnoreCase(yn)) {
				System.out.println(">> 구직자 회원탈퇴 취소!! <<");
				break;

			} else {
				System.out.println(">> Y 또는 N 만 입력하세요!! << \n");
				break;
			}
		} while (true);
		
		return result;
	}
	// 구직자 회원탈퇴 끝 //
	
	
	// 8. 채용응모한것조회 //
	public void JobApplicationInquiry(Scanner sc, int emp_seq) {
		
		List<RecruitEntryDto> RecruitEntryList = edao_ksj.JobApplicationInquiry(emp_seq);
		
		System.out.println(RecruitEntryList.size());
		
		if (RecruitEntryList.size() > 0) {
			System.out.println("\n회사이름\t\t공고제목\t전형\t직무\t우대자격증\t채용인원\t요구학력\t요구경력\t급여\t모집시작일자\t\t모집마감일자\t\t합격자 발표일\t\t채용담당자이름\t채용담당자이메일\t\t고용기간\t\t응모날짜\t\t\t합격여부");
			System.out.println("-".repeat(250));
			
			StringBuilder sb = new StringBuilder();
			
			for(RecruitEntryDto recruitEntryDto : RecruitEntryList) {
				
				sb.append(recruitEntryDto.getRecruitDto().getComp_name() + "\t\t"
						+ recruitEntryDto.getRecruitDto().getRecruit_title()+ "\t"
						+ recruitEntryDto.getRecruitDto().getScreening() + "\t"
						+ recruitEntryDto.getRecruitDto().getPosition() + "\t"
						+ recruitEntryDto.getRecruitDto().getLicense() + "\t"
						+ recruitEntryDto.getRecruitDto().getRecruit_num() + "\t"
						+ recruitEntryDto.getRecruitDto().getEdu_level() + "\t"
						+ recruitEntryDto.getRecruitDto().getExp_level() + "\t"
						+ recruitEntryDto.getRecruitDto().getSalary() + "\t"
						+ recruitEntryDto.getRecruitDto().getRecruit_start_date() + "\t"
						+ recruitEntryDto.getRecruitDto().getRecruit_end_date() + "\t"
						+ recruitEntryDto.getRecruitDto().getResult_date() + "\t"
						+ recruitEntryDto.getRecruitDto().getRecruit_manager_name() + "\t"
						+ recruitEntryDto.getRecruitDto().getRecruit_manager_email() + "\t"
						+ recruitEntryDto.getRecruitDto().getHire_period() + "\t"
						+ recruitEntryDto.getEntry_date() + "\t"
						+ recruitEntryDto.getPass_status() + "\n");
				
			} // end of for-----------------------------
			
			
			System.out.println(sb.toString());
		}
		else {
			System.out.println("응모하신 회사가 없습니다.");
		}
		
		
		
		
	}
	// 8. 채용응모한것조회 끝 // 
	
	
	public void showApplicationCareer(Scanner sc, EmployeeLoginDto employee) {
		System.out.println("------ 이력서 조회 -------");
//		StringBuilder sb1 = new StringBuilder();

		List<Map<String, String>> applicationDtoList = edao_ksj.viewApplication(employee.getEmp_seq());

		if (applicationDtoList.size() > 0) {

			System.out.println("\n이력서번호\t이름\t이메일\t전화번호\t사는지역\t희망고용형태\t병역여부\t희망연봉\t지원동기");
			System.out.println("-".repeat(250));

			StringBuilder sb = new StringBuilder();


			for (Map<String, String> appliMap : applicationDtoList) {

				sb.append(appliMap.get("application_seq") + "\t" + appliMap.get("emp_name") + "\t" + appliMap.get("emp_email") + "\t" +
						appliMap.get("emp_tel") + "\t" + appliMap.get("location_name") + "\t" + appliMap.get("employ_type_name") + "\t" +
						appliMap.get("military_status") + "\t" + appliMap.get("license") + "\t" + appliMap.get("hope_sal") + "\t" + 
						appliMap.get("motive") + "\n"
						);

			} // end of for-----------------------------

			System.out.println(sb.toString()); // 찍음 

			String appNum = ""; // 이력서 번호
			
			while(true) {
				try {
					System.out.println("> 학력을 조회하고 싶은 이력서 번호를 입력하세요(학력조회를 하고 싶지 않을 경우 엔터): ");
					appNum = sc.nextLine();
		
					if(!appNum.isBlank()) {
						List<Map<String, String>> eduMapList = edao_ksj.showApplicationEdu(Integer.parseInt(appNum), employee.getEmp_seq()); // 사용자가 입력한 이력서 번호를 매개변수로 제공
						if(eduMapList.size() > 0) {
								System.out.println("\n이력서번호\t이름\t이메일\t전화번호\t사는지역\t희망고용형태\t병역여부\t희망연봉\t지원동기\t학교명\t학과명");
								System.out.println("-".repeat(250));
				
								sb = new StringBuilder();
								
								
								for (Map<String, String> eduMap : eduMapList) {
									sb.append(eduMap.get("application_seq") + "\t" + eduMap.get("emp_name") + "\t" +
											eduMap.get("emp_email") + "\t" + eduMap.get("emp_tel") + "\t" + eduMap.get("location_name") + "\t" +
											eduMap.get("employ_type_name") + "\t" + eduMap.get("military_status") + "\t" + eduMap.get("license") + "\t" +
											eduMap.get("hope_sal") + "\t" + eduMap.get("motive") + "\t" + eduMap.get("school_name") + "\t" +
											eduMap.get("department_name") + "\n");
					
								} 
							System.out.println(sb.toString());
							break;
							
						} else {
							System.out.println("해당 이력서에는 작성된 학력이 없습니다.");
						}
					}   else { //비어 있지 않다면 
						break; //탈출
					}
				} catch (NumberFormatException e) {
					 System.out.println("올바른 숫자 형식이 아닙니다. 다시 입력하세요.");
				}
		} // end of while(true) -----------------------

		while(true) {
			try {
				System.out.println("> 경력을 조회하고 싶은 이력서 번호를 입력하세요(경력조회를 하고 싶지 않을 경우 엔터): ");
				appNum = sc.nextLine();
				
				//경력조회 시작~~~ 
				if(!appNum.isBlank()) {
					List<Map<String, String>> carMapList = edao_ksj.showApplicationCareer(Integer.parseInt(appNum), employee.getEmp_seq()); // 사용자가 입력한 이력서 번호를 매개변수로 제공
					if(carMapList.size() > 0) {
							System.out.println("\n이력서번호\t이름\t이메일\t전화번호\t사는지역\t희망고용형태\t병역여부\t희망연봉\t지원동기\t직무명\t경력시작\t경력끝");
							System.out.println("-".repeat(250));
			
							sb = new StringBuilder();
							
							
							for (Map<String, String> carMap : carMapList) {
								sb.append(carMap.get("application_seq") + "\t" + carMap.get("emp_name") + "\t" +
										carMap.get("emp_email") + "\t" + carMap.get("emp_tel") + "\t" + carMap.get("location_name") + "\t" +
										carMap.get("employ_type_name") + "\t" + carMap.get("military_status") + "\t" + carMap.get("license") + "\t" +
										carMap.get("hope_sal") + "\t" + carMap.get("motive") + "\t" + carMap.get("position") + "\t" +
										carMap.get("career_start_date") + "\t" + carMap.get("career_end_date") + "\n");
				
							} 
							System.out.println(sb.toString());
							break;
						
					} else {
						System.out.println("해당 이력서에는 작성된 경력이 없습니다.");
					}
					
				} else { //비어 있지 않다면 
					break; //탈출
				}
		} catch (NumberFormatException e) {
			 System.out.println("올바른 숫자 형식이 아닙니다. 다시 입력하세요.");
		}
			
	}
			
			
	} else {
		System.out.println("작성하신 이력서가 없습니다.");
	}
	// 이력서의 경력 자세히 보기 끝 //

	}
	
	
	
	
	
	

}
