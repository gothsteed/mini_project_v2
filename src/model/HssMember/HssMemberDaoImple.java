package model.HssMember;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import dbConnect.MyDBConnection;
import domain.ApplicationDto;
import domain.CareerDto;
import domain.CompanyDto;
import domain.EducationDto;
import domain.EmployeeDto;
import domain.EmployeeLoginDto;
import domain.RecruitDto;

public class HssMemberDaoImple implements HssMemberDao {

	private Connection conn = MyDBConnection.getConn();
	private PreparedStatement pstmt;
	private ResultSet rs;
	
	private void close() {
		try {
			if (rs != null) {
				rs.close();
				rs = null;
			}
			if (pstmt != null) {
				pstmt.close();
				pstmt = null;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	//이력서 작성하기 
	@Override
	public int insert_application(ApplicationDto applicationDto, EmployeeLoginDto employeeDto) {
		int result = 0;
		String sql = "";
		try {
			
			conn.setAutoCommit(false);
	
			sql = " insert into tbl_application(application_seq, emp_seq, location_seq, employ_type_seq, military_seq, license, hope_sal, motive) "
					+ " values(APP_SEQ.nextval, ?,?,?,?,?,?,?)  ";

			pstmt = conn.prepareStatement(sql);

			
			pstmt.setInt(1, employeeDto.getEmp_seq()); // employee 객체에서 로그인된 id(=seq) 를 받아와서 그걸 인서트 해준다.
			pstmt.setInt(2, applicationDto.getLocation_seq());
			pstmt.setInt(3, applicationDto.getEmploy_type_seq());
			pstmt.setInt(4, applicationDto.getMilitary_seq());
			pstmt.setString(5, applicationDto.getLicense());
			pstmt.setInt(6, applicationDto.getHope_sal());
			pstmt.setString(7, applicationDto.getMotive());


			int n = pstmt.executeUpdate(); 

			
			
			if(n == 1) {
				
				applicationDto.setApplication_seq(getLastApplicationSeq()); // 메소드실행후 시퀀스값 넣음
	            
				conn.commit();
				result = 1;
		   
			} else {
	            conn.rollback();
	            result = -1;
			}


		} catch (SQLException e) {
			
			try {
				conn.rollback();
				result = -1; // DB 에서 문제가 생겼을 경우
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
				
		} finally {
			
			try {
				conn.setAutoCommit(true);
			} catch (SQLException e) {
	
				e.printStackTrace();
			}
			close();
		}
		
		
		return result;
	}

	// 학력을 입력하는 메소드 -------------------------------------
	@Override
	 public void insert_education(ApplicationDto applicationDto, List<EducationDto> educationList) {
		try {
			conn.setAutoCommit(false);
			// 학력 정보 입력(있다면)
			if(!educationList.isEmpty()) {
		        for (EducationDto education : educationList) {
		            String educationSql = " insert into tbl_education(edu_seq, application_seq, school_name, department_name) "
		            		+ " values(SEQ_EDUCATION.nextval, ?, ?, ?) ";
		            pstmt = conn.prepareStatement(educationSql);
		            pstmt.setInt(1, applicationDto.getApplication_seq());
		            pstmt.setString(2, education.getSchoolName());
		            pstmt.setString(3, education.getDepartmentName());
		            pstmt.executeUpdate();
		        }
		        	conn.commit();
			}
	 } catch (SQLException e) {
			try {
	            conn.rollback(); // 롤백 처리
	        } catch (SQLException ex) {
	            ex.printStackTrace();
	        }
		
	} finally {
			close();
		}
	
	 }
	
	// 경력을 입력하는 메소드 -------------------------------------
	@Override
	 public void insert_career(ApplicationDto applicationDto, List<CareerDto> careerList) {
		try {
			conn.setAutoCommit(false);
	        // 경력 정보 입력(있다면)
			if (!careerList.isEmpty()) {
		        for (CareerDto career : careerList) {
		            String careerSql = " insert into tbl_career(career_seq, application_seq, career_start_date, career_end_date, position) "
		            		+ " values(SEQ_CAREER.nextval, ?, ?, ?, ?) ";
		            pstmt = conn.prepareStatement(careerSql);
		            
		            pstmt.setInt(1, applicationDto.getApplication_seq());
		            pstmt.setString(2, career.getCareerStartDate());
		            pstmt.setString(3, career.getCareerEndDate());
		            pstmt.setString(4, career.getPosition());
		            
		            pstmt.executeUpdate();
		        }
		        conn.commit();
			}
		
	 } catch (SQLException e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
				
		} finally {
			close();
		}
	
	 }
	        

	// 최신 application_seq 값을 가져오는 메서드
	private int getLastApplicationSeq() {
		try {
		    String sql = " SELECT MAX(application_seq) FROM tbl_application "; //가장 최근 작성된 시퀀스값
		    pstmt = conn.prepareStatement(sql); // 쿼리실행
		    ResultSet rs = pstmt.executeQuery(); //실행한거 rs에 저장
		    int lastSeq = -1;
		    if (rs.next()) {
		        lastSeq = rs.getInt(1);
		    }
		    return lastSeq;
		} catch(SQLException e) {
			e.printStackTrace();
			return -1; //실패시 -1 
		}
	}

	//회사이름검색메소드 
	@Override
	public List<CompanyDto> compNameSearch(String compName) {
		List<CompanyDto> compList = new ArrayList<>();
		try {
			String sql = " select comp_name, comp_scale, comp_est_date, comp_addr, comp_ceo, comp_emp_cnt, comp_capital, comp_sales "
					+ " from tbl_comp "
					+ " where comp_name LIKE ? ";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, "%" +compName+ "%");
			ResultSet rs = pstmt.executeQuery();
			
			while(rs.next()) {
				CompanyDto cdto = new CompanyDto();
				
				cdto.setComp_name(rs.getString("comp_name"));
				cdto.setComp_scale(rs.getString("comp_scale"));
				cdto.setComp_est_date(rs.getString("comp_est_date"));
				cdto.setComp_addr(rs.getString("comp_addr"));
				cdto.setComp_ceo(rs.getString("comp_ceo"));
				cdto.setComp_emp_cnt(rs.getInt("comp_emp_cnt"));
				cdto.setComp_capital(rs.getInt("comp_capital"));
				cdto.setComp_sales(rs.getInt("comp_sales"));
				
				compList.add(cdto);
				
			}
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return compList;
		
	}

	@Override
	public List<CompanyDto> compCapitalSearch(int capital) {
		List<CompanyDto> compList = new ArrayList<>();
		
		try {
			String sql = " select comp_name, comp_scale, comp_est_date, comp_addr, comp_ceo, comp_emp_cnt, comp_capital, comp_sales "
					+ " from tbl_comp "
					+ " where comp_capital > ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, capital);
			ResultSet rs = pstmt.executeQuery();
			
			while(rs.next()) {
				CompanyDto cdto = new CompanyDto();
				
				cdto.setComp_name(rs.getString("comp_name"));
				cdto.setComp_scale(rs.getString("comp_scale"));
				cdto.setComp_est_date(rs.getString("comp_est_date"));
				cdto.setComp_addr(rs.getString("comp_addr"));
				cdto.setComp_ceo(rs.getString("comp_ceo"));
				cdto.setComp_emp_cnt(rs.getInt("comp_emp_cnt"));
				cdto.setComp_capital(rs.getInt("comp_capital"));
				cdto.setComp_sales(rs.getInt("comp_sales"));
				
				compList.add(cdto);
				
			}
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return compList;
	}

	//아이디 찾기 메서드 
	@Override
	public EmployeeDto empIdSearch(Map<String, String> paramap) {
		EmployeeDto empdto = null;
		
		try {
			String sql = "select emp_id "
					+ " from tbl_employee "
					+ " where emp_name = ? and emp_email = ? ";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, paramap.get("emp_name"));
			pstmt.setString(2, paramap.get("emp_email"));
			
			ResultSet rs = pstmt.executeQuery();
			
			if(rs.next()) {
				empdto = new EmployeeDto();
				empdto.setEmp_id(rs.getString("emp_id"));
			}
					
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		
		return empdto;
	}

	//비밀번호 찾기 메서드

	@Override
	public EmployeeDto empPswdSearch(Map<String, String> paramap) {
		EmployeeDto empdto = null;
		
		try {
			String sql = " select emp_password "
					+ " from tbl_emp_login "
					+ " where emp_name = ? and emp_id = ? ";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, paramap.get("emp_name"));
			pstmt.setString(2, paramap.get("emp_id"));
			
			ResultSet rs = pstmt.executeQuery();
			
			if(rs.next()) {
				empdto = new EmployeeDto();
				empdto.setEmp_password(rs.getString("emp_password"));
			}
					
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		
		return empdto;
	}
	
	
	//새 비밀번호가 최근 바꾼 3개중 있는지 조회하는 메소드
	@Override
	public List<String> empNewPswdCheck(Map<String, String> newParaMap) {
		List<String> pswdList = new ArrayList<>();
		try {
			//조회해오기.. rownum3만..
			String sql = " SELECT emp_passwd "
					+ " FROM "
					+ " ( "
					+ " SELECT rownum AS RNO, emp_passwd, modify_date "
					+ " FROM "
					+ " ( "
					+ " select emp_passwd, to_char(modify_date, 'yyyy-mm-dd hh24:mi:ss') AS modify_date "
					+ " from emp_pswd_threemon "
					+ " where emp_id = ? "
					+ " order by modify_date desc "
					+ " ) V "
					+ " ) T "
					+ " WHERE RNO <= 3 ";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, newParaMap.get("emp_id")); //map에 저장해둔 empid를 set
			ResultSet rs = pstmt.executeQuery(); //쿼리돌리기 
			
			while(rs.next()) {
				pswdList.add(rs.getString("emp_passwd"));
			}	
	
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		
		return pswdList;
	        
	}

	//새 비밀번호로 세팅해주는 메소드-update 후 비밀번호목록 에도 insert필요
	@Override
	public int empNewPswdSet(Map<String, String> newParaMap) {
		int result = 0;
		 // 시퀀스 가져오기 위한 아이디 객체 생성
		EmployeeDto empdto = new EmployeeDto();
		try {
		
			conn.setAutoCommit(false);
			
			//시퀀스 가져오기
			String sql = " select emp_seq "
					+ " from tbl_employee "
					+ " where emp_id = ? ";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, newParaMap.get("emp_id")); //map에 저장해둔 empid를 set
			ResultSet rs = pstmt.executeQuery(); //쿼리돌리기 
			
			if(rs.next()) {
				empdto.setEmp_seq(rs.getInt("emp_seq"));
			}	

			
			
			//비밀번호 업데이트 쿼리
			sql = " update tbl_emp_login set emp_password = ? "
					+ " where emp_id = ? ";

			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, newParaMap.get("new_passwd"));
			pstmt.setString(2, newParaMap.get("emp_id"));
			
			int n = pstmt.executeUpdate();
			
			if (n == 1) {
			//비번 업데이트 후 비번테이블에 insert처리 해야함 -> .. insert해야 하는거 
				sql = " insert into emp_pswd_threemon "
						+ " (emp_passwd, emp_id, emp_seq, EMP_PASSWD_SEQ ) "
						+ " values (?, ?, ?, EMP_PASSWD_SEQ.nextval )";
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, newParaMap.get("new_passwd"));
				pstmt.setString(2, newParaMap.get("emp_id"));
				pstmt.setInt(3, empdto.getEmp_seq());
				
				result = pstmt.executeUpdate();
				conn.commit();
			}
			
			
		} catch (SQLException e) {

			try {
				e.printStackTrace();
				conn.rollback(); // 롤백을 해준다.
				result = -1; // DB 에서 문제가 생겼을 경우
			} catch (SQLException e1) {
				e1.printStackTrace();
			}

		} finally { // 성공하든 안하든 무조건 해야한다. (try - finally
			try {
				conn.setAutoCommit(true);// 자동 commit 으로 복원시킨다.
			} catch (SQLException e2) {
				e2.printStackTrace();
			}

			close(); // 자원 반납하기

		}
		
		
		
		return result;
	}

	
	
	@Override
	public List<RecruitDto> titleSearch(String title) {
		List<RecruitDto> rcList = new ArrayList<>();
		
		try {
		
			String sql = " select comp_name, recruit_title, license, screening, position, edu_level, exp_level, "
					+ " salary, recruit_start_date, recruit_end_date, result_date, recruit_manager_name, "
					+ " recruit_manager_email, hire_period "
					+ " from TBL_RECRUIT "
					+ " where recruit_title like ? ";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, "%" + title + "%");
			ResultSet rs = pstmt.executeQuery();
			
			while(rs.next()) {
				RecruitDto rdto = new RecruitDto();
				
				rdto.setComp_name(rs.getString("comp_name"));
				rdto.setRecruit_title(rs.getString("recruit_title"));
				rdto.setLicense(rs.getString("license"));
				rdto.setScreening(rs.getString("screening"));
				rdto.setPosition(rs.getString("position"));
				rdto.setEdu_level(rs.getString("edu_level"));
				rdto.setExp_level(rs.getString("exp_level"));
				rdto.setSalary(rs.getInt("salary"));
				rdto.setRecruit_start_date(rs.getString("recruit_start_date"));
				rdto.setRecruit_end_date(rs.getString("recruit_end_date"));
				rdto.setResult_date(rs.getString("result_date"));
				rdto.setRecruit_manager_name(rs.getString("recruit_manager_name"));
				rdto.setRecruit_manager_email(rs.getString("recruit_manager_email"));
				rdto.setHire_period(rs.getString("hire_period"));
				
				rcList.add(rdto);
				
			}
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return rcList;
	}

	
	
	
	
	
	
	
	
	
}

