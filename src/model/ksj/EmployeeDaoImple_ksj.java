package model.ksj;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dbConnect.MyDBConnection;
import domain.EmployeeDto;
import domain.EmployeeLoginDto;
import domain.RecruitDto;
import domain.RecruitEntryDto;

public class EmployeeDaoImple_ksj implements EmployeeDao_ksj {

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

	// *** 구직자 정보 수정할 때 회사 정보 보여주는 메소드 *** //
	@Override
	public EmployeeLoginDto viewContents(int emp_seq) {

		EmployeeLoginDto empLoginDto = new EmployeeLoginDto();

		try {
			String sql = " SELECT EMP_SEQ, EMP_ID, EMP_PASSWORD, EMP_NAME " + " FROM TBL_EMP_LOGIN "
					+ " WHERE emp_seq = ? ";

			pstmt = conn.prepareStatement(sql);

			pstmt.setInt(1, emp_seq); // 1번째 물음표에 넣을 값(COMP_SEQ)

			rs = pstmt.executeQuery(); // sql 문 실행

			if (rs.next()) {

				empLoginDto.setEmp_seq(rs.getInt("EMP_SEQ"));
				empLoginDto.setEmp_id(rs.getString("EMP_ID"));
				empLoginDto.setEmp_password(rs.getString("EMP_PASSWORD"));
				empLoginDto.setEmp_name(rs.getString("EMP_NAME"));

				sql = " select EMP_SEQ, EMP_ID, EMP_EMAIL, EMP_NAME, JUBUN, EMP_ADDRESS, EMP_TEL, STATUS "
						+ " from tbl_employee " + " WHERE emp_seq = ? ";

				pstmt = conn.prepareStatement(sql);

				pstmt.setInt(1, emp_seq); // 1번째 물음표에 넣을 값(boardno)
				rs = pstmt.executeQuery(); // sql 문 실행

				if (rs.next()) {
					EmployeeDto edto = new EmployeeDto();

					edto.setEmp_seq(rs.getInt("EMP_SEQ"));
					edto.setEmp_id(rs.getString("EMP_ID"));
					edto.setEmp_email(rs.getString("EMP_EMAIL"));
					edto.setEmp_name(rs.getString("EMP_NAME"));
					edto.setJubun(rs.getString("JUBUN"));
					edto.setEmp_address(rs.getString("EMP_ADDRESS"));
					edto.setEmp_tel(rs.getString("EMP_TEL"));
					edto.setStatus(rs.getInt("STATUS"));

					empLoginDto.setEmployeeDto(edto);
				}
			}

		} catch (SQLException e) {
			// SQLException 발생 시 로그 기록
			e.printStackTrace();
		} finally {
			close(); // close() 메소드가 리소스를 제대로 닫고 있는지 확인
		}

		return empLoginDto;
	} // end of public EmployeeLoginDto viewContents(int
		// emp_seq)-----------------------------------
		// *** 구직자 정보 수정할 때 회사 정보 보여주는 메소드 끝 *** //

	// *** 구직자 정보 수정 메소드 *** //
	@Override
	public int update_emp_info(EmployeeLoginDto employeeLoginDto) {
		int result = 0;
		try {
			conn.setAutoCommit(false); // 트랜잭션 시작

			// 구직자 정보 업데이트
			String sql = " UPDATE tbl_employee " + " SET EMP_EMAIL = ?, EMP_NAME = ?, EMP_ADDRESS = ?, "
					+ " EMP_TEL = ? " + " WHERE EMP_SEQ = ? ";

			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, employeeLoginDto.getEmployeeDto().getEmp_email());
			pstmt.setString(2, employeeLoginDto.getEmployeeDto().getEmp_name());
			pstmt.setString(3, employeeLoginDto.getEmployeeDto().getEmp_address());
			pstmt.setString(4, employeeLoginDto.getEmployeeDto().getEmp_tel());
			pstmt.setInt(5, employeeLoginDto.getEmployeeDto().getEmp_seq());

			int n1 = pstmt.executeUpdate();
			
			if (n1 == 1) {
				// 구직자 로그인 정보 업데이트
				sql = " UPDATE tbl_emp_login " + " SET EMP_PASSWORD = ?, EMP_NAME = ? " + " WHERE EMP_SEQ = ? ";

				pstmt = conn.prepareStatement(sql);

				pstmt.setString(1, employeeLoginDto.getEmp_password());
				pstmt.setString(2, employeeLoginDto.getEmp_name());
				pstmt.setInt(3, employeeLoginDto.getEmp_seq());

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
	// *** 구직자 정보 수정 메소드 끝 *** /

	// *** 구직자 탈퇴 메소드 *** //
	@Override
	public int drop_Member(int emp_seq) {
		int result = 0;


		try {
			conn.setAutoCommit(false); // 트랜잭션 시작

			// 구직자 로그인 정보 업데이트
			String sql = " UPDATE TBL_EMPLOYEE SET STATUS = 0 " + " WHERE EMP_SEQ = ? ";

			pstmt = conn.prepareStatement(sql);

			pstmt.setInt(1, emp_seq);

			int n1 = pstmt.executeUpdate();
			if (n1 == 1) {
				conn.commit(); // 커밋을 해준다.
				result = 1;
			}

		} catch (SQLException e) {
			e.printStackTrace();
			try {
				conn.rollback(); // 롤백을 해준다.
				result = -1; // DB 에서 문제가 생겼을 경우
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} finally { // 성공하든 안하든 무조건 해야한다. (try - finally)
			try {
				if (conn != null)
				conn.setAutoCommit(true); // 자동 commit 으로 복원시킨다.
			} catch (SQLException e) {
				e.printStackTrace();
			}
			close(); // 자원 반납
		}
		return result;
	}
	// *** 구직자 탈퇴 메소드 끝 *** //

	// 8. 채용응모한것조회 메소드 //
	@Override
	public List<RecruitEntryDto> JobApplicationInquiry(int emp_seq) {

		RecruitEntryDto recruitEntryDto = new RecruitEntryDto();
		List<RecruitEntryDto> RecruitEntryList = new ArrayList<>();

		try {
			String sql = " WITH " + " V1 AS " + "    ( "
					+ "    select RE.ENTRY_SEQ, RE.RECRUIT_SEQ, AC.APPLICATION_SEQ, RE.ENTRY_DATE, RE.PASS_STATUS "
					+ "    from TBL_RECRUIT_ENTRY RE join TBL_APPLICATION AC "
					+ "    on RE.APPLICATION_SEQ = AC.APPLICATION_SEQ AND " + "       AC.EMP_SEQ = ? " + "    ) "
					+ " , " + " V2 AS " + "    ( "
					+ "    SELECT RE.ENTRY_SEQ, RC.RECRUIT_SEQ, RE.APPLICATION_SEQ, RE.ENTRY_DATE, RE.PASS_STATUS "
					+ "    FROM TBL_RECRUIT_ENTRY RE JOIN TBL_RECRUIT RC " + "    ON RE.RECRUIT_SEQ = RC.RECRUIT_SEQ "
					+ "    ) " + " SELECT V1.ENTRY_SEQ AS ENTRY_SEQ, V1.RECRUIT_SEQ AS RECRUIT_SEQ, "
					+ "		   V1.APPLICATION_SEQ AS APPLICATION_SEQ, V1.ENTRY_DATE AS ENTRY_DATE, V1.PASS_STATUS AS PASS_STATUS "
					+ " FROM V1 JOIN V2 " + " ON V1.RECRUIT_SEQ = V2.RECRUIT_SEQ AND  "
					+ "    V1.APPLICATION_SEQ = V2.APPLICATION_SEQ ";

			pstmt = conn.prepareStatement(sql);

			pstmt.setInt(1, emp_seq); // 1번째 물음표에 넣을 값(getEmp_seq)

			rs = pstmt.executeQuery();

			if (rs.next()) {

				recruitEntryDto.setEntry_seq(rs.getInt("ENTRY_SEQ"));
				recruitEntryDto.setRecruit_seq(rs.getInt("RECRUIT_SEQ"));
				recruitEntryDto.setApplication_seq(rs.getInt("APPLICATION_SEQ"));
				recruitEntryDto.setEntry_date(rs.getString("ENTRY_DATE"));
				recruitEntryDto.setPass_status(rs.getInt("PASS_STATUS"));

				sql = " select RECRUIT_SEQ, COMP_SEQ, LOCATION_SEQ, EMPLOY_TYPE_SEQ, COMP_NAME, "
						+ "      RECRUIT_TITLE, LICENSE, SCREENING, POSITION, RECRUIT_NUM, EDU_LEVEL, "
						+ "      EXP_LEVEL, SALARY, RECRUIT_START_DATE, RECRUIT_END_DATE, RESULT_DATE, RECRUIT_MANAGER_NAME,"
						+ "		 RECRUIT_MANAGER_EMAIL, HIRE_PERIOD " + " from TBL_RECRUIT "
						+ " where RECRUIT_SEQ = ? ";

				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, recruitEntryDto.getRecruit_seq());
				rs = pstmt.executeQuery();

				if (rs.next()) {
					RecruitDto recruitDto = new RecruitDto();

					recruitDto.setRecruit_seq(rs.getInt("RECRUIT_SEQ"));
					recruitDto.setComp_seq(rs.getInt("COMP_SEQ"));
					recruitDto.setLocation_seq(rs.getInt("LOCATION_SEQ"));
					recruitDto.setEmploy_type_seq(rs.getInt("EMPLOY_TYPE_SEQ"));
					recruitDto.setComp_name(rs.getString("COMP_NAME"));
					recruitDto.setRecruit_title(rs.getString("RECRUIT_TITLE"));
					recruitDto.setLicense(rs.getString("LICENSE"));
					recruitDto.setScreening(rs.getString("SCREENING"));
					recruitDto.setPosition(rs.getString("POSITION"));
					recruitDto.setRecruit_num(rs.getInt("RECRUIT_NUM"));
					recruitDto.setEdu_level(rs.getString("EDU_LEVEL"));
					recruitDto.setExp_level(rs.getString("EXP_LEVEL"));
					recruitDto.setSalary(rs.getInt("SALARY"));
					recruitDto.setRecruit_start_date(rs.getString("RECRUIT_START_DATE"));
					recruitDto.setRecruit_end_date(rs.getString("RECRUIT_END_DATE"));
					recruitDto.setResult_date(rs.getString("RESULT_DATE"));
					recruitDto.setRecruit_manager_name(rs.getString("RECRUIT_MANAGER_NAME"));
					recruitDto.setRecruit_manager_email(rs.getString("RECRUIT_MANAGER_EMAIL"));
					recruitDto.setHire_period(rs.getString("HIRE_PERIOD"));

					recruitEntryDto.setRecruitDto(recruitDto);

					RecruitEntryList.add(recruitEntryDto);
				}
			}

		} catch (SQLException e) {
			// SQLException 발생 시 로그 기록
			e.printStackTrace();
		} finally {
			close(); // close() 메소드가 리소스를 제대로 닫고 있는지 확인
		}

		return RecruitEntryList;
	}
	// // 8. 채용응모한것조회 메소드 끝 //

	// 3개월 이상 비밀번호 변경하지 않았을 경우 //
	@Override
	public void updatePasswd(EmployeeLoginDto employeeLoginDto) {

		try {
			conn.setAutoCommit(false); // 트랜잭션 시작

			// 구직자 로그인 정보 업데이트
			String sql = " UPDATE tbl_emp_login " + " SET EMP_PASSWORD = ? " + " WHERE EMP_SEQ = ? ";

			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, employeeLoginDto.getEmp_password());
			pstmt.setInt(2, employeeLoginDto.getEmp_seq());

			int n1 = pstmt.executeUpdate();
			System.out.println(n1 + "n1은");
			if (n1 == 1) {
				// 구직자 3개월 비밀번호 변경 업데이트
				sql = " insert all " + " WHEN EMP_SEQ = ? THEN "
						+ "           into emp_pswd_threemon(EMP_PASSWD_SEQ, EMP_SEQ, EMP_PASSWD, EMP_ID) "
						+ "           values(EMP_PASSWD_SEQ.NEXTVAL, EMP_SEQ, EMP_PASSWORD, EMP_ID) "
						+ "   select E.EMP_SEQ " + "        , EL.EMP_PASSWORD " + "		   , E.EMP_ID	"
						+ "    from TBL_EMPLOYEE E JOIN TBL_EMP_LOGIN EL " + "    ON E.EMP_SEQ = EL.EMP_SEQ "
						+ "    order by 1 ";

				pstmt = conn.prepareStatement(sql);

				pstmt.setInt(1, employeeLoginDto.getEmp_seq());

				pstmt.executeUpdate(); // sql 문 실행하기

			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally { // 성공하든 안하든 무조건 해야한다. (try - finally)
			close(); // 자원 반납
		}

	}
	// 3개월 이상 비밀번호 변경하지 않았을 경우 끝 //

	// 이력서 보기 //
	@Override
	public List<Map<String, String>> viewApplication(int emp_seq) {
		List<Map<String, String>> applicationDtoList = new ArrayList<>();

		Map<String, String> appliMap = null;

		try {
			String sql = " select A.application_seq, E.emp_name, E.emp_email, E.emp_tel, L.location_name, ET.employ_type_name, M.military_status "
					+ " , A.license, A.hope_sal,  A.motive " + " from " + " ( " + " select * "
					+ " from tbl_application " + " where emp_seq = ?" + " ) A " + " join " + " tbl_employee E "
					+ " on A.emp_seq = E.emp_seq " + " join " + " tbl_military M "
					+ " on M.military_seq = A.military_seq " + " join " + " tbl_location L "
					+ " on L.location_seq = A.location_seq " + " join " + " tbl_employ_type ET "
					+ " on ET.employ_type_seq = A.employ_type_seq " + " where A.emp_seq = ? ";

			pstmt = conn.prepareStatement(sql);

			pstmt.setInt(1, emp_seq);
			pstmt.setInt(2, emp_seq);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				appliMap = new HashMap<>();

				appliMap.put("application_seq", rs.getString("application_seq"));
				appliMap.put("emp_name", rs.getString("emp_name"));
				appliMap.put("emp_email", rs.getString("emp_email"));
				appliMap.put("emp_tel", rs.getString("emp_tel"));
				appliMap.put("location_name", rs.getString("location_name"));
				appliMap.put("employ_type_name", rs.getString("employ_type_name"));
				appliMap.put("military_status", rs.getString("military_status"));
				appliMap.put("license", rs.getString("license"));
				appliMap.put("hope_sal", rs.getString("hope_sal"));
				appliMap.put("motive", rs.getString("motive"));

				applicationDtoList.add(appliMap);

			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}

		return applicationDtoList;
	}
	// 이력서 보기 끝//

	// 이력서의 학력 자세히 보기 //
	@Override
	public List<Map<String, String>> showApplicationEdu(int application_seq, int emp_seq) {
		List<Map<String, String>> eduList = new ArrayList<>();

		Map<String, String> eduMap = null;

		try {
			String sql = " select A.application_seq, E.emp_name, E.emp_email, E.emp_tel, L.location_name, ET.employ_type_name, M.military_status "
					+ " , A.license, A.hope_sal, A.motive, ED.school_name, ED.department_name " + " from "
					+ " ( select * from tbl_application ) A join tbl_employee E " + " on A.emp_seq = E.emp_seq "
					+ " left join  tbl_military M " + " on M.military_seq = A.military_seq "
					+ " left join tbl_location L " + " on L.location_seq = A.location_seq "
					+ " left join tbl_employ_type ET " + " on ET.employ_type_seq = A.employ_type_seq "
					+ " left join tbl_education ED " + " on A.application_seq = ED.application_seq "
					+ " where A.application_seq = ? and A.emp_seq = ?";

			pstmt = conn.prepareStatement(sql);

			pstmt.setInt(1, application_seq);
			pstmt.setInt(2, emp_seq);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				eduMap = new HashMap<>();

				eduMap.put("application_seq", rs.getString("application_seq"));
				eduMap.put("emp_name", rs.getString("emp_name"));
				eduMap.put("emp_email", rs.getString("emp_email"));
				eduMap.put("emp_tel", rs.getString("emp_tel"));
				eduMap.put("location_name", rs.getString("location_name"));
				eduMap.put("employ_type_name", rs.getString("employ_type_name"));
				eduMap.put("military_status", rs.getString("military_status"));
				eduMap.put("license", rs.getString("license"));
				eduMap.put("hope_sal", rs.getString("hope_sal"));
				eduMap.put("motive", rs.getString("motive"));
				eduMap.put("school_name", rs.getString("school_name"));
				eduMap.put("department_name", rs.getString("department_name"));

				eduList.add(eduMap);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}

		return eduList;
	}
	// 이력서의 학력 자세히 보기 끝 //

	@Override
	public List<Map<String, String>> showApplicationCareer(int application_seq, int emp_seq) {

		List<Map<String, String>> eduList = new ArrayList<>();

		Map<String, String> eduMap = null;

		try {
			String sql = " select A.application_seq, E.emp_name, E.emp_email, E.emp_tel, L.location_name, ET.employ_type_name, M.military_status "
					+ " , A.license, A.hope_sal,  A.motive, C.position, C.career_start_date, C.career_end_date "
					+ " from " + " ( select * from tbl_application ) A join tbl_employee E "
					+ " on A.emp_seq = E.emp_seq " + " left join tbl_military M "
					+ " on M.military_seq = A.military_seq " + " left join tbl_location L "
					+ " on L.location_seq = A.location_seq " + " left join tbl_employ_type ET "
					+ " on ET.employ_type_seq = A.employ_type_seq " + " left join tbl_career C "
					+ " on A.application_seq = C.application_seq " + " where A.application_seq = ? and A.emp_seq = ? ";

			pstmt = conn.prepareStatement(sql);

			pstmt.setInt(1, application_seq);
			pstmt.setInt(2, emp_seq);

			rs = pstmt.executeQuery();

			while (rs.next()) {

				eduMap = new HashMap<>();

				eduMap.put("application_seq", rs.getString("application_seq"));
				eduMap.put("emp_name", rs.getString("emp_name"));
				eduMap.put("emp_email", rs.getString("emp_email"));
				eduMap.put("emp_tel", rs.getString("emp_tel"));
				eduMap.put("location_name", rs.getString("location_name"));
				eduMap.put("employ_type_name", rs.getString("employ_type_name"));
				eduMap.put("military_status", rs.getString("military_status"));
				eduMap.put("license", rs.getString("license"));
				eduMap.put("hope_sal", rs.getString("hope_sal"));
				eduMap.put("motive", rs.getString("motive"));
				eduMap.put("position", rs.getString("position"));
				eduMap.put("career_start_date", rs.getString("career_start_date"));
				eduMap.put("career_end_date", rs.getString("career_end_date"));

				eduList.add(eduMap);

			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}

		return eduList;

	}

	// 이력서의 경력 자세히 보기 시작 //

	// 경력 자세히 보기 끝 //

}
