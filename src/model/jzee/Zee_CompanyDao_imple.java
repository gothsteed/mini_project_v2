package model.jzee;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import dbConnect.MyDBConnection;
import domain.ApplicationDto;
import domain.CareerDto;
import domain.CompanyDto;
import domain.CompanyLoginDto;
import domain.EmployeeDto;
import domain.EmployeeLoginDto;
import domain.RecruitEntryDto;

public class Zee_CompanyDao_imple implements Zee_CompanyDao {
	
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



	
	
	// *** 우리회사 정보 보기 ***
	@Override
	public CompanyDto showmyCompInfo(int comp_seq) {
		
		CompanyDto comp = null;
		
		
		try {
			conn = MyDBConnection.getConn();
			
			String sql = " SELECT C.COMP_ID, L.COMP_PASSWORD, C.COMP_NAME, C.COMP_SCALE, C.COMP_ADDR, C.COMP_EMAIL, COMP_CEO "
					   + "      , C.COMP_EMP_CNT, C.COMP_SALES, C.COMP_CAPITAL, C.COMP_EST_DATE, C.COMP_INSURANCE, c.indust_name"
					   + " FROM TBL_COMP C JOIN TBL_COMP_LOGIN L  "
					   + " ON C.COMP_ID = L.COMP_ID "
					   + " where L.comp_seq = ? " ;
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, comp_seq);
			
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				comp = new CompanyDto();
				CompanyLoginDto compL = new CompanyLoginDto();
				
				comp.setComp_id(rs.getString("COMP_ID"));
				compL.setComp_password(rs.getString("COMP_PASSWORD"));
				
				comp.setCompanyLoginDto(compL);
				
				comp.setComp_name(rs.getString("COMP_NAME"));
				comp.setComp_scale(rs.getString("COMP_SCALE"));
				comp.setComp_addr(rs.getString("COMP_ADDR"));
				comp.setComp_email(rs.getString("COMP_EMAIL"));
				comp.setComp_ceo(rs.getString("COMP_CEO"));
				comp.setComp_emp_cnt(rs.getInt("COMP_EMP_CNT"));
				comp.setComp_sales(rs.getInt("COMP_SALES"));
				comp.setComp_capital(rs.getInt("COMP_CAPITAL"));
				
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
				
				comp.setComp_est_date(sdf.format(rs.getDate("COMP_EST_DATE")));
				comp.setComp_insurance(rs.getString("COMP_INSURANCE"));
				comp.setIndust_name(rs.getString("COMP_INSURANCE"));

				
			}//end of while----------------------------
			
		}catch (SQLException e) {
			e.printStackTrace();
			
		} finally {
			close();
		}
		
		return comp;
	}




	//회사가 모든구직자 조회
	@Override
	public List<EmployeeDto> showAllEmployee() {
		
		List<EmployeeDto> employeeList = new ArrayList<>();
		
		try {
			conn = MyDBConnection.getConn();
			
			String sql = " SELECT E.emp_id, L.emp_password, E.emp_name, E.emp_email, E.emp_tel  "
//					+ "    , CASE WHEN SUBSTR(JUBUN, 7, 1) = '1' OR SUBSTR(JUBUN, 7, 1) = '2' "
//					+ "            THEN  "
//					+ "                '19' || SUBSTR(JUBUN, 1, 6) "
//					+ "            ELSE\r\n"
//					+ "                '20' || SUBSTR(JUBUN, 1, 6) "
//					+ "            END AS birthdate "
//					+ "    ,case when substr(jubun,7) IN (1,3) THEN '남' "
//					+ "          WHEN substr(jubun,7) IN (2,4) THEN '여' END AS gender  "
					+ "     , jubun "
					+ " FROM tbl_employee E JOIN tbl_emp_login L "
					+ " 	ON E.emp_id = L.emp_id "
					+ "  where status = 1  ";
				
			
			pstmt = conn.prepareStatement(sql);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				EmployeeDto emplo = new EmployeeDto();
				
				emplo.setEmp_id(rs.getString("emp_id"));
				emplo.setEmp_password(rs.getString("emp_password"));
				emplo.setEmp_name(rs.getString("emp_name"));
				emplo.setEmp_email(rs.getString("emp_email"));
				emplo.setEmp_tel(rs.getString("emp_tel"));
				emplo.setJubun(rs.getString("jubun"));
				
				employeeList.add(emplo);
				
			}//end of while----------------------------
			
		} catch (SQLException e) { 
			e.printStackTrace();
			
		} finally {
			close();
		}
		
		return employeeList;
	}//end of showAllComp-----------------------------




	// 구직자번호 검색
	@Override
	public EmployeeDto searchEmployeeSeq(int emp_seq) {
		
		EmployeeDto emplo = null;
		
		try {
			conn = MyDBConnection.getConn();
			
			String sql = " SELECT e.emp_seq, E.emp_id, E.emp_name, E.emp_email, E.emp_tel  "
//					+ "    , CASE WHEN SUBSTR(JUBUN, 7, 1) = '1' OR SUBSTR(JUBUN, 7, 1) = '2' "
//					+ "            THEN  "
//					+ "                '19' || SUBSTR(JUBUN, 1, 6) "
//					+ "            ELSE\r\n"
//					+ "                '20' || SUBSTR(JUBUN, 1, 6) "
//					+ "            END AS birthdate "
//					+ "    ,case when substr(jubun,7) IN (1,3) THEN '남' "
//					+ "          WHEN substr(jubun,7) IN (2,4) THEN '여' END AS gender  "
					+ "     , jubun "
					+ " FROM tbl_employee E JOIN tbl_emp_login L "
					+ " 	ON E.emp_id = L.emp_id "
					+ "  where status = 1  and  e.emp_seq =  ? ";
				
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, emp_seq);
			
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
			
				emplo = new EmployeeDto();
				
				emplo.setEmp_seq(rs.getInt("emp_seq"));
				emplo.setEmp_id(rs.getString("emp_id"));
				emplo.setEmp_name(rs.getString("emp_name"));
				emplo.setEmp_email(rs.getString("emp_email"));
				emplo.setEmp_tel(rs.getString("emp_tel"));
				emplo.setJubun(rs.getString("jubun"));
				
				
			}//end of while----------------------------
			
		} catch (SQLException e) { 
			e.printStackTrace();
			
		} finally {
			close();
		}
		
		return emplo;
	}//end of searchEmployeeSeq-----------------------------




	// *** 이력서번호로 검색 상세정보***
	@Override
	public ApplicationDto searchAppliSeq(int application_seq) {

		
		ApplicationDto rdto = null;
		
		try {
			
		String sql = "select A.application_seq, E.emp_name, E.emp_email, E.emp_tel, L.location_name, ET.employ_type_name, M.military_status "
				+ "	    ,  A.license, A.hope_sal, A. status, A.motive "
				+ "	from "
				+ " ( "
				+ "     select * "
				+ "     from tbl_application "
				+ "     where application_seq = ? "
				+ " ) A "
				+ "	join "
				+ "	tbl_employee E "
				+ "	on A.emp_seq = E.emp_seq "
				+ "	join  "
				+ "	tbl_military M "
				+ "	on M.military_seq = A.military_seq "
				+ "	join "
				+ "	tbl_location L "
				+ "	on L.location_seq = A.location_seq"
				+ " join "
				+ " tbl_employ_type ET"
				+ " on ET.employ_type_seq = A.employ_type_seq ";
//				+ "	where A.application_seq = ? ";
		
		pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, application_seq);
		rs = pstmt.executeQuery();
		
		if(rs.next()) {
			rdto = new ApplicationDto();
			
			rdto.setApplication_seq(rs.getInt("application_seq"));
			
			EmployeeDto employeeDto = new EmployeeDto();
			employeeDto.setEmp_name(rs.getString("emp_name"));
			employeeDto.setEmp_email(rs.getString("emp_email"));
			employeeDto.setEmp_tel(rs.getString("emp_tel"));
			rdto.setEmployeeDto(employeeDto);
			
			
			rdto.setLoctaion_name(rs.getString("location_name"));
			
		
			rdto.setEmp_type_name(rs.getString("employ_type_name"));
			rdto.setMilitary_status(rs.getString("military_status"));
			rdto.setLicense(rs.getString("license"));
			rdto.setHope_sal(rs.getInt("hope_sal"));
			rdto.setStatus(rs.getInt("status"));
			rdto.setMotive(rs.getString("motive"));
			
			
		}//end of while----------------------------
		
	} catch (SQLException e) { 
		e.printStackTrace();
		
	} finally {
		close();
	}
	
	return rdto;

		
		
	}

	
	
}
