package dto;

//데이터베이스에서 domain 이란?
//엔티티의 속성들이 가질 수 있는 값들의 집합을 뜻하는 것이다.
//대부분의 DBMS에서 도메인이란 속성에 대응하는 컬럼에 대한 데이터 타입(Data Type)과 길이를 의미한다.

import domain.Employee;

//=== DTO(Data Transfer Object, 데이터전송(운반)객체 )
//쉽게 말해서 DTO는 테이블의 1개 행(ROW)을 말한다.
//어떤 테이블에 데이터를 insert 하고자 할때 DTO에 담아서 보낸다.
//또한 어떤 테이블에서 데이터를 select 하고자 할때도 DTO에 담아서 읽어온다.
public class EmployeeDto {

	// userseq 얘들이 컬럼

	// field, attribute, property, 속성

	private int emp_seq;             // 회원번호 NN SQ
	private String emp_id;           // 회원아이디 NN UQ
	private String emp_email;        // 회원이메일 NN UQ
	private String emp_name;         // 회원이름 NN
	private String jubun;            // 주민번호 NN(13)
	private String emp_address;      // 주소
	private String emp_tel;          // 전화번호 NN

	private String gender;
	private int age;
	private int ageRange;

	public EmployeeDto(int emp_seq, String emp_id, String emp_email, String emp_name, String jubun, String emp_address, String emp_tel, String gender, int age, int ageRange) {
		this.emp_seq = emp_seq;
		this.emp_id = emp_id;
		this.emp_email = emp_email;
		this.emp_name = emp_name;
		this.jubun = jubun;
		this.emp_address = emp_address;
		this.emp_tel = emp_tel;
		this.gender = gender;
		this.age = age;
		this.ageRange = ageRange;
	}

	static public EmployeeDto of(Employee employee) {
		return new EmployeeDto(
				employee.getEmpSeq(),
				employee.getEmpId(),
				employee.getEmpEmail(),
				employee.getEmpName(),
				employee.getJubun(),
				employee.getEmpAddr(), // Assuming there's a method getEmpAddr() in Employee
				employee.getEmpTel(),
				employee.getGender(),
				employee.getAge(),
				employee.getAgeRange()
		);
	}


	
	// 데이터 유효성 검사는 생략하겠음.
	// method, operation, 기능
	
	//private EmployeeLoginDto employeeLoginDto;

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

	public String getEmp_email() {
		return emp_email;
	}

	public void setEmp_email(String emp_email) {
		this.emp_email = emp_email;
	}

	public String getEmp_name() {
		return emp_name;
	}

	public void setEmp_name(String emp_name) {
		this.emp_name = emp_name;
	}

	public String getJubun() {
		return jubun;
	}

	public void setJubun(String jubun) {
		this.jubun = jubun;
	}

	public String getEmp_address() {
		return emp_address;
	}

	public void setEmp_address(String emp_address) {
		this.emp_address = emp_address;
	}

	public String getEmp_tel() {
		return emp_tel;
	}

	public void setEmp_tel(String emp_tel) {
		this.emp_tel = emp_tel;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public int getAgeRange() {
		return ageRange;
	}

	public void setAgeRange(int ageRange) {
		this.ageRange = ageRange;
	}

//	public EmployeeLoginDto getEmployeeLoginDto() {
//		return employeeLoginDto;
//	}
//
//	public void setEmployeeLoginDto(EmployeeLoginDto employeeLoginDto) {
//		this.employeeLoginDto = employeeLoginDto;
//	}

//	@Override
//	public String toString() {
//
//		return "=== 나의 정보===\n"
//			 + "◇ 성명 : " + emp_name + "\n"
// 			 + "◇ 연락처 : " + emp_tel + "\n"
//			 + "◇ 이메일 : " + emp_email + "\n";
//	}
	
} // end of public class MemberDTO--------------------------
