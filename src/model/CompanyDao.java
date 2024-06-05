package model;

import java.util.Map;

import domain.Company;
import domain.CompanyManager;
import domain.LoginObject;
import domain.PasswordHistoryManager;
import dto.CompanyDto;

public interface CompanyDao {

	int insert(Map<String, String> userInput);

	LoginObject selectById(String userId);

	LoginObject selectByNameAndEmail(String name, String email);


	PasswordHistoryManager selectRecentPassword(String id);

	int updatePassword(String id, String newPasswd);

	CompanyManager selectAllCompany();

	CompanyManager selectCompanyByName(String name);

    CompanyManager selectCompanyCapitalBiggerThan(int capital);

	CompanyManager selectCompanyByEmployeeCount(int min, int max);

	CompanyManager selectCompanyBySales(int min, int max);

    Company getCompany(int compSeq);

	int updateCompany(CompanyDto companyDto);

    int updateCompanyToDrop(int compSeq);
}
