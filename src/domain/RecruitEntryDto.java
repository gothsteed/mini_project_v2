package domain;

public class RecruitEntryDto {
	
    private int entry_seq;          // ENTRY_SEQ (PK)
    private int recruit_seq;        // RECRUIT_SEQ (FK)
    private int application_seq;    // APPLICATION_SEQ (FK)
    private String entry_date;        // ENTRY_DATE
    private int pass_status;        // PASS_STATUS
    
    
    //select 용 필드
    private CompanyDto companyDto;
    private EmployeeDto employeeDto;
    
    private RecruitDto recruitDto;
    private ApplicationDto applicationDto;
    
    
    
    
    
    
    
    
    
    
	public ApplicationDto getApplicationDto() {
		return applicationDto;
	}
	public void setApplicationDto(ApplicationDto applicationDto) {
		this.applicationDto = applicationDto;
	}
	public RecruitDto getRecruitDto() {
		return recruitDto;
	}
	public void setRecruitDto(RecruitDto recruitDto) {
		this.recruitDto = recruitDto;
	}
	public CompanyDto getCompanyDto() {
		return companyDto;
	}
	public void setCompanyDto(CompanyDto companyDto) {
		this.companyDto = companyDto;
	}
	public EmployeeDto getEmployeeDto() {
		return employeeDto;
	}
	public void setEmployeeDto(EmployeeDto employeeDto) {
		this.employeeDto = employeeDto;
	}
	
	
	public int getEntry_seq() {
		return entry_seq;
	}
	public void setEntry_seq(int entry_seq) {
		this.entry_seq = entry_seq;
	}
	public int getRecruit_seq() {
		return recruit_seq;
	}
	public void setRecruit_seq(int recruit_seq) {
		this.recruit_seq = recruit_seq;
	}
	public int getApplication_seq() {
		return application_seq;
	}
	public void setApplication_seq(int application_seq) {
		this.application_seq = application_seq;
	}
	public String getEntry_date() {
		return entry_date;
	}
	public void setEntry_date(String entry_date) {
		this.entry_date = entry_date;
	}
	public int getPass_status() {
		return pass_status;
	}
	public void setPass_status(int pass_status) {
		this.pass_status = pass_status;
	}
    
    
    

}
