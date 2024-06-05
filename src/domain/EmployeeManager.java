package domain;

import dto.EmployeeDto;

import java.util.ArrayList;
import java.util.List;

public class EmployeeManager {

    private List<Employee> employeeList;

    public EmployeeManager(List<Employee> employeeList) {
        this.employeeList = employeeList;
    }

    public EmployeeStats getStats() {
        return new EmployeeStats(employeeList);
    }

    public List<EmployeeDto> getEmployeeDtoList() {
        List<EmployeeDto> employeeDtoList = new ArrayList<>();

        for(Employee e : employeeList) {
            employeeDtoList.add(EmployeeDto.of(e));
        }

        return employeeDtoList;
    }


    public List<EmployeeDto> searchEmployeeByAgeRange(int min, int max) {
        List<EmployeeDto> employeeDtoList = new ArrayList<>();

        for(Employee e : employeeList) {
            if(e.getAgeRange() >= min && e.getAgeRange() <= max) {
                employeeDtoList.add(EmployeeDto.of(e));
            }
        }

        return employeeDtoList;
    }


    public List<EmployeeDto> searchEmployeeByGender(String gender) {
        List<EmployeeDto> employeeDtoList = new ArrayList<>();

        String genderData = "";

        if(gender.equals("남")) {
            genderData = "male";
        }
        else {
            genderData ="female";
        }


        for(Employee e : employeeList) {
            if(genderData.equalsIgnoreCase(e.getGender())) {
                employeeDtoList.add(EmployeeDto.of(e));
            }
        }

        return employeeDtoList;

    }
}
