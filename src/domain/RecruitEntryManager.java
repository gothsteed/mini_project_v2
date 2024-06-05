package domain;

import dto.RecruitDto;
import dto.RecruitEntryDto;

import java.util.ArrayList;
import java.util.List;

public class RecruitEntryManager {
    private List<RecruitEntry> recruitEntryList;

    public RecruitEntryManager(List<RecruitEntry> recruitEntryList) {
        this.recruitEntryList = recruitEntryList;
    }


    public List<RecruitEntryDto> getRecruitEntryDtos() {
        List<RecruitEntryDto> dtos = new ArrayList<>();

        for(RecruitEntry recruitEntry : recruitEntryList) {

            dtos.add(RecruitEntryDto.of(recruitEntry));
        }

        return dtos;
    }

    public boolean isApplied(int recruitSeq) {

        for(RecruitEntry recruitEntry : recruitEntryList) {
            if(recruitEntry.getRecruit().getRecruitSeq() == recruitSeq) {
                return true;
            }
        }

        return false;
    }
}
