package model.leeJungYeon;

import java.util.List;
import java.util.Map;

import domain.ApplicationDto;
import domain.CareerDto;
import domain.CompanyDto;
import domain.EducationDto;
import domain.EmployeeDto;
import domain.RecruitDto;
import domain.RecruitEntryDto;

public interface MyDao {

	ApplicationDto viewApplication(int application_seq);

	int update_application(ApplicationDto myApplicationDto);

	List<CareerDto> getCareerList(int application_seq);

	List<EducationDto> getEducationList(int application_seq);

	int updateCareer(CareerDto career);

	int updateEducation(EducationDto education);

	RecruitDto getRecruit(int recruit_seq);

	int updateRecruit(RecruitDto recruitDto);

	int changeToDeleteApplication(int application_seq);

	int changeToDeleteRecruit(int recruit_seq);

	int apply(RecruitEntryDto recruitEntryDto, int emp_seq);

	List<CompanyDto> searchCompanyByEmployeeCount(int min, int max);

	List<CompanyDto> searchCompanyBySales(int min, int max);

	List<EmployeeDto> searchEmployeeByAgeGroup(int min, int max);

	List<RecruitDto> getCompanyRecruits(int comp_seq);
	
	List<Map<String, String>> getRecruitStats(int recruit_seq);

	EmployeeDto getEmp(int emp_seq);

}
