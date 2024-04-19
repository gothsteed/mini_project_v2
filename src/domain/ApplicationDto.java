package domain;

public class ApplicationDto {
	// 글번호

	private int application_seq;
	private int emp_seq;
	private int location_seq;
	private int employ_type_seq;
	private int military_seq;
	private String license;
	private int hope_sal;
	private int status;
	private String motive;
	
	private EmployeeDto employeeDto;
	private CareerDto careerDto;
	
	
	
	private String loctaion_name;
	private String military_status;
	private String emp_type_name;
	
	private String careerStartDate;
	private String careerEndDate;
	private String position;
	
	
	
	
	
	
	
	
	
	public CareerDto getCareerDto() {
		return careerDto;
	}
	public void setCareerDto(CareerDto careerDto) {
		this.careerDto = careerDto;
	}
	public String getLoctaion_name() {
		return loctaion_name;
	}
	public void setLoctaion_name(String loctaion_name) {
		this.loctaion_name = loctaion_name;
	}
	public String getMilitary_status() {
		return military_status;
	}
	public void setMilitary_status(String military_status) {
		this.military_status = military_status;
	}
	public String getEmp_type_name() {
		return emp_type_name;
	}
	public void setEmp_type_name(String emp_type_name) {
		this.emp_type_name = emp_type_name;
	}
	public String getCareerStartDate() {
		return careerStartDate;
	}
	public void setCareerStartDate(String careerStartDate) {
		this.careerStartDate = careerStartDate;
	}
	public String getCareerEndDate() {
		return careerEndDate;
	}
	public void setCareerEndDate(String careerEndDate) {
		this.careerEndDate = careerEndDate;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	public EmployeeDto getEmployeeDto() {
		return employeeDto;
	}
	public void setEmployeeDto(EmployeeDto employeeDto) {
		this.employeeDto = employeeDto;
	}
	public String getMotive() {
		return motive;
	}
	public void setMotive(String motive) {
		this.motive = motive;
	}
	
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	
	
	public int getApplication_seq() {
		return application_seq;
	}
	public void setApplication_seq(int application_seq) {
		this.application_seq = application_seq;
	}
	public int getEmp_seq() {
		return emp_seq;
	}
	public void setEmp_seq(int emp_seq) {
		this.emp_seq = emp_seq;
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
	public int getMilitary_seq() {
		return military_seq;
	}
	public void setMilitary_seq(int military_seq) {
		this.military_seq = military_seq;
	}
	public String getLicense() {
		return license;
	}
	public void setLicense(String license) {
		this.license = license;
	}
	public int getHope_sal() {
		return hope_sal;
	}
	public void setHope_sal(int hope_sal) {
		this.hope_sal = hope_sal;
	}
	   
	   
	

}
