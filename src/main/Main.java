package main;

import auth.AuthService;
import auth.AuthenticationManager;
import controller.CompanyController;
import controller.EmployeeController;
import controller.MainController;
import model.*;
import reader.Reader;
import service.CompanyLoginService;
import service.CompanyService;
import service.EmployeeLoginService;
import service.EmployeeService;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        CompanyDao companyDao = new CompanyDaoImple();
        EmployeeDao employeeDao = new EmployeeDaoImple();
        RecruitDao recruitDao = new RecruitDaoImple();
        ApplicationDao applicationDao = new ApplicationDaoImple();
        Reader reader = new Reader(new Scanner(System.in));

        AuthService empAuth = new EmployeeLoginService(employeeDao);
        AuthService compAuth = new CompanyLoginService(companyDao);
        Map<String, AuthService> serviceMap = new HashMap<>();
        serviceMap.put("company", compAuth);
        serviceMap.put("employee", empAuth);

        AuthenticationManager authenticationManager = new AuthenticationManager(serviceMap, null);


        CompanyService companyService = new CompanyService(employeeDao, companyDao, recruitDao, applicationDao);
        EmployeeService employeeService = new EmployeeService(employeeDao, companyDao, recruitDao, applicationDao);




        CompanyController companyController = new CompanyController(companyService, employeeService, reader, authenticationManager);
        EmployeeController employeeController = new EmployeeController(companyService, employeeService ,reader, authenticationManager);
        MainController mainController = new MainController(companyService, employeeService, employeeController, companyController, reader, authenticationManager);




        mainController.mainMenu();
    }
}
