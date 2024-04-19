package model.jzee;


import java.util.List;

import domain.ApplicationDto;
import domain.CompanyDto;
import domain.EmployeeDto;
import domain.EmployeeLoginDto;
import domain.RecruitEntryDto;

public interface Zee_CompanyDao {



	List<EmployeeDto> showAllEmployee();//모든구직자 조회

	CompanyDto showmyCompInfo(int comp_seq);//회사가 자기회사정보조회

	EmployeeDto searchEmployeeSeq(int emp_seq);//구직자번호 검색

	ApplicationDto searchAppliSeq(int application_seq);// *** 접수된 이력서에서 이력서번호로 검색 -> 상세정보***

	

	
	
}
