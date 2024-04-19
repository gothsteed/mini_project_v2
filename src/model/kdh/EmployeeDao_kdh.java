package model.kdh;

import java.util.List;

import domain.RecruitDto;

public interface EmployeeDao_kdh {


	List<RecruitDto> viewCompanyallRecruits();


	// 우리회사 채용공고 조회에서 공고 일렬번호 입력시 해당 공고 상세보기

	RecruitDto viewRecruitDetail(int recruit_seq);
}
