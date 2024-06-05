package domain;

import java.util.Date;

public class Career {
    private Integer careerSeq;
    private Integer applicationSeq;
    private String position;
    private Date careerStartDate;
    private Date careerEndDate;


    public Career(Integer careerSeq, Integer applicationSeq, String position, Date careerStartDate, Date careerEndDate) {
        this.careerSeq = careerSeq;
        this.applicationSeq = applicationSeq;
        this.position = position;
        this.careerStartDate = careerStartDate;
        this.careerEndDate = careerEndDate;
    }

    public Integer getCareerSeq() {
        return careerSeq;
    }

    public Integer getApplicationSeq() {
        return applicationSeq;
    }

    public String getPosition() {
        return position;
    }

    public Date getCareerStartDate() {
        return careerStartDate;
    }

    public Date getCareerEndDate() {
        return careerEndDate;
    }
}
