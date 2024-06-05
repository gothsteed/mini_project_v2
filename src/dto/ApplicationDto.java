package dto;

import domain.Application;

import java.security.cert.Certificate;
import java.util.List;

public class ApplicationDto {
	// 글번호

	private Integer applicationSeq;
	private Integer empSeq;
	private Integer locationSeq;
	private Integer employTypeSeq;
	private Integer militarySeq;
	private String license;

	private Integer hopeSal;

	private String motive;


	private List<CareerDto> careerDtoList;
	private List<EducationDto> educationDtoList;



	public static ApplicationDto of(Application application) {
		ApplicationDto applicationDto = new ApplicationDto();

		applicationDto.setApplicationSeq(application.getApplicationSeq());
		applicationDto.setEmpSeq(application.getEmpSeq());
		applicationDto.setLocationSeq(application.getLocationSeq());
		applicationDto.setEmployTypeSeq(application.getEmployTypeSeq());
		applicationDto.setMilitarySeq(application.getMilitarySeq());
		applicationDto.setLicense(application.getLicense());
		applicationDto.setHopeSal(application.getHopeSal());
		applicationDto.setMotive(application.getMotive());

		if(application.getCareerManager() != null) {
			applicationDto.setCareerDtoList(application.getCareerManager().getCareerDtoList());
		}

		if(application.getEducationManager() != null) {
			applicationDto.setEducationDtoList(application.getEducationManager().getEducationDtoList());
		}

		return applicationDto;
	}


	public Integer getApplicationSeq() {
		return applicationSeq;
	}

	public void setApplicationSeq(Integer applicationSeq) {
		this.applicationSeq = applicationSeq;
	}

	public Integer getEmpSeq() {
		return empSeq;
	}

	public void setEmpSeq(Integer empSeq) {
		this.empSeq = empSeq;
	}

	public Integer getLocationSeq() {
		return locationSeq;
	}

	public void setLocationSeq(Integer locationSeq) {
		this.locationSeq = locationSeq;
	}

	public Integer getEmployTypeSeq() {
		return employTypeSeq;
	}

	public void setEmployTypeSeq(Integer employTypeSeq) {
		this.employTypeSeq = employTypeSeq;
	}

	public Integer getMilitarySeq() {
		return militarySeq;
	}

	public void setMilitarySeq(Integer militarySeq) {
		this.militarySeq = militarySeq;
	}

	public String getLicense() {
		return license;
	}

	public void setLicense(String license) {
		this.license = license;
	}

	public Integer getHopeSal() {
		return hopeSal;
	}

	public void setHopeSal(Integer hopeSal) {
		this.hopeSal = hopeSal;
	}

	public String getMotive() {
		return motive;
	}

	public void setMotive(String motive) {
		this.motive = motive;
	}


	public List<CareerDto> getCareerDtoList() {
		return careerDtoList;
	}

	public void setCareerDtoList(List<CareerDto> careerDtoList) {
		this.careerDtoList = careerDtoList;
	}

	public List<EducationDto> getEducationDtoList() {
		return educationDtoList;
	}

	public void setEducationDtoList(List<EducationDto> educationDtoList) {
		this.educationDtoList = educationDtoList;
	}
}
