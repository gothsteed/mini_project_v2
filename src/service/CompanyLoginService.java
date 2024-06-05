package service;

import auth.AuthService;
import domain.LoginObject;
import model.CompanyDao;
import model.EmployeeDao;

import java.util.Map;

public class CompanyLoginService implements AuthService {

    private CompanyDao companyDao;

    public CompanyLoginService(CompanyDao companyDao) {
        this.companyDao = companyDao;
    }

    @Override
    public LoginObject login(String id, String password) {
        LoginObject loginObject = companyDao.selectById(id);

        if(loginObject != null && loginObject.checkPassword(password)) {

            return loginObject;
        }

        return  null;
    }
}
