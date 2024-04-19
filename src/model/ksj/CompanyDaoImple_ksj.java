package model.ksj;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dbConnect.MyDBConnection;
import domain.ApplicationDto;
import domain.CompanyDto;
import domain.CompanyLoginDto;
import domain.EmployeeDto;
import domain.RecruitEntryDto;

public class CompanyDaoImple_ksj implements CompanyDao_ksj {

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

	// *** 회사정보 수정할 때 회사 정보 보여주는 메소드 *** //
	@Override
    public CompanyLoginDto viewContents(int comp_seq) {

        CompanyLoginDto compLoginDto = new CompanyLoginDto();

        try {
            String sql = " SELECT COMP_SEQ, COMP_ID, COMP_PASSWORD, COMP_NAME "
                    + " FROM tbl_comp_login "
                    + " WHERE COMP_SEQ = ? ";

            pstmt = conn.prepareStatement(sql);

            pstmt.setInt(1, comp_seq);

            rs = pstmt.executeQuery();

            if (rs.next()) {
                	compLoginDto.setComp_seq(rs.getInt("COMP_SEQ"));
                    compLoginDto.setComp_id(rs.getString("COMP_ID"));
                    compLoginDto.setComp_password(rs.getString("COMP_PASSWORD"));
                    compLoginDto.setComp_name(rs.getString("COMP_NAME"));

                sql = " SELECT COMP_SEQ, COMP_ID, COMP_EMAIL, COMP_NAME, COMP_SCALE, COMP_EST_DATE, COMP_ADDR, COMP_CEO, "
                        + "        COMP_EMP_CNT, COMP_CAPITAL, COMP_SALES, COMP_INSURANCE, STATUS "
                        + " FROM TBL_COMP "
                        + " WHERE COMP_SEQ = ? ";

                pstmt = conn.prepareStatement(sql);

                pstmt.setInt(1, comp_seq);
                rs = pstmt.executeQuery();

                if (rs.next()) {
                    CompanyDto cdto = new CompanyDto();

                    cdto.setComp_seq(rs.getInt("COMP_SEQ"));
                    cdto.setComp_id(rs.getString("COMP_ID"));
                    cdto.setComp_email(rs.getString("COMP_EMAIL"));
                    cdto.setComp_name(rs.getString("COMP_NAME"));
                    cdto.setComp_scale(rs.getString("COMP_SCALE"));
                    cdto.setComp_est_date(rs.getString("COMP_EST_DATE"));
                    cdto.setComp_addr(rs.getString("COMP_ADDR"));
                    cdto.setComp_ceo(rs.getString("COMP_CEO"));
                    cdto.setComp_emp_cnt(rs.getInt("COMP_EMP_CNT"));
                    cdto.setComp_capital(rs.getInt("COMP_CAPITAL"));
                    cdto.setComp_sales(rs.getInt("COMP_SALES"));
                    cdto.setComp_insurance(rs.getString("COMP_INSURANCE"));
                    cdto.setStatus(rs.getInt("STATUS"));

                    compLoginDto.setCdto(cdto);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close();
        }

        return compLoginDto;
    } // end of public CompanyDto viewContents(int comp_seq)-----------------------------------
	// *** 회사정보 수정할 때 회사 정보 보여주는 메소드 끝  *** //



	
	// 회사정보 수정하기 //
	@Override
	public int update_emp_info(CompanyLoginDto companyLoginDto) {
		int result = 0;
		try {
			conn.setAutoCommit(false); // 트랜잭션 시작

			// 회사 정보 업데이트
			String sql = " UPDATE TBL_COMP " 
				+ " SET COMP_EMAIL = ?, COMP_NAME = ?, COMP_ADDR = ?, "
				+ " COMP_SCALE = ?, COMP_CEO = ?, COMP_EMP_CNT = ?, COMP_CAPITAL = ?, "
				+ " COMP_SALES = ?, COMP_INSURANCE = ? " 
				+ " WHERE COMP_SEQ = ? ";

			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, companyLoginDto.getCdto().getComp_email());
			pstmt.setString(2, companyLoginDto.getCdto().getComp_name());
			pstmt.setString(3, companyLoginDto.getCdto().getComp_addr());
			pstmt.setString(4, companyLoginDto.getCdto().getComp_scale());
			pstmt.setString(5, companyLoginDto.getCdto().getComp_ceo());
			pstmt.setInt(6, companyLoginDto.getCdto().getComp_emp_cnt());
			pstmt.setInt(7, companyLoginDto.getCdto().getComp_capital());
			pstmt.setInt(8, companyLoginDto.getCdto().getComp_sales());
			pstmt.setString(9, companyLoginDto.getCdto().getComp_insurance());
			pstmt.setInt(10, companyLoginDto.getCdto().getComp_seq());

			int n1 = pstmt.executeUpdate(); // sql 문 실행하기

			if (n1 == 1) {

				// 회사 로그인 정보 업데이트
				sql = " UPDATE tbl_comp_login " 
					+ " SET COMP_PASSWORD = ?, COMP_NAME = ? " 
					+ " WHERE COMP_SEQ = ? ";

				pstmt = conn.prepareStatement(sql);

				pstmt.setString(1, companyLoginDto.getComp_password());
				pstmt.setString(2, companyLoginDto.getComp_name());
				pstmt.setInt(3, companyLoginDto.getComp_seq());

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
	// 회사정보 수정하기 끝 //
	
	
	
	// *** 회사 탈퇴 메소드  *** //
	@Override
	public int drop_Member(int comp_seq) {
		int result = 0;
		try {
			conn.setAutoCommit(false); // 트랜잭션 시작
			
			System.out.println(comp_seq);
			
			// 구직자 정보 업데이트
			String sql = " UPDATE TBL_COMP " 
				+ " SET STATUS = 0 " 
				+ " WHERE COMP_SEQ = ? ";

			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, comp_seq);
		
			int n1 = pstmt.executeUpdate(); // sql 문 실행하기

			if (n1 == 1) {
				conn.commit(); // 커밋을 해준다.
				result = 1;	
			}
			
		} catch (SQLException e) {

			try {
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

			close(); // 자원 반납하

		}
		return result;
	}
	// *** 회사 탈퇴 메소드 끝  *** //
	
	@Override
	public List<RecruitEntryDto> searchApplicantsByJobPosting(int comp_seq) {
		
		List<RecruitEntryDto> RecruitEntryList = new ArrayList<>();
		
		try {
			
			String sql  = "select "
					+ "    r.recruit_seq "
					+ "    , A.application_seq "
					+ "    , E.emp_name "
					+ "    ,military_seq "
					+ "    ,hope_sal "
					+ "    , motive "
					+ "    , emp_tel "
					+ "    ,emp_email "
					+ " from tbl_recruit R join tbl_recruit_entry RE"
					+ " on r.recruit_seq = RE.recruit_seq "
					+ " join tbl_application A "
					+ " on RE.application_seq = A.application_seq "
					+ " join tbl_employee E "
					+ " on A.emp_seq = E.emp_seq "
					+ " where r.comp_seq = ? ";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, comp_seq);
			
			rs = pstmt.executeQuery();
			
			
			while(rs.next()) {
				
				RecruitEntryDto recruitEntryDto =  new RecruitEntryDto();
				
				recruitEntryDto.setApplication_seq(rs.getInt(1));
				recruitEntryDto.setApplication_seq(rs.getInt(2));
				
				EmployeeDto employeeDto = new EmployeeDto();
				ApplicationDto applicationDto = new ApplicationDto();
				
				employeeDto.setEmp_name(rs.getString(3));
				applicationDto.setMilitary_seq(rs.getInt(4));
				applicationDto.setHope_sal(rs.getInt(5));
				applicationDto.setMotive(rs.getString(6));
				employeeDto.setEmp_tel(rs.getString(7));
				employeeDto.setEmp_email(rs.getString(8));
				
				
				
				recruitEntryDto.setApplicationDto(applicationDto);
				recruitEntryDto.setEmployeeDto(employeeDto);
				
				RecruitEntryList.add(recruitEntryDto);
				
			}
			
			
			
		} catch (SQLException e) {
			// SQLException 발생 시 로그 기록
			e.printStackTrace();
		} finally {
			close(); // close() 메소드가 리소스를 제대로 닫고 있는지 확인
		}
		
		return RecruitEntryList;
	}




}
