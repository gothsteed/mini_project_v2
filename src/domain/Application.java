package domain;

import model.ApplicationDao;

public class Application {
    private Integer applicationSeq;
    private Integer empSeq;
    private Integer locationSeq;
    private Integer employTypeSeq;
    private Integer militarySeq;
    private String license;
    private Integer hopeSal;
    private Integer status;
    private String motive;



    private EducationManager educationManager;
    private CareerManager careerManager;


    public Application(Integer applicationSeq, Integer empSeq, Integer locationSeq, Integer employTypeSeq, Integer militarySeq, String license, Integer hopeSal, Integer status, String motive) {
        this.applicationSeq = applicationSeq;
        this.empSeq = empSeq;
        this.locationSeq = locationSeq;
        this.employTypeSeq = employTypeSeq;
        this.militarySeq = militarySeq;
        this.license = license;
        this.hopeSal = hopeSal;
        this.status = status;
        this.motive = motive;
    }

    public Application(Integer applicationSeq, Integer empSeq, Integer locationSeq, Integer employTypeSeq, Integer militarySeq, String license, Integer hopeSal, Integer status, String motive, EducationManager educationManager, CareerManager careerManager) {
        this.applicationSeq = applicationSeq;
        this.empSeq = empSeq;
        this.locationSeq = locationSeq;
        this.employTypeSeq = employTypeSeq;
        this.militarySeq = militarySeq;
        this.license = license;
        this.hopeSal = hopeSal;
        this.status = status;
        this.motive = motive;
        this.educationManager = educationManager;
        this.careerManager = careerManager;
    }

    //    public CareerManager getCareer(ApplicationDao applicationDao) {
//        return  applicationDao.selectCareer();
//    }


    public Integer getApplicationSeq() {
        return applicationSeq;
    }

    public Integer getEmpSeq() {
        return empSeq;
    }

    public Integer getLocationSeq() {
        return locationSeq;
    }

    public Integer getEmployTypeSeq() {
        return employTypeSeq;
    }

    public Integer getMilitarySeq() {
        return militarySeq;
    }

    public String getLicense() {
        return license;
    }


    public Integer getHopeSal() {
        return hopeSal;
    }

    public Integer getStatus() {
        return status;
    }

    public String getMotive() {
        return motive;
    }

    public EducationManager getEducationManager() {
        return educationManager;
    }

    public CareerManager getCareerManager() {
        return careerManager;
    }
}
