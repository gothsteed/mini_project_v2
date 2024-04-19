package controller.ksj;

import java.util.List;
import java.util.Scanner;

import domain.CompanyDto;
import domain.CompanyLoginDto;
import domain.RecruitEntryDto;
import model.ksj.CompanyDaoImple_ksj;
import model.ksj.CompanyDao_ksj;

public class CompanyController_ksj {

	CompanyDao_ksj cdao_ksj = new CompanyDaoImple_ksj();

	
	
	// 2. 회사정보 수정 //
	public void update_comp_info(Scanner sc, int comp_seq) {
		int result = 0;
		
		CompanyDto companyDto = new CompanyDto();  
		CompanyLoginDto companyLoginDto = new CompanyLoginDto();

		System.out.println("\n>>> 회사정보 수정하기 <<<");

		companyLoginDto = cdao_ksj.viewContents(comp_seq);
		// 회사 로그인 한 정보의 시퀀스를 가져와서 비밀번호를 가져오고

		// 위의 시퀀스를 이용해 회사에 있는 자료들을 가져온다.
		System.out.println("--------------------------------------");
		System.out.println("[수정전 회사아이디] : " + companyLoginDto.getComp_id());
		System.out.println("[수정전 회사비밀번호] : " + companyLoginDto.getComp_password());
		System.out.println("[수정전 회사이름] : " + companyLoginDto.getComp_name());
		System.out.println("[수정전 회사이메일] : " + companyLoginDto.getCdto().getComp_email());
		System.out.println("[수정전 회사규모] : " + companyLoginDto.getCdto().getComp_scale());
		System.out.println("[수정전 회사설립일] : " + companyLoginDto.getCdto().getComp_est_date());
		System.out.println("[수정전 회사주소] : " + companyLoginDto.getCdto().getComp_addr());
		System.out.println("[수정전 회사대표자] : " + companyLoginDto.getCdto().getComp_ceo());
		System.out.println("[수정전 회사사원수] : " + companyLoginDto.getCdto().getComp_emp_cnt());
		System.out.println("[수정전 회사자본금] : " + companyLoginDto.getCdto().getComp_capital());
		System.out.println("[수정전 회사매출액] : " + companyLoginDto.getCdto().getComp_sales());
		System.out.println("[수정전 회사사대보험여부] : " + companyLoginDto.getCdto().getComp_insurance());
		System.out.println("--------------------------------------");
		System.out.println("---------------구직자 정보수정-----------------");

		companyDto.setComp_seq(comp_seq);
		companyLoginDto.setComp_seq(comp_seq);


		System.out.print("사용하실 비밀번호 입력 [변경하지 않으려면 그냥 엔터] : ");
		String comp_password = sc.nextLine();
		if (companyLoginDto.getComp_password() != null && comp_password.isBlank()) {
			comp_password = companyLoginDto.getComp_password();
		}
		companyLoginDto.setComp_password(comp_password);

		System.out.print("회사명 입력 [변경하지 않으려면 그냥 엔터] : ");
		String comp_name = sc.nextLine();
		if (companyLoginDto.getComp_name() != null && comp_name.isBlank()) {
			comp_name = companyLoginDto.getComp_name();
		}
		companyDto.setComp_name(comp_name);
		companyLoginDto.setComp_name(comp_name);

		System.out.print("사용하실 회사이메일 주소 입력 [변경하지 않으려면 그냥 엔터] : ");
		String comp_email = sc.nextLine();
		if (companyLoginDto.getCdto().getComp_email() != null && comp_email.isBlank()) {
			comp_email = companyLoginDto.getCdto().getComp_email();
		}
		companyDto.setComp_email(comp_email);
		
		System.out.print("회사 규모 [변경하지 않으려면 그냥 엔터] : ");
		String comp_scale = sc.nextLine();
		if (companyLoginDto.getCdto().getComp_scale() != null && comp_scale.isBlank()) {
			comp_scale = companyLoginDto.getCdto().getComp_scale();
		}
		companyDto.setComp_scale(comp_scale);

		System.out.print("회사주소 입력 [변경하지 않으려면 그냥 엔터] : ");
		String comp_addr = sc.nextLine();
		if (companyLoginDto.getCdto().getComp_addr() != null && comp_addr.isBlank()) {
			comp_addr = companyLoginDto.getCdto().getComp_addr();
		}
		companyDto.setComp_addr(comp_addr);

		System.out.print("회사 대표 입력 [변경하지 않으려면 그냥 엔터] : ");
		String comp_ceo = sc.nextLine();
		if (companyLoginDto.getCdto().getComp_ceo() != null && comp_ceo.isBlank()) {
			comp_ceo = companyLoginDto.getCdto().getComp_ceo();
		}
		companyDto.setComp_ceo(comp_ceo);
		
		System.out.print("사원수 [변경하지 않으려면 그냥 엔터] : ");
		String comp_emp_cnt = sc.nextLine();
		if (companyLoginDto.getCdto().getComp_emp_cnt() != 0 && comp_emp_cnt.isBlank()) {
			comp_emp_cnt = String.valueOf(companyLoginDto.getCdto().getComp_emp_cnt());
		}
		companyDto.setComp_emp_cnt(Integer.parseInt(comp_emp_cnt));
		
		System.out.print("자본금 [변경하지 않으려면 그냥 엔터] : ");
		String comp_capital = sc.nextLine();
		if (companyLoginDto.getCdto().getComp_capital() != 0 && comp_capital.isBlank()) {
			comp_capital = String.valueOf(companyLoginDto.getCdto().getComp_capital());
		}
		companyDto.setComp_capital(Integer.parseInt(comp_capital));
		
		System.out.print("매출액 [변경하지 않으려면 그냥 엔터] : ");
		String comp_sales = sc.nextLine();
		if (companyLoginDto.getCdto().getComp_sales() != 0 && comp_sales.isBlank()) {
			comp_sales = String.valueOf(companyLoginDto.getCdto().getComp_sales());
		}
		companyDto.setComp_sales(Integer.parseInt(comp_sales));
		
		System.out.print("사대보험 가입여부 [변경하지 않으려면 그냥 엔터] : ");
		String comp_insurance = sc.nextLine();
		if (companyLoginDto.getCdto().getComp_insurance() != null && comp_insurance.isBlank()) {
			comp_insurance = companyLoginDto.getCdto().getComp_insurance();
		}
		companyDto.setComp_insurance(comp_insurance);
		
		
		companyLoginDto.setCdto(companyDto);

		do {
			////////////////////////////////////////////////////////////
			System.out.print("▷ 이 정보로 수정 하시겠습니까? [Y/N] : ");
			String yn = sc.nextLine();

			// 게시판 글쓰기
			if ("y".equalsIgnoreCase(yn)) {
				result = cdao_ksj.update_emp_info(companyLoginDto);
				if (result == 1) {
					System.out.println(">>> 구직자 정보 수정 성공!! <<<");
				} else if (result == -1) {
					System.out.println(">>> 데이터를 정확하게 입력해주세요!! <<<");
					System.out.println(">> 구직자 정보 수정 실패!! <<");
				}
				break;

			} else if ("n".equalsIgnoreCase(yn)) {
				System.out.println(">> 구직자 정보 수정 취소!! <<");
				break;

			} else {
				System.out.println(">> Y 또는 N 만 입력하세요!! << \n");
				break;
			}
		} while (true);
	}
	// 2. 회사정보 수정 끝 //

	
	// 9. 회사 탈퇴하기 //
	public int drop_Member(Scanner sc, int comp_seq) {
		int result = 0;

		do {

			////////////////////////////////////////////////////////////
			System.out.print("▷ 정말로 회사 회원탈퇴 하시겠습니까? [Y/N] : ");
			String yn = sc.nextLine();
			// 게시판 글쓰기
			if ("y".equalsIgnoreCase(yn)) {
				result = cdao_ksj.drop_Member(comp_seq);
				if (result == 1) {
					System.out.println(">>> 회사 회원탈퇴 성공!! <<<");
					break;
				} else if (result == -1) {
					System.out.println(">>> 데이터를 정확하게 입력해주세요!! <<<");
					System.out.println(">> 회사 회원탈퇴 실패!! <<");
				}
				break;

			} else if ("n".equalsIgnoreCase(yn)) {
				System.out.println(">> 회사 회원탈퇴 취소!! <<");
				break;

			} else {
				System.out.println(">> Y 또는 N 만 입력하세요!! << \n");
				break;
			}
		} while (true);
		
		return result;

	}
	// 9. 회사 탈퇴하기 끝 //
	
	public void searchApplicantsByJobPosting(Scanner sc, int comp_seq) {
		List<RecruitEntryDto> RecruitEntryList = cdao_ksj.searchApplicantsByJobPosting(comp_seq);
		
		System.out.println(RecruitEntryList.size());
		
		
		if (RecruitEntryList.size() > 0) {
			System.out.println("\n공고번호\t이름\t\t이메일\t\t전화번호\t\t지원동기\t\t소유자격증\t학교명\t학과명\t희망연봉");
			System.out.println("-".repeat(250));
			
			StringBuilder sb = new StringBuilder();
			
			for(RecruitEntryDto recruitEntryDto : RecruitEntryList) {
				
				sb.append(recruitEntryDto.getRecruit_seq() + "\t"
						+ recruitEntryDto.getEmployeeDto().getEmp_name() + "\t\t"
						+ recruitEntryDto.getEmployeeDto().getEmp_email() + "\t"
						+ recruitEntryDto.getEmployeeDto().getEmp_tel() + "\t"
						+ recruitEntryDto.getApplicationDto().getMotive() + "\t"
						+ recruitEntryDto.getApplicationDto().getLicense() + "\t"
						+ recruitEntryDto.getApplicationDto().getHope_sal() + "\n");
				
			} // end of for-----------------------------
			
			
			System.out.println(sb.toString());
		}
		else {
			System.out.println("지원한 구직자가 없습니다.");
		}		
	}
	
	
	
	
	
	
	
	
	
}
