package model;

import java.util.Map;

import domain.EmployeeDto;
import domain.EmployeeLoginDto;

public interface EmployeeDao {

	int register(EmployeeDto employeeDto, EmployeeLoginDto employeeloginDto);

	EmployeeLoginDto login(Map<String, String> paraMap);

}
