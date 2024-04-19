package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

import dbConnect.MyDBConnection;
import domain.EmployeeDto;
import domain.EmployeeLoginDto;

public class EmployeeDaoImple implements EmployeeDao {
	
	private Connection conn = MyDBConnection.getConn();
	private PreparedStatement pstmt;
	private ResultSet rs;
	
	private void close() {
		
		try {
			
			if(rs != null) rs.close();
			
			
			if(pstmt != null) pstmt.close();
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}

		
		
	}

	@Override
	public int register(EmployeeDto employeeDto, EmployeeLoginDto employeeloginDto) {
		
		int result = 0;
		
		try {
			
			conn.setAutoCommit(false);
			
			String sql = " insert into "
					+ "         tbl_employee(emp_seq, emp_id, emp_email, emp_name, jubun, emp_address, emp_tel) "
					+ " values(emp_seq.nextval, ?, ?, ?, ?, ?, ?)  ";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, employeeDto.getEmp_id());
			pstmt.setString(2, employeeDto.getEmp_email());
			pstmt.setString(3, employeeDto.getEmp_name());
			pstmt.setString(4, employeeDto.getJubun());
			pstmt.setString(5, employeeDto.getEmp_address());
			pstmt.setString(6, employeeDto.getEmp_tel());
			
			int n = pstmt.executeUpdate();
			
			if(n == 1) {
				
				sql = " select emp_seq "
						+ " from tbl_employee "
						+ " where emp_id = ? ";
				
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, employeeDto.getEmp_id());
				
				
				rs = pstmt.executeQuery();
				int emp_seq = -1;
				if(rs.next()) {
					
					emp_seq = rs.getInt(1);
					
					sql = " insert into "
						+ "         tbl_emp_login(emp_seq, emp_id, emp_password, emp_name) "
						+ " values( ?, ?, ?, ?)  ";
					
					
					pstmt = conn.prepareStatement(sql);
					pstmt.setInt(1, emp_seq);
					pstmt.setString(2, employeeloginDto.getEmp_id());
					pstmt.setString(3, employeeloginDto.getEmp_password());
					pstmt.setString(4, employeeloginDto.getEmp_name());
					
					
					n = pstmt.executeUpdate();
					
//					if(n == 1) {
//						
//						result = 1;
//						conn.commit();
//					}
					
					if(n != 1) {
						
						conn.rollback();
						return result;
					}
					
	
					sql = " insert into EMP_PSWD_THREEMON(EMP_PASSWD_SEQ, emp_seq, emp_passwd, emp_id) "
							+ " values(EMP_PASSWD_SEQ.nextval, ?, ?, ?) ";

					pstmt = conn.prepareStatement(sql);
					pstmt.setInt(1, emp_seq);
					pstmt.setString(2, employeeloginDto.getEmp_password());
					pstmt.setString(3, employeeDto.getEmp_id());
					
					
					n = pstmt.executeUpdate();
					
					if(n == 1) {
						
						conn.commit();
						result = 1;
					}else {
						conn.rollback();
						result = 0;
						
					}
					

					
					
				}
				
				
			}
			
			
			
		}catch (SQLException e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			
			e.printStackTrace();
			
			
		}catch (Exception e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
		finally {
			
			try {
				conn.setAutoCommit(true);
			} catch (SQLException e) {
	
				e.printStackTrace();
			}
			close();
		}
		
		
		return result;
	}

	@Override
	public EmployeeLoginDto login(Map<String, String> paraMap) {
		EmployeeLoginDto empLoginMem = null; // 변수
		try {
			// >>> 1. 오라클 드라이버 로딩 <<< 
			conn = MyDBConnection.getConn();
			String sql = " select A.emp_seq, A.emp_id, A. emp_password, A. emp_name, B.status "
					+ " from tbl_emp_login A RIGHT OUTER JOIN tbl_employee B "
					+ " ON A.EMP_SEQ = B.EMP_SEQ "
					+ " WHERE B.STATUS = 1 and A.emp_id = ? and A.emp_password = ? ";
			//status 로 회원탈퇴하지않은 회원만 가져와야하는데, login테이블에 없어서 조인했습니다
			
			pstmt = conn.prepareStatement(sql); 
			// 매핑을 해 준다.
			pstmt.setString(1, paraMap.get("userid")); 
			pstmt.setString(2, paraMap.get("passwd"));
			
			rs = pstmt.executeQuery();

			if(rs.next()) {
				empLoginMem = new EmployeeLoginDto();

				empLoginMem.setEmp_seq(rs.getInt("emp_seq"));
				empLoginMem.setEmp_id(rs.getString("emp_id"));
				empLoginMem.setEmp_name(rs.getString("emp_name"));
				empLoginMem.setEmp_password(rs.getString("emp_password"));
				
			}
			
		} catch (SQLException e) { 
			e.printStackTrace();
		} finally {
			close();
		}
		
		
		return empLoginMem;
	}
	
	

}
