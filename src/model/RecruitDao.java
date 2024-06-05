package model;

import domain.Recruit;
import domain.RecruitEntryManager;
import domain.RecruitManager;
import dto.RecruitDto;

public interface RecruitDao {


    RecruitManager selectRecruitByTitle(String title);

    RecruitManager selectAllRecruit();

    Recruit selectBySeq(int recruitSeq);

    RecruitEntryManager selectFromRecruitEntryByEmpSeq(int empSeq);

    int insert(RecruitDto recruitDto);

    RecruitManager selectRecruitByCompSeq(int seq);
}
