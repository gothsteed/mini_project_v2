package domain;

import java.util.Date;

public class Recruit {
    private Integer recruitSeq;
    private Integer compSeq;
    private Integer locationSeq;
    private Integer employTypeSeq;
    private String compName;
    private String recruitTitle;
    private String license;
    private String screening;
    private String position;
    private Integer recruitNum;
    private String eduLevel;
    private String expLevel;
    private Integer salary;
    private Date recruitStartDate;
    private Date recruitEndDate;
    private Date resultDate;
    private String recruitManagerName;
    private String recruitManagerEmail;
    private String hirePeriod;
    private Integer status;
    private Integer applicationCount;

    public Recruit(Integer recruitSeq, Integer compSeq, Integer locationSeq, Integer employTypeSeq, String compName, String recruitTitle, String license, String screening, String position, Integer recruitNum, String eduLevel, String expLevel, Integer salary, Date recruitStartDate, Date recruitEndDate, Date resultDate, String recruitManagerName, String recruitManagerEmail, String hirePeriod, Integer status, Integer applicationCount) {
        this.recruitSeq = recruitSeq;
        this.compSeq = compSeq;
        this.locationSeq = locationSeq;
        this.employTypeSeq = employTypeSeq;
        this.compName = compName;
        this.recruitTitle = recruitTitle;
        this.license = license;
        this.screening = screening;
        this.position = position;
        this.recruitNum = recruitNum;
        this.eduLevel = eduLevel;
        this.expLevel = expLevel;
        this.salary = salary;
        this.recruitStartDate = recruitStartDate;
        this.recruitEndDate = recruitEndDate;
        this.resultDate = resultDate;
        this.recruitManagerName = recruitManagerName;
        this.recruitManagerEmail = recruitManagerEmail;
        this.hirePeriod = hirePeriod;
        this.status = status;
        this.applicationCount = applicationCount;
    }

    public Recruit(Integer recruitSeq, Integer compSeq, Integer locationSeq, Integer employTypeSeq,
                   String compName, String recruitTitle, String license, String screening, String position,
                   Integer recruitNum, String eduLevel, String expLevel, Integer salary, Date recruitStartDate,
                   Date recruitEndDate, Date resultDate, String recruitManagerName, String recruitManagerEmail,
                   String hirePeriod, Integer status) {
        this.recruitSeq = recruitSeq;
        this.compSeq = compSeq;
        this.locationSeq = locationSeq;
        this.employTypeSeq = employTypeSeq;
        this.compName = compName;
        this.recruitTitle = recruitTitle;
        this.license = license;
        this.screening = screening;
        this.position = position;
        this.recruitNum = recruitNum;
        this.eduLevel = eduLevel;
        this.expLevel = expLevel;
        this.salary = salary;
        this.recruitStartDate = recruitStartDate;
        this.recruitEndDate = recruitEndDate;
        this.resultDate = resultDate;
        this.recruitManagerName = recruitManagerName;
        this.recruitManagerEmail = recruitManagerEmail;
        this.hirePeriod = hirePeriod;
        this.status = status;
    }



    public Integer getRecruitSeq() {
        return recruitSeq;
    }

    public Integer getCompSeq() {
        return compSeq;
    }

    public Integer getLocationSeq() {
        return locationSeq;
    }

    public Integer getEmployTypeSeq() {
        return employTypeSeq;
    }

    public String getCompName() {
        return compName;
    }

    public String getRecruitTitle() {
        return recruitTitle;
    }

    public String getLicense() {
        return license;
    }

    public String getScreening() {
        return screening;
    }

    public String getPosition() {
        return position;
    }

    public Integer getRecruitNum() {
        return recruitNum;
    }

    public String getEduLevel() {
        return eduLevel;
    }

    public String getExpLevel() {
        return expLevel;
    }

    public Integer getSalary() {
        return salary;
    }

    public Date getRecruitStartDate() {
        return recruitStartDate;
    }

    public Date getRecruitEndDate() {
        return recruitEndDate;
    }

    public Date getResultDate() {
        return resultDate;
    }

    public String getRecruitManagerName() {
        return recruitManagerName;
    }

    public String getRecruitManagerEmail() {
        return recruitManagerEmail;
    }

    public String getHirePeriod() {
        return hirePeriod;
    }

    public Integer getStatus() {
        return status;
    }

    public Integer getApplicationCount() {
        return applicationCount;
    }
}
