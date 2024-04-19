package model.ksj;

import java.util.List;

import domain.CompanyLoginDto;
import domain.RecruitEntryDto;

public interface CompanyDao_ksj {

	CompanyLoginDto viewContents(int comp_seq);

	int drop_Member(int comp_seq);

	int update_emp_info(CompanyLoginDto companyLoginDto);
	
	List<RecruitEntryDto> searchApplicantsByJobPosting(int comp_seq);




}
