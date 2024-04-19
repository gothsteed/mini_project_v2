package controller.jzee;



import java.util.List;
import java.util.Scanner;

import javax.swing.text.Position;

import domain.ApplicationDto;
import domain.CompanyDto;
import domain.CompanyLoginDto;
import domain.EmployeeDto;
import domain.EmployeeLoginDto;
import domain.RecruitEntryDto;
import model.jzee.Zee_CompanyDao;
import model.jzee.Zee_CompanyDao_imple;
import model.jzee.Zee_EmployeeDao;
import model.jzee.Zee_EmployeeDao_imple;


public class Zee_CompanyController {

	Zee_CompanyDao cdao = new Zee_CompanyDao_imple();
	Zee_EmployeeDao edao = new Zee_EmployeeDao_imple();
	
	
	// *** 회사가 자기회사정보 조회 *** //
	public void showmyCompInfo(int comp_seq) {
		
		CompanyDto comp = cdao.showmyCompInfo(comp_seq);
		
		
	
		System.out.println("-".repeat(40));			
		System.out.println("회사ID\t비밀번호\t회사이름\t회사규모 \t회사주소\t회사이메일\t대표자\t사원수\t매출액\t자본금\t설립일\t사대보험여부\t산업명");	           
		System.out.println("-".repeat(40));

		StringBuilder sb = new StringBuilder();
		
		
		sb.append(comp.getComp_id()+"\t" + 
				comp.getCompanyLoginDto().getComp_password()+"\t" + 
				comp.getComp_name()+"\t" + 
				comp.getComp_scale()+"\t" + 
				comp.getComp_addr()+"\t" + 
				comp.getComp_email()+"\t" + 
				comp.getComp_ceo()+"\t" + 
				comp.getComp_emp_cnt()+"\t" + 
				comp.getComp_sales()+"\t" + 
				comp.getComp_capital()+"\t" + 
				comp.getComp_est_date()+"\t" + 
				comp.getComp_insurance()+"\t" + 
				comp.getIndust_name() + "\n");
		
		System.out.println(sb.toString());
				
			
	}
		



	//회사가 모든구직자 조회
	public void showAllEmployee() {

		
		
		List<EmployeeDto> employeeList = cdao.showAllEmployee();
		
		if(employeeList.size() > 0 ) { //구직자가 있다면

			System.out.println( "------------------------------------------------------------------------------------------------------\r\n"
							  + "  아이디       비밀번호        성명                이메일                  생년월일      성별     전화번호\r\n"
							  + "-----------------------------------------------------------------------------------------------------------—-\r\n");

			
			StringBuilder sb = new StringBuilder();
			
			
			for(EmployeeDto emplo  : employeeList ) {
				
			
				sb.append(emplo.getEmp_id()+ "\t" +
						emplo.getEmp_password()+ "\t" + //회사가 왜 비번을 볼까?????
						emplo.getEmp_name()+ "\t" +
						emplo.getEmp_email()+ "\t" +
						emplo.getJubun().substring(0, 6) + "\t" +
						(emplo.getJubun().charAt(6) == '1' || emplo.getJubun().charAt(6) == '3'? "남" : "여") + "\t" +
						emplo.getEmp_tel()+"\n");
				
			}// end of for----------------------------

			System.out.println(sb.toString());
			
		}
		else { //구직자가 없다면
			System.out.println(">>>등록된 구직자가 아직 없습니다.<<<");
		}
		
	}//end of showAllComp-------------------------------
	
	
	
	
	
	// *** 구직자번호검색 ***
	public void searchEmployeeSeq (Scanner sc) {
		
		int searchSeq = 0;
		String yn = "";
		
	do{
	
		System.out.println(" ▶ 검색할 구직자 번호를 입력하세요 : ");
		searchSeq = Integer.parseInt(sc.nextLine());
	
	
		EmployeeDto edto= cdao.searchEmployeeSeq(searchSeq);//실행
		
		if(edto==null) {
			
			System.out.println("검색결과 구직자 번호가 " + searchSeq + "인 사원은 없습니다.");
		}
		
		else {
			System.out.println( "------------------------------------------------------------------------------------------------------\r\n"
					+ "  	구직자번호		아이디       비밀번호        성명                이메일                  생년월일      성별     전화번호\r\n"
					+ "-----------------------------------------------------------------------------------------------------------—-\r\n");

			StringBuilder sb = new StringBuilder();
			
			sb.append(edto.getEmp_seq()+ "\t" +
					edto.getEmp_id()+ "\t" +
					edto.getEmp_name()+ "\t" +
					edto.getEmp_email()+ "\t" +
					edto.getJubun().substring(0, 6) + "\t" +
					(edto.getJubun().charAt(6) == '1' || edto.getJubun().charAt(6) == '3'? "남" : "여") + "\t" +
					edto.getEmp_tel()+"\n");
			
			System.out.println(sb.toString());
					
		}
		
		System.out.println("다시 검색하시겠습니까? [Y/N]");
		yn = sc.nextLine();

		
		}while(!( "n".equalsIgnoreCase(yn)));
	
	}
	    
	
	
	
	
	// *** 이력서번호로 검색 상세정보***
	public void searchAppliSeq (Scanner sc) {
	
		int selectNo = 0; 
		String yn = "";
		
	do{
		System.out.println(" 이력서 번호 입력 : ");
		selectNo = Integer.parseInt(sc.nextLine());
		
		
		ApplicationDto adto = cdao.searchAppliSeq(selectNo);
		
		if (adto==null) {
			System.out.println("지원한 이력서가 없습니다.");
		}
		
	else {
			
			
			System.out.println("\n 이력서번호\t\t이름\t\t사는지역\t\t전화번호\t\t이메일\t\t\t고용형태종류\t\t병역상태\t\t소유자격증\t\t학교명\t\t학과명\t\t희망연봉\t\t지원동기\t\t이력서삭제여부");
			System.out.println("-".repeat(250));
			
			StringBuilder sb = new StringBuilder();
			
			sb.append(adto.getApplication_seq() + "\t\t"
					+adto.getEmployeeDto().getEmp_name() + "\t\t"
					+adto.getLoctaion_name() + "\t\t"
					+adto.getEmployeeDto().getEmp_tel() + "\t\t"
					+adto.getEmployeeDto().getEmp_email() + "\t\t"
					+adto.getEmp_type_name() + "\t\t"
					+adto.getMilitary_status() + "\t\t"
					+adto.getLicense() + "\t\t"
					+adto.getHope_sal() + "\t\t"
					+adto.getMotive() + "\t\t"
					+adto.getStatus() + "\t\t"
					);
			
			System.out.println(sb.toString());
			
		}
		
		System.out.println("다시 검색하시겠습니까? [Y/N]");
		yn = sc.nextLine();

		
		}while(!( "n".equalsIgnoreCase(yn)));
				
		}		
	 

		
	
}
