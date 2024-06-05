package model;

import domain.Application;
import domain.ApplicationManager;
import dto.ApplicationDto;

public interface ApplicationDao {
    int insertIntoRecruitEntry(int applicationSeq, int recruitSeq);

    Application selectByEmpSeqApplicationSeq(int empSeq, int applicationSeq);

    int insertApplication(ApplicationDto applicationDto);

    Application selectApplication(int applicationSeq);

    int updateApplication(ApplicationDto applicationDto);

    int deleteApplication(int applicationSeq, int empSeq);

    ApplicationManager selectApplicationByEmpSeq(int empSeq);

    ApplicationManager selectApplicationFromRecruitEntryByRecruitSeq(int recruitSeq);
}
