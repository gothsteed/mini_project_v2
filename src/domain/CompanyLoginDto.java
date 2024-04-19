package domain;

public class CompanyLoginDto {

	// field, attribute, property, 속성
	
	private int comp_seq;       	// not null number        
	private String comp_id;         // not null nvarchar2(8)  
	private String comp_password;   // not null NVARCHAR2(14) 
	private String comp_name;       // not null nvarchar2(20) 
	private int status;             // not null number(1)  
	
	CompanyDto cdto = new CompanyDto(); 

	// method, operation, 기능
	

	public int getComp_seq() {
		return comp_seq;
	}
	public void setComp_seq(int comp_seq) {
		this.comp_seq = comp_seq;
	}
	public String getComp_id() {
		return comp_id;
	}
	public void setComp_id(String comp_id) {
		this.comp_id = comp_id;
	}
	public String getComp_password() {
		return comp_password;
	}
	public void setComp_password(String comp_password) {
		this.comp_password = comp_password;
	}
	public String getComp_name() {
		return comp_name;
	}
	public void setComp_name(String comp_name) {
		this.comp_name = comp_name;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	
	
	public CompanyDto getCdto() {
		return cdto;
	}
	public void setCdto(CompanyDto cdto) {
		this.cdto = cdto;
	}

	
	
}		
