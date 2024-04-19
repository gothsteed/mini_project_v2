package controller.hss;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

import domain.*;
import model.HssCompany.*;
import model.HssMember.HssMemberDao;


public class HssSearchEmpCtrl {
	private HssCompanyDao HssCompanyDao = new HssCompanyDaoImple();




	//1. 성별검색
	public void genderSearch(Scanner sc) {

		System.out.print("▷ 검색하실 성별을 입력하세요(남,여로만 입력하세요) : ");
		String gender = sc.nextLine();
		
		List<EmployeeDto> empGenderlist = HssCompanyDao.empGenderSearch(gender);
		if(empGenderlist.size() > 0) {
			System.out.println("-".repeat(50));
			System.out.println("성별\t아이디\t이메일\t이름\t"
					+ "거주지역\t전화번호");
			System.out.println("-".repeat(50));
			
			StringBuilder sb = new StringBuilder();
			
			for(EmployeeDto empdto : empGenderlist) {
				sb.append( gender + "\t" + empdto.getEmp_id() + "\t" + empdto.getEmp_email() + "\t" +
						   empdto.getEmp_name() + "\t" + empdto.getEmp_address() + "\t" +
						   empdto.getEmp_tel() + "\n"
						);
			}
			System.out.println(sb.toString());
			
			
		} else {
			System.out.println("검색결과 성별이 " + gender + "인 사원은 없습니다.");
		}
		
	}
	



	
}

