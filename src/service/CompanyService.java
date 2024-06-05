package service;

import domain.*;
import dto.ApplicationDto;
import dto.CompanyDto;
import dto.EmployeeDto;
import dto.RecruitDto;
import model.ApplicationDao;
import model.CompanyDao;
import model.EmployeeDao;
import model.RecruitDao;
import reader.Reader;
import service.resultData.PasswordChangeResult;

import java.util.List;
import java.util.Map;

public class CompanyService {

    private EmployeeDao employeeDao;
    private CompanyDao companyDao;
    private RecruitDao recruitDao;
    private ApplicationDao applicationDao;


    public CompanyService(EmployeeDao employeeDao, CompanyDao companyDao, RecruitDao recruitDao, ApplicationDao applicationDao) {
        this.employeeDao = employeeDao;
        this.companyDao = companyDao;
        this.recruitDao = recruitDao;
        this.applicationDao = applicationDao;
    }

    public int register(Map<String, String> userInput) {

        int n = companyDao.insert(userInput);
        return  n;
    }

    private boolean doesCompanyExists(String id) {
        LoginObject companyLogin = companyDao.selectById(id);

        if(companyLogin != null) {
            return true;
        }
        return  false;
    }

    private boolean isPasswordUsedRecently(String id, String password) {


        //HssMemberDao empNewPswdCheck 다시 확인

        PasswordHistoryManager passwordManager =  companyDao.selectRecentPassword(id);

        return  passwordManager.isUsedRecently(password);

    }



    public String findId(String name, String email) {
        LoginObject companyLogin = companyDao.selectByNameAndEmail(name, email);

        if(companyLogin != null) {

            return companyLogin.getId();
        }

        return null;

    }


    public PasswordChangeResult changeCompPassword(Reader reader, String id) {

        boolean isExist = doesCompanyExists(id);

        if(!isExist) {
            return PasswordChangeResult.MEMBER_NOT_FOUND;
        }

        boolean isPass;


        do {

            String new_passwd = reader.getValidatedInput("새로운 비밀번호를 입력하세요. : ",
                    string -> string.equals(reader.getInputWithPrompt("새로운 비밀번호를 한번 더 입력하세요. (위와 일치해야 함): ")),
                    "비밀번호가 일치하지 않습니다. 다시 입력하세요.");

            isPass = isPasswordUsedRecently(id, new_passwd);

            if (isPass) {

                int result = companyDao.updatePassword(id, new_passwd);

                if (result == 1) {
                    return PasswordChangeResult.SUCCESS;
                } else {

                    return PasswordChangeResult.FAILURE;
                }


            } else {
                return  PasswordChangeResult.PASSWORD_RECENTLY_USED;
            }
        }while(!isPass);

    }


    //todo: employeeServie로??
    public List<CompanyDto> getAllComp() {
        CompanyManager companyManager = companyDao.selectAllCompany();

        return companyManager.getAllCompanyDtos();

    }

    public List<CompanyDto> getCompByName(String name) {
        CompanyManager companyManager = companyDao.selectCompanyByName(name);

        return companyManager.getAllCompanyDtos();
    }

    public List<CompanyDto> getCompCapitalBiggerThen(int capital) {

        CompanyManager companyManager = companyDao.selectCompanyCapitalBiggerThan(capital);

        return companyManager.getAllCompanyDtos();
    }

    public List<CompanyDto> searchCompanyByEmployeeCount(int min, int max) {
        CompanyManager companyManager = companyDao.selectCompanyByEmployeeCount(min, max);

        return companyManager.getAllCompanyDtos();
    }

    public List<CompanyDto> searchCompanyBySales(int min, int max) {
        CompanyManager companyManager = companyDao.selectCompanyBySales(min, max);

        return companyManager.getAllCompanyDtos();
    }
////////////////////////////////////////////////////////////////////



    public CompanyDto getCompanyDto(int compSeq) {
        Company company = companyDao.getCompany(compSeq);

        return CompanyDto.of(company);
    }


    public int updateCompany(CompanyDto companyDto) {
        return companyDao.updateCompany(companyDto);
    }


    public List<EmployeeDto> getAllEmployeeDtoList() {

        EmployeeManager employeeManager = employeeDao.selectAllEmployee();


        return employeeManager.getEmployeeDtoList();
    }

    public List<EmployeeDto> searchEmployeeByAgeRage(int minNum, int maxNum) {

        EmployeeManager employeeManager = employeeDao.selectAllEmployee();

        return  employeeManager.searchEmployeeByAgeRange(minNum, maxNum);
    }

    public List<EmployeeDto> searchEmployeeByGender(String gender) {
        EmployeeManager employeeManager = employeeDao.selectAllEmployee();

        return  employeeManager.searchEmployeeByGender(gender);

    }

    public int insertRecruit(RecruitDto recruitDto) {
        return recruitDao.insert(recruitDto);

    }

    public List<RecruitDto> getCompanyRecruits(int comSeq) {
        RecruitManager recruitManager =  recruitDao.selectRecruitByCompSeq(comSeq);
        return recruitManager.getRecruitDtos();
    }

    public List<ApplicationDto> getAppliedApplications(int recruitSeq) {
        ApplicationManager applicationManager = applicationDao.selectApplicationFromRecruitEntryByRecruitSeq(recruitSeq);
        return  applicationManager.getApplicationDtoList();
    }


    public int dropMember(int compSeq) {
        return companyDao.updateCompanyToDrop(compSeq);
    }
}
