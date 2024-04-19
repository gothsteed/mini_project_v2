package controller.leeJungYeon;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import controller.jzee.Zee_CompanyController;
import domain.ApplicationDto;
import domain.CareerDto;
import domain.CompanyDto;
import domain.CompanyLoginDto;
import domain.EducationDto;
import domain.EmployeeDto;
import domain.EmployeeLoginDto;
import domain.RecruitDto;
import domain.RecruitEntryDto;
import model.leeJungYeon.MyDaoImple;
import myUtil.MyUtil;
import model.jzee.Zee_CompanyDao;
import model.jzee.Zee_CompanyDao_imple;
import model.jzee.Zee_EmployeeDao;
import model.jzee.Zee_EmployeeDao_imple;
import model.kdh.CompanyDaoImple_kdh;
import model.kdh.CompanyDao_kdh;
import model.kdh.EmployeeDaoImple_kdh;
import model.kdh.EmployeeDao_kdh;
import model.leeJungYeon.MyDao;
import oracle.net.aso.a;

public class LeeJungYeonController {
	
	MyDao myDao = new MyDaoImple();
	CompanyDao_kdh companyDao_kdh = new CompanyDaoImple_kdh();
	EmployeeDao_kdh edao_ksj = new EmployeeDaoImple_kdh();
	
	Zee_CompanyDao cdao = new Zee_CompanyDao_imple();
	Zee_EmployeeDao edao = new Zee_EmployeeDao_imple();
	
	
	
	//TODO : 트랜젝션 처리!!!!
	public void edit_application(Scanner sc, EmployeeLoginDto employeeDto) {
		
		System.out.println("------------ 이력서 수정 -------------");
		
		int result = 0;
		
		try {
			
			System.out.println("수정할 이력서 번호를 입력하시오: ");
			int application_seq = Integer.parseInt(sc.nextLine());
			
			ApplicationDto myApplicationDto = myDao.viewApplication(application_seq);
			
			
			String loc = "";
			
			if(myApplicationDto != null && employeeDto.getEmp_seq() == myApplicationDto.getEmp_seq()) {
				
				while(true) {
					System.out.println("현재 희망 근무 지역: " + myApplicationDto.getLocation_seq());
					System.out.println("수정할 희망 근무 지역 입력(변경 안할시 엔터) : ");
					System.out.println("1. 서울\t 2.경기도");
					
					loc = sc.nextLine();
					
					if(loc.trim().equals("1") || loc.trim().equals("2") || loc.trim().equals("")) {
						break;
					}else {
						System.out.println("똑바로 입력하시오");
					}
					
				}
				
				if(!loc.trim().equals("")) {
					myApplicationDto.setLocation_seq(Integer.parseInt(loc));
				}
				
				
				String empType = "";
				while(true) {
					System.out.println("현재 희망 근무 타입: " + myApplicationDto.getEmploy_type_seq());
					System.out.println("수정할 희망 근무 타입(변경 안할시 엔터) : ");
					System.out.println("0. 계약직\t 1.정규직");
					
					empType = sc.nextLine();
					
					if(empType.trim().equals("0") || empType.trim().equals("1") || empType.trim().equals("")) {
						break;
					}else {
						System.out.println("똑바로 입력하시오");
					}
					
				}
				if(!empType.trim().equals("")) {
					myApplicationDto.setEmploy_type_seq(Integer.parseInt(empType));
				}
				
				
				String mil = "";
				while(true) {
					System.out.println("현재 병역 상태: " + myApplicationDto.getMilitary_seq());
					System.out.println("수정할 병역상태(변경 안할시 엔터) : ");
					System.out.println("0. 미필\t 1.군필 \t 2.면제 \t 3.복무중" );
					
					mil = sc.nextLine();
					
					if(mil.trim().equals("0") || mil.trim().equals("1") ||  mil.trim().equals("2") ||  mil.trim().equals("3") ||  mil.trim().equals("")) {
						break;
					}else {
						System.out.println("똑바로 입력하시오");
					}
					
				}
				if(!mil.trim().equals("")) {
					myApplicationDto.setMilitary_seq(Integer.parseInt(mil));
				}
				
				String license = "";

				System.out.println("현재 자격증: " + myApplicationDto.getLicense());
				System.out.println("수정할 자격증(변경 안할시 엔터) : ");
				license  = sc.nextLine();
				if(!license.trim().equals("")) {
					myApplicationDto.setLicense(license);
				}
				
				
				String motive = "";

				System.out.println("현재 자기소개: " + myApplicationDto.getMotive());
				System.out.println("수정할 자기소개(변경 안할시 엔터) : ");
				motive  = sc.nextLine();
				if(!motive.trim().equals("")) {
					myApplicationDto.setMotive(motive);
				}
				
				
				int n = myDao.update_application(myApplicationDto);
				
				if(n == 1) {
					
					System.out.println("!!이력서 수정 성공!!");
					
				}

				
				List<CareerDto> careerList = myDao.getCareerList(application_seq);
				
				for(int i = 0; i < careerList.size(); i++) {
		            CareerDto career = careerList.get(i);
		            System.out.println(career);
		            System.out.print("수정하세겠습니까? (Y/N): ");
		            
		            String affirm = "";
		            
		            while (!(affirm.equalsIgnoreCase("y") || affirm.equalsIgnoreCase("n"))) {
		            	
		            	affirm = sc.nextLine();
		            	
			            if(affirm.equalsIgnoreCase("y")) {
			                System.out.print("Enter new position (press enter to pass): ");
			                String position = sc.nextLine();
			                if(!position.trim().equals("")) {
			                	career.setPosition(position);
			                }
			                
			                System.out.print("Enter new career start date (YYYY/MM/DD)(press enter to pass): ");
			                String startDate = sc.nextLine();
			                if(!startDate.trim().equals("")) {
			                	career.setCareerStartDate(startDate);
			                }
			                
			                
			                System.out.print("Enter new career end date (YYYY/MM/DD) (press enter to pass): ");
			                String endDate = sc.nextLine();
			                if(!endDate.trim().equals("")) {
			                	career.setCareerEndDate(endDate);
			                }
			                

			                
			                int updateResult = myDao.updateCareer(career);
			                
			                if(updateResult == 1) {
			                	System.out.println("Career updated: " + career);
			                }
			                
			                
			   
			            } else if(affirm.equalsIgnoreCase("n")) {
			                continue; // 다음 항목으로 넘어갑니다.
			            } else {
			                System.out.println("잘못된 입력입니다. Y 또는 N을 입력해주세요.");
			            }
		            	
		            	
		            	
		            }

		        }
				
				
				


				
				List<EducationDto> educationList = myDao.getEducationList(application_seq);
				
				for(int i = 0; i < educationList.size(); i++) {
					EducationDto education = educationList.get(i);
					System.out.println("현재 학력 정보: ");
					System.out.println("학교 이름: " + education.getSchoolName());
					System.out.println("학과 이름: " + education.getDepartmentName());
					System.out.print("수정하세겠습니까? (Y/N): ");
		            
		            String affirm =  "";
		            
		            while (!(affirm.equalsIgnoreCase("y") || affirm.equalsIgnoreCase("n"))) {
		            	
		            	affirm = sc.nextLine();
		            	
		            	if(affirm.equalsIgnoreCase("y")) {
			                System.out.print("새 학교 이름을 입력하세요 (press enter to pass): ");
			                String newSchoolName = sc.nextLine();
			                if(!newSchoolName.trim().equals("")) {
			                	education.setSchoolName(newSchoolName);
			                }
			                
			                
			                System.out.print("새 학과 이름을 입력하세요 (press enter to pass): ");
			                String newDepartmentName = sc.nextLine();
			                if(!newDepartmentName.trim().equals("")) {
			                	education.setDepartmentName(newDepartmentName);
			                }

			                
			                int updateResult = myDao.updateEducation(education);
			                
			                if(updateResult == 1) {
			 
			                	System.out.println("학력 정보가 업데이트되었습니다 (press enter to pass): " + education);
			                }
			                
			                
			            } else if(affirm.equalsIgnoreCase("n")) {
			                continue; // 다음 항목으로 넘어갑니다.
			            } else {
			                System.out.println("잘못된 입력입니다. Y 또는 N을 입력해주세요.");
			            }
		            	
		            }

		        }

				
			}else {
				System.out.println("!!입력하신 이력서는 존재하지 않습니다!!");
				
			}
			
			
			
		}catch (NumberFormatException e) {
			System.out.println("!!숫자만 입력하시오!!");
		}

	}
	
	public void edit_recruit(Scanner sc, CompanyLoginDto companyDto) {
	    System.out.println("------ 이력서 수정 ------");

	    try {
	        System.out.println("수정할 공고 번호를 입력하시오: ");
	        int recruit_seq = Integer.parseInt(sc.nextLine());
	        RecruitDto recruitDto = myDao.getRecruit(recruit_seq);

	        if(recruitDto != null && recruitDto.getComp_seq() == companyDto.getComp_seq()) {


	            System.out.println("회사 이름을 입력하세요: ");
	            recruitDto.setComp_name(sc.nextLine());

	            System.out.println("공고 제목을 입력하세요: ");
	            recruitDto.setRecruit_title(sc.nextLine());

	            System.out.println("필요한 자격증을 입력하세요: ");
	            recruitDto.setLicense(sc.nextLine());

	            System.out.println("선별 과정을 입력하세요: ");
	            recruitDto.setScreening(sc.nextLine());

	            System.out.println("직위를 입력하세요: ");
	            recruitDto.setPosition(sc.nextLine());
	            
	            while(true) {
	            	
	            	try {
	            		System.out.println("모집 인원을 입력하세요: ");
	            		recruitDto.setRecruit_num(Integer.parseInt(sc.nextLine()));
	            		
	            		break;
	            	}catch (NumberFormatException e) {
						System.out.println("!!숫자만 입력하시오!!");
					}
	            	
	            }
	            
	            System.out.println("학력 요건을 입력하세요: ");
	            recruitDto.setEdu_level(sc.nextLine());

	            System.out.println("경력 요건을 입력하세요: ");
	            recruitDto.setExp_level(sc.nextLine());
	            
	            
	            while(true) {
	            	
	            	try {
	            		
	    	            System.out.println("급여를 입력하세요: ");
	    	            recruitDto.setSalary(Integer.parseInt(sc.nextLine()));
	            		
	            		break;
	            	}catch (NumberFormatException e) {
	            		
						System.out.println("!!숫자만 입력하시오!!");
						
					}
	            	
	            }
	            

	            //TODO:유효성 검사//////////////////////////////
	            
	            while(recruitDto.getRecruit_start_date().equals("")) {
	            	System.out.println("공고 시작 날짜를 입력하세요 (yyyy-MM-dd): ");
	            	String temp = sc.nextLine();
	            	if(MyUtil.isTrueDate(temp)) {
	            		recruitDto.setRecruit_start_date(temp);
	            		break;
	            	}else {
	            		
	            		System.out.println("!!올바른 날짜를 입력하십쇼!!");
	            	}
	            	
	            }
	            
	            
	            while(recruitDto.getRecruit_end_date().equals("")) {
	            	System.out.println("공고 종료 날짜를 입력하세요 (yyyy-MM-dd): ");
	            	String temp = sc.nextLine();
	            	if(MyUtil.isTrueDate(temp)) {
	            		recruitDto.setRecruit_end_date(temp);
	            		break;
	            	}else {
	            		
	            		System.out.println("!!올바른 날짜를 입력하십쇼!!");
	            	}
	            	
	            }
	            
	            
	            while(recruitDto.getResult_date().equals("")) {
	            	System.out.println("결과 발표 날짜를 입력하세요 (yyyy-MM-dd): ");
	            	String temp = sc.nextLine();
	            	if(MyUtil.isTrueDate(temp)) {
	            		recruitDto.setResult_date(temp);
	            		break;
	            	}else {
	            		
	            		System.out.println("!!올바른 날짜를 입력하십쇼!!");
	            	}
	            	
	            }
	            /////////////////////////////////////////////////////////

	            System.out.println("채용 담당자 이름을 입력하세요: ");
	            recruitDto.setRecruit_manager_name(sc.nextLine());

	            //TODO:유효성 검사//////////////////////////////
	            while(recruitDto.getRecruit_manager_email().equals("")) {
	            	System.out.println("이메일를 입력하시오: ");
	            	String temp = sc.nextLine();
	            	if(MyUtil.isTrueEmail(temp)) {
	            		recruitDto.setRecruit_manager_email(temp);
	            		break;
	            	}else {
	            		
	            		System.out.println("!!올바른 이메일을 입력하십쇼!!");
	            	}
	            	
	            }

	            
	            System.out.println("고용 기간을 입력하세요: ");
	            recruitDto.setHire_period(sc.nextLine());

	            int result = myDao.updateRecruit(recruitDto);
	            
	            
	            if(result !=  0) {
	                System.out.println("공고 정보가 성공적으로 수정되었습니다.");
	            } else {
	                System.out.println("공고 정보 수정 실패.");
	            }
	        } else {
	            System.out.println("해당 공고 번호의 정보를 찾을 수 없거나 권한이 없습니다.");
	        }
	    } catch (NumberFormatException e) {
	        System.out.println("!!숫자만 입력하세요!!");
	    } catch (Exception e) {
	        System.out.println("오류 발생: " + e.getMessage());
	    }
	}
	
	
	
	public void deleteApplication(Scanner sc, EmployeeLoginDto employeeDto) {
		
		System.out.println("------ 이력서 삭제 -------");

		try {
			
			int result = 0;
			
			System.out.println("삭제할 이력서 번호를 선택하시오 : ");
			int application_seq = Integer.parseInt(sc.nextLine());
			
			ApplicationDto applicationDto = myDao.viewApplication(application_seq);
			
			if(applicationDto != null && applicationDto.getEmp_seq() == employeeDto.getEmp_seq()
					&& applicationDto.getStatus() == 1) {
				
				result = myDao.changeToDeleteApplication(application_seq);

				
			}else {
				
				
				System.out.println("해당 이력서 번호의 정보를 찾을 수 없거나 권한이 없습니다.");
				
			}
			
			
			if(result == 1) {
				
				System.out.println("이력서 삭제 성공!!");
			}else {
				System.out.println("!!이력서 삭제 실패!!");
				
			}
			
			
			
		}catch (NumberFormatException e) {
			System.out.println("!!숫자만 입력하시오!!");
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}
	
	
	public void deleteRecruit(Scanner sc, CompanyLoginDto company) {
		
		System.out.println("------ 채용공고 마감 -------");

		try {
			
			int result = 0;
			
			System.out.println("마감할 채용공고 번호를 선택하시오 : ");
			int recruit_seq = Integer.parseInt(sc.nextLine());
			
			RecruitDto recruitDto = myDao.getRecruit(recruit_seq);
			
			if(recruitDto != null && recruitDto.getComp_seq() == company.getComp_seq()
					&& recruitDto.getStatus() == 1) {
				
				result = myDao.changeToDeleteRecruit(recruit_seq);

				
			}else {
				
				
				System.out.println("해당 공고 번호의 정보를 찾을 수 없거나 권한이 없습니다.");
				
			}
			
			
			if(result == 1) {
				
				System.out.println("공고 삭제 성공!!");
			}else {
				System.out.println("!!공고 삭제 실패!!");
				
			}
			
			
			
		}catch (NumberFormatException e) {
			System.out.println("!!숫자만 입력하시오!!");
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		
		
	}
	
	
	public void apply(Scanner sc, EmployeeLoginDto employee) {
		
		System.out.println("------- 원서 넣기 -------");
		
		
		int recruitSeq = -1;
		while(true) {
			
			try {
				System.out.println("지원할 공고 번호 입력: ");
				recruitSeq = Integer.parseInt(sc.nextLine());
				break;
			} catch (NumberFormatException e) {
				System.out.println("!!숫자를 입력하시오!!");
			}

			
		}
		
		int application_seq = -1;
		while(true) {
			
			try {
				System.out.println("지원할 내 이력서 번호 입력: ");
				application_seq = Integer.parseInt(sc.nextLine());
				break;
			} catch (NumberFormatException e) {
				System.out.println("!!숫자를 입력하시오!!");
			}

		}
		
		
		RecruitEntryDto recruitEntryDto = new RecruitEntryDto();
		recruitEntryDto.setRecruit_seq(recruitSeq);
		recruitEntryDto.setApplication_seq(application_seq);
		
		int result =  myDao.apply(recruitEntryDto, employee.getEmp_seq());
		
		
		if(result == 1) {
			
			System.out.println("지원 성공!!");
		}else if(result == -1) {
			
			System.out.println("지원 실패!! : 이미 지원한 공고 입니다!!");
		}else {
			
			System.out.println("지원 실패!! : 존재하지 않는 공고이거나 이력서가 존재하지 않습니다.");
		}
		
		
	}
	
	
//	public void searchCompany(Scanner sc) {
//		
//		
//		
//		
//		
//	}
	
//	private void searchCompanyByName(Scanner sc) {
//		
//		System.out.println("------- 이름으로 회사 검색 ------");
//		
//		System.out.println("검색할 회사 이름 검색 : ");
//		String name = sc.nextLine();
//		
//		List<CompanyDto> searchList = myDao.searchCompanyByName(name);
//		
//		
//		System.out.println("seq \t 이름 \t 산업 \t 이메일 \t규모 \t 설립일 \t 주소 \t 대표 \t 직원수 \t 자본금 \t 매출 \t 보험여부");
//	    if (!searchList.isEmpty()) {
//	        for (CompanyDto company : searchList) {
//	            System.out.print(company.getComp_seq() + "\t");
//	            System.out.print(company.getComp_name() + "\t");
//	            
//	            System.out.print(company.getIndustry_name() + "\t");
//	            System.out.print(company.getComp_email() + "\t");
//	            
//	            System.out.print(company.getComp_scale() + "\t");
//	            System.out.print(company.getComp_est_date() + "\t");
//	            System.out.print(company.getComp_addr() + "\t");
//	            System.out.print(company.getComp_ceo() + "\t");
//	            System.out.print(company.getComp_emp_cnt() + "\t");
//	            System.out.print(company.getComp_capital() + "\t");
//	            System.out.print(company.getComp_sales() + "\t");
//	            System.out.print(company.getComp_insurance()+ "\t");
//	            System.out.print(company.getStatus() + "\t");
//	            System.out.println("----------------------------------");
//	        }
//	    } else {
//	        System.out.println("해당 회사를 찾을 수 없음: " + name);
//	    }
//		
//		
//		
//		
//	}
	

	
	public void searchCompanyByEmployeeCount(Scanner sc) {
		
		
		System.out.println("------- 직원 수로 회사 검색 ------");
		
		
		int min = 0;
		
		while (true) {
			
			try {
				System.out.println("검색할 최소 직원 수 입력 : ");
				String temp = sc.nextLine();
				if(temp.trim().equals("")) {
					min = 0;
				}else {
					
					min = Integer.parseInt(temp);
					
				}
			
				break;
			}catch (NumberFormatException e) {
				System.out.println("!!숫자만 입력하시오!!");
			}
		
		}
		
		
		int max = 0;

		while (true) {
			
			try {
				System.out.println("검색할 최대 직원 수 입력 : ");
				String temp = sc.nextLine();
				if(temp.trim().equals("")) {
					max = 0;
				}else {
					
					max = Integer.parseInt(temp);
					
				}
			
				break;
			}catch (NumberFormatException e) {
				System.out.println("!!숫자만 입력하시오!!");
			}
		
		}
		
		

		List<CompanyDto> searchList = myDao.searchCompanyByEmployeeCount(min, max);
		
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
	
	public void searchCompanyBySales(Scanner sc) {
		
		
		System.out.println("------- 매출로 회사 검색 ------");
		
		
		
		int min = 0;
		
		while (true) {
			
			try {
				System.out.println("검색할 최소 매출 입력 : ");
				String temp = sc.nextLine();
				if(temp.trim().equals("")) {
					min = 0;
				}else {
					
					min = Integer.parseInt(temp);
					
				}
			
				break;
			}catch (NumberFormatException e) {
				System.out.println("!!숫자만 입력하시오!!");
			}
		
		}
		
		
		int max = 0;
		
		while (true) {
			
			try {
				System.out.println("검색할 최대 매출 입력 : ");
				String temp = sc.nextLine();
				if(temp.trim().equals("")) {
					max = 0;
				}else {
					
					max = Integer.parseInt(temp);
					
				}
			
				break;
			}catch (NumberFormatException e) {
				System.out.println("!!숫자만 입력하시오!!");
			}
		
		}
		
		

		List<CompanyDto> searchList = myDao.searchCompanyBySales(min, max);
		
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
	
	public void searchEmployeeByAgeGroup(Scanner sc) {
		
		System.out.println("--------- 연령대로 구직자 검색 -------" );
		
		
	int min = 0;
		
		while (true) {
			
			try {
				System.out.println("검색할 최소 연령대 입력 : ");
				String temp = sc.nextLine();
				if(temp.trim().equals("")) {
					min = 0;
				}else {
					
					min = Integer.parseInt(temp)/10*10;
					
				}
			
				break;
			}catch (NumberFormatException e) {
				System.out.println("!!숫자만 입력하시오!!");
			}
		
		}
		
		
		int max = 0;
		
		while (true) {
			
			try {
				System.out.println("검색할 최대 연령대 입력 : ");
				String temp = sc.nextLine();
				if(temp.trim().equals("")) {
					max = 0;
				}else {
					
					max = (Integer.parseInt(temp)/10)*10;
					
				}
			
				break;
			}catch (NumberFormatException e) {
				System.out.println("!!숫자만 입력하시오!!");
			}
		
		}
		
		List<EmployeeDto> searchList = myDao.searchEmployeeByAgeGroup(min, max);
		
		System.out.println("seq \t 이름 \t생년월일  \t성별 \t 이메일 \t전화번호 \t 주소 ");
	    if (!searchList.isEmpty()) {
	        for (EmployeeDto employee : searchList) {
	        	System.out.print(employee.getEmp_seq() + "\t");
	        	System.out.print(employee.getEmp_name() + "\t");
	        	System.out.print(employee.getJubun().substring(0, 6) + "\t");
	        	System.out.print(employee.getJubun().charAt(6) == 1 || employee.getJubun().charAt(6) == 3? "남 \t" : "여\t" );
	        	System.out.print(employee.getEmp_email() + "\t");
	        	System.out.print(employee.getEmp_tel()+ "\t");
	        	System.out.print(employee.getEmp_address() + "\n");
	        	

	        }
	    } else {
	        System.out.println("해당 범위의 구직자를 찾을 수 없음");
	    }
		
		
		
		
		
	}
	
	
	public void viewCompanyRecruits(Scanner sc, CompanyLoginDto company) {
		
		System.out.println("-------- 우리 회사 공고 현황 --------");
		
		List<RecruitDto> recruitList = myDao.getCompanyRecruits(company.getComp_seq());
		
		
		System.out.println("공고번호 \t\t 제목 \t\t 시작일자 \t\t 마감날자 \t\t 마감여부");
		StringBuilder sb = new StringBuilder();
		for(RecruitDto recruit : recruitList) {
			
			sb.append(recruit.getRecruit_seq() + "\t\t");
			sb.append(recruit.getRecruit_title() + "[" + recruit.getCount() + "]" + "\t\t");
			sb.append(recruit.getRecruit_start_date()+ "\t\t");
			sb.append(recruit.getRecruit_end_date()+ "\t");
			sb.append(recruit.getStatus() == 1 ? "모집중 \n" : "마감 \n");
		}
		
		System.out.println(sb);
		
		int no = -1;
		while(true) {
			try {
				System.out.print("상세보기 번호 (나가려면 그냥 엔터를 누르시오): ");
				String temp = sc.nextLine();
				if(temp.trim().equals("")) {
					return;
				}
				no = Integer.parseInt(temp);
				
				break;
			} catch (NumberFormatException e) {
				System.out.println("!!숫자만 입력하시오!!");
			}
			
			
		}

		
		RecruitDto recruitDto = companyDao_kdh.viewRecruitDetailList(company.getComp_seq(), no );
		
		
		
		System.out.println("------------이력서 "  + recruitDto.getRecruit_seq() + ": "+  recruitDto.getRecruit_title() + "-----------------");
		System.out.println("회사이름 : " + company.getComp_name());
		System.out.println("채용인원 : " + recruitDto.getRecruit_num());
		System.out.println("직무 : " + recruitDto.getPosition());
		System.out.println("모집 시작일자 : " + recruitDto.getRecruit_start_date());
		System.out.println("모집 마감일자 : " + recruitDto.getRecruit_end_date());
		
		System.out.println("--------------------------------------------------------");
		System.out.println("이력서 번호  \t  이름");
		
		sb = new StringBuilder();
		for(ApplicationDto app : recruitDto.getApplicationList()) {
			sb.append(app.getApplication_seq() + "  \t  ");
			sb.append(app.getEmployeeDto().getEmp_name() +  "  \n ");
			
		}
		System.out.println(sb);
		
		
		int application_seq = -1;
		while(true) {
			try {
				System.out.print("이력서 상세보기 번호 (나가려면 그냥 엔터를 누르시오): ");
				String temp = sc.nextLine();
				if(temp.trim().equals("")) {
					return;
				}
				application_seq = Integer.parseInt(temp);
				
				break;
			} catch (NumberFormatException e) {
				System.out.println("!!숫자만 입력하시오!!");
			}
			
			
			
			
			
			
		}
		
		
		ApplicationDto adto = cdao.searchAppliSeq(application_seq);
		
		if(adto == null) {
			
			System.out.println("!!이력서가 존재 하지 않습니다!!");
			
			
		}else {
			System.out.println("\n 이력서번호\t\t이름\t\t사는지역\t\t전화번호\t\t이메일\t\t\t고용형태종류\t\t병역상태\t\t소유자격증\t\t학교명\t\t학과명\t\t희망연봉\t\t지원동기\t\t이력서삭제여부");
			System.out.println("-".repeat(250));
			
			sb = new StringBuilder();
			
			sb.append(adto.getApplication_seq() + "\t\t"
					+adto.getEmployeeDto().getEmp_name() + "\t\t"
					+adto.getLoctaion_name() + "\t\t"
					+adto.getEmployeeDto().getEmp_tel() + "\t\t"
					+adto.getEmployeeDto().getEmp_email() + "\t\t"
					+adto.getEmp_type_name() + "\t\t"
					+adto.getMilitary_status() + "\t\t"
					+adto.getLicense() + "\t\t"
					+adto.getHope_sal() + "\t\t"
					+adto.getMotive() + "\t\t"
					+adto.getStatus() + "\t\t"
					);
			
			System.out.println(sb.toString());
			
		}
		

		
	}

	public void viewMyInfo(EmployeeLoginDto member) {
		System.out.println("------------내 정보------------");
		
		EmployeeDto employeeDto = myDao.getEmp(member.getEmp_seq());
		StringBuilder sb = new StringBuilder();
		
		sb.append("seq : " + employeeDto.getEmp_seq() + "\n");
		sb.append("아이디 : " + employeeDto.getEmp_id() + "\n");
		sb.append("이름 : " +  employeeDto.getEmp_name() + "\n");
		sb.append("이메일 : " +employeeDto.getEmp_email() + "\n");
		sb.append("전번 : " + employeeDto.getEmp_tel() + "\n");
		
		System.out.println(sb);
	}
	
	
	
	

	

}






















