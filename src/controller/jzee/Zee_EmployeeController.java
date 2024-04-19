package controller.jzee;

import java.util.List;

import domain.CompanyDto;
import model.jzee.Zee_CompanyDao;
import model.jzee.Zee_CompanyDao_imple;
import model.jzee.Zee_EmployeeDao;
import model.jzee.Zee_EmployeeDao_imple;




public class Zee_EmployeeController {

	Zee_CompanyDao zee_CompanyDao = new Zee_CompanyDao_imple();
	Zee_EmployeeDao edao = new Zee_EmployeeDao_imple();
	
	 
	 
	 
	 
	 
	// *** 구직자가 모든구인회사 조회 *** //
	public void showAllComp() {
		
		
		List<CompanyDto> companyList = edao.showAllComp();
		
		if(companyList.size() > 0 ) { //구인회사가 있다면
			System.out.println("-".repeat(40));
			System.out.print(" 회사이름      회사규모      회사주소	    회사이메일	       대표자	  사원수	");
			System.out.print("  	 매출액		 자본금          설립일         사대보험여부 \n");
			System.out.println("-".repeat(40));
			
			StringBuilder sb = new StringBuilder();
			
			
			for(CompanyDto comp  : companyList ) {
				
				sb.append(comp.getComp_name()+ " \t"+
						 comp.getComp_scale()+ " \t"+
						 comp.getComp_addr()+ " \t"+
						 comp.getComp_email()+ "  \t"+
						 comp.getComp_ceo()+ " \t"+
						 comp.getComp_emp_cnt()+ " \t"+
						 comp.getComp_sales()+ "  \t"+
						 comp.getComp_capital()+ "  \t"+
						 comp.getComp_est_date()+ "  \t"+
						 comp.getComp_insurance()+ " \t"+
						 comp.getIndust_name()+"\n");
				
			}// end of for----------------------------

			System.out.println(sb.toString());
			
		}
		else { //구인회사가 없다면
			System.out.println(">>>등록된 회사가 아직 없습니다.<<<");
		}
		
	}//end of showAllComp---------------------------------
	
}
