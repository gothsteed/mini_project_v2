package model.kdh;


import java.util.List;
import java.util.Map;

import domain.CompanyDto;
import domain.CompanyLoginDto;
import domain.RecruitDto;



public interface CompanyDao_kdh {

	// 우리회사 채용공고 조회에서 공고 일렬번호 입력시 해당 공고 상세보기

	RecruitDto viewRecruitDetailList(int comp_seq, int recruit_seq);

}

