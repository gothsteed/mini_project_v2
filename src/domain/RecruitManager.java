package domain;

import dto.RecruitDto;

import java.util.ArrayList;
import java.util.List;

public class RecruitManager {
    private List<Recruit> recruits;

    public RecruitManager(List<Recruit> recruits) {
        this.recruits = recruits;
    }


    public List<RecruitDto> getRecruitDtos() {
        List<RecruitDto> dtos = new ArrayList<>();

        for(Recruit recruit : recruits) {

            dtos.add(RecruitDto.of(recruit));
        }

        return dtos;
    }

    public boolean doseExist(int recruitSeq) {
        for(Recruit r : recruits) {
            if(r.getRecruitSeq() == recruitSeq) {
                return true;
            }
        }

        return false;
    }
}
