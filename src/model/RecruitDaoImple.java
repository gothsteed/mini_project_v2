package model;

import dbConnect.MyDBConnection;
import domain.*;
import dto.RecruitDto;

import javax.sound.midi.Sequencer;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class RecruitDaoImple implements RecruitDao {


    private Connection conn = MyDBConnection.getConn();
    private PreparedStatement pstmt;
    private ResultSet rs;

    private void close() {
        try {
            if (rs != null) {
                rs.close();
                rs = null;
            }
            if (pstmt != null) {
                pstmt.close();
                pstmt = null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private List<Recruit> createRecruitList(ResultSet rs) throws SQLException {
        List<Recruit> recruits = new ArrayList<>();

        while(rs.next()) {
            Recruit recruit = createRecruit(rs);
            recruits.add(recruit);
        }

        return recruits;
    }

    private Recruit createRecruit(ResultSet rs) throws SQLException {

        Recruit recruit = new Recruit(
                    rs.getInt("RECRUIT_SEQ"),
                    rs.getInt("comp_seq"),
                    rs.getInt("location_seq"),
                    rs.getInt("employ_type_seq"),
                    rs.getString("comp_name"),
                    rs.getString("recruit_title"),
                    rs.getString("license"),
                    rs.getString("screening"),
                    rs.getString("position"),
                    rs.getInt("recruit_num"),
                    rs.getString("edu_level"),
                    rs.getString("exp_level"),
                    rs.getInt("salary"),
                    rs.getDate("recruit_start_date"),
                    rs.getDate("recruit_end_date"),
                    rs.getDate("result_date"),
                    rs.getString("recruit_manager_name"),
                    rs.getString("recruit_manager_email"),
                    rs.getString("hire_period"),
                    rs.getInt("status")
            );

        return recruit;
    }

    private Recruit createRecruitWithCountList(ResultSet rs) throws SQLException {

        return new Recruit(
                rs.getInt("RECRUIT_SEQ"),
                rs.getInt("comp_seq"),
                rs.getInt("location_seq"),
                rs.getInt("employ_type_seq"),
                rs.getString("comp_name"),
                rs.getString("recruit_title"),
                rs.getString("license"),
                rs.getString("screening"),
                rs.getString("position"),
                rs.getInt("recruit_num"),
                rs.getString("edu_level"),
                rs.getString("exp_level"),
                rs.getInt("salary"),
                rs.getDate("recruit_start_date"),
                rs.getDate("recruit_end_date"),
                rs.getDate("result_date"),
                rs.getString("recruit_manager_name"),
                rs.getString("recruit_manager_email"),
                rs.getString("hire_period"),
                rs.getInt("status"),
                rs.getInt("application_count")
        );


    }

    private Application createApplication(ResultSet rs) throws SQLException {

        Application application = new Application(
                rs.getInt("APPLICATION_SEQ"),
                rs.getInt("EMP_SEQ"),
                rs.getInt("LOCATION_SEQ"),
                rs.getInt("EMPLOY_TYPE_SEQ"),
                rs.getInt("MILITARY_SEQ"),
                rs.getString("LICENSE"),
                rs.getInt("HOPE_SAL"),
                rs.getInt("STATUS"),
                rs.getString("MOTIVE")
        );

        return application ;
    }


    private RecruitEntry creatRecruitEntry(ResultSet rs, Application application, Recruit recruit) throws SQLException {

        RecruitEntry recruitEntry = new RecruitEntry(
                rs.getInt("entry_seq"),
                recruit,
                application,
                rs.getDate("entry_date"),
                rs.getInt("pass_status")
        );

        return recruitEntry ;
    }





    @Override
    public RecruitManager selectRecruitByTitle(String title) {
        List<Recruit> rcList = new ArrayList<>();

        try {

            String sql = " select RECRUIT_SEQ, comp_seq, location_seq, employ_type_seq, comp_name, recruit_title, license, screening, "
                    + " position, recruit_num, edu_level, exp_level, salary, recruit_start_date, recruit_end_date, "
                    + " result_date, recruit_manager_name, recruit_manager_email, hire_period, status "
                    + " from TBL_RECRUIT "
                    + " where recruit_title like ? ";

            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, "%" + title + "%");
            ResultSet rs = pstmt.executeQuery();

            rcList = createRecruitList(rs);
        } catch(SQLException e) {
            e.printStackTrace();
        } finally {
            close();
        }
        return new RecruitManager(rcList);
    }

    @Override
    public RecruitManager selectAllRecruit() {
        List<Recruit> recruits = new ArrayList<>();

        try {

            conn = MyDBConnection.getConn();

            String sql = " select * "
                    + " from tbl_recruit "
                    + " where status = 1 and to_char(recruit_end_date, 'yyyy-mm-dd') >= to_char(sysdate, 'yyyy-mm-dd') ";


            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();	// sql문 실행

            recruits = createRecruitList(rs);

        } catch (SQLException e) {    // ip 가 틀렸거나 비밀번호가 틀렸거나 해당 exception을 try-catch 해준다.
            e.printStackTrace();
        }

        return new RecruitManager(recruits);
    }

    @Override
    public Recruit selectBySeq(int recruitSeq) {
        Recruit recruit = null;

        try {

            conn = MyDBConnection.getConn();

            String sql = " select * "
                    + " from tbl_recruit "
                    + " where status = 1 and to_char(recruit_end_date, 'yyyy-mm-dd') >= to_char(sysdate, 'yyyy-mm-dd') " +
                    " and RECRUIT_SEQ = ? ";


            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, recruitSeq);
            rs = pstmt.executeQuery();	// sql문 실행

            if(rs.next()) {
                recruit = createRecruit(rs);
            }

        } catch (SQLException e) {    // ip 가 틀렸거나 비밀번호가 틀렸거나 해당 exception을 try-catch 해준다.
            e.printStackTrace();
        }

        return recruit;
    }

    @Override
    public RecruitEntryManager selectFromRecruitEntryByEmpSeq(int empSeq) {
        List<RecruitEntry> recruitEntryList = new ArrayList<>();

        try {

            conn = MyDBConnection.getConn();

            String sql = " SELECT DISTINCT \n" +
                    "    A.ENTRY_SEQ,\n" +
                    "    A.ENTRY_DATE,\n" +
                    "    A.PASS_STATUS,\n" +
                    "    B.APPLICATION_SEQ,\n" +
                    "    B.EMP_SEQ,\n" +
                    "    B.LOCATION_SEQ,\n" +
                    "    B.EMPLOY_TYPE_SEQ,\n" +
                    "    B.MILITARY_SEQ,\n" +
                    "    B.LICENSE,\n" +
                    "    B.HOPE_SAL,\n" +
                    "    B.STATUS,\n" +
                    "    B.MOTIVE,\n" +
                    "    C.RECRUIT_SEQ,\n" +
                    "    C.COMP_SEQ,\n" +
                    "    C.COMP_NAME,\n" +
                    "    C.RECRUIT_TITLE,\n" +
                    "    C.LICENSE ,\n" +
                    "    C.SCREENING,\n" +
                    "    C.POSITION,\n" +
                    "    C.RECRUIT_NUM,\n" +
                    "    C.EDU_LEVEL,\n" +
                    "    C.EXP_LEVEL,\n" +
                    "    C.SALARY,\n" +
                    "    C.RECRUIT_START_DATE,\n" +
                    "    C.RECRUIT_END_DATE,\n" +
                    "    C.RESULT_DATE,\n" +
                    "    C.RECRUIT_MANAGER_NAME,\n" +
                    "    C.RECRUIT_MANAGER_EMAIL,\n" +
                    "    C.HIRE_PERIOD,\n" +
                    "    C.STATUS\n" +
                    "FROM tbl_recruit_entry A \n" +
                    "JOIN tbl_application B ON A.application_seq = B.application_seq \n" +
                    "JOIN tbl_recruit C ON A.recruit_seq = C.recruit_seq\n" +
                    "WHERE B.emp_seq = ? ";


            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, empSeq);
            rs = pstmt.executeQuery();	// sql문 실행


            while(rs.next()) {

                Application application = createApplication(rs);
                Recruit recruit = createRecruit(rs);
                RecruitEntry recruitEntry = creatRecruitEntry(rs, application, recruit);
                recruitEntryList.add(recruitEntry);

            }



        } catch (SQLException e) {    // ip 가 틀렸거나 비밀번호가 틀렸거나 해당 exception을 try-catch 해준다.
            e.printStackTrace();
        }

        return new RecruitEntryManager(recruitEntryList);
    }

    @Override
    public int insert(RecruitDto recruitDto) {
        int result = 0;
        String sql = "";
        try {

            sql = " insert into TBL_RECRUIT(RECRUIT_SEQ, COMP_SEQ, LOCATION_SEQ, EMPLOY_TYPE_SEQ, COMP_NAME, RECRUIT_TITLE, LICENSE, SCREENING, "
                    + " POSITION, RECRUIT_NUM, EDU_LEVEL, EXP_LEVEL, SALARY, RECRUIT_START_DATE, RECRUIT_END_DATE, RESULT_DATE, "
                    + " RECRUIT_MANAGER_NAME, RECRUIT_MANAGER_EMAIL, HIRE_PERIOD) "
                    + " values(RECRUIT_ID.nextval, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)  ";

            pstmt = conn.prepareStatement(sql);

            pstmt.setInt(1, recruitDto.getComp_seq()); // company 객체에서 로그인된 id(=seq) 를 받아와서 그걸 인서트 해준다.
            pstmt.setInt(2, recruitDto.getLocation_seq());
            pstmt.setInt(3, recruitDto.getEmploy_type_seq());
            pstmt.setString(4, recruitDto.getComp_name());
            pstmt.setString(5, recruitDto.getRecruit_title());
            pstmt.setString(6, recruitDto.getLicense());
            pstmt.setString(7, recruitDto.getScreening());
            pstmt.setString(8, recruitDto.getPosition());
            pstmt.setInt(9, recruitDto.getRecruit_num());
            pstmt.setString(10, recruitDto.getEdu_level());
            pstmt.setString(11, recruitDto.getExp_level());
            pstmt.setInt(12, recruitDto.getSalary());


            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");
            String recruitStartDateString = simpleDateFormat.format(recruitDto.getRecruit_start_date());
            String recruitEndDateString = simpleDateFormat.format(recruitDto.getRecruit_end_date());
            String resultDateString = simpleDateFormat.format(recruitDto.getResult_date());

            pstmt.setString(13, recruitStartDateString);
            pstmt.setString(14, recruitEndDateString);
            pstmt.setString(15, resultDateString);
            pstmt.setString(16, recruitDto.getRecruit_manager_name());
            pstmt.setString(17, recruitDto.getRecruit_manager_email());
            pstmt.setString(18, recruitDto.getHire_period());



            result = pstmt.executeUpdate();


        } catch (SQLException e) {
            result = -1;
            e.printStackTrace();

        } finally {
            close();
        }


        return result;
    }

    @Override
    public RecruitManager selectRecruitByCompSeq(int seq) {
        List<Recruit> rcList = new ArrayList<>();

        try {

            String sql = " SELECT V.* , application_count " +
                    " FROM tbl_recruit V " +
                    " LEFT JOIN (" +
                    "    SELECT" +
                    "        A.recruit_seq," +
                    "        COUNT(*) AS application_count" +
                    "    FROM" +
                    "        tbl_recruit_entry A" +
                    "    JOIN" +
                    "        tbl_application B ON A.application_seq = B.application_seq" +
                    "    GROUP BY A.recruit_seq "  +
                    " ) V2 ON V.recruit_seq = V2.recruit_seq "  +
                    " WHERE comp_seq = ? ";

            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, seq);
            ResultSet rs = pstmt.executeQuery();
            while(rs.next()) {
                Recruit recruit = createRecruitWithCountList(rs);
                rcList.add(recruit);
            }
        } catch(SQLException e) {
            e.printStackTrace();
        } finally {
            close();
        }
        return new RecruitManager(rcList);
    }

}
