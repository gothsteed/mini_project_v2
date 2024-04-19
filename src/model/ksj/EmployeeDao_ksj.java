package model.ksj;

import java.util.List;
import java.util.Map;

import domain.EmployeeLoginDto;
import domain.RecruitEntryDto;

public interface EmployeeDao_ksj {

	EmployeeLoginDto viewContents(int emp_seq);

	int update_emp_info(EmployeeLoginDto employeeLoginDto);

	int drop_Member(int emp_seq);

	List<RecruitEntryDto> JobApplicationInquiry(int emp_seq);

	void updatePasswd(EmployeeLoginDto employeeLoginDto);

	List<Map<String, String>> viewApplication(int emp_seq);

	List<Map<String, String>> showApplicationEdu(int application_seq, int emp_seq);
	List<Map<String, String>> showApplicationCareer(int application_seq, int emp_seq);




}
