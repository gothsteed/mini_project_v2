package service;

import domain.*;
import dto.*;
import model.ApplicationDao;
import model.CompanyDao;
import model.EmployeeDao;
import model.RecruitDao;
import reader.Reader;
import service.resultData.ApplyResult;
import service.resultData.PasswordChangeResult;

import java.util.List;
import java.util.Map;

public class EmployeeService {

    private EmployeeDao employeeDao;
    private CompanyDao companyDao;
    private RecruitDao recruitDao;
    private ApplicationDao applicationDao;

    public EmployeeService(EmployeeDao employeeDao, CompanyDao companyDao, RecruitDao recruitDao,
                           ApplicationDao applicationDao) {
        this.employeeDao = employeeDao;
        this.companyDao = companyDao;
        this.recruitDao = recruitDao;
        this.applicationDao = applicationDao;
    }

    public int register(Map<String, String> userInput) {

        int n = employeeDao.insert(userInput);
        return  n;
    }


    public EmployeeDto getEmployeeDto(int seq) {

        Employee employee =  employeeDao.selectEmployeeBySeq(seq);

        return EmployeeDto.of(employee);
    }


    public String findId(String name, String email) {

        LoginObject employeeLogin = employeeDao.selectByNameAndEmail(name, email);

        if(employeeLogin != null) {

            return employeeLogin.getId();
        }

        return null;
    }

    private boolean doesEmployeeExists(String id) {
        LoginObject employeeLogin = employeeDao.selectById(id);

        if(employeeLogin != null) {
            return true;
        }
        return  false;
    }

    private boolean isPasswordUsedRecently(String id, String password) {


        //HssMemberDao empNewPswdCheck 다시 확인

        PasswordHistoryManager passwordManger =  employeeDao.selectRecentPassword(id);

        return passwordManger.isUsedRecently(password);
    }


    public PasswordChangeResult changeEmpPassword(Reader reader, String emp_id) {

        boolean isExist = doesEmployeeExists(emp_id);

        if(!isExist) {
            return PasswordChangeResult.MEMBER_NOT_FOUND;
        }

        boolean isPass = true;


        do {

            String new_passwd = reader.getValidatedInput("새로운 비밀번호를 입력하세요. : ",
                    string -> string.equals(reader.getInputWithPrompt("새로운 비밀번호를 한번 더 입력하세요. (위와 일치해야 함): ")),
                    "비밀번호가 일치하지 않습니다. 다시 입력하세요.");

            isPass = isPasswordUsedRecently(emp_id, new_passwd);

            if (isPass) {

                int result = employeeDao.updatePassword(emp_id, new_passwd);

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

    ///////////////////////////////employee Login Service////////////////////////////

    public int updateInfo(EmployeeDto employeeDto) {

        int n = employeeDao.updateEmployee(employeeDto);

        return n;
    }


    public List<RecruitDto> titleSearch(String title) {
        RecruitManager recruitManager = recruitDao.selectRecruitByTitle(title);

        return  recruitManager.getRecruitDtos();
    }

    public List<RecruitDto> getAllRecruit() {
        RecruitManager recruitManager = recruitDao.selectAllRecruit();

        return  recruitManager.getRecruitDtos();
    }


    public List<RecruitEntryDto> getAppliedRecruit(int empSeq) {

        RecruitEntryManager recruitEntryManager = recruitDao.selectFromRecruitEntryByEmpSeq(empSeq);

        return recruitEntryManager.getRecruitEntryDtos();
    }



    public EmployeeStatsDto getRecruitStats(int seq) {
        EmployeeManager employeeManager = employeeDao.selectEmployeeFromRecruitEntryBySeq(seq);
        EmployeeStats employeeStats = employeeManager.getStats();

        return EmployeeStatsDto.of(employeeStats);
    }

    public ApplyResult apply(int empSeq, int applicationSeq, int recruitSeq) {

        Application application = applicationDao.selectByEmpSeqApplicationSeq(empSeq, applicationSeq);
        Recruit recruit = recruitDao.selectBySeq(recruitSeq);

        if(application ==null) {
            return ApplyResult.APPLICATION_NOT_EXIST;
        }

        if(recruit == null) {
            return  ApplyResult.RECRUIT_NOT_EXIST;
        }

//        RecruitManager recruitManager = recruitDao.selectByEmpSeq(empSeq);

        RecruitEntryManager recruitEntryManager = recruitDao.selectFromRecruitEntryByEmpSeq(empSeq);

        if(recruitEntryManager.isApplied(recruitSeq)) {
            return ApplyResult.ALREADY_APPLIED;
        }

        if(applicationDao.insertIntoRecruitEntry(applicationSeq, recruitSeq) == 1) {
            return ApplyResult.SUCCESS;

        }
        return ApplyResult.FAILED;
    }


    public int dropMember(int empSeq) {
        return employeeDao.updateEmployeeStatusToDrop(empSeq);
    }

    public boolean insertApplication(ApplicationDto applicationDto) {
        //todo: dto울 도메인 모델로 변경 후 validation 진행

        int result = applicationDao.insertApplication(applicationDto);

        return result == 1;
    }

    public ApplicationDto getApplicationDto(int applicationSeq) {
        Application application = applicationDao.selectApplication(applicationSeq);

        if(application != null) {
            return ApplicationDto.of(application);
        }

        return  null;
    }

    public List<ApplicationDto> getApplicationDtoListOfEmp(int empSeq) {
        ApplicationManager applicationManager = applicationDao.selectApplicationByEmpSeq(empSeq);

        return applicationManager.getApplicationDtoList();
    }

    public int updateApplication(ApplicationDto applicationDto) {

        return applicationDao.updateApplication(applicationDto);

    }


    public int deleteApplication(int empSeq, int applicationSeq) {

        return applicationDao.deleteApplication(applicationSeq, empSeq);
    }

    public List<ApplicationDto> getApplicationDtoList(int empSeq) {

        ApplicationManager applicationManager = applicationDao.selectApplicationByEmpSeq(empSeq);

        return applicationManager.getApplicationDtoList();
    }
}
