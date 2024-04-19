package domain;

public class EducationDto {
	

    private int eduSeq;
    private int applicationSeq;
    private String schoolName;
    private String departmentName;
    
    
	public int getEduSeq() {
		return eduSeq;
	}
	public void setEduSeq(int eduSeq) {
		this.eduSeq = eduSeq;
	}
	public int getApplicationSeq() {
		return applicationSeq;
	}
	public void setApplicationSeq(int applicationSeq) {
		this.applicationSeq = applicationSeq;
	}
	public String getSchoolName() {
		return schoolName;
	}
	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}
	public String getDepartmentName() {
		return departmentName;
	}
	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}
    
    

}
