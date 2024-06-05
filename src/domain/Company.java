package domain;

import model.CompanyDao;

import java.util.Date;

public class Company {

    private Integer compSeq;
    private String compId;
    private String compEmail;
    private String industryName;
    private String compName;
    private String compScale;
    private Date compEstDate;
    private String compAddr;
    private String compCeo;
    private Integer compEmpCnt;
    private Integer compCapital;
    private Integer compSales;
    private String compInsurance;
    private Integer status;


    public Company(Integer compSeq, String compId, String compEmail, String industryName,
                   String compName, String compScale, Date compEstDate, String compAddr,
                   String compCeo, Integer compEmpCnt, Integer compCapital, Integer compSales,
                   String compInsurance, Integer status) {
        this.compSeq = compSeq;
        this.compId = compId;
        this.compEmail = compEmail;
        this.industryName = industryName;
        this.compName = compName;
        this.compScale = compScale;
        this.compEstDate = compEstDate;
        this.compAddr = compAddr;
        this.compCeo = compCeo;
        this.compEmpCnt = compEmpCnt;
        this.compCapital = compCapital;
        this.compSales = compSales;
        this.compInsurance = compInsurance;
        this.status = status;
    }


    public Integer getCompSeq() {
        return compSeq;
    }

    public String getCompId() {
        return compId;
    }

    public String getCompEmail() {
        return compEmail;
    }

    public String getIndustryName() {
        return industryName;
    }

    public String getCompName() {
        return compName;
    }

    public String getCompScale() {
        return compScale;
    }

    public Date getCompEstDate() {
        return compEstDate;
    }

    public String getCompAddr() {
        return compAddr;
    }

    public String getCompCeo() {
        return compCeo;
    }

    public Integer getCompEmpCnt() {
        return compEmpCnt;
    }

    public Integer getCompCapital() {
        return compCapital;
    }

    public Integer getCompSales() {
        return compSales;
    }

    public String getCompInsurance() {
        return compInsurance;
    }
}
