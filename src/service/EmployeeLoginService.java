package service;

import auth.AuthService;
import domain.LoginObject;
import model.EmployeeDao;

import java.util.Map;

public class EmployeeLoginService implements AuthService {

    private EmployeeDao employeeDao;

    public EmployeeLoginService(EmployeeDao employeeDao) {
        this.employeeDao = employeeDao;
    }

    @Override
    public LoginObject login(String id, String password) {
        LoginObject loginObject = employeeDao.selectById(id);

        if(loginObject != null && loginObject.checkPassword(password)) {

            return loginObject;
        }

        return  null;
    }
}
