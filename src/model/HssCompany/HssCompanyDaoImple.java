package model.HssCompany;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dbConnect.MyDBConnection;
import domain.CareerDto;
import domain.CompanyDto;
import domain.CompanyLoginDto;
import domain.EmployeeDto;
import domain.RecruitDto;

public class HssCompanyDaoImple implements HssCompanyDao {

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
	
	@Override
	public int insertRecruit(RecruitDto recruitDto, CompanyLoginDto companyLoginDto) {
		// TODO Auto-generated method stub
		int result = 0;
		String sql = "";
		try {
			
			conn.setAutoCommit(false);
	
			sql = " insert into TBL_RECRUIT(RECRUIT_SEQ, COMP_SEQ, LOCATION_SEQ, EMPLOY_TYPE_SEQ, COMP_NAME, RECRUIT_TITLE, LICENSE, SCREENING, "
					+ " POSITION, RECRUIT_NUM, EDU_LEVEL, EXP_LEVEL, SALARY, RECRUIT_START_DATE, RECRUIT_END_DATE, RESULT_DATE, "
					+ " RECRUIT_MANAGER_NAME, RECRUIT_MANAGER_EMAIL, HIRE_PERIOD) "
					+ " values(RECRUIT_ID.nextval, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)  ";

			pstmt = conn.prepareStatement(sql);

			
			pstmt.setInt(1, companyLoginDto.getComp_seq()); // company 객체에서 로그인된 id(=seq) 를 받아와서 그걸 인서트 해준다.
			pstmt.setInt(2, recruitDto.getLocation_seq());
			pstmt.setInt(3, recruitDto.getEmploy_type_seq());
			pstmt.setString(4, recruitDto.getComp_name());
			pstmt.setString(5, recruitDto.getRecruit_title());
			pstmt.setString(6, recruitDto.getLicense());
			pstmt.setString(7, recruitDto.getScreening());
			pstmt.setString(8, recruitDto.getPosition());
			pstmt.setInt(9, recruitDto.getRecruit_num());
			pstmt.setString(10, recruitDto.getEdu_level());
			pstmt.setString(11, recruitDto.getExp_level());
			pstmt.setInt(12, recruitDto.getSalary());
			pstmt.setString(13, recruitDto.getRecruit_start_date());
			pstmt.setString(14, recruitDto.getRecruit_end_date());
			pstmt.setString(15, recruitDto.getResult_date());
			pstmt.setString(16, recruitDto.getRecruit_manager_name());
			pstmt.setString(17, recruitDto.getRecruit_manager_email());
			pstmt.setString(18, recruitDto.getHire_period());
			


			int n = pstmt.executeUpdate(); 

			
			
			if(n == 1) {
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
			e.printStackTrace();
				
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

	//구직자성별검색메소드
	@Override
	public List<EmployeeDto> empGenderSearch(String gender) {
		List<EmployeeDto> empList = new ArrayList<>();
		
		try {
			String sql = " select gender, emp_id, emp_email, emp_name, emp_address, emp_tel "
					+ " from "
					+ " ( select CASE WHEN substr(jubun,7) IN (1,3) THEN '남' "
					+ " WHEN substr(jubun,7) IN (2,4) THEN '여' END AS GENDER, "
					+ " emp_id, emp_email, emp_name, emp_address, emp_tel "
					+ " from tbl_employee ) "
					+ "where GENDER = ? ";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1,gender);
			ResultSet rs = pstmt.executeQuery();
			
			while(rs.next()) {
				EmployeeDto empdto = new EmployeeDto();
				empdto.setEmp_id(rs.getString("emp_id"));
				empdto.setEmp_email(rs.getString("emp_email"));	
				empdto.setEmp_name(rs.getString("emp_name"));
				empdto.setEmp_address(rs.getString("emp_address"));
				empdto.setEmp_tel(rs.getString("emp_tel"));
				
				empList.add(empdto);

			}
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		
		
		
		
		return empList;
	}

	// 경력있는 사람 보이는 메소드
	@Override
	public List<CareerDto> empCareerSearch() {
		
		List<CareerDto> compCarrerList = new ArrayList<>();
		
		try {
				// empname / carrer는 커리어 dto에 있을텐데
			String sql = " with A AS ("
					+ " select a.emp_seq, a.emp_name, a.emp_address, a.emp_tel "
					+ " from tbl_employee A join tbl_application B "
					+ " on A.emp_seq = b.emp_seq "
					+ " ) , B AS ( "
					+ " select a.emp_seq, b.career_start_date, b.career_end_date, b.position "
					+ " from tbl_application A join tbl_career B  "
					+ " on A.application_seq = B.application_seq) "
					+ " SELECT A.emp_name, A.emp_address, A.emp_tel, B.career_start_date, B.career_end_date, B.position "
					+ " from a join b "
					+ " on a.emp_seq = b.emp_seq ";
			pstmt = conn.prepareStatement(sql);
			
			ResultSet rs = pstmt.executeQuery();
			
			while(rs.next()) {
				
				CareerDto crdto = new CareerDto();
				crdto.setCareerStartDate(rs.getString("career_start_date"));
				crdto.setCareerEndDate(rs.getString("career_end_date"));
				crdto.setPosition(rs.getString("position"));
				
				EmployeeDto empdto = new EmployeeDto();
				empdto.setEmp_name(rs.getString("emp_name"));
				empdto.setEmp_address(rs.getString("emp_address"));
				empdto.setEmp_tel(rs.getString("emp_tel"));
				
				crdto.setEmployeeDto(empdto);
				
				compCarrerList.add(crdto);

			}
			
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		
		
		return compCarrerList;
	}
	
	
	//아이디 찾기 메서드 
	@Override
	public CompanyDto compIdSearch(Map<String, String> paramap) {
		
		CompanyDto cldto = null;
		
		try {
				String sql = " select comp_id "
						+ " from tbl_comp "
						+ " where comp_name = ? and comp_email = ? ";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, paramap.get("comp_name"));
			pstmt.setString(2, paramap.get("comp_email"));
			
			ResultSet rs = pstmt.executeQuery();
			
			if(rs.next()) {
				cldto = new CompanyDto();
				cldto.setComp_id(rs.getString("comp_id"));
			}
				
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		
	
		return cldto;
		
		
		
	}
	
	//비밀번호 찾기 시작 메서드
	@Override
	public CompanyLoginDto compPswdSearch(Map<String, String> paramap) {
		CompanyLoginDto comdto = null;
		try {
				String sql = " select comp_password "
						+ " from tbl_comp_login "
						+ " where comp_name = ? and comp_id = ? ";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, paramap.get("comp_name"));
			pstmt.setString(2, paramap.get("comp_id"));
			
			ResultSet rs = pstmt.executeQuery();
	
			if(rs.next()) {
				comdto = new CompanyLoginDto();
				comdto.setComp_password(rs.getString("comp_password"));
			}

				
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return comdto;
		
	}
	
	//새 비밀번호가 최근 바꾼 3개중 있는지 조회하는 메소드
	@Override
	public List<String> compNewPswdCheck(Map<String, String> newParaMap) {
		List<String> pswdList = new ArrayList<>();
		try {
			String sql = " SELECT comp_passwd "
					+ " FROM "
					+ "( "
					+ " SELECT rownum AS RNO, comp_passwd, modify_date "
					+ " FROM "
					+ " ( "
					+ " select comp_passwd, to_char(modify_date, 'yyyy-mm-dd hh24:mi:ss') AS modify_date "
					+ " from comp_pswd_threemon "
					+ " where comp_id = ? "
					+ " order by modify_date desc "
					+ "  ) V "
					+ ") T "
					+ "WHERE RNO <= 3 ";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, newParaMap.get("comp_id")); //map에 저장해둔 comid를 set
			ResultSet rs = pstmt.executeQuery(); //쿼리돌리기 
			
			while(rs.next()) {
				pswdList.add(rs.getString("comp_passwd"));
			}	
	
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		
		return pswdList;
	        
		
		
	}

	@Override
	public int compNewPswdSet(Map<String, String> newParaMap) {
		int result = 0;
		 // 시퀀스 가져오기 위한 아이디 객체 생성
		CompanyDto comdto = new CompanyDto();
		try {
		
			conn.setAutoCommit(false);
			
			//시퀀스 가져오기
			String sql = " select comp_seq "
					+ " from tbl_comp "
					+ " where comp_id = ? ";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, newParaMap.get("comp_id")); //map에 저장해둔 empid를 set
			ResultSet rs = pstmt.executeQuery(); //쿼리돌리기 
			
			if(rs.next()) {
				comdto.setComp_seq(rs.getInt("comp_seq"));
			}	

			
			
			//비밀번호 업데이트 쿼리
			sql = " update tbl_comp_login set comp_password = ? "
					+ " where comp_id = ? ";

			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, newParaMap.get("new_passwd"));
			pstmt.setString(2, newParaMap.get("comp_id"));
			
			int n = pstmt.executeUpdate();
			
			if (n == 1) {
			//비번 업데이트 후 비번테이블에 insert처리 해야함 -> .. insert해야 하는거 
				sql = " insert into comp_pswd_threemon "
						+ " (comp_passwd, comp_id, comp_seq, COMP_PASSWD_SEQ) "
						+ " values (?,?,?, COMP_PASSWD_SEQ.nextval) ";
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, newParaMap.get("new_passwd"));
				pstmt.setString(2, newParaMap.get("comp_id"));
				pstmt.setInt(3, comdto.getComp_seq());
				
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
	
	

}
