package controller.hss;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import domain.CompanyDto;
import domain.CompanyLoginDto;
import domain.RecruitDto;
import model.CompanyDao;
import model.CompanyDaoImple;
import model.HssCompany.HssCompanyDao;
import model.HssCompany.HssCompanyDaoImple;
import myUtil.*;



public class HssComanyCtrl {
	
	
	private CompanyDao companyDao = new CompanyDaoImple();	
	private HssCompanyDao hssCompanyDao = new HssCompanyDaoImple();
	

	//채용공고 작성하기
	public void writeRecruit(Scanner sc, CompanyLoginDto CompanyLoginDto) {
		RecruitDto RecruitDto = new RecruitDto();
		
		System.out.println("채용공고를 작성 시작합니다.");

		//근무지역 location_seq Not null
	      String loc = "";
	      while(true) {
	    	 System.out.print("1. 근무지역을 숫자로 입력하세요(1.서울 2.경기도) :");
	    	 loc = sc.nextLine();
	         
	         if(loc.trim().equals("1") || loc.trim().equals("2")) {
	        	 RecruitDto.setLocation_seq(Integer.parseInt(loc));
	        	break;
	         }else {
	            System.out.println("1, 2만 입력 가능합니다.");
	         }
	         
	      }
		
		//고용형태 employ_type_seq Not null
	      String empType = ""; // 
	      while(true) {
	         System.out.print("2. 고용형태를 숫자로 입력하세요(0. 계약직  1.정규직): ");
	         empType = sc.nextLine();
	         
	         if(empType.trim().equals("0") || empType.trim().equals("1")) {
	        	 RecruitDto.setEmploy_type_seq(Integer.parseInt(empType));
	            break;
	         }else {
	            System.out.println("0, 1만 입력 가능합니다.");
	         }
	         
	      }      
		
		//회사이름 comp_name Not null		
		String compName = "";
		while(true) {
			System.out.print("3. 회사이름을 입력하세요:");
			compName = sc.nextLine();
			if(!compName.isBlank()) {
				RecruitDto.setComp_name(compName);
				break;	
			} else {
				System.out.println("회사이름을 입력하지 않았습니다. 다시 입력하세요.");
			}
		}

		//공고제목 recruit_title Not null		
		String title = "";
		while(true) {
			System.out.print("4. 공고 제목을 입력하세요:");
			title = sc.nextLine();
			if(!title.isBlank()) {
				RecruitDto.setRecruit_title(title);
				break;	
			} else {
				System.out.println("공고 제목을 입력하지 않았습니다. 다시 입력하세요.");
			}
		}
		
		//우대자격증 license null 
		String license = "";
		System.out.print("5. 요구 자격증을 입력하세요(없을시 엔터):");
		license = sc.nextLine();
		RecruitDto.setLicense(license);

		//전형 screening Not null
		String screening = "";
		while(true) {
			System.out.print("6. 모집 전형을 입력하세요(예: 면접전형, 필기전형):");
			screening = sc.nextLine();
			if(!screening.isBlank()) {
				RecruitDto.setScreening(screening);
				break;	
			} else {
				System.out.println("모집 전형을 입력하지 않았습니다. 다시 입력하세요.");
			}
		}
		
		//직무 position Not null
		String position = "";
		while(true) {
			System.out.print("7. 모집 직무를 입력하세요(예: 개발자, 사무직):");
			position = sc.nextLine();
			if(!position.isBlank()) {
				RecruitDto.setPosition(position);
				break;	
			} else {
				System.out.println("모집 직무를 입력하지 않았습니다. 다시 입력하세요.");
			}
		}		
				
		//채용인원(int반환필요) recruit_num Not null
		String recruitNum = "";
		while(true) {
			System.out.print("8. 채용 인원을 입력하세요:");
			recruitNum = sc.nextLine();
			if(!recruitNum.isBlank()) {
				try {
					RecruitDto.setRecruit_num(Integer.parseInt(recruitNum));
					break;
				} catch(NumberFormatException e) { //문자 입력했을 경우
					System.out.println("숫자로 입력하세요.");
				}
			} else {
				System.out.println("채용 인원을 입력하지 않았습니다. 다시 입력하세요.");
			}
		}		
				
		//요구학력 edu_level null
		String eduLevel = "";
		System.out.print("9. 요구학력을 입력하세요(없을시 엔터):");
		eduLevel = sc.nextLine();
		RecruitDto.setEdu_level(eduLevel);
				
		//요구경력 exp_level null 
		String expLevel = "";
		System.out.print("10. 요구경력을 입력하세요(없을시 엔터):");
		expLevel = sc.nextLine();
		RecruitDto.setExp_level(expLevel);
		
		//급여(int반환필요) salary null
		String salary = "";
		while(true) {
			System.out.print("11. 급여를 입력하세요(협의시 엔터):");
			salary = sc.nextLine();
			if(!salary.isBlank()) {
				try {
					RecruitDto.setSalary(Integer.parseInt(salary));
					break;
				} catch(NumberFormatException e) {
					 System.out.println("올바른 숫자 형식이 아닙니다. 다시 입력하세요.");
				}
			} else { //엔터를 눌렀을경우
				 RecruitDto.setSalary(0); 
			     break;
			}
		}
		
		//모집시작일자(date) recruit_start_date Not null
		String startDate = "";
		while(true) {
			System.out.print("11. 모집 시작 일자를 입력하세요.(yyyy-MM-dd 형식으로 입력하세요) :");
			startDate = sc.nextLine();
			if(!startDate.isBlank() && MyUtil.isTrueDate(startDate)) {
				RecruitDto.setRecruit_start_date(startDate);
				break;	
			} else {
				System.out.println(" 모집 시작 일자를 입력하지 않았거나 유효하지 않은 날짜입니다. 다시 입력하세요.");
			}
		}	
		
		//모집마감일자(date) recruit_end_date Not null
		String endDate = "";
		while(true) {
			System.out.print("12. 모집 마감 일자를 입력하세요.(yyyy-MM-dd 형식으로 입력하세요) :");
			endDate = sc.nextLine();
			if(!endDate.isBlank() && MyUtil.isTrueDate(endDate)) {
				RecruitDto.setRecruit_end_date(endDate);
				break;	
			} else {
				System.out.println("모집 마감 일자를 입력하지 않았거나 유효하지 않은 날짜입니다. 다시 입력하세요.");
			}
		}	
		

		//합격자발표일(date)  result_date not null
		String resultDate = "";
		while(true) {
		System.out.print("13. 합격자 발표일을 입력하세요.(yyyy-MM-dd 형식으로 입력) :");
		resultDate = sc.nextLine();
		if(!resultDate.isBlank() && MyUtil.isTrueDate(resultDate)) {
			RecruitDto.setResult_date(resultDate);
			break;
		} else {
			System.out.println("합격자 발표일자를 입력하지 않았거나 유효하지 않은 날짜입니다. 다시 입력하세요.");
			}
		}
		
		//채용담당자이름 recruit_manager_name null
		String managerName = "";
		System.out.print("14. 채용담당자 이름을 입력하세요(없을시 엔터를 누르세요) :");
		managerName = sc.nextLine();
		RecruitDto.setRecruit_manager_name(managerName);
		
		//채용담당자이메일 recruit_manager_email null
		String managerEmail = "";
		System.out.print("15. 채용담당자 이메일을 입력하세요(없을시 엔터를 누르세요) :");
		managerEmail = sc.nextLine();
		RecruitDto.setRecruit_manager_email(managerEmail);
		
		
		//고용기간(nvarchar2) hire_period null
		String hirePeriod = "";
		System.out.print("16. 고용기간을 입력하세요(없을시 엔터를 누르세요) :");
		hirePeriod = sc.nextLine();
		RecruitDto.setHire_period(hirePeriod);
	
		int isSuccess = hssCompanyDao.insertRecruit(RecruitDto, CompanyLoginDto);
		
		if(isSuccess == 1) {

			System.out.println("!!채용공고 작성 성공!!");
			
		} else {
			System.out.println("!!채용공고 작성 실패!!");
		}

	}// 채용공고 작성하기 끝
	


	//회사 아이디,비번찾기 메뉴 띄우기
	public void compIdPasswdSearch(Scanner sc) {
		
		String menuNo = "";
		
		do {
			
			System.out.println("----------------------------------------------");
			System.out.println("   1. 아이디 찾기    2. 비밀번호 찾기    3. 나가기");
			System.out.println("----------------------------------------------");
			
			System.out.print("메뉴번호 선택 : ");
			menuNo = sc.nextLine();
		
			switch(menuNo) {
				case "1": 
					compIdSearch(sc);
					break;
				case "2":
					compPswdSearch(sc);
					break;
				case "3":
					break;
				default:
					System.out.println("-----------!!올바른 메뉴번호를 입력해 주세요!!!-----------");
					break;
			}
			
		}while(!"3".equals(menuNo));
	}



	// 회사 아이디 찾기
	private void compIdSearch(Scanner sc) {
		CompanyDto company = null;
		System.out.print("이름을 입력하세요. : ");
		String comp_name = sc.nextLine();
		System.out.print("이메일을 입력하세요. : ");
		String comp_email = sc.nextLine();
		
		Map<String,String> paramap = new HashMap<>();
		paramap.put("comp_name", comp_name);
		paramap.put("comp_email", comp_email);
		
		company = hssCompanyDao.compIdSearch(paramap);
		if(company == null) {
			System.out.println("\n >>> 가입하지 않은 회원입니다. <<< ");
			return;
		}
		System.out.println("고객님의 아이디는 " + company.getComp_id() + "입니다.");
		
	}
	
	//회사 비밀번호찾기 -> 비밀번호 바꾸기 -> 현재암호랑 똑같으면 안됨!!
	private void compPswdSearch(Scanner sc) {
		
		CompanyLoginDto company = new CompanyLoginDto();
		System.out.print("이름을 입력하세요. : ");
		String comp_name = sc.nextLine();
		System.out.print("아이디를 입력하세요. : ");
		String comp_id = sc.nextLine();
		
		Map<String,String> paraMap = new HashMap<>();
		paraMap.put("comp_name", comp_name);
		paraMap.put("comp_id", comp_id);
		
		company = hssCompanyDao.compPswdSearch(paraMap);

		// 통과했을경우 
		if(company != null) {
			
			boolean isPass = true;// true로 시작
			String new_passwd = "";
			String new_checkPasswd = "";
			do {
				System.out.print("새로운 비밀번호를 입력하세요. : ");
				new_passwd = sc.nextLine();
				
				System.out.println("새로운 비밀번호를 한번 더 입력하세요. (위와 일치해야 함): ");
				new_checkPasswd = sc.nextLine();
			
				if(new_passwd.equals(new_checkPasswd)) {
					isPass = true;
					Map<String,String> newParaMap = new HashMap<>();
					newParaMap.put("new_passwd", new_passwd); // 새로운 패스워드
					newParaMap.put("comp_id", comp_id); // 사용자가 위에 입력한 아이디
					List<String> pswdList = hssCompanyDao.compNewPswdCheck(newParaMap); //최근 변경한 3개중 있는지 조회하는 메소드
					
					if(pswdList.size() > 0) {
						for(int i = 0; i < pswdList.size(); i++) {
							if(pswdList.get(i).equals(new_passwd)) {
								isPass = false; //실패
								System.out.println("최근 사용한 비밀번호입니다. 다시 입력하세요!");
							}
						}
					}
					if(isPass) { //true라면
						int result = hssCompanyDao.compNewPswdSet(newParaMap);
						if(result == 1) {
							System.out.println("변경 성공");
							isPass = true;
						} else {
							System.out.println("변경 실패");
						
						}
					}
					
				} else {
					System.out.println("비밀번호가 일치하지 않거나 최근에 사용한 비밀번호입니다. 다시 입력하세요.");
					isPass = false;
				}
				
			}while(!isPass);

		} else {
			System.out.println("\n >>> 비밀번호 찾기에 실패했습니다. <<< ");
			return;
		}
	}
}
