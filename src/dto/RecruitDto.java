package dto;

import domain.Recruit;

import java.util.Date;
import java.util.List;

public class RecruitDto {

	// field, attribute, property, 속성
	
	private int recruit_seq;				//(pk)	n/a	number
	private int comp_seq;					//(fk)	n/a	number
	private int location_seq;				//(fk)	n/a	number
	private int employ_type_seq;			//(fk)	n/a	number
	private String comp_name;				//n/a	nvarchar2(10)
	private String recruit_title;			//n/a	nvarchar2(20)
	private String license;					//n/a	nvarchar2(80)
	private String screening;				//n/a	nvarchar2(10)
	private String position;				//n/a	nvarchar2(10)
	private int recruit_num;				//n/a	number(2)
	private String edu_level;				//n/a	nvarchar2(5)
	private String exp_level;				//n/a	nvarchar2(4)
	private int salary;						//n/a	number
	private Date recruit_start_date;		//n/a	date
	private Date recruit_end_date;		//n/a	date
	private Date result_date;				//n/a	date
	private String recruit_manager_name;	//n/a	nvarchar2(10)
	private String recruit_manager_email;	//n/a	nvarchar2(20)
	private String hire_period;				//n/a	nvarchar2(10)
	private int status;
	
	
	//select 용 필드
	private int applicationCount;
	private List<ApplicationDto> applicationList;

	public static RecruitDto of(Recruit recruit) {

		RecruitDto dto = new RecruitDto();
		dto.setRecruit_seq(recruit.getRecruitSeq());
		dto.setComp_seq(recruit.getCompSeq());
		dto.setLocation_seq(recruit.getLocationSeq());
		dto.setEmploy_type_seq(recruit.getEmployTypeSeq());
		dto.setComp_name(recruit.getCompName());
		dto.setRecruit_title(recruit.getRecruitTitle());
		dto.setLicense(recruit.getLicense());
		dto.setScreening(recruit.getScreening());
		dto.setPosition(recruit.getPosition());
		dto.setRecruit_num(recruit.getRecruitNum());
		dto.setEdu_level(recruit.getEduLevel());
		dto.setExp_level(recruit.getExpLevel());
		dto.setSalary(recruit.getSalary());
		dto.setRecruit_start_date(recruit.getRecruitStartDate()); // Assuming simple date to String conversion
		dto.setRecruit_end_date(recruit.getRecruitEndDate());
		dto.setResult_date(recruit.getResultDate());
		dto.setRecruit_manager_name(recruit.getRecruitManagerName());
		dto.setRecruit_manager_email(recruit.getRecruitManagerEmail());
		dto.setHire_period(recruit.getHirePeriod());
		dto.setStatus(recruit.getStatus());

		if(recruit.getApplicationCount() != null) {
			dto.setApplicationCount(recruit.getApplicationCount());
		}
		// Additional fields like count or applicationList need to be set elsewhere as they are not part of the Recruit object

		return dto;
	}


	public int getApplicationCount() {
		return applicationCount;
	}

	public void setApplicationCount(int applicationCount) {
		this.applicationCount = applicationCount;
	}

	public List<ApplicationDto> getApplicationList() {
		return applicationList;
	}
	public void setApplicationList(List<ApplicationDto> applicationList) {
		this.applicationList = applicationList;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	
	
	public int getRecruit_seq() {
		return recruit_seq;
	}
	public void setRecruit_seq(int recruit_seq) {
		this.recruit_seq = recruit_seq;
	}
	public int getComp_seq() {
		return comp_seq;
	}
	public void setComp_seq(int comp_seq) {
		this.comp_seq = comp_seq;
	}
	public int getLocation_seq() {
		return location_seq;
	}
	public void setLocation_seq(int location_seq) {
		this.location_seq = location_seq;
	}
	public int getEmploy_type_seq() {
		return employ_type_seq;
	}
	public void setEmploy_type_seq(int employ_type_seq) {
		this.employ_type_seq = employ_type_seq;
	}
	public String getComp_name() {
		return comp_name;
	}
	public void setComp_name(String comp_name) {
		this.comp_name = comp_name;
	}
	public String getRecruit_title() {
		return recruit_title;
	}
	public void setRecruit_title(String recruit_title) {
		this.recruit_title = recruit_title;
	}
	public String getLicense() {
		return license;
	}
	public void setLicense(String license) {
		this.license = license;
	}
	public String getScreening() {
		return screening;
	}
	public void setScreening(String screening) {
		this.screening = screening;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	public int getRecruit_num() {
		return recruit_num;
	}
	public void setRecruit_num(int recruit_num) {
		this.recruit_num = recruit_num;
	}
	public String getEdu_level() {
		return edu_level;
	}
	public void setEdu_level(String edu_level) {
		this.edu_level = edu_level;
	}
	public String getExp_level() {
		return exp_level;
	}
	public void setExp_level(String exp_level) {
		this.exp_level = exp_level;
	}
	public int getSalary() {
		return salary;
	}
	public void setSalary(int salary) {
		this.salary = salary;
	}
	public Date getRecruit_start_date() {
		return recruit_start_date;
	}
	public void setRecruit_start_date(Date recruit_start_date) {
		this.recruit_start_date = recruit_start_date;
	}
	public Date getRecruit_end_date() {
		return recruit_end_date;
	}
	public void setRecruit_end_date(Date recruit_end_date) {
		this.recruit_end_date = recruit_end_date;
	}
	public Date getResult_date() {
		return result_date;
	}
	public void setResult_date(Date result_date) {
		this.result_date = result_date;
	}
	public String getRecruit_manager_name() {
		return recruit_manager_name;
	}
	public void setRecruit_manager_name(String recruit_manager_name) {
		this.recruit_manager_name = recruit_manager_name;
	}
	public String getRecruit_manager_email() {
		return recruit_manager_email;
	}
	public void setRecruit_manager_email(String recruit_manager_email) {
		this.recruit_manager_email = recruit_manager_email;
	}
	public String getHire_period() {
		return hire_period;
	}
	public void setHire_period(String hire_period) {
		this.hire_period = hire_period;
	}
	
	
	// method, operation, 기능
	

	

	
	
}
