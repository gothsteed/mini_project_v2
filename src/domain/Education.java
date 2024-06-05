package domain;

public class Education {
    private Integer eduSeq;
    private Integer applicationSeq;
    private String schoolName;
    private String departmentName;

    public Education(Integer eduSeq, Integer applicationSeq, String schoolName, String departmentName) {
        this.eduSeq = eduSeq;
        this.applicationSeq = applicationSeq;
        this.schoolName = schoolName;
        this.departmentName = departmentName;
    }


    public Integer getEduSeq() {
        return eduSeq;
    }

    public Integer getApplicationSeq() {
        return applicationSeq;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public String getDepartmentName() {
        return departmentName;
    }
}
