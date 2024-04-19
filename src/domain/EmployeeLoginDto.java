package domain;

public class EmployeeLoginDto {
	
	private int emp_seq;             // 회원번호 PK
	private String emp_id;           // 회원아이디 NN 
	private String emp_password;     // 회원비밀번호 NN
	private String emp_name;         // 회원이름 NN
	
	
	
	private EmployeeDto employeeDto;
	

	public EmployeeDto getEmployeeDto() {
		return employeeDto;
	}
	public void setEmployeeDto(EmployeeDto employeeDto) {
		this.employeeDto = employeeDto;
	}
	// getter/setter
	public int getEmp_seq() {
		return emp_seq;
	}
	public void setEmp_seq(int emp_seq) {
		this.emp_seq = emp_seq;
	}
	public String getEmp_id() {
		return emp_id;
	}
	public void setEmp_id(String emp_id) {
		this.emp_id = emp_id;
	}
	public String getEmp_password() {
		return emp_password;
	}
	public void setEmp_password(String emp_password) {
		this.emp_password = emp_password;
	}
	public String getEmp_name() {
		return emp_name;
	}
	public void setEmp_name(String emp_name) {
		this.emp_name = emp_name;
	}

}
