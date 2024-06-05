package domain;

import dto.CompanyDto;

import java.util.ArrayList;
import java.util.List;

public class CompanyManager {
    private List<Company> companies;

    public CompanyManager(List<Company> companies) {
        this.companies = companies;
    }


    public List<CompanyDto> getAllCompanyDtos() {
        List<CompanyDto> result = new ArrayList<>();

        for(int i=0; i<companies.size(); i++) {
            result.add(CompanyDto.of(companies.get(i)));
        }

        return result;

    }
}
