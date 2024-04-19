package controller.hss;

import java.util.List;
import java.util.Scanner;

import domain.*;
import model.HssMember.*;


public class HssSearchComCtrl {
	private HssMemberDao HssMemberDao = new HssMemberDaoImple();


	//1. 회사이름검색
	public void compNameSearch(Scanner sc) {

		System.out.print("▷ 검색하실 회사명을 입력하세요 : ");
		String compName = sc.nextLine();
		
		List<CompanyDto> compList = HssMemberDao.compNameSearch(compName);
		if(compList.size() > 0) {
			System.out.println("-".repeat(50));
			System.out.println("회사명\t기업규모\t설립일\t회사주소\t"
					+ "대표자이름\t사원수\t자본금\t매출액\t");
			System.out.println("-".repeat(50));
			
			StringBuilder sb = new StringBuilder();
			
			for(CompanyDto cpdto : compList) {
				sb.append(cpdto.getComp_name() + "\t" + cpdto.getComp_scale() + "\t" + cpdto.getComp_est_date() + "\t"
						+ cpdto.getComp_addr() + "\t" + cpdto.getComp_ceo() + "\t" + cpdto.getComp_emp_cnt() + "\t"
						+ cpdto.getComp_capital() + "\t" + cpdto.getComp_sales() + "\n"
						);
			}
			System.out.println(sb.toString());
			
			
		} else {
			System.out.println("검색결과 회사명이 " + compName + "인 회사는 없습니다.");
		}
		
	}
	
	//2. 자본금검색
	public void compCapital(Scanner sc) {
		
		while(true) {
			try {
				System.out.print("▷ 검색시 최소 자본금을 입력하세요 : ");
				String capital = sc.nextLine();
				List<CompanyDto> compList = HssMemberDao.compCapitalSearch(Integer.parseInt(capital));
				if(compList.size() > 0) {
					System.out.println("-".repeat(50));
					System.out.println("회사명\t기업규모\t설립일\t회사주소\t"
							+ "대표자이름\t사원수\t자본금\t매출액\t");
					System.out.println("-".repeat(50));
					
					StringBuilder sb = new StringBuilder();
					
					for(CompanyDto cpdto : compList) {
						sb.append(cpdto.getComp_name() + "\t" + cpdto.getComp_scale() + "\t" + cpdto.getComp_est_date() + "\t"
								+ cpdto.getComp_addr() + "\t" + cpdto.getComp_ceo() + "\t" + cpdto.getComp_emp_cnt() + "\t"
								+ cpdto.getComp_capital() + "\t" + cpdto.getComp_sales() + "\n"
								);
					}
					System.out.println(sb.toString());
					break;
				} else {
					System.out.println("검색결과 자본금이 " + capital + "이상인 회사는 없습니다.");
					break;
				}
			} catch (NumberFormatException e) {
				System.out.println("[경고] 숫자만 입력하세요.");
			}
		}
	}
	

	//5. 공고제목검색 
	public void titleSearch(Scanner sc) {
		


		System.out.print("▷ 검색하실 공고명을 입력하세요 : ");
		String title = sc.nextLine();
		
		List<RecruitDto> rcList = HssMemberDao.titleSearch(title);
		if(rcList.size() > 0) {
			System.out.println("-".repeat(50));
			System.out.println("회사명\t공고제목\t요구자격증\t전형\t"
					+ "직무\t채용인원\t요구학력\t요구경력\t급여\t모집시작일자\t모집마감일자\t합격자발표일\t"
					+ "채용담당자이름\t채용담당자이메일\t고용기간");
			System.out.println("-".repeat(50));
			
			StringBuilder sb = new StringBuilder();
			
			for(RecruitDto rdto : rcList) {
				sb.append(rdto.getComp_name() + "\t" + rdto.getRecruit_title() + "\t" +
						rdto.getLicense() + "\t" + rdto.getScreening() + "\t" + rdto.getPosition() + 
						rdto.getEdu_level() + "\t" + rdto.getExp_level() + "\t" + rdto.getSalary() + 
						rdto.getRecruit_start_date() + "\t" + rdto.getRecruit_end_date() + "\t" +
						rdto.getResult_date() + "\t" + rdto.getRecruit_manager_name() + "\t" +
						rdto.getRecruit_manager_email() + "\t" + rdto.getHire_period() + "\n"

						);
			}
			System.out.println(sb.toString());
			
			
		} else {
			System.out.println("검색결과 " + title + "을(를) 포함하는 공고는 없습니다.");
		}
		
		
	}	
}

