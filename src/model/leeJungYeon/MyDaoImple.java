package model.leeJungYeon;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.naming.spi.DirStateFactory.Result;

import dbConnect.MyDBConnection;
import domain.ApplicationDto;
import domain.CareerDto;
import domain.CompanyDto;
import domain.EducationDto;
import domain.EmployeeDto;
import domain.RecruitDto;
import domain.RecruitEntryDto;
import oracle.net.aso.f;

public class MyDaoImple implements MyDao {
	
	
	private Connection conn = MyDBConnection.getConn();
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
	public EmployeeDto getEmp(int emp_seq) {
		
		EmployeeDto employeeDto = null;
		
		try {
			String sql = "select * "
				+ " from tbl_employee "
				+  " where emp_seq = ? ";
		
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, emp_seq);
			
			rs = pstmt.executeQuery();
			
			
			
			if(rs.next()) {
				employeeDto = new EmployeeDto();
				
				employeeDto.setEmp_seq(rs.getInt("emp_seq"));
				employeeDto.setEmp_id(rs.getString("emp_id"));
				employeeDto.setEmp_name(rs.getString("emp_name"));
				employeeDto.setEmp_email(rs.getString("emp_email"));
				employeeDto.setEmp_tel(rs.getString("emp_tel"));
				
			}
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close();
		}
		
		
		
		return employeeDto;
	}
	
	

	@Override
	public ApplicationDto viewApplication(int application_seq) {
		
		ApplicationDto applicationDto = null;
		
		try {
			String sql = "select * "
				+ " from tbl_application "
				+  " where application_seq = ? ";
		
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, application_seq);
			
			rs = pstmt.executeQuery();
			
			
			
			if(rs.next()) {
				applicationDto = new ApplicationDto();
				
				applicationDto.setApplication_seq(application_seq);
				applicationDto.setEmp_seq(rs.getInt("EMP_SEQ"));
				applicationDto.setLocation_seq(rs.getInt("location_seq"));
				applicationDto.setEmploy_type_seq(rs.getInt("employ_type_seq"));
				applicationDto.setMilitary_seq(rs.getInt("military_seq"));
				applicationDto.setLicense(rs.getString("license"));
				applicationDto.setHope_sal(rs.getInt("hope_sal"));
				applicationDto.setStatus(rs.getInt("status"));
				applicationDto.setMotive(rs.getString("motive"));
				
				
			}
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close();
		}
		
		
		
		return applicationDto;
	}

	@Override
	public int update_application(ApplicationDto myApplicationDto) {
	    int result = 0;

	    try {
	        String sql = "UPDATE tbl_application SET "
	                   + "emp_seq = ?, "
	                   + "location_seq = ?, "
	                   + "employ_type_seq = ?, "
	                   + "military_seq = ?, "
	                   + "license = ?, "
	                   + "hope_sal = ?, "
	                   + "status = ?, "
	                   + "motive = ? "
	                   + "WHERE application_seq = ?";

	        pstmt = conn.prepareStatement(sql);
	        pstmt.setInt(1, myApplicationDto.getEmp_seq());
	        pstmt.setInt(2, myApplicationDto.getLocation_seq());
	        pstmt.setInt(3, myApplicationDto.getEmploy_type_seq());
	        pstmt.setInt(4, myApplicationDto.getMilitary_seq());
	        pstmt.setString(5, myApplicationDto.getLicense());
	        pstmt.setInt(6, myApplicationDto.getHope_sal());
	        pstmt.setInt(7, myApplicationDto.getStatus());
	        pstmt.setString(8, myApplicationDto.getMotive());
	        pstmt.setInt(9, myApplicationDto.getApplication_seq());

	        result = pstmt.executeUpdate();

	    } catch (SQLException e) {
	        e.printStackTrace();
	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        close();
	    }

	    return result;
	}
	

	@Override
	public List<CareerDto> getCareerList(int application_seq) {
		List<CareerDto> careerDtos = new ArrayList<CareerDto>();
		
		
		try {
			String sql = "select career_seq ,  application_seq, position,"
					+ "      to_char(career_start_date, 'yyyy/mm/dd') as career_start_date, "
					+ "      to_char(career_end_date, 'yyyy/mm/dd') as career_end_date "
					+ " from tbl_career "
					+  " where application_seq = ? ";
		
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, application_seq);
			
			rs = pstmt.executeQuery();
			
			
			
			while(rs.next()) {
				CareerDto careerDto = new CareerDto();
				
				careerDto.setCareerSeq(rs.getInt("career_seq"));
				careerDto.setApplicationSeq(application_seq);
				careerDto.setPosition(rs.getString("position"));
				careerDto.setCareerStartDate(rs.getString("career_start_date"));
				careerDto.setCareerEndDate(rs.getString("career_start_date"));
				
				careerDtos.add(careerDto);
				
				
			}
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close();
		}
		
		
		return careerDtos;
	}

	@Override
	public List<EducationDto> getEducationList(int application_seq) {
		
		List<EducationDto> educationList = new ArrayList<EducationDto>();
		
		
		try {
			String sql = "select edu_seq ,  application_seq, school_name, department_name "
					+ " from tbl_education "
					+  " where application_seq = ? ";
		
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, application_seq);
			
			rs = pstmt.executeQuery();
			
			
			
			while(rs.next()) {
				EducationDto educationDto = new EducationDto();
				
				educationDto.setEduSeq(rs.getInt("edu_seq"));
				educationDto.setApplicationSeq(application_seq);
				educationDto.setSchoolName(rs.getString("school_name"));
				educationDto.setDepartmentName(rs.getString("department_name"));
				
				educationList.add(educationDto);
				
				
			}
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close();
		}
		
		
		return educationList;
	}

	@Override
	public int updateCareer(CareerDto career) {
		int result = 0;
		
		try {
			
			String sql = " update  tbl_career "
					+ " set "
					+ "    position= ?,"
					+ "    career_start_date = to_date(?, 'yyyy/mm/dd'), "
					+ "    career_end_date = to_date(?, 'yyyy/mm/dd')  " 
					+ " where career_seq = ?  ";
			
			pstmt = conn.prepareStatement(sql);
			
			
			pstmt.setString(1, career.getPosition());
			pstmt.setString(2, career.getCareerStartDate());
			pstmt.setString(3, career.getCareerEndDate());
			pstmt.setInt(4, career.getCareerSeq());
			
			
			result = pstmt.executeUpdate();

			
			
		}catch (SQLException e) {
			e.printStackTrace();
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			close();
		}
		
		
		
		return result;
	}

	@Override
	public int updateEducation(EducationDto education) {
		
		int result = 0;
		
		try {
			
			String sql = " update  tbl_education "
					+ " set "
					+ "    school_name = ?, "
					+ "    department_name = ?  " 
					+ " where edu_seq= ?  ";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, education.getSchoolName());
			pstmt.setString(2, education.getDepartmentName());
			pstmt.setInt(3, education.getEduSeq());
			
			
			result = pstmt.executeUpdate();

			
			
		}catch (SQLException e) {
			e.printStackTrace();
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			close();
		}
		
		
		
		return result;

	}

	@Override
	public RecruitDto getRecruit(int recruit_seq) {
		
		RecruitDto recruitDto = null;
		
		try {
			String sql = "select * "
				+ " from tbl_recruit "
				+  " where recruit_seq = ? ";
		
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, recruit_seq);
			
			rs = pstmt.executeQuery();
			
			
			
			if(rs.next()) {
				recruitDto = new RecruitDto();
				
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
	            recruitDto.setStatus(rs.getInt("STATUS"));
	            
	            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
	            
	            recruitDto.setRecruit_start_date(sdf.format(rs.getDate("RECRUIT_START_DATE")));
	            recruitDto.setRecruit_end_date(sdf.format(rs.getDate("RECRUIT_END_DATE")));
	            recruitDto.setResult_date(sdf.format(rs.getDate("RESULT_DATE")) );
	            recruitDto.setRecruit_manager_name(rs.getString("RECRUIT_MANAGER_NAME"));
	            recruitDto.setRecruit_manager_email(rs.getString("RECRUIT_MANAGER_EMAIL"));
	            recruitDto.setHire_period(rs.getString("HIRE_PERIOD"));
			}
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close();
		}
		
		
		
		return recruitDto;
	}

	@Override
	public int updateRecruit(RecruitDto recruitDto) {
		
		int result = 0;
		
		try {

			String sql = " UPDATE tbl_recruit "
					+ " SET "
					+ "    comp_seq = ? , "
					+ "    location_seq = ?,  "
					+ "    employ_type_seq = ?, "
					+ "    comp_name = ?, "
					+ "    recruit_title = ?, "
					+ "    license = ?, "
					+ "    screening = ?, "
					+ "    position = ?, "
					+ "    recruit_num = ?, "
					+ "    edu_level = ?, "
					+ "    exp_level = ?, "
					+ "    salary = ?, "
					+ "    recruit_start_date = TO_DATE(?, 'YYYY/MM/DD'), "
					+ "    recruit_end_date = TO_DATE(?, 'YYYY/MM/DD'), "
					+ "    result_date = TO_DATE(?, 'YYYY/MM/DD'), "
					+ "    recruit_manager_name = ?, "
					+ "    recruit_manager_email = ?, "
					+ "    hire_period = ? "
					+ " WHERE recruit_seq = ? ";
			
			pstmt = conn.prepareStatement(sql);
			
		    pstmt.setInt(1, recruitDto.getComp_seq());
		    pstmt.setInt(2, recruitDto.getLocation_seq()); // Assuming these fields exist
		    pstmt.setInt(3, recruitDto.getEmploy_type_seq()); // Assuming
		    pstmt.setString(4, recruitDto.getComp_name());
		    pstmt.setString(5, recruitDto.getRecruit_title());
		    pstmt.setString(6, recruitDto.getLicense());
		    pstmt.setString(7, recruitDto.getScreening());
		    pstmt.setString(8, recruitDto.getPosition());
		    pstmt.setInt(9, recruitDto.getRecruit_num());
		    pstmt.setString(10, recruitDto.getEdu_level());
		    pstmt.setString(11, recruitDto.getExp_level());
		    pstmt.setInt(12, recruitDto.getSalary());
		    pstmt.setString(13, recruitDto.getRecruit_start_date());
		    pstmt.setString(14, recruitDto.getRecruit_end_date());
		    pstmt.setString(15, recruitDto.getResult_date());
		    pstmt.setString(16, recruitDto.getRecruit_manager_name());
		    pstmt.setString(17, recruitDto.getRecruit_manager_email());
		    pstmt.setString(18, recruitDto.getHire_period());
		    pstmt.setInt(19, recruitDto.getRecruit_seq());
		    
		    
		    
		    result =  pstmt.executeUpdate();
			
			
			
		}catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close();
		}
		
		
		return result;
	}

	@Override
	public int changeToDeleteApplication(int application_seq) {
		
		int result = 0;
		
		
		try {
			
			String sql = " update tbl_application "
					+ " set "
					+ "     status = 0 "
					+ " where application_seq = ?";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, application_seq);
			
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
	public int changeToDeleteRecruit(int recruit_seq) {

		int result = 0;
		
		
		try {
			
			String sql = " update tbl_recruit "
					+ " set "
					+ "     status = 0 "
					+ " where recruit_seq = ?";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, recruit_seq);
			
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
	
	
	private Boolean isApplied(int recruit_seq, int application_seq) {
		
		String sql = " select * "
				+ " from tbl_recruit_entry "
				+ " where application_seq = ? and recruit_seq = ? ";
		
		try {
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, application_seq);
			pstmt.setInt(2, recruit_seq);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				return true;
			}
			
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			close();
		}
		
		return false;
	}
 
	@Override
	public int apply(RecruitEntryDto recruitEntryDto, int emp_seq) {
		
		int result = 0;
		
		try {
			
			RecruitDto recruitDto = getRecruit(recruitEntryDto.getRecruit_seq());
			
			if(recruitDto == null) {
				return result;
			}
			
			ApplicationDto applicationDto = viewApplication(recruitEntryDto.getApplication_seq());
			if(applicationDto == null || applicationDto.getEmp_seq() != emp_seq) {
				
				return result;
			}
			
			if(isApplied(recruitDto.getRecruit_seq(), applicationDto.getApplication_seq())) {
				
				return -1;
			}
			
			
			
			String applySql = " insert into TBL_RECRUIT_ENTRY( "
					+ "    ENTRY_SEQ, "
					+ "    RECRUIT_SEQ, "
					+ "    APPLICATION_SEQ "
					+ " ) "
					+ " values ( "
					+ "    SEQ_RECRUIT_ENTRY.nextval, "
					+ "    ?, "
					+ "    ? "
					+ " ) ";
			
			pstmt = conn.prepareStatement(applySql);
			
			pstmt.setInt(1, recruitEntryDto.getRecruit_seq());
			pstmt.setInt(2, recruitEntryDto.getApplication_seq());
			
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
	public List<CompanyDto> searchCompanyByEmployeeCount(int min, int max) {
		List<CompanyDto> result = new ArrayList<CompanyDto>();
		
		
		String sql = " select * "
				+ " from tbl_comp "
				+ " where COMP_EMP_CNT between ? and ? "
				+ "  and status = 1  ";
		
		
		try {
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, min);
			pstmt.setInt(2, max);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				
				CompanyDto company = new CompanyDto();

	            company.setComp_seq(rs.getInt("COMP_SEQ"));
	            company.setComp_id(rs.getString("COMP_ID"));
	            company.setComp_email(rs.getString("COMP_EMAIL")); // Note: There is a typo in the column name.
	            
	            
	            company.setIndust_name(rs.getString("INDUST_NAME")); // Note: There is a typo in the DTO method name as well.
	            company.setComp_name(rs.getString("COMP_NAME"));
	            company.setComp_scale(rs.getString("COMP_SCALE"));
	            company.setComp_est_date(rs.getString("COMP_EST_DATE"));
	            company.setComp_addr(rs.getString("COMP_ADDR"));
	            company.setComp_ceo(rs.getString("COMP_CEO"));
	            company.setComp_emp_cnt(rs.getInt("COMP_EMP_CNT"));
	            company.setComp_capital(rs.getInt("COMP_CAPITAL"));
	            company.setComp_sales(rs.getInt("COMP_SALES"));
	            company.setComp_insurance(rs.getString("COMP_INSURANCE"));
	            company.setStatus(rs.getInt("STATUS"));

	            result.add(company);
	            
	            
	            
				
				
			}
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			close();
		}

		
		return result;
	}

	@Override
	public List<CompanyDto> searchCompanyBySales(int min, int max) {
		
		List<CompanyDto> result = new ArrayList<CompanyDto>();
		
		String sql = " select * "
				+ " from tbl_comp "
				+ " where comp_sales between ? and ? "
				+ " and status = 1 ";
		
		try {
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, min);
			pstmt.setInt(2, max);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				
				CompanyDto company = new CompanyDto();

	            company.setComp_seq(rs.getInt("COMP_SEQ"));
	            company.setComp_id(rs.getString("COMP_ID"));
	            company.setComp_email(rs.getString("COMP_EMAIL")); // Note: There is a typo in the column name.
	            
	            company.setIndust_name(rs.getString("INDUST_NAME")); // Note: There is a typo in the DTO method name as well.
	            company.setComp_name(rs.getString("COMP_NAME"));
	            company.setComp_scale(rs.getString("COMP_SCALE"));
	            company.setComp_est_date(rs.getString("COMP_EST_DATE"));
	            company.setComp_addr(rs.getString("COMP_ADDR"));
	            company.setComp_ceo(rs.getString("COMP_CEO"));
	            company.setComp_emp_cnt(rs.getInt("COMP_EMP_CNT"));
	            company.setComp_capital(rs.getInt("COMP_CAPITAL"));
	            company.setComp_sales(rs.getInt("COMP_SALES"));
	            company.setComp_insurance(rs.getString("COMP_INSURANCE"));
	            company.setStatus(rs.getInt("STATUS"));

	            result.add(company);
				
				
			}
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			close();
		}

		
		return result;
		
		
	}

	@Override
	public List<EmployeeDto> searchEmployeeByAgeGroup(int min, int max) {
	List<EmployeeDto> result = new ArrayList<EmployeeDto>();
		
		String sql = " SELECT "
				+ "        EMP_SEQ, "
				+ "        EMP_ID, "
				+ "        EMP_EMAIL, "
				+ "        EMP_NAME, "
				+ "        JUBUN, "
				+ "        EMP_ADDRESS, "
				+ "        EMP_TEL, "
				+ "        STATUS, "
				+ "        CASE WHEN TO_DATE( EXTRACT(YEAR FROM SYSDATE) ||SUBSTR(BIRTHDATE, 5, 4), 'YYYYMMDD') > TO_DATE(TO_CHAR(SYSDATE, 'YYYYMMDD'), 'YYYYMMDD') "
				+ "                THEN EXTRACT(YEAR FROM SYSDATE) - TO_NUMBER(SUBSTR(BIRTHDATE, 1, 4)) -1 "
				+ "                ELSE EXTRACT(YEAR FROM SYSDATE) - TO_NUMBER(SUBSTR(BIRTHDATE, 1, 4)) "
				+ "                END AS AGE "
				+ " from( "
				+ "    SELECT "
				+ "        EMP_SEQ, "
				+ "        EMP_ID, "
				+ "        EMP_EMAIL, "
				+ "        EMP_NAME, "
				+ "        JUBUN, "
				+ "        EMP_ADDRESS, "
				+ "        EMP_TEL, "
				+ "        STATUS, "
				+ "        CASE WHEN SUBSTR(JUBUN, 7, 1) = '1' OR SUBSTR(JUBUN, 7, 1) = '2' "
				+ "            THEN  "
				+ "                '19' || SUBSTR(JUBUN, 1, 6) "
				+ "            ELSE "
				+ "                '20' || SUBSTR(JUBUN, 1, 6) "
				+ "            END AS BIRTHDATE "
				+ "            "
				+ "    FROM "
				+ "        TBL_EMPLOYEE "
				+ " )  "
				+ "  WHERE TRUNC(       CASE WHEN TO_DATE( EXTRACT(YEAR FROM SYSDATE) ||SUBSTR(BIRTHDATE, 5, 4), 'YYYYMMDD') > TO_DATE(TO_CHAR(SYSDATE, 'YYYYMMDD'), 'YYYYMMDD') "
				+ "				               THEN EXTRACT(YEAR FROM SYSDATE) - TO_NUMBER(SUBSTR(BIRTHDATE, 1, 4)) -1 "
				+ "				               ELSE EXTRACT(YEAR FROM SYSDATE) - TO_NUMBER(SUBSTR(BIRTHDATE, 1, 4)) "
				+ "				               END , -1) BETWEEN ? AND ?";
		
		try {
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, min);
			pstmt.setInt(2, max);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				
				
				EmployeeDto employee = new EmployeeDto();
				
				employee.setEmp_seq(rs.getInt("EMP_SEQ"));
				employee.setEmp_id(rs.getString("EMP_ID"));
				employee.setEmp_email(rs.getString("EMP_EMAIL"));
				employee.setEmp_name(rs.getString("EMP_NAME"));
				employee.setJubun(rs.getNString("JUBUN"));
				employee.setEmp_address(rs.getString("EMP_ADDRESS"));
				employee.setEmp_tel(rs.getString("EMP_TEL"));

				
				result.add(employee);
				
				
			}
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			close();
		}

		
		return result;
	}

	@Override
	public List<RecruitDto> getCompanyRecruits(int comp_seq) {
		
		List<RecruitDto> result = new ArrayList<RecruitDto>();
		
		String sql = " select * "
				+ " from tbl_recruit V "
				+ " left join ( "
				+ "    select "
				+ "        A.recruit_seq, "
				+ "        count(*) "
				+ "    from "
				+ "    tbl_recruit_entry A "
				+ "    join "
				+ "    tbl_application B "
				+ "    on A.application_seq = B.application_seq "
				+ "    group by A.RECRUIT_SEQ "
				+ " )V2 "
				+ " on V.recruit_seq = V2.recruit_seq "
				+ " where comp_seq = ? ";
		

		
		
		try {
			
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, comp_seq);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				
				RecruitDto recruitDto = new RecruitDto();
				
				SimpleDateFormat sdfm = new SimpleDateFormat("yyyy/MM/dd");
				
				recruitDto.setRecruit_seq(rs.getInt("RECRUIT_SEQ"));
				recruitDto.setRecruit_title(rs.getString("RECRUIT_TITLE"));
				recruitDto.setRecruit_start_date(sdfm.format(rs.getDate("RECRUIT_START_DATE")) );
				recruitDto.setRecruit_end_date(sdfm.format(rs.getDate("RECRUIT_END_DATE")));
				recruitDto.setCount(rs.getInt("COUNT(*)"));
				recruitDto.setStatus(rs.getInt("status"));
				
				result.add(recruitDto);
				
			}
			
			
			
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			
			close();
		}
		
		return result;
	}

	@Override
	public List<Map<String, String>> getRecruitStats(int recruit_seq) {
		
		List<Map<String, String>> mapList = new ArrayList<>();
		
		String sql =  " SELECT gender, "
				+ "       age_range, "
				+ "       group_count, "
				+ "       (group_count / (SELECT COUNT(*) FROM tbl_recruit_entry WHERE recruit_seq = 5)) * 100 AS percentage "
				+ " FROM ( "
				+ "    SELECT  "
				+ "        CASE  "
				+ "            WHEN SUBSTR(B.jubun, 7, 1) IN ('1', '3') THEN '남성' "
				+ "            ELSE '여성' "
				+ "        END as gender, "
				+ "        FLOOR((SYSDATE - TO_DATE( "
				+ "        CASE  "
				+ "            WHEN SUBSTR(B.jubun, 7, 1) IN ('1', '2') THEN '19'||SUBSTR(B.jubun, 1, 2) "
				+ "            WHEN SUBSTR(B.jubun, 7, 1) IN ('3', '4') THEN '20'||SUBSTR(B.jubun, 1, 2) "
				+ "            ELSE '1900' "
				+ "        END, 'YYYY')) / 365 / 10) * 10 as age_range, "
				+ "        COUNT(*) as group_count, "
				+ "        SUM(COUNT(*)) OVER() as total_count "
				+ "    FROM "
				+ "    ( "
				+ "        SELECT * "
				+ "        FROM tbl_recruit_entry "
				+ "        WHERE recruit_seq = ? "
				+ "    ) A "
				+ "    JOIN "
				+ "    ( "
				+ "        SELECT application_seq, tbl_employee.jubun as jubun "
				+ "        FROM tbl_application  "
				+ "        JOIN tbl_employee "
				+ "        ON tbl_application.emp_seq = tbl_employee.emp_seq "
				+ "    ) B "
				+ "    ON A.application_seq = B.application_seq "
				+ "    GROUP BY CUBE( "
				+ "        CASE  "
				+ "            WHEN SUBSTR(B.jubun, 7, 1) IN ('1', '3') THEN '남성' "
				+ "            ELSE '여성' "
				+ "        END, "
				+ "        FLOOR((SYSDATE - TO_DATE( "
				+ "            CASE  "
				+ "                WHEN SUBSTR(B.jubun, 7, 1) IN ('1', '2') THEN '19'||SUBSTR(B.jubun, 1, 2) "
				+ "                WHEN SUBSTR(B.jubun, 7, 1) IN ('3', '4') THEN '20'||SUBSTR(B.jubun, 1, 2) "
				+ "                ELSE '1900' "
				+ "            END, 'YYYY')) / 365 / 10) * 10 "
				+ "    ) "
				+ " ) ";
		
		
		
		try {
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, recruit_seq);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				
				Map<String, String> map = new HashMap<>();
	
	        	   
                map.put("gender", rs.getString("gender"));
                map.put("age_range", String.valueOf(rs.getInt("age_range")) );
                map.put("group_count", String.valueOf(rs.getInt("group_count")));
                map.put("percentage", String.valueOf(rs.getDouble("percentage")));

                mapList.add(map);

				
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			close();
			
		}
		
		
		
		return mapList;
	}


}















