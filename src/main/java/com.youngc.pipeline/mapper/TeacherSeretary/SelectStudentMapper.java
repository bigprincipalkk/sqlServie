package com.youngc.pipeline.mapper.TeacherSeretary;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import com.youngc.pipeline.model.ClassModel;
import com.youngc.pipeline.model.College;
import com.youngc.pipeline.model.StudentManagerModel;
import com.youngc.pipeline.sqlProvider.system.SystemSqlProvider;
import org.apache.ibatis.annotations.*;
import com.youngc.pipeline.model.Major;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

@Component
public interface SelectStudentMapper {

    @Select("select College_number,College_name from College")
    List<College> getCollege();

    @InsertProvider(type = SystemSqlProvider.class, method = "addStudentExcel")
    boolean addStudentExcel(List<StudentManagerModel> data,Long userId,Long devId);

    @Select("SELECT major_number,major_name FROM Major WHERE college_number =#{colNum}")
    List<Major> getMajor(@Param("colNum") String collegeNumber);

    @Select("SELECT class_id,class_name FROM Class WHERE class_name LIKE CONCAT('%',#{majorName},'%')")
    List<ClassModel> getClass(@Param("majorName") String majorName);

    @Select("SELECT major_name from Major where major_number=#{majorNumber} and college_number =#{collegeNumber}")
    String getMajorName(@Param("collegeNumber")String collegeNumber,@Param("majorNumber") String majorNumber);

    @Select("SELECT student_number,email,student_name,sex from Student WHERE student_number LIkE CONCAT('%',#{searchNumber},'%')")
    List<StudentManagerModel> getStudent(@Param("searchNumber")String searchNumber);

    @Update("UPDATE Student SET password=#{pad} WHERE student_number=#{studentName}")
    boolean resetPassword(@Param("studentName")String studentName,@Param("pad")String pad);

    @Update("UPDATE Student SET student_number=#{studentNumber},email=#{email} WHERE student_Number=#{oldStudentNumber}")
    boolean updateStudent(StudentManagerModel studentManagerModel) ;

    @Select("SELECT COUNT(*) from slect_elective_course  WHERE student_number in ( ${deleteStudentNumbers} )")
    int isExistStudentInElectiveCourse(@Param("deleteStudentNumbers")String deleteStudentNumbers);

    @Delete(" DELETE FROM Student WHERE student_number in ( ${deleteStudentNumbers} )")
    boolean deleteStudent(@Param("deleteStudentNumbers")String deleteStudentNumbers);

    @Insert("INSERT into Student values(#{studentNumber},#{password},#{email},#{studentName},#{sex},#{grade},#{entranceYear})")
    boolean addStudentSingal(StudentManagerModel studentManagerModel);
}
