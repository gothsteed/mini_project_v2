package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import dbConnect.MyDBConnection;
import domain.*;
import dto.CompanyDto;

public class CompanyDaoImple implements CompanyDao {
	
	private Connection conn = MyDBConnection.getConn();;
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
			// if (conn != null) {conn.close(); conn = null;}	
			// 지금은 싱글톤 패턴을 사용했기 때문에 프로그램 종료할 때만 사용한다. conn.close() 사용하면 안된다!(conn은 DB 연결)//
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	//TODO : 객체생성과 데이터  불러오기 책임 분리해서 single responsibility  principle 지키기
	@Override
	public int insert(Map<String, String> userInput) {
		int result = 0;

		try {
			conn.setAutoCommit(false);

			String sql = " insert into TBL_COMP(COMP_SEQ, COMP_ID, COMP_EMAIL, COMP_NAME, COMP_SCALE," +
					" COMP_EST_DATE, COMP_ADDR, COMP_CEO, COMP_EMP_CNT, COMP_CAPITAL," +
					" COMP_SALES, COMP_INSURANCE, INDUST_NAME) "
					+ " values(COMP_SEQ.nextval, ?,?,?,?,?,?,?,?,?,?,?, ?) ";

			pstmt = conn.prepareStatement(sql); // String sql insert 문을 전달해라, pstmt에 넣어줘야 null 값을 전달하지 않고 해당 insert문을
			// 전달한다.
			// 여기서는 위에 아이디나 암호를 잘못입력하더라도 null이 아니고 그 값 그대로 들어온다. -> 이는
			// executeUpdate() 에서 null로 처리된다. ? 11개

			// mapping
			// DTO 에 있다.
			pstmt.setString(1, userInput.get("comp_id")); // 1번째 물음표에 넣을 값(userid)
			pstmt.setString(2, userInput.get("comp_email"));
			pstmt.setString(3, userInput.get("comp_name")); //
			pstmt.setString(4, userInput.get("comp_scale"));
			pstmt.setString(5, userInput.get("comp_est_date"));
			pstmt.setString(6, userInput.get("comp_addr"));
			pstmt.setString(7, userInput.get("comp_ceo"));
			pstmt.setInt(8, Integer.parseInt(userInput.get("comp_emp_cnt")));
			pstmt.setInt(9, Integer.parseInt(userInput.get("comp_capital")));
			pstmt.setInt(10, Integer.parseInt(userInput.get("comp_sales")));
			pstmt.setString(11, userInput.get("comp_insurance"));
			pstmt.setString(12, userInput.get("indust_name"));


			int n = pstmt.executeUpdate(); // insert 가 성공하면 result 값은 1



			if(n == 1) {

				sql = " select COMP_SEQ "
						+ " from TBL_COMP "
						+ " where COMP_ID = ? ";

				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, userInput.get("comp_id"));


				rs = pstmt.executeQuery();

				int comp_seq = -1;
				if(rs.next()) {
					comp_seq = rs.getInt(1);

				}


				if(comp_seq == -1) {
					conn.rollback();
					return result;

				}


				sql = " insert into TBL_COMP_LOGIN(COMP_SEQ, COMP_ID, COMP_PASSWORD, COMP_NAME) "
						+ " values(?, ?, ?, ?)  ";


				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, comp_seq);
				pstmt.setString(2, userInput.get("comp_id"));
				pstmt.setString(3, userInput.get("comp_password"));
				pstmt.setString(4, userInput.get("comp_name"));


				n = pstmt.executeUpdate();

				if(n != 1) {

					conn.rollback();
					return result;
				}


				sql = " insert into COMP_PSWD_THREEMON(COMP_PASSWD_SEQ, comp_seq, comp_passwd, comp_id) "
						+ " values(COMP_PASSWD_SEQ.nextval, ?, ?, ?) ";

				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, comp_seq);
				pstmt.setString(2, userInput.get("comp_passwd"));
				pstmt.setString(3, userInput.get("comp_id"));


				n = pstmt.executeUpdate();

				if(n == 1) {

					conn.commit();
					result = 1;
				}else {
					conn.rollback();
					result = 0;

				}


			}


		} catch (SQLException e) { // ip 가 틀렸거나 비밀번호가 틀렸거나 해당 exception을 try-catch 해준다.

			if (e.getErrorCode() == 1) {
				// e.getErrorCode() == 1 는 중복에러일 경우(id만 중복된다. userseq 는 시퀀스이기 때문에 중복불가)
				// userid unique(userid)
				System.out.println(">> 아이디가 중복되었습니다. 새로운 아이디를 입력하세요!!");
			} else { // 아이디 중복 외의 Exception 일 경우
				e.printStackTrace();
			}

			try {
				conn.rollback(); // 롤백을 해준다.
				result = -1; // DB 에서 문제가 생겼을 경우
			} catch (SQLException e1) {
				e1.printStackTrace();
			}

		} finally { // 성공하든 안하든 무조건 해야한다. (try - finally)
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
	public LoginObject selectById(String userId) {
		LoginObject comLoginMem = null; // 변수
		try {
			// >>> 1. 오라클 드라이버 로딩 <<<
			conn = MyDBConnection.getConn();
			String sql =  " select A.comp_seq, A.comp_id, A.comp_password, A.comp_name, B.status "
					+ " from tbl_comp_login A RIGHT OUTER JOIN tbl_comp B "
					+ " ON A.COMP_SEQ = B.COMP_SEQ "
					+ " WHERE B.STATUS = 1 and A.COMP_id = ? ";
			//status 로 탈퇴하지않은 회사만 가져와야하는데, login테이블에 없어서 조인했습니다

			pstmt = conn.prepareStatement(sql);
			// 매핑을 해 준다.
			pstmt.setString(1, userId);

			rs = pstmt.executeQuery();

			if(rs.next()) {
				comLoginMem = new LoginObject(rs.getInt("comp_seq"),
						rs.getString("comp_id"),
						rs.getString("comp_name"),
						rs.getString("comp_password"));


			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}


		return comLoginMem;
	}

	@Override
	public LoginObject selectByNameAndEmail(String name, String email) {
		LoginObject cldto = null;

		try {
			conn = MyDBConnection.getConn();
			String sql = " select A.comp_seq, A.comp_id, A.comp_password, A.comp_name, B.status "
					+ " from tbl_comp_login A RIGHT OUTER JOIN tbl_comp B "
					+ " ON A.COMP_SEQ = B.COMP_SEQ "
					+ " WHERE B.STATUS = 1 and A.COMP_NAME = ? and COMP_EMAIL = ? ";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, name);
			pstmt.setString(2, email);

			ResultSet rs = pstmt.executeQuery();

			if(rs.next()) {
				cldto = new LoginObject(rs.getInt("comp_seq"),
						rs.getString("comp_id"),
						rs.getString("comp_password"),
						rs.getString("comp_name"));
			}

		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}


		return cldto;
	}

	@Override
	public PasswordHistoryManager selectRecentPassword(String id) {
		List<PasswordThreeMonth> pswdList = new ArrayList<>();
		try {
			conn = MyDBConnection.getConn();
			//조회해오기.. rownum3만..
			String sql = " SELECT COMP_PASSWD_SEQ, COMP_SEQ, COMP_PASSWD, MODIFY_DATE "
					+ " FROM "
					+ " ( "
					+ " SELECT rownum AS RNO, COMP_PASSWD_SEQ, COMP_SEQ, COMP_PASSWD, modify_date "
					+ " FROM "
					+ " ( "
					+ " select COMP_PASSWD,COMP_PASSWD_SEQ, COMP_SEQ, to_char(modify_date, 'yyyy-mm-dd hh24:mi:ss') AS modify_date "
					+ " from COMP_PSWD_THREEMON "
					+ " where COMP_ID = ? "
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
	public int updatePassword(String id, String newPassword) {
		int result = 0;
		try {
			conn = MyDBConnection.getConn();
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
	private List<Company> createCompanyList(ResultSet rs) throws SQLException {
		List<Company> companies = new ArrayList<>();


		while(rs.next()) {
			Company comp = createCompany(rs);

			companies.add(comp);
		}


		return companies;
	}

	private Company createCompany(ResultSet rs) throws SQLException {

        return new Company(
				rs.getInt("comp_seq"),
				rs.getString("comp_id"),
				rs.getString("comp_email"),
				rs.getString("indust_name"),
				rs.getString("comp_name"),
				rs.getString("comp_scale"),
				rs.getDate("comp_est_date"),
				rs.getString("comp_addr"),
				rs.getString("comp_ceo"),
				rs.getInt("comp_emp_cnt"),
				rs.getInt("comp_capital"),
				rs.getInt("COMP_SALES"),
				rs.getString("comp_insurance"),
				rs.getInt("STATUS")
		);
	}




	@Override
	public CompanyManager selectAllCompany() {
		List<Company> companyList = new ArrayList<>();


		try {
			conn = MyDBConnection.getConn();

			String sql = " select *"
					+ " from tbl_comp ";

			pstmt = conn.prepareStatement(sql);

			rs = pstmt.executeQuery();

			companyList = createCompanyList(rs);

		} catch (SQLException e) {
			e.printStackTrace();

		} finally {
			close();
		}

		return new CompanyManager(companyList);

	}

	@Override
	public CompanyManager selectCompanyByName(String name) {
		List<Company> compList = new ArrayList<>();
		try {
			conn = MyDBConnection.getConn();

			String sql = " select * "
					+ " from tbl_comp "
					+ " where comp_name LIKE ? ";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, "%" +name+ "%");
			ResultSet rs = pstmt.executeQuery();

			compList = createCompanyList(rs);
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return new CompanyManager(compList);
	}

	@Override
	public CompanyManager selectCompanyCapitalBiggerThan(int capital) {
		List<Company> compList = new ArrayList<>();

		try {
			String sql = " select *"
					+ " from tbl_comp "
					+ " where comp_capital > ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, capital);
			ResultSet rs = pstmt.executeQuery();

			compList = createCompanyList(rs);
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return new CompanyManager(compList);
	}

	@Override
	public CompanyManager selectCompanyByEmployeeCount(int min, int max) {
		List<Company> result = new ArrayList<>();


		String sql = " select * "
				+ " from tbl_comp "
				+ " where COMP_EMP_CNT between ? and ? "
				+ "  and status = 1  ";


		try {
			pstmt = conn.prepareStatement(sql);

			pstmt.setInt(1, min);
			pstmt.setInt(2, max);

			rs = pstmt.executeQuery();

			result = createCompanyList(rs);

		} catch (SQLException e) {

			e.printStackTrace();

		} finally {
			close();
		}


		return new CompanyManager(result);
	}

	@Override
	public CompanyManager selectCompanyBySales(int min, int max) {

		List<Company> result = new ArrayList<>();

		String sql = " select * "
				+ " from tbl_comp "
				+ " where comp_sales between ? and ? "
				+ " and status = 1 ";

		try {
			pstmt = conn.prepareStatement(sql);

			pstmt.setInt(1, min);
			pstmt.setInt(2, max);

			rs = pstmt.executeQuery();

			result = createCompanyList(rs);

		} catch (SQLException e) {

			e.printStackTrace();
		}finally {
			close();
		}


		return new CompanyManager(result);

	}

	@Override
	public Company getCompany(int compSeq) {
		Company comp = null;


		try {
			conn = MyDBConnection.getConn();

//			String sql = " SELECT C.COMP_ID, L.COMP_PASSWORD, C.COMP_NAME, C.COMP_SCALE, C.COMP_ADDR, C.COMP_EMAIL, COMP_CEO "
//					+ "      , C.COMP_EMP_CNT, C.COMP_SALES, C.COMP_CAPITAL, C.COMP_EST_DATE, C.COMP_INSURANCE, c.indust_name"
//					+ " FROM TBL_COMP C JOIN TBL_COMP_LOGIN L  "
//					+ " ON C.COMP_ID = L.COMP_ID "
//					+ " where L.comp_seq = ? " ;

			String sql = " select *  " +
					" from tbl_comp " +
					" where COMP_SEQ = ? ";

			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, compSeq);

			rs = pstmt.executeQuery();

			if (rs.next()) {
				comp = createCompany(rs);

			}//end of while----------------------------

		}catch (SQLException e) {
			e.printStackTrace();

		} finally {
			close();
		}

		return comp;
	}

	@Override
	public int updateCompany(CompanyDto companyDto) {
		int result = 0;
		try {
			conn.setAutoCommit(false); // 트랜잭션 시작

			// 구직자 정보 업데이트
			String sql = " UPDATE TBL_COMP " +
					"SET " +
					"    COMP_ID = ?, " +
					"    COMP_EMAIL = ?, " +
					"    INDUST_NAME = ?, " +
					"    COMP_NAME = ?, " +
					"    COMP_SCALE = ?, " +
					"    COMP_EST_DATE = ?, " +
					"    COMP_ADDR = ?, " +
					"    COMP_CEO = ?, " +
					"    COMP_EMP_CNT = ?, " +
					"    COMP_CAPITAL = ?, " +
					"    COMP_SALES = ?, " +
					"    COMP_INSURANCE = ? " +
					" where COMP_SEQ = ?";

			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, companyDto.getComp_id());
			pstmt.setString(2, companyDto.getComp_email());
			pstmt.setString(3, companyDto.getIndust_name());
			pstmt.setString(4, companyDto.getComp_name());
			pstmt.setString(5, companyDto.getComp_scale());

			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");
			String compEstDate = simpleDateFormat.format(companyDto.getComp_est_date());
			pstmt.setString(6, compEstDate);
			pstmt.setString(7, companyDto.getComp_addr());
			pstmt.setString(8, companyDto.getComp_ceo());
			pstmt.setInt(9, companyDto.getComp_emp_cnt());
			pstmt.setInt(10, companyDto.getComp_capital());
			pstmt.setInt(11, companyDto.getComp_sales());
			pstmt.setString(12, companyDto.getComp_insurance());
			pstmt.setInt(13, companyDto.getComp_seq());


			int n1 = pstmt.executeUpdate();

			if (n1 == 1) {
				// 구직자 로그인 정보 업데이트
				sql = " UPDATE TBL_COMP_LOGIN " + " SET  COMP_NAME = ? " + " WHERE COMP_SEQ = ? ";

				pstmt = conn.prepareStatement(sql);

				pstmt.setString(1, companyDto.getComp_name());
				pstmt.setInt(2, companyDto.getComp_seq());

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

	@Override
	public int updateCompanyToDrop(int compSeq) {
		int result = 0;
		try {

			System.out.println(compSeq);

			// 구직자 정보 업데이트
			String sql = " UPDATE TBL_COMP "
					+ " SET STATUS = 0 "
					+ " WHERE COMP_SEQ = ? ";

			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, compSeq);

			int n1 = pstmt.executeUpdate(); // sql 문 실행하기

			if (n1 == 1) {
				result = 1;
			}

		} catch (SQLException e) {
			e.printStackTrace();

		} finally { // 성공하든 안하든 무조건 해야한다. (try - finally
			close(); // 자원 반납하

		}
		return result;
	}


}
