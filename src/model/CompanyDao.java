package model;

import java.util.Map;

import domain.CompanyDto;
import domain.CompanyLoginDto;

public interface CompanyDao {

	int memberRegister(CompanyDto member, CompanyLoginDto memberLogin);

	CompanyLoginDto login(Map<String, String> paraMap);

}
