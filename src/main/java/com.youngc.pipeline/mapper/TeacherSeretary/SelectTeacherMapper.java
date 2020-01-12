package com.youngc.pipeline.mapper.TeacherSeretary;

import com.youngc.pipeline.model.Major;
import com.youngc.pipeline.model.TeacherManageModel;
import com.youngc.pipeline.sqlProvider.system.SystemSqlProvider;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;
import java.util.Date;

import java.util.List;

@Component
public interface SelectTeacherMapper {
    @Select("SELECT teacher_number,teacher_name,password,hiredate,prof,e_mail from Teacher " +
            "WHERE (major_number=#{selectmajor}) and (College_number=#{selectcollege})")
    List<TeacherManageModel> getmajorTeacher(@Param("selectmajor") String selectmajor,@Param("selectcollege") String selectcollege);

    @Select("SELECT teacher_number,teacher_name,password,hiredate,prof,e_mail from Teacher WHERE College_number=#{selectcollege}")
    List<TeacherManageModel> getcollegeTeacher(@Param("selectcollege") String selectcollege);

    @Insert("insert into Teacher (teacher_number,teacher_name,password,hiredate,e_mail,College_number,major_number,prof)"+
    "values(#{teacherNumber},#{teacherName},#{password},#{hiredate},#{email},#{selectCollege},#{selectMajor},#{prof})")
    boolean addTeacher(TeacherManageModel teachermanagemodel);

    @Select("select teacher_number from Teacher where teacher_number=#{teacherNumber}")
    String getTeacherNumber(String teacherNumber);

    @Update("update Teacher set teacher_name=#{teacherName}," +
            "hiredate=#{hiredate},e_mail=#{email},College_number=#{selectCollege},major_number=#{selectMajor},prof=#{prof}" +
            "where teacher_number=#{otherTeacherNumber}" )
    boolean updateTeacher(TeacherManageModel teachermanagemodel);

    @Select("select count(*) from Elective_course where teacher_number in (${teacherNumbers})")
    int selectElectives(@Param("teacherNumbers") String teacherNumbers);

    @Select("select count(*) from obligatory_teacher where teacher_number in (${teacherNumbers})")
    int selectObligatoryTeachers(@Param("teacherNumbers") String teacherNumbers);

    @Select("select count(*) from Elective_course where teacher_number=#{otherTeacherNumber}")
    int selectElective(@Param("otherTeacherNumber") String otherTeacherNumber);

    @Select("select count(*) from obligatory_teacher where teacher_number=#{otherTeacherNumber}")
    int selectObligatoryTeacher(@Param("otherTeacherNumber") String otherTeacherNumber);

    @Delete("DELETE FROM Teacher where teacher_number in (${teacherNumbers})")
    int deleteTeacherList(@Param("teacherNumbers") String teacherNumbers);

    @Update("update Teacher set password=#{encrypt} where teacher_number=#{teacherNumber}")
    boolean resetPassword(@Param("encrypt") String encrypt,@Param("teacherNumber") String teacherNumber);

    @Select("select m.major_number,major_name from Teacher t,Major m where teacher_number=#{otherTeacherNumber} and " +
            "t.major_number=m.major_number and m.College_number=#{collegeNumber}")
    Major getTeacherMajor(@Param("otherTeacherNumber") String otherTeacherNumber, @Param("collegeNumber") String collegeNumber);

    @Select("select e_mail from Teacher where e_mail=#{email}")
    String getEmail(@Param("email") String email);

    @Select("select College_number from Teacher where teacher_number=#{otherTeacherNumber}")
    String getTeacherCollegeNumber(@Param("otherTeacherNumber") String otherTeacherNumber);

    @Select("select major_number from Teacher where teacher_number=#{otherTeacherNumber}")
    String getTeacherMajorNumber(@Param("otherTeacherNumber") String otherTeacherNumber);

    @Select("select teacher_number from Teacher where e_mail=#{email}")
    String emailValidateTeacherNumber(@Param("email") String email);
}
