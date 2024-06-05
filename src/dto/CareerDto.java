package dto;

import domain.Career;

import java.io.CharArrayReader;
import java.util.Date;

public class CareerDto {
	
	private Integer careerSeq;
	private Integer applicationSeq;
	private String position;
	private Date careerStartDate;
	private Date careerEndDate;
	
	private EmployeeDto employeeDto;
	
	
	
	
	public EmployeeDto getEmployeeDto() {
		return employeeDto;
	}
	public void setEmployeeDto(EmployeeDto employeeDto) {
		this.employeeDto = employeeDto;
	}
	public Integer getCareerSeq() {
		return careerSeq;
	}
	public void setCareerSeq(int careerSeq) {
		this.careerSeq = careerSeq;
	}
	public Integer getApplicationSeq() {
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

	static public CareerDto of(Career career) {
		CareerDto careerDto =   new CareerDto();

		careerDto.setCareerSeq(career.getCareerSeq());
		careerDto.setApplicationSeq(career.getApplicationSeq());
		careerDto.setPosition(careerDto.getPosition());
		careerDto.setCareerStartDate(career.getCareerStartDate());
		careerDto.setCareerEndDate(career.getCareerEndDate());

		return  careerDto;
	}



	public Date getCareerStartDate() {
		return careerStartDate;
	}

	public void setCareerStartDate(Date careerStartDate) {
		this.careerStartDate = careerStartDate;
	}

	public Date getCareerEndDate() {
		return careerEndDate;
	}

	public void setCareerEndDate(Date careerEndDate) {
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
