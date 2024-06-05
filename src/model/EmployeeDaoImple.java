package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import dbConnect.MyDBConnection;
import domain.*;
import dto.EmployeeDto;

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

	private List<Employee> createEmployeeList(ResultSet rs) throws SQLException {
		List<Employee> employeeList = new ArrayList<>();

		while(rs.next()) {
			Employee employee = new Employee(
					rs.getInt("EMP_SEQ"),
					rs.getString("EMP_ID"),
					rs.getString("EMP_NAME"),
					rs.getString("EMP_EMAIL"),
					rs.getString("JUBUN"),
					rs.getString("emp_address"),
					rs.getString("EMP_TEL"),
					rs.getInt("STATUS") // Assuming STATUS is an int, change to getString if it's stored as a string
			);

			employeeList.add(employee);
		}

		return employeeList;

	}

	private Employee createEmployee(ResultSet rs) throws SQLException {

        return new Employee(
				rs.getInt("EMP_SEQ"),
				rs.getString("EMP_ID"),
				rs.getString("emp_email"),
				rs.getString("EMP_name"),
				rs.getString("JUBUN"),
				rs.getString("emp_address"),
				rs.getString("EMP_TEL"),
				rs.getInt("STATUS") // Assuming STATUS is an int, change to getString if it's stored as a string
		);
	}


	@Override
	public Employee selectEmployeeBySeq(int emp_seq) {

		Employee employee = null;

		try {
			String sql = "select * "
					+ " from tbl_employee "
					+  " where emp_seq = ? ";

			pstmt = conn.prepareStatement(sql);

			pstmt.setInt(1, emp_seq);

			rs = pstmt.executeQuery();



			if(rs.next()) {
				employee = createEmployee(rs);


			}


		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close();
		}



		return employee;
	}

	//TODO : 객체생성과 데이터  불러오기 책임 분리해서 single responsibility  principle 지키기
	@Override
	public int insert(Map<String, String> userInput) {
		int result = 0;

		try {

			conn.setAutoCommit(false);

			String sql = " insert into "
					+ "         tbl_employee(emp_seq, emp_id, emp_email, emp_name, jubun, emp_address, emp_tel) "
					+ " values(emp_seq.nextval, ?, ?, ?, ?, ?, ?)  ";

			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, userInput.get("emp_id"));
			pstmt.setString(2, userInput.get("emp_email"));
			pstmt.setString(3, userInput.get("emp_name"));
			pstmt.setString(4, userInput.get("emp_jubun"));
			pstmt.setString(5, userInput.get("emp_address"));
			pstmt.setString(6, userInput.get("emp_tel"));

			int n = pstmt.executeUpdate();

			if(n == 1) {

				sql = " select emp_seq "
						+ " from tbl_employee "
						+ " where emp_id = ? ";

				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, userInput.get("emp_id"));


				rs = pstmt.executeQuery();
				int emp_seq = -1;
				if(rs.next()) {

					emp_seq = rs.getInt(1);

					sql = " insert into "
							+ "         tbl_emp_login(emp_seq, emp_id, emp_password, emp_name) "
							+ " values( ?, ?, ?, ?)  ";


					pstmt = conn.prepareStatement(sql);
					pstmt.setInt(1, emp_seq);
					pstmt.setString(2, userInput.get("emp_id"));
					pstmt.setString(3, userInput.get("emp_password"));
					pstmt.setString(4, userInput.get("emp_name"));


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
					pstmt.setString(2, userInput.get("emp_password"));
					pstmt.setString(3, userInput.get("emp_id"));


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
	public LoginObject selectById(String id) {
		LoginObject empLoginMem = null; // 변수
		try {
			// >>> 1. 오라클 드라이버 로딩 <<<
			conn = MyDBConnection.getConn();
			String sql = " select A.emp_seq, A.emp_id, A. emp_password, A. emp_name, B.status "
					+ " from tbl_emp_login A RIGHT OUTER JOIN tbl_employee B "
					+ " ON A.EMP_SEQ = B.EMP_SEQ "
					+ " WHERE B.STATUS = 1 and A.emp_id = ? ";
			//status 로 회원탈퇴하지않은 회원만 가져와야하는데, login테이블에 없어서 조인했습니다

			pstmt = conn.prepareStatement(sql);
			// 매핑을 해 준다.
			pstmt.setString(1, id);


			rs = pstmt.executeQuery();

			if(rs.next()) {
				empLoginMem = new LoginObject(rs.getInt("emp_seq"),
						rs.getString("emp_id"),
						rs.getString("emp_name"),
						rs.getString("emp_password"));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}


		return empLoginMem;
	}

	@Override
	public LoginObject selectByNameAndEmail(String name, String email) {
		LoginObject empLoginMem = null; // 변수
		try {
			// >>> 1. 오라클 드라이버 로딩 <<<
			conn = MyDBConnection.getConn();
			String sql = " select A.emp_seq, A.emp_id, A. emp_password, A. emp_name, B.status "
					+ " from tbl_emp_login A RIGHT OUTER JOIN tbl_employee B "
					+ " ON A.EMP_SEQ = B.EMP_SEQ "
					+ " WHERE B.STATUS = 1 and A.emp_name = ? and B.EMP_EMAIL = ?";
			//status 로 회원탈퇴하지않은 회원만 가져와야하는데, login테이블에 없어서 조인했습니다

			pstmt = conn.prepareStatement(sql);
			// 매핑을 해 준다.
			pstmt.setString(1, name);
			pstmt.setString(2, email);

			rs = pstmt.executeQuery();

			if(rs.next()) {
				empLoginMem = new LoginObject(rs.getInt("emp_seq"),
						rs.getString("emp_id"),
						rs.getString("emp_name"),
						rs.getString("emp_password"));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}


		return empLoginMem;
	}

	@Override
	public PasswordHistoryManager selectRecentPassword(String id) {

		List<PasswordThreeMonth> pswdList = new ArrayList<>();
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
			pstmt.setString(1, id); //map에 저장해둔 empid를 set
			ResultSet rs = pstmt.executeQuery(); //쿼리돌리기

			while(rs.next()) {
				PasswordThreeMonth histoy = new PasswordThreeMonth(rs.getInt(1),
						rs.getInt(2),
						rs.getString(3),
						rs.getDate(4));


				pswdList.add(histoy);
			}

		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}

		return new PasswordHistoryManager(pswdList);
	}

	@Override
	public int updatePassword(String id ,String newPassword) {
		int result = 0;
		// 시퀀스 가져오기 위한 아이디 객체 생성

		try {

			conn.setAutoCommit(false);

			//시퀀스 가져오기
			String sql = " select emp_seq "
					+ " from tbl_employee "
					+ " where emp_id = ? ";

			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id); //map에 저장해둔 empid를 set
			ResultSet rs = pstmt.executeQuery(); //쿼리돌리기

			int seq = -1;
			if(rs.next()) {
				seq = rs.getInt("emp_seq");
			}



			//비밀번호 업데이트 쿼리
			sql = " update tbl_emp_login set emp_password = ? "
					+ " where emp_id = ? ";

			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, newPassword);
			pstmt.setString(2, id);

			int n = pstmt.executeUpdate();

			if (n == 1) {
				//비번 업데이트 후 비번테이블에 insert처리 해야함 -> .. insert해야 하는거
				sql = " insert into emp_pswd_threemon "
						+ " (emp_passwd, emp_id, emp_seq, EMP_PASSWD_SEQ ) "
						+ " values (?, ?, ?, EMP_PASSWD_SEQ.nextval )";
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, newPassword);
				pstmt.setString(2, id);
				pstmt.setInt(3, seq);

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
	public int updateEmployee(EmployeeDto employeeDto) {
		int result = 0;
		try {
			conn.setAutoCommit(false); // 트랜잭션 시작

			// 구직자 정보 업데이트
			String sql = " UPDATE tbl_employee " + " SET EMP_EMAIL = ?, EMP_NAME = ?, EMP_ADDRESS = ?, "
					+ " EMP_TEL = ? " + " WHERE EMP_SEQ = ? ";

			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, employeeDto.getEmp_email());
			pstmt.setString(2, employeeDto.getEmp_name());
			pstmt.setString(3, employeeDto.getEmp_address());
			pstmt.setString(4, employeeDto.getEmp_tel());
			pstmt.setInt(5, employeeDto.getEmp_seq());

			int n1 = pstmt.executeUpdate();

			if (n1 == 1) {
				// 구직자 로그인 정보 업데이트
				sql = " UPDATE tbl_emp_login " + " SET  EMP_NAME = ? " + " WHERE EMP_SEQ = ? ";

				pstmt = conn.prepareStatement(sql);

				pstmt.setString(1, employeeDto.getEmp_name());
				pstmt.setInt(2, employeeDto.getEmp_seq());

				int n2 = pstmt.executeUpdate(); // sql 문 실행하기

				if (n2 == 1) { // 전제조건 n1 이 1 일 때(tbl_member 테이블에 update 가 성공되었다라면)
					conn.commit(); // 커밋을 해준다.
					result = 1;
				}
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
			} catch (SQLException e) {
				e.printStackTrace();
			}

			close(); // 자원 반납하기

		}
		return result;
	}


	//todo: 테스트 하기
	@Override
	public EmployeeManager selectEmployeeFromRecruitEntryBySeq(int seq) {

		List<Employee> employeeList = new ArrayList<>();
		try {
			//조회해오기.. rownum3만..
			String sql  =" select V3.emp_Seq, V3.emp_Id, V3.emp_email, V3.emp_name, V3.jubun, V3.emp_address, V3.emp_tel, v3.status, v3.join_date " +
					" from " +
					" ( " +
					"    select application_seq " +
					"    from  tbl_recruit_entry " +
					"    where entry_seq = ? " +
					" )V1 " +
					" join " +
					" ( " +
					"    select application_seq, emp_seq\n" +
					"    from tbl_application" +
					" )V2 " +
					" on v1.application_seq = v2.application_seq " +
					" join " +
					" ( " +
					"    select * " +
					"    from tbl_employee " +
					" )V3 " +
					" on v2.emp_seq = v3.emp_seq ";


			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, seq); //map에 저장해둔 empid를 set
			ResultSet rs = pstmt.executeQuery(); //쿼리돌리기

			employeeList = createEmployeeList(rs);

		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}

		return new EmployeeManager(employeeList);
	}

	@Override
	public int updateEmployeeStatusToDrop(int empSeq) {

		int result = 0;


		try {

			String sql = " update TBL_EMPLOYEE "
					+ " set "
					+ "     status = 0 "
					+ " where EMP_SEQ = ?";

			pstmt = conn.prepareStatement(sql);

			pstmt.setInt(1, empSeq);

			result = pstmt.executeUpdate();


		}catch (SQLException e) {
			e.printStackTrace();
		}

		catch (Exception e) {
			e.printStackTrace();
		}finally {
			close();
		}



		return result;


	}

	@Override
	public EmployeeManager selectAllEmployee() {
		List<Employee> employeeList = new ArrayList<>();
		try {

			String sql  =" select * " +
					" from tbl_employee ";


			pstmt = conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery(); //쿼리돌리기

			employeeList = createEmployeeList(rs);

		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}

		return new EmployeeManager(employeeList);
	}


}
