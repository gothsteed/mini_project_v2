package model.kdh;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import dbConnect.MyDBConnection;
import domain.RecruitDto;


public class EmployeeDaoImple_kdh implements EmployeeDao_kdh {


	private Connection conn = MyDBConnection.getConn();
	private PreparedStatement pstmt;
	private ResultSet rs;

	// 우리회사 채용공고 조회에서 공고 일렬번호 입력시 해당 공고 상세보기
	@Override
	public RecruitDto viewRecruitDetail(int recruit_seq) {
		
		RecruitDto rdto = null;

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
		 */
	
		try {
			
				conn = MyDBConnection.getConn();
				
//				String sql = " select v.recruit_seq, v.recruit_title, v.recruit_num, v.comp_name, v.license, v.screening, v.position, v.edu_level, v.exp_level, v.salary, v.recruit_start_date, v.recruit_end_date "
//						+ " , v.result_date, v.recruit_manager_name, v.recruit_manager_email, v.hire_period, v.status, "
//						+ " v.recruit_seq "
//						+ " from "
//						+ " ( "
//						+ " select "
//						+ " * "
//						+ " from tbl_recruit "
//						+ " where recruit_seq = ? "
//						+ " )V "
//						+ " join "
//						+ " ( "
//						+ " select A.recruit_seq, B.application_seq, C.emp_name "
//						+ " from "
//						+ " tbl_recruit_entry A "
//						+ " join tbl_application B "
//						+ " on A.application_seq = B.application_seq "
//						+ " join tbl_employee C "
//						+ " on B.emp_seq = C.emp_seq "
//						+ " )V2 "
//						+ " on V.recruit_seq = v2.recruit_seq ";
				
				String sql = " select recruit_seq, recruit_title, recruit_num, comp_name, license, screening, position, edu_level, exp_level, salary, recruit_start_date, recruit_end_date, "
						+ " result_date, recruit_manager_name, recruit_manager_email, hire_period, status "
						+ " from tbl_recruit "
						+ " where recruit_seq = ? ";

				
				pstmt = conn.prepareStatement(sql);

				pstmt.setInt(1, recruit_seq);

				rs = pstmt.executeQuery();	// sql문 실행
				
				if(rs.next()) {	// 행이 있냐
					rdto = new RecruitDto();
					
					rdto.setRecruit_seq(rs.getInt("recruit_seq"));
					rdto.setRecruit_title(rs.getString("recruit_title"));
					rdto.setRecruit_num(rs.getInt("recruit_num"));
					rdto.setComp_name(rs.getString("comp_name"));
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
					rdto.setStatus(rs.getInt("status"));
					
					
						
					
				}// end of while ---------------------------------------------
			} catch (SQLException e) {    // ip 가 틀렸거나 비밀번호가 틀렸거나 해당 exception을 try-catch 해준다.
					e.printStackTrace();
			}
			
			return rdto;
		}// end of 	public List<RecruitDto> viewRecruitDetailList(int comp_seq, int recruit_seq)---------------------------------------------


	
	// 6. (구직자가) 모든채용공고 조회 
	
	@Override
	public List<RecruitDto> viewCompanyallRecruits() {				//status가 0인 회사 공고는 보여주지 않는다.
		/*
		 공고번호
		 공고제목
		 시작일자
		 마감날짜
		 통계
		 --> 성별, 연령대
		 */
		List<RecruitDto> viewRecruitallList = new ArrayList<>();
		
		try {
			
				conn = MyDBConnection.getConn();
				
				String sql = " select * "
						+ " from tbl_recruit "
						+ " where status = 1 and to_char(recruit_end_date, 'yyyy-mm-dd') >= to_char(sysdate, 'yyyy-mm-dd') ";
				
				
				pstmt = conn.prepareStatement(sql);
				rs = pstmt.executeQuery();	// sql문 실행
				
				while(rs.next()) {	// 행이 있냐
					
					RecruitDto rcdto = new RecruitDto();		
					
					rcdto.setRecruit_seq(rs.getInt("recruit_seq"));
					rcdto.setRecruit_title(rs.getString("recruit_title"));
					rcdto.setRecruit_start_date(rs.getString("recruit_start_date"));
					rcdto.setRecruit_end_date(rs.getString("recruit_end_date"));
					
					
					viewRecruitallList.add(rcdto);					//bdto 안에 담겨진 내용 다 담아준다.
					
				}// end of while ---------------------------------------------
				  Scanner sc = new Scanner(System.in);
				



				
			} catch (SQLException e) {    // ip 가 틀렸거나 비밀번호가 틀렸거나 해당 exception을 try-catch 해준다.
					e.printStackTrace();
			}
			
			return viewRecruitallList;
	}
	
	
	
	


	

	   
}