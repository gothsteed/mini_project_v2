package domain;

import dto.ApplicationDto;

import java.util.ArrayList;
import java.util.List;

public class ApplicationManager {

    List<Application> applicationList;

    public ApplicationManager(List<Application> applicationList) {
        this.applicationList = applicationList;
    }

    public List<ApplicationDto> getApplicationDtoList() {
        List<ApplicationDto> applicationDtoList = new ArrayList<>();

        for (Application application : applicationList) {
            applicationDtoList.add(ApplicationDto.of(application));
        }

        return applicationDtoList;
    }
}
