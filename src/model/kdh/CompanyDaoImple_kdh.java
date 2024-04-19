package model.kdh;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


import dbConnect.MyDBConnection;
import domain.ApplicationDto;
import domain.CompanyDto;
import domain.CompanyLoginDto;
import domain.EmployeeDto;
import domain.RecruitDto;




public class CompanyDaoImple_kdh implements CompanyDao_kdh {
	
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
	
	



	// 우리회사 채용공고 조회에서 공고 일렬번호 입력시 해당 공고 상세보기 + 해당공고의 이력서 상세보기
	@Override
	public RecruitDto viewRecruitDetailList(int comp_seq, int recruit_seq) {
	

		/*
		 공고번호
		 공고제목
		 채용인원
		 회사이름
		 우대자격증
		 전형
		 직무
		 요구학력
		 요구경력
		 급여
		 모집시작일자
		 모집마감일자
		 합격자 발표일
		 모집공고담당자이름
		 모집공고담당자이메일
		 고용기간
		 채용공고 유지상태
		 ------------------------------------
		 이력서 번호	지원자성명
		 ------------------------------------
		 .
		 .
		 .
		 --> 이력서 상세보기 이력서 번호 : 
		 */
		RecruitDto rcdto = new RecruitDto();
		
		try {
			
				
			String sql = " select "
					+ " * "
					+ " from "
					+ " ( "
					+ " select "
					+ " * "
					+ " from tbl_recruit "
					+ " where comp_seq = ? "
					+ " )V "
					+ " join "
					+ " ( "
					+ " select A.recruit_seq, B.application_seq, C.emp_name "
					+ " from "
					+ " tbl_recruit_entry A "
					+ " join tbl_application B "
					+ " on A.application_seq = B.application_seq "
					+ " join tbl_employee C "
					+ " on B.emp_seq = C.emp_seq "
					+ " )V2 "
					+ " on V.recruit_seq = v2.recruit_seq "
					+ " where V.recruit_seq = ? ";
			
			
//			String sql = " select * "
//					+ " from tbl_recruit "
//					+ " where comp_seq = ? and recruit_seq = ? ";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, comp_seq);
			pstmt.setInt(2, recruit_seq);
			
			
			rs = pstmt.executeQuery();	// sql문 실행
			
			
			int count = 0;
			
			
			List<ApplicationDto> applicationList = new ArrayList<ApplicationDto>();
			while(rs.next()) {	// 행이 있냐
				
				if(count == 0)
				{
					rcdto = new RecruitDto();
					
					rcdto.setRecruit_seq(rs.getInt("recruit_seq"));
					rcdto.setRecruit_title(rs.getString("recruit_title"));
					rcdto.setRecruit_num(rs.getInt("recruit_num"));
					rcdto.setComp_name(rs.getString("comp_name"));
					rcdto.setLicense(rs.getString("license"));
					rcdto.setScreening(rs.getString("screening"));
					rcdto.setPosition(rs.getString("position"));
					rcdto.setEdu_level(rs.getString("edu_level"));
					rcdto.setExp_level(rs.getString("exp_level"));
					rcdto.setSalary(rs.getInt("salary"));
					rcdto.setRecruit_start_date(rs.getString("recruit_start_date"));
					rcdto.setRecruit_end_date(rs.getString("recruit_end_date"));
					rcdto.setResult_date(rs.getString("result_date"));
					rcdto.setRecruit_manager_name(rs.getString("recruit_manager_name"));
					rcdto.setRecruit_manager_email(rs.getString("recruit_manager_email"));
					rcdto.setHire_period(rs.getString("hire_period"));
					rcdto.setStatus(rs.getInt("status"));
				
				}
				

				ApplicationDto application = new ApplicationDto();

				application.setApplication_seq(rs.getInt("application_seq"));

				
				EmployeeDto employee = new EmployeeDto();
				employee.setEmp_name(rs.getString("emp_name"));
				application.setEmployeeDto(employee);

				
				////////////////////////////////////////////////////////////////////////////
				//bdto 안에 담겨진 내용 다 담아준다.
				
				applicationList.add(application);
				
				
				
			}// end of while ---------------------------------------------
			
			rcdto.setApplicationList(applicationList);
		} catch (SQLException e) {    // ip 가 틀렸거나 비밀번호가 틀렸거나 해당 exception을 try-catch 해준다.
					e.printStackTrace();
			} finally {				//성공하든 안하든 무조건 해야한다. (try - finally)
				close();
			}// end of finally--------------------------

			
			return rcdto;
		}// end of public List<BoardDTO> boardList()---------------------------------------------






}
