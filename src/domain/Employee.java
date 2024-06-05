package domain;

import java.awt.event.MouseAdapter;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;

public class Employee {

    private int empSeq;
    private String empId;
    private String empEmail;
    private String empName;
    private String jubun;
    private String empAddr;
    private String empTel;
    private Integer status;

    public Employee(int empSeq, String empId, String empEmail, String empName,
                    String jubun, String empAddr, String empTel, Integer status) {
        this.empSeq = empSeq;
        this.empId = empId;
        this.empEmail = empEmail;
        this.empName = empName;
        this.jubun = jubun;
        this.empAddr = empAddr;
        this.empTel = empTel;
        this.status = status;
    }

    public int getEmpSeq() {
        return empSeq;
    }

    public String getEmpId() {
        return empId;
    }

    public String getEmpEmail() {
        return empEmail;
    }

    public String getEmpName() {
        return empName;
    }

    public String getJubun() {
        return jubun;
    }

    public String getEmpAddr() {
        return empAddr;
    }

    public String getEmpTel() {
        return empTel;
    }

    public String getGender() {
        if(jubun.charAt(6) == '1' || jubun.charAt(6) == '3'){
            return "MALE";
        }

        return "FEMALE";
    }

    public int getAge() {

        String birthDateString = jubun.substring(0, 6);

        if(jubun.charAt(6) == '1' || jubun.charAt(6) == '2') {
            birthDateString  = "19" + birthDateString;
        }
        else {
            birthDateString  = "20" + birthDateString;
        }

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        LocalDate birthDate = LocalDate.parse(birthDateString, dateTimeFormatter);

        LocalDate currentDate = LocalDate.now();

        Period agePeriod = Period.between(birthDate, currentDate);

        return  agePeriod.getYears();
    }


    public int getAgeRange() {
        int age = getAge();
        return (age / 10) * 10;
    }
}
