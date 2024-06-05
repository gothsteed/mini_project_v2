package model;

import dbConnect.MyDBConnection;
import domain.*;
import dto.ApplicationDto;
import dto.CareerDto;
import dto.EducationDto;
import dto.RecruitDto;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class ApplicationDaoImple implements ApplicationDao {


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
            // if (conn != null) {conn.close(); conn = null;}
            // 지금은 싱글톤 패턴을 사용했기 때문에 프로그램 종료할 때만 사용한다. conn.close() 사용하면 안된다!(conn은 DB 연결)//
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Override
    public int insertIntoRecruitEntry(int applicationSeq, int recruitSeq) {
        int result = 0;

        try {

//            RecruitDto recruitDto = getRecruit(recruitEntryDto.getRecruit_seq());
//
//            if(recruitDto == null) {
//                return result;
//            }
//
//            ApplicationDto applicationDto = viewApplication(recruitEntryDto.getApplication_seq());
//            if(applicationDto == null || applicationDto.getEmp_seq() != emp_seq) {
//
//                return result;
//            }
//
//            if(isApplied(recruitDto.getRecruit_seq(), applicationDto.getApplication_seq())) {
//
//                return -1;
//            }



            String applySql = " insert into TBL_RECRUIT_ENTRY( "
                    + "    ENTRY_SEQ, "
                    + "    RECRUIT_SEQ, "
                    + "    APPLICATION_SEQ "
                    + " ) "
                    + " values ( "
                    + "    SEQ_RECRUIT_ENTRY.nextval, "
                    + "    ?, "
                    + "    ? "
                    + " ) ";

            pstmt = conn.prepareStatement(applySql);

            pstmt.setInt(1, recruitSeq);
            pstmt.setInt(2, applicationSeq);

            result = pstmt.executeUpdate();


        }catch (SQLException e) {
            e.printStackTrace();
        }

        catch (Exception e) {
            e.printStackTrace();
        }finally {
            close();
        }




        return result;
    }

    @Override
    public Application selectByEmpSeqApplicationSeq(int empSeq, int applicationSeq) {
        Application application = null;

        try {

            conn = MyDBConnection.getConn();

            String sql = " select * "
                    + " from TBL_APPLICATION "
                    + " where APPLICATION_SEQ = ? and EMP_SEQ = ?";


            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, applicationSeq);
            pstmt.setInt(2, empSeq);
            rs = pstmt.executeQuery();	// sql문 실행

            if(rs.next()) {
                application = new Application(
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
            }

        } catch (SQLException e) {    // ip 가 틀렸거나 비밀번호가 틀렸거나 해당 exception을 try-catch 해준다.
            e.printStackTrace();
        }

        return application;
    }





    @Override
    public int insertApplication(ApplicationDto applicationDto) {
        int result = 0;

        try {

            conn.setAutoCommit(false);

            String sql = " insert into "
                    + "         TBL_APPLICATION(application_seq, emp_seq, location_seq, employ_type_seq, military_seq, license, hope_sal, motive) "
                    + " values(APP_SEQ.nextval, ?, ?, ?, ?, ?, ?, ?)  ";

            pstmt = conn.prepareStatement(sql, new String[] {"application_seq"});
            pstmt.setInt(1, applicationDto.getEmpSeq());
            pstmt.setInt(2, applicationDto.getLocationSeq());
            pstmt.setInt(3, applicationDto.getEmployTypeSeq());
            pstmt.setInt(4, applicationDto.getMilitarySeq());
            pstmt.setString(5, applicationDto.getLicense());
            pstmt.setInt(6, applicationDto.getHopeSal());
            pstmt.setString(7, applicationDto.getMotive());

            int n = pstmt.executeUpdate();

            if(n == 1) {
                rs = pstmt.getGeneratedKeys();

                int applicationSeq = -1;
                if(rs.next()) {
                    applicationSeq = rs.getInt(1);
                }


                for (EducationDto education : applicationDto.getEducationDtoList()) {
                    sql = " insert into tbl_education(edu_seq, application_seq, school_name, department_name) "
                            + " values(SEQ_EDUCATION.nextval, ?, ?, ?) ";
                    pstmt = conn.prepareStatement(sql);
                    pstmt.setInt(1, applicationSeq);
                    pstmt.setString(2, education.getSchoolName());
                    pstmt.setString(3, education.getDepartmentName());
                    n = pstmt.executeUpdate();
                    if(n != 1) {
                        throw new SQLException("education insert Fail");
                    }
                }

                for (CareerDto career : applicationDto.getCareerDtoList()) {
                    sql = " insert into tbl_career(career_seq, application_seq, career_start_date, career_end_date, position) "
                            + " values(SEQ_CAREER.nextval, ?, ?, ?, ?) ";
                    pstmt = conn.prepareStatement(sql);

                    pstmt.setInt(1, applicationSeq);
                    pstmt.setString(2, career.getCareerStartDate().toString());
                    pstmt.setString(3, career.getCareerEndDate().toString());
                    pstmt.setString(4, career.getPosition());

                    n = pstmt.executeUpdate();

                    if(n!=1) {
                        throw  new SQLException("career insert fail");
                    }
                }


                if(n == 1) {

                    conn.commit();
                    result = 1;
                }else {
                    conn.rollback();
                    result = 0;

                }

            }

        }catch (SQLException e) {
            try {
                conn.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }

            e.printStackTrace();


        }
        finally {

            try {
                conn.setAutoCommit(true);
            } catch (SQLException e) {

                e.printStackTrace();
            }
            close();
        }


        return result;
    }

    @Override
    public Application selectApplication(int applicationSeq) {
        Application application = null;

        try {

//            sql = "select career_seq ,  application_seq, position,"
//                    + "      to_char(career_start_date, 'yyyy/mm/dd') as career_start_date, "
//                    + "      to_char(career_end_date, 'yyyy/mm/dd') as career_end_date "
//                    + " from tbl_career "
//                    +  " where application_seq = ? ";

            String sql = "select career_seq ,  application_seq, position,"
                    + "      career_start_date, "
                    + "      career_end_date "
                    + " from tbl_career "
                    +  " where application_seq = ? ";
            pstmt = conn.prepareStatement(sql);

            pstmt.setInt(1, applicationSeq);

            rs = pstmt.executeQuery();

            List<Career> careerList = new ArrayList<>();

            while(rs.next()) {
                Career career = new Career(rs.getInt("career_seq"),
                        applicationSeq,
                        rs.getString("position"),
                        rs.getDate("career_start_date"),
                        rs.getDate("career_start_date"));


                careerList.add(career);

            }


            List<Education> educationList = new ArrayList<>();
            sql = "select edu_seq ,  application_seq, school_name, department_name "
                    + " from tbl_education "
                    +  " where application_seq = ? ";

            pstmt = conn.prepareStatement(sql);

            pstmt.setInt(1, applicationSeq);

            rs = pstmt.executeQuery();

            while(rs.next()) {
                Education education = new Education(
                        rs.getInt("edu_seq"),
                        applicationSeq,
                        rs.getString("school_name"),
                        rs.getString("department_name")
                );
                educationList.add(education);
            }

            sql = "select * "
                    + " from tbl_application"
                    +  " where application_seq = ? ";

            pstmt = conn.prepareStatement(sql);

            pstmt.setInt(1, applicationSeq);

            rs = pstmt.executeQuery();



            if(!rs.next()) {
                return application;
            }

            //System.out.println("app dao selecet app: " + rs.getString("motive"));

            application = new Application(
                    rs.getInt("application_seq"),
                    rs.getInt("EMP_SEQ"),
                    rs.getInt("location_seq"),
                    rs.getInt("employ_type_seq"),
                    rs.getInt("military_seq"),
                    rs.getString("license"),
                    rs.getInt("hope_sal"),
                    rs.getInt("status"),
                    rs.getString("motive")
                    ,new EducationManager(educationList),
                    new CareerManager(careerList)
            );



        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            close();
        }



        return application;
    }

    @Override
    public int updateApplication(ApplicationDto applicationDto) {
        int result = 0;

        try {
            conn.setAutoCommit(false);

            String sql = "UPDATE tbl_application SET "
                    + "emp_seq = ?, "
                    + "location_seq = ?, "
                    + "employ_type_seq = ?, "
                    + "military_seq = ?, "
                    + "license = ?, "
                    + "hope_sal = ?, "
                    + "motive = ? "
                    + "WHERE application_seq = ? and STATUS = 1";

            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, applicationDto.getEmpSeq());
            pstmt.setInt(2, applicationDto.getLocationSeq());
            pstmt.setInt(3, applicationDto.getEmployTypeSeq());
            pstmt.setInt(4, applicationDto.getMilitarySeq());
            pstmt.setString(5, applicationDto.getLicense());
            pstmt.setInt(6, applicationDto.getHopeSal());
            pstmt.setString(7, applicationDto.getMotive());
            pstmt.setInt(8, applicationDto.getApplicationSeq());

            int n = pstmt.executeUpdate();

            if(n != 1) {
                return result;
            }

            for(CareerDto careerDto : applicationDto.getCareerDtoList()) {
//                sql = " update  tbl_career "
//                        + " set "
//                        + "    position= ?,"
//                        + "    career_start_date = to_date(?, 'yyyy/mm/dd'), "
//                        + "    career_end_date = to_date(?, 'yyyy/mm/dd')  "
//                        + " where career_seq = ?  ";

                sql = " update  tbl_career "
                        + " set "
                        + "    position= ?, "
                        + "    career_start_date = ?, "
                        + "    career_end_date = ?"
                        + " where career_seq = ?  ";

                pstmt = conn.prepareStatement(sql);


                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");
                String startDateString = simpleDateFormat.format(careerDto.getCareerStartDate());
                String endDateString = simpleDateFormat.format(careerDto.getCareerEndDate());

                pstmt.setString(1, careerDto.getPosition());
                pstmt.setString(2, startDateString);
                pstmt.setString(3, endDateString);
                pstmt.setInt(4, careerDto.getCareerSeq());


                n = pstmt.executeUpdate();

                if(n != 1){
                    conn.rollback();
                    return result;

                }

            }

            for(EducationDto educationDto : applicationDto.getEducationDtoList()) {

                sql = " update  tbl_education "
                        + " set "
                        + "    school_name = ?, "
                        + "    department_name = ?  "
                        + " where edu_seq= ?  ";

                pstmt = conn.prepareStatement(sql);
                pstmt.setString(1, educationDto.getSchoolName());
                pstmt.setString(2, educationDto.getDepartmentName());
                pstmt.setInt(3, educationDto.getEduSeq());


                n = pstmt.executeUpdate();
                if(n != 1){
                    conn.rollback();
                    return result;
                }
            }


            result = 1;
            conn.commit();

        } catch (SQLException e) {
            try {
                conn.rollback();
            } catch (SQLException ex) {
                e.printStackTrace();
                throw new RuntimeException(ex);
            }
            e.printStackTrace();
        }finally {
            try {
                conn.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
            close();
        }

        return result;
    }

    @Override
    public int deleteApplication(int applicationSeq, int emqSeq) {
        int result = 0;


        try {

            String sql = " update tbl_application "
                    + " set "
                    + "     status = 0 "
                    + " where application_seq = ? and EMP_SEQ = ?";

            pstmt = conn.prepareStatement(sql);

            pstmt.setInt(1, applicationSeq);
            pstmt.setInt(2, emqSeq);
            result = pstmt.executeUpdate();


        }catch (SQLException e) {
            e.printStackTrace();
            result = -1;
        }
        finally {
            close();
        }



        return result;
    }

    @Override
    public ApplicationManager selectApplicationByEmpSeq(int empSeq) {
        List<Application> applicationList = new ArrayList<>();

        try {

//            sql = "select career_seq ,  application_seq, position,"
//                    + "      to_char(career_start_date, 'yyyy/mm/dd') as career_start_date, "
//                    + "      to_char(career_end_date, 'yyyy/mm/dd') as career_end_date "
//                    + " from tbl_career "
//                    +  " where application_seq = ? ";



            String sql = "select * "
                    + " from tbl_application"
                    +  " where EMP_SEQ = ? and status = 1";

            pstmt = conn.prepareStatement(sql);

            pstmt.setInt(1, empSeq);

            rs = pstmt.executeQuery();



            while(rs.next()) {

                Integer applicationSeq = rs.getInt("application_seq");
                Integer locationSeq = rs.getInt("location_seq");
                Integer employeeTypeSeq = rs.getInt("employ_type_seq");
                Integer militarySeq = rs.getInt("military_seq");
                String licence =   rs.getString("license");
                Integer hopeSal =  rs.getInt("hope_sal");
                Integer status = 1;
                String motive = rs.getString("motive");


                sql = "select career_seq ,  application_seq, position,"
                        + "      career_start_date, "
                        + "      career_end_date "
                        + " from tbl_career "
                        +  " where application_seq = ? ";
                pstmt = conn.prepareStatement(sql);

                pstmt.setInt(1, applicationSeq);

                ResultSet rsCareer = pstmt.executeQuery();

                List<Career> careerList = new ArrayList<>();

                while(rsCareer.next()) {
                    Career career = new Career(rsCareer.getInt("career_seq"),
                            applicationSeq,
                            rsCareer.getString("position"),
                            rsCareer.getDate("career_start_date"),
                            rsCareer.getDate("career_start_date"));

                    careerList.add(career);
                }




                sql = "select edu_seq ,  application_seq, school_name, department_name "
                        + " from tbl_education "
                        +  " where application_seq = ? ";

                pstmt = conn.prepareStatement(sql);
                pstmt.setInt(1, applicationSeq);
                ResultSet rsEducation = pstmt.executeQuery();

                List<Education> educationList = new ArrayList<>();
                while(rsEducation.next()) {
                    Education education = new Education(
                            rsEducation.getInt("edu_seq"),
                            applicationSeq,
                            rsEducation.getString("school_name"),
                            rsEducation.getString("department_name")
                    );
                    educationList.add(education);
                }

                applicationList.add(new Application(
                        applicationSeq,
                        empSeq,
                        locationSeq,
                        employeeTypeSeq,
                        militarySeq,
                        licence,
                        hopeSal,
                        status,
                        motive,
                        new EducationManager(educationList),
                        new CareerManager(careerList)
                ));
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            close();
        }



        return new ApplicationManager(applicationList);
    }

    @Override
    public ApplicationManager selectApplicationFromRecruitEntryByRecruitSeq(int recruitSeq) {
        List<Application> applicationList = new ArrayList<>();

        try {

//            sql = "select career_seq ,  application_seq, position,"
//                    + "      to_char(career_start_date, 'yyyy/mm/dd') as career_start_date, "
//                    + "      to_char(career_end_date, 'yyyy/mm/dd') as career_end_date "
//                    + " from tbl_career "
//                    +  " where application_seq = ? ";



            String sql = " SELECT B.* " +
                    " FROM tbl_recruit_entry A " +
                    " JOIN tbl_application B ON A.application_seq = B.application_seq " +
                    " WHERE A.recruit_seq = ? ";

            pstmt = conn.prepareStatement(sql);

            pstmt.setInt(1, recruitSeq);

            rs = pstmt.executeQuery();



            while(rs.next()) {

                Integer applicationSeq = rs.getInt("application_seq");
                Integer empSeq = rs.getInt("emp_seq");
                Integer locationSeq = rs.getInt("location_seq");
                Integer employeeTypeSeq = rs.getInt("employ_type_seq");
                Integer militarySeq = rs.getInt("military_seq");
                String licence =   rs.getString("license");
                Integer hopeSal =  rs.getInt("hope_sal");
                Integer status = 1;
                String motive = rs.getString("motive");


                sql = "select career_seq ,  application_seq, position,"
                        + "      career_start_date, "
                        + "      career_end_date "
                        + " from tbl_career "
                        +  " where application_seq = ? ";
                pstmt = conn.prepareStatement(sql);

                pstmt.setInt(1, applicationSeq);

                ResultSet rsCareer = pstmt.executeQuery();

                List<Career> careerList = new ArrayList<>();

                while(rsCareer.next()) {
                    Career career = new Career(rsCareer.getInt("career_seq"),
                            applicationSeq,
                            rsCareer.getString("position"),
                            rsCareer.getDate("career_start_date"),
                            rsCareer.getDate("career_start_date"));

                    careerList.add(career);
                }




                sql = "select edu_seq ,  application_seq, school_name, department_name "
                        + " from tbl_education "
                        +  " where application_seq = ? ";

                pstmt = conn.prepareStatement(sql);
                pstmt.setInt(1, applicationSeq);
                ResultSet rsEducation = pstmt.executeQuery();

                List<Education> educationList = new ArrayList<>();
                while(rsEducation.next()) {
                    Education education = new Education(
                            rsEducation.getInt("edu_seq"),
                            applicationSeq,
                            rsEducation.getString("school_name"),
                            rsEducation.getString("department_name")
                    );
                    educationList.add(education);
                }

                applicationList.add(new Application(
                        applicationSeq,
                        empSeq,
                        locationSeq,
                        employeeTypeSeq,
                        militarySeq,
                        licence,
                        hopeSal,
                        status,
                        motive,
                        new EducationManager(educationList),
                        new CareerManager(careerList)
                ));
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            close();
        }



        return new ApplicationManager(applicationList);
    }
}
