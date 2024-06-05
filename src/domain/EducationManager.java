package domain;

import dto.CareerDto;
import dto.EducationDto;
import dto.EmployeeDto;

import java.util.ArrayList;
import java.util.List;

public class EducationManager {
    private List<Education> educationList;

    public EducationManager(List<Education> educationList) {
        this.educationList = educationList;
    }

    public List<EducationDto> getEducationDtoList() {
        List<EducationDto> educationDtoList = new ArrayList<>();

        for(Education education : educationList) {
            educationDtoList.add(EducationDto.of(education));
        }

        return educationDtoList;
    }

}
