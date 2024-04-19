package model.HssMember;

import java.util.List;
import java.util.Map;

import domain.ApplicationDto;
import domain.CareerDto;
import domain.CompanyDto;
import domain.EducationDto;
import domain.EmployeeDto;
import domain.EmployeeLoginDto;
import domain.RecruitDto;

public interface HssMemberDao {

	int insert_application(ApplicationDto applicationDto, EmployeeLoginDto employeeDto);

	void insert_education(ApplicationDto applicationDto, List<EducationDto> educationList);
	
	void insert_career(ApplicationDto applicationDto, List<CareerDto> careerList);

	List<CompanyDto> compNameSearch(String compName);

	List<CompanyDto> compCapitalSearch(int capital);

	EmployeeDto empIdSearch(Map<String, String> paramap);

	EmployeeDto empPswdSearch(Map<String, String> paramap);
	
	List<String> empNewPswdCheck(Map<String, String> newParaMap);

	int empNewPswdSet(Map<String, String> newParaMap);

	List<RecruitDto> titleSearch(String title);

}
