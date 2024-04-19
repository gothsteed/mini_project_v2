package model.jzee;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dbConnect.MyDBConnection;
import domain.CompanyDto;

public class Zee_EmployeeDao_imple implements Zee_EmployeeDao {

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

	
	// *** 구직자가 모든구인회사 조회 ***
	@Override
	public List<CompanyDto> showAllComp() {
		
		List<CompanyDto> companyList = new ArrayList<>();
		
		try {
			conn = MyDBConnection.getConn();
			
			String sql = " select comp_name, comp_scale, comp_addr, comp_email, comp_ceo, comp_emp_cnt, "
					   + " 		  comp_sales, comp_capital, comp_est_date, comp_insurance "
				  	   + " from tbl_comp ";
			
			pstmt = conn.prepareStatement(sql);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				CompanyDto comp = new CompanyDto();
				
				comp.setComp_name(rs.getString("comp_name"));
				comp.setComp_scale(rs.getString("comp_scale"));
				comp.setComp_addr(rs.getString("comp_addr"));
				comp.setComp_email(rs.getString("comp_email"));
				comp.setComp_ceo(rs.getString("comp_ceo"));
				comp.setComp_emp_cnt(rs.getInt("comp_emp_cnt"));
				comp.setComp_sales(rs.getInt("comp_sales"));
				comp.setComp_capital(rs.getInt("comp_capital"));
				comp.setComp_est_date(rs.getString("comp_est_date"));
				comp.setComp_insurance(rs.getString("comp_insurance"));
				
				companyList.add(comp);
				
			}//end of while----------------------------
			
		} catch (SQLException e) { 
			e.printStackTrace();
			
		} finally {
			close();
		}
		
		return companyList;
	}//end of showAllComp-----------------------------



	

}
