package domain;

public class CareerDto {
	
	private int careerSeq;
	private int applicationSeq;
	private String position;
	private String careerStartDate;
	private String careerEndDate;
	
	private EmployeeDto employeeDto;
	
	
	
	
	public EmployeeDto getEmployeeDto() {
		return employeeDto;
	}
	public void setEmployeeDto(EmployeeDto employeeDto) {
		this.employeeDto = employeeDto;
	}
	public int getCareerSeq() {
		return careerSeq;
	}
	public void setCareerSeq(int careerSeq) {
		this.careerSeq = careerSeq;
	}
	public int getApplicationSeq() {
		return applicationSeq;
	}
	public void setApplicationSeq(int applicationSeq) {
		this.applicationSeq = applicationSeq;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
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
	
	
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		sb.append("careerSeq : " + careerSeq + "\n");
		sb.append("position: " + position + "\n");
		sb.append("careerStartDate: " +careerStartDate + "\n");
		sb.append("careerEndDate : " + careerEndDate + "\n");
		return sb.toString();
	}
	
	
   public String CareerListOne() {
		return careerStartDate + "\t" + careerEndDate + "\t\t" + position
			   + "\t" + employeeDto.getEmp_name() + "\t\t" + employeeDto.getEmp_address() + "\t\t" + employeeDto.getEmp_tel();
   }
	

}
