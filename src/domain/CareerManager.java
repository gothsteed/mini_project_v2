package domain;

import dto.CareerDto;

import java.util.ArrayList;
import java.util.List;

public class CareerManager {
    private List<Career> careerList;

    public CareerManager(List<Career> careerList) {
        this.careerList = careerList;
    }


    public List<CareerDto> getCareerDtoList() {
        List<CareerDto> careerDtoList = new ArrayList<>();

        for(Career career : careerList) {
            careerDtoList.add(CareerDto.of(career));
        }

        return careerDtoList;
    }


}
