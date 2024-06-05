package domain;

import java.util.Date;

public class RecruitEntry {
    private Integer entrySeq;
    private Recruit recruit;
    private Application application;
    private Date entryDate;
    private Integer passStatus;

    public RecruitEntry(Integer entrySeq, Recruit recruit, Application application, Date entryDate, Integer passStatus) {
        this.entrySeq = entrySeq;
        this.recruit = recruit;
        this.application = application;
        this.entryDate = entryDate;
        this.passStatus = passStatus;
    }

    public Integer getEntrySeq() {
        return entrySeq;
    }

    public Recruit getRecruit() {
        return recruit;
    }

    public Application getApplication() {
        return application;
    }

    public Date getEntryDate() {
        return entryDate;
    }

    public Integer getPassStatus() {
        return passStatus;
    }
}
