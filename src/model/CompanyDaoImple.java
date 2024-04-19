package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

import dbConnect.MyDBConnection;
import domain.CompanyDto;
import domain.CompanyLoginDto;

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
	
	

	@Override
	public int memberRegister(CompanyDto member, CompanyLoginDto memberLogin) {
		int result = 0;

		try {
			conn.setAutoCommit(false);
			
			String sql = " insert into TBL_COMP(COMP_SEQ, COMP_ID, COMP_EMAIL, COMP_NAME, COMP_SCALE, COMP_EST_DATE, COMP_ADDR, COMP_CEO, COMP_EMP_CNT, COMP_CAPITAL, COMP_SALES, COMP_INSURANCE, INDUST_NAME) "
					+ " values(COMP_SEQ.nextval, ?,?,?,?,?,?,?,?,?,?,?, ?) ";

			pstmt = conn.prepareStatement(sql); // String sql insert 문을 전달해라, pstmt에 넣어줘야 null 값을 전달하지 않고 해당 insert문을
												// 전달한다.
												// 여기서는 위에 아이디나 암호를 잘못입력하더라도 null이 아니고 그 값 그대로 들어온다. -> 이는
												// executeUpdate() 에서 null로 처리된다. ? 11개

			// mapping
			// DTO 에 있다.
			pstmt.setString(1, member.getComp_id()); // 1번째 물음표에 넣을 값(userid)
			pstmt.setString(2, member.getComp_email());
			pstmt.setString(3, member.getComp_name()); // 
			pstmt.setString(4, member.getComp_scale());
			pstmt.setString(5, member.getComp_est_date());
			pstmt.setString(6, member.getComp_addr());
			pstmt.setString(7, member.getComp_ceo());
			pstmt.setInt(8, member.getComp_emp_cnt());
			pstmt.setInt(9, member.getComp_capital());
			pstmt.setInt(10, member.getComp_sales());
			pstmt.setString(11, member.getComp_insurance());
			pstmt.setString(12, member.getIndust_name());
			

			int n = pstmt.executeUpdate(); // insert 가 성공하면 result 값은 1
			
			
			
			if(n == 1) {
						
				sql = " select COMP_SEQ "
					+ " from TBL_COMP "
					+ " where COMP_ID = ? ";
				
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, member.getComp_id());
				
				
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
				pstmt.setString(2, memberLogin.getComp_id());
				pstmt.setString(3, memberLogin.getComp_password());
				pstmt.setString(4, memberLogin.getComp_name());
				
				
				n = pstmt.executeUpdate();
				
				if(n != 1) {
					
					conn.rollback();
					return result;
				}
				
				
				sql = " insert into COMP_PSWD_THREEMON(COMP_PASSWD_SEQ, comp_seq, comp_passwd, comp_id) "
						+ " values(COMP_PASSWD_SEQ.nextval, ?, ?, ?) ";

				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, comp_seq);
				pstmt.setString(2, memberLogin.getComp_password());
				pstmt.setString(3, member.getComp_id());
				
				
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
	public CompanyLoginDto login(Map<String, String> paraMap) {

		CompanyLoginDto comLoginMem = null; // 변수
		try {
			// >>> 1. 오라클 드라이버 로딩 <<< 
			conn = MyDBConnection.getConn();
			String sql =  " select A.comp_seq, A.comp_id, A.comp_password, A.comp_name, B.status "
					+ " from tbl_comp_login A RIGHT OUTER JOIN tbl_comp B "
					+ " ON A.COMP_SEQ = B.COMP_SEQ "
					+ " WHERE B.STATUS = 1 and A.COMP_id = ? and A.COMP_password = ? ";
			//status 로 탈퇴하지않은 회사만 가져와야하는데, login테이블에 없어서 조인했습니다
			
			pstmt = conn.prepareStatement(sql); 
			// 매핑을 해 준다.
			pstmt.setString(1, paraMap.get("userid")); 
			pstmt.setString(2, paraMap.get("passwd"));
			
			rs = pstmt.executeQuery();

			if(rs.next()) {
				comLoginMem = new CompanyLoginDto();

				comLoginMem.setComp_seq(rs.getInt("comp_seq"));
				comLoginMem.setComp_id(rs.getString("comp_id"));
				comLoginMem.setComp_name(rs.getString("comp_name"));
				comLoginMem.setComp_password(rs.getString("comp_password"));
				
			}
			
		} catch (SQLException e) { 
			e.printStackTrace();
		} finally {
			close();
		}
		
		
		return comLoginMem;
		
	}

}
