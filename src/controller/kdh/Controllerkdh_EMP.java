package controller.kdh;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import dbConnect.MyDBConnection;
import domain.RecruitDto;

import model.kdh.EmployeeDaoImple_kdh;
import model.kdh.EmployeeDao_kdh;
import model.leeJungYeon.MyDao;
import model.leeJungYeon.MyDaoImple;


public class Controllerkdh_EMP {

    private EmployeeDao_kdh employeeDao_kdh = new EmployeeDaoImple_kdh();
    private MyDao myDao = new MyDaoImple();
    private RecruitDto rdto = new RecruitDto();

	
	
    public void viewCompanyRecruits(Scanner sc) {

    	
        System.out.println("-------- 전체 회사 공고 현황 --------");

        List<RecruitDto> recruitList = employeeDao_kdh.viewCompanyallRecruits(); 

        System.out.println("공고번호 \t\t 제목 \t\t 시작일자 \t\t 마감날짜 \t\t 마감여부");
        
        StringBuilder sb = new StringBuilder();
        for (RecruitDto r : recruitList) { 
            sb.append(r.getRecruit_seq() + "\t\t");
            //sb.append(r.getRecruit_title() + "[" + r.getCount() + "]" + "\t\t");
            sb.append(r.getRecruit_title() + "\t\t");
            sb.append(r.getRecruit_start_date() + "\t\t");
            sb.append(r.getRecruit_end_date() + "\t\t");
            sb.append(r.getStatus() + "\n");
        }

        System.out.println(sb);

        int no = -1;
        while (true) {
            try {
                System.out.print("상세보기 번호: ");
                no = Integer.parseInt(sc.nextLine());

                break;
            } catch (NumberFormatException e) {
                System.out.println("!!숫자만 입력하시오!!");
            }
        }
        
        RecruitDto rcdto = employeeDao_kdh.viewRecruitDetail(no);
        
        if(rcdto == null) {
        	System.out.println("해당 공고가 없습니다.");
        	return;
        }

  
        sb = new StringBuilder();
        
        sb.append("공고번호 : " + rcdto.getRecruit_seq()+"\n");
        sb.append("공고제목 : " + rcdto.getRecruit_title()+"\n");
        sb.append("채용인원 : " + rcdto.getRecruit_num()+"\n");
        sb.append("회사이름 : " + rcdto.getComp_name()+"\n");
        sb.append("우대자격증 : " + rcdto.getLicense()+"\n");
        sb.append("전형 : " + rcdto.getScreening()+"\n");
        sb.append("직무 : " + rcdto.getPosition()+"\n");
        sb.append("요구학력 : " + rcdto.getEdu_level()+"\n");
        sb.append("요구경력 : " + rcdto.getExp_level()+"\n");
        sb.append("급여 : " + rcdto.getSalary()+"\n");
        sb.append("모집시작일자 : " + rcdto.getRecruit_start_date()+"\n");
        sb.append("모집마감일자 : " + rcdto.getRecruit_end_date()+"\n");
        sb.append("합격자 발표일 : " + rcdto.getResult_date()+"\n");
        sb.append("모집공고담당자이름 : " + rcdto.getRecruit_manager_name()+"\n");
        sb.append("모집공고담당자이메일 : " + rcdto.getRecruit_manager_email()+"\n");
        sb.append("고용기간 : " + rcdto.getHire_period()+"\n");
        sb.append("채용공고 유지상태 : " + rcdto.getStatus()+"\n"+"-".repeat(60));
        
        System.out.println(sb);
        
        
        
        // 통계
        myDao.getRecruitStats(no);
        List<Map<String, String>> getRecruitStats = myDao.getRecruitStats(no);
        
        String static_menu = (getRecruitStats.isEmpty()?"":"성별 \t\t 연령대 \t\t GROUPCOUNT \t\t 퍼센티지 \n --------------------------------------------------------------------");
        System.out.println(static_menu);
        
        sb = new StringBuilder();
        for (Map<String, String> rst : getRecruitStats) { 
        	
            sb.append(( rst.get("gender")==null? "전체" :rst.get("gender") ) + "\t\t");
            //sb.append(r.getRecruit_title() + "[" + r.getCount() + "]" + "\t\t");
            sb.append((rst.get("age_range").equals("0")? "전체":rst.get("age_range")) + "\t\t");
            sb.append(rst.get("group_count") + "\t\t");
            sb.append(rst.get("percentage") + "\n");
        }

        System.out.println(sb);
        
        
    }
}

