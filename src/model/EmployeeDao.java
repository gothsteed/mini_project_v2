package model;

import java.util.Map;

import domain.Employee;
import domain.EmployeeManager;
import domain.LoginObject;
import domain.PasswordHistoryManager;
import dto.EmployeeDto;

public interface EmployeeDao {

	int insert(Map<String, String> userInput);

	Employee selectEmployeeBySeq(int seq);


	LoginObject selectById(String id);


	LoginObject selectByNameAndEmail(String name, String email);

	PasswordHistoryManager selectRecentPassword(String id);

	int updatePassword(String id ,String newPassword);

	int updateEmployee(EmployeeDto employeeDto);

    EmployeeManager selectEmployeeFromRecruitEntryBySeq(int seq);

    int updateEmployeeStatusToDrop(int empSeq);

    EmployeeManager selectAllEmployee();
}
