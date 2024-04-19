package controller.hss;

import java.awt.Menu;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import domain.*;
import model.*;
import model.HssMember.*;
import myUtil.*;

public class HssEmployeeCtrl {
	
	private EmployeeDao employeeDao = new EmployeeDaoImple();
	// 내 dao
	private HssMemberDao HssMemberDao = new HssMemberDaoImple();
	
	private HssSearchComCtrl HssSearchComCtrl = new HssSearchComCtrl();

	


	public void writeApplication(Scanner sc, EmployeeLoginDto employeeDto) {

		 ApplicationDto ApplicationDto = new ApplicationDto();

		System.out.println("이력서를 작성 시작합니다.");

		// 자격증 LICENSE 
		String license = "";
		System.out.print("1. 소유 자격증을 입력하세요(없을시 엔터):");
		license = sc.nextLine();
		ApplicationDto.setLicense(license);
		

		// 희망연봉 HOPE_SAL
        String hopeSal = "";

		while(true) {
			System.out.print("2. 희망연봉을 입력하세요(없을시 엔터):"); 
			hopeSal = sc.nextLine();
			if(!hopeSal.isBlank()) {
				try {
					ApplicationDto.setHope_sal(Integer.parseInt(hopeSal));
					break;
				} catch(NumberFormatException e) {
					 System.out.println("올바른 숫자 형식이 아닙니다. 다시 입력하세요.");
				}
			} else { //엔터를 눌렀을경우
				ApplicationDto.setHope_sal(0); //0으로 세팅 - 0:협의
			     break;
			}
		}
        

		// 병역 MILITARY_SEQ Not Null 
		String mil = "";
	    do {
	         System.out.print("3. 병역 여부를 숫자로 입력하세요(0.미필 1.군필 2.면제 3.복무중):");
	         
	         mil = sc.nextLine();
	         
	         if(mil.trim().equals("0") || mil.trim().equals("1") || mil.trim().equals("2") || mil.trim().equals("3") ) {
	        	 ApplicationDto.setLocation_seq(Integer.parseInt(mil));
	        	 break;
	         }else {
	            System.out.println("0, 1, 2, 3만 입력 가능합니다.");
	         }
	         
	      }while(true);
	    
	    // 근무지역 LOCATION_SEQ Not Null 
	      String loc = "";
	      while(true) {
	    	 System.out.print("4. 희망근무지역을 숫자로 입력하세요(1.서울 2.경기도) :");
	    	 loc = sc.nextLine();
	         
	         if(loc.trim().equals("1") || loc.trim().equals("2")) {
	            ApplicationDto.setLocation_seq(Integer.parseInt(loc));
	        	break;
	         }else {
	            System.out.println("1, 2만 입력 가능합니다.");
	         }
	         
	      }
	      // 희망 근무 전형 EMPLOY_TYPE_SEQ Not Null 
	      String empType = ""; // 
	      while(true) {
	         System.out.print("5. 희망 근무 전형을 숫자로 입력하세요(0. 계약직  1.정규직): ");
	         empType = sc.nextLine();
	         
	         if(empType.trim().equals("0") || empType.trim().equals("1")) {
	        	 ApplicationDto.setEmploy_type_seq(Integer.parseInt(empType));
	            break;
	         }else {
	            System.out.println("0, 1만 입력 가능합니다.");
	         }
	         
	      }
	      
 
	      // 경력 입력받기 
            List<CareerDto> CareerList = new ArrayList<>();            
            String careerIn = "";
            do {
                while (true) {
                    System.out.print("6.경력을 입력하시겠습니까? (Y/N): ");
                    careerIn = sc.nextLine();

                    if (careerIn.equalsIgnoreCase("y")) {
                        CareerDto career = new CareerDto();
                        while(true) {
	                        System.out.print("직무명 : ");
	                        String position = sc.nextLine();
	                        if(!position.isBlank()) {
	                        	career.setPosition(position);
	                        	break;
	                        } else {
	                        	System.out.println("직무명을 입력하지 않았습니다. 다시 입력하세요.");
	                        }
                        }

                        while(true) {
	                        System.out.print("경력시작일(yyyy-MM-dd 형식으로 입력하세요) : ");
	                        String startDate = sc.nextLine();
	                        if(!startDate.isBlank() && MyUtil.isTrueDate(startDate)) {
	                        	career.setCareerStartDate(startDate);
	                        	break;
	                        } else {
	                        	System.out.println("경력시작일을 입력하지 않았거나 유효하지 않은 날짜입니다. 다시 입력하세요.");
	                        }
                        }
                        
                        while(true) {
	                        System.out.print("경력종료일(yyyy-MM-dd 형식으로 입력하세요) : ");
	                        String endDate = sc.nextLine();
	                        if(!endDate.isBlank() && MyUtil.isTrueDate(endDate)) {
	                        	career.setCareerEndDate(endDate);
	                        	break;
	                        } else {
	                        	System.out.println("경력종료일을 입력하지 않았거나 유효하지 않은 날짜입니다. 다시 입력하세요.");
	                        }
                        }
                        CareerList.add(career); // 경력 정보를 리스트에 추가
                    } else if (careerIn.equalsIgnoreCase("n")) {
                        break; // 경력 입력 종료
                    } else {
                        System.out.println("잘못된 입력입니다. Y 또는 N을 입력해주세요.");
                    }
                }

            } while (careerIn.equalsIgnoreCase("y"));
            
            
         // 학력 입력받기 

            String educationIn = "";
            List<EducationDto> educationList = new ArrayList<>(); //리스트 생성
            
            do {
            	
            	while(true) {
            		
            		System.out.print("7.학력을 입력하시겠습니까? (Y/N): ");
	            	educationIn = sc.nextLine();
	            	
	                if(educationIn.equalsIgnoreCase("y")) {
	            	   EducationDto education = new EducationDto(); //객체 생성
	                   
	            	   while(true) {
		            	   System.out.print("학교명 : ");
		                   String schoolName = sc.nextLine();
		                   if(!schoolName.isBlank()) {
		                	   education.setSchoolName(schoolName);
		                	   break;
		                   } else {
		                	   System.out.println("학교명을 입력하지 않았습니다. 다시 입력하세요.");
		                   }
	                   } 
	            	   while(true) {
		                   System.out.print("학과명 : ");
		                   String departmentName = sc.nextLine();
		                   if(!departmentName.isBlank()) {
			                   education.setDepartmentName(departmentName);
			                   break;
		                   } else {
		                	   System.out.println("학과명을 입력하지 않았습니다. 다시 입력하세요.");
		                   }
	            	   }
	                   educationList.add(education); // 리스트 추가 
	                     
	               } else if(educationIn.equalsIgnoreCase("n")) {
	            	   break; //학력 입력 종료
	               } else {
	                   System.out.println("잘못된 입력입니다. Y 또는 N을 입력해주세요.");
	               }
            }
  
           } while (careerIn.equalsIgnoreCase("y"));

            
			String motive = "";
			while(true) {
				System.out.print("지원동기를 입력하세요. : ");
				motive = sc.nextLine();
				if(!motive.isBlank()) {
					ApplicationDto.setMotive(motive);
					break;	
				} else {
					System.out.println("지원동기를 입력하지 않았습니다. 다시 입력하세요.");
				}
			}
            

            
            //DAO 메서드 호출 1. 이력서 작성 메소드 2. 경력 작성 메소드 3. 학력 작성 메소드
            
			
			int isSuccess = HssMemberDao.insert_application(ApplicationDto, employeeDto);
			
			if(isSuccess == 1) {
	            
				HssMemberDao.insert_career(ApplicationDto, CareerList);
				HssMemberDao.insert_education(ApplicationDto, educationList);
				System.out.println("!!이력서 작성 성공!!");
				
			} else {
				System.out.println("!!이력서 작성 실패!!");
			}
			
				
     
     } // end of private void writeApplication(Scanner sc, int emp_seq) --------------

	
	


	//회원 아이디,비번찾기 메뉴 띄우기
	public void empIdPasswdSearch(Scanner sc) {
		String menuNo = "";
		do {
			System.out.println("----------------------------------------------");
			System.out.println("   1. 아이디 찾기    2. 비밀번호 찾기    3. 나가기");
			System.out.println("----------------------------------------------");
			
			System.out.print("메뉴번호 선택 : ");
			menuNo = sc.nextLine();
			switch(menuNo) {
				case "1": 
					empIdSearch(sc);
					break;
				case "2":
					empPswdSearch(sc);
					break;
				case "3":
					break;
				default:
					System.out.println("-----------!!올바른 메뉴번호를 입력해 주세요!!!-----------");
					break;
			}
		}while(!"3".equals(menuNo));
	}

	


	public void empIdSearch(Scanner sc) {
		EmployeeDto employee = null;
		System.out.print("이름을 입력하세요. : ");
		String emp_name = sc.nextLine();
		System.out.print("이메일을 입력하세요. : ");
		String emp_email = sc.nextLine();
		
		Map<String,String> paramap = new HashMap<>();
		paramap.put("emp_name", emp_name);
		paramap.put("emp_email", emp_email);
		
		employee = HssMemberDao.empIdSearch(paramap);
		if(employee == null) {
			System.out.println("\n >>> 가입하지 않은 회원입니다. <<< ");
			return;
		}
		System.out.println("고객님의 아이디는 " + employee.getEmp_id() + "입니다.");
		
	}
      


	private void empPswdSearch(Scanner sc) {
		
		
		EmployeeDto employee = new EmployeeDto();
		System.out.print("이름을 입력하세요. : ");
		String emp_name = sc.nextLine();
		System.out.print("아이디를 입력하세요. : ");
		String emp_id = sc.nextLine();
		
		Map<String,String> paramap = new HashMap<>();
		paramap.put("emp_name", emp_name);
		paramap.put("emp_id", emp_id);
		
		
		employee = HssMemberDao.empPswdSearch(paramap);
		
		// 통과했을경우 
		if(employee != null) {
			
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
					newParaMap.put("emp_id", emp_id); // 사용자가 위에 입력한 아이디
					List<String> pswdList = HssMemberDao.empNewPswdCheck(newParaMap); //최근 변경한 3개중 있는지 조회하는 메소드
					
					if(pswdList.size() > 0) {
						for(int i = 0; i < pswdList.size(); i++) {
							if(pswdList.get(i).equals(new_passwd)) {
								isPass = false; //실패
								System.out.println("최근 사용한 비밀번호입니다. 다시 입력하세요!");
							}
						}
					}
					if(isPass) { //true라면
						int result = HssMemberDao.empNewPswdSet(newParaMap);
						if(result == 1) {
							System.out.println("변경 성공");
							isPass = true;
						} else {
							System.out.println("변경 실패");
						
						}
					}
					
				} else {
					System.out.println("비밀번호가 일치하지 않습니다. 다시 입력하세요.");
					isPass = false;
				}
				
			}while(!isPass);
			

		} else {
			System.out.println("\n >>> 비밀번호 찾기에 실패했습니다. <<< ");
			return;
		}
	}



	
		
		
		
		
		
		
		
		
		
	}

