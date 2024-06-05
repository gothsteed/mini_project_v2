package dto;

import domain.RecruitEntry;

import java.util.Date;

public class RecruitEntryDto {
	
    private int entry_seq;          // ENTRY_SEQ (PK)
//    private int recruit_seq;        // RECRUIT_SEQ (FK)
//    private int application_seq;    // APPLICATION_SEQ (FK)
    private Date entry_date;        // ENTRY_DATE
    private int pass_status;        // PASS_STATUS
    
    
    //select 용 필드
//    private CompanyDto companyDto;
//    private EmployeeDto employeeDto;
    
    private RecruitDto recruitDto;
    private ApplicationDto applicationDto;


	static public RecruitEntryDto of(RecruitEntry recruitEntry) {
		RecruitEntryDto recruitEntryDto = new RecruitEntryDto();
		recruitEntryDto.setEntry_seq(recruitEntryDto.getEntry_seq());
		recruitEntryDto.setEntry_date(recruitEntry.getEntryDate());
		recruitEntryDto.setPass_status(recruitEntry.getPassStatus());

		recruitEntryDto.setApplicationDto(ApplicationDto.of(recruitEntry.getApplication()));
		recruitEntryDto.setRecruitDto(RecruitDto.of(recruitEntry.getRecruit()));

		return recruitEntryDto;
	}


	public int getEntry_seq() {
		return entry_seq;
	}

	public void setEntry_seq(int entry_seq) {
		this.entry_seq = entry_seq;
	}

	public Date getEntry_date() {
		return entry_date;
	}

	public void setEntry_date(Date entry_date) {
		this.entry_date = entry_date;
	}

	public int getPass_status() {
		return pass_status;
	}

	public void setPass_status(int pass_status) {
		this.pass_status = pass_status;
	}

	public RecruitDto getRecruitDto() {
		return recruitDto;
	}

	public void setRecruitDto(RecruitDto recruitDto) {
		this.recruitDto = recruitDto;
	}

	public ApplicationDto getApplicationDto() {
		return applicationDto;
	}

	public void setApplicationDto(ApplicationDto applicationDto) {
		this.applicationDto = applicationDto;
	}
}
