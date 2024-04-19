package model.HssCompany;

import java.util.List;
import java.util.Map;

import domain.CareerDto;
import domain.CompanyDto;
import domain.CompanyLoginDto;
import domain.EmployeeDto;
import domain.RecruitDto;

public interface HssCompanyDao {

	int insertRecruit(RecruitDto recruitDto, CompanyLoginDto companyLoginDto);

	List<EmployeeDto> empGenderSearch(String compName);

	List<CareerDto> empCareerSearch();
	
	CompanyDto compIdSearch(Map<String, String> paramap);

	CompanyLoginDto compPswdSearch(Map<String, String> paramap);

	List<String> compNewPswdCheck(Map<String, String> newParaMap);

	int compNewPswdSet(Map<String, String> newParaMap);
	




}
