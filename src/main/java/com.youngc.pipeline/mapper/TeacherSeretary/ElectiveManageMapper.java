package com.youngc.pipeline.mapper.TeacherSeretary;

import com.youngc.pipeline.model.CourseManageModel;
import com.youngc.pipeline.model.TeacherManageModel;
import com.youngc.pipeline.model.electiveDetailsModel;
import com.youngc.pipeline.sqlProvider.system.SystemSqlProvider;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface ElectiveManageMapper {

    @Select("SELECT course_number,course_name,course_nature,course_credit,course_hour,major_number,College_number FROM Course" +
            " WHERE major_number=#{majorNumber} AND College_number=#{collegeNumber} AND course_nature='选修'")
    List<CourseManageModel> search(@Param("collegeNumber") String collegeNumber,@Param("majorNumber") String majorNumber);

    @Select("SELECT course_number,course_name,course_nature,course_credit,course_hour,major_number,College_number FROM Course" +
            " WHERE  College_number=#{collegeNumber} AND course_nature='选修'")
    List<CourseManageModel> searchBycollegeNumber(@Param("collegeNumber") String collegeNumber);


    @Select("SELECT teacher_number,teacher_name FROM Teacher WHERE major_number=#{majorNumber} AND College_number=#{collegeNumber}")
    List<TeacherManageModel> getTeacher(@Param("collegeNumber") String collegeNumber,@Param("majorNumber") String majorNumber);

    @InsertProvider(type = SystemSqlProvider.class, method = "addTeacher")
    boolean addTeacher(@Param("selectTeachers") String selectTeachers,@Param("courseNumber") String courseNumber,@Param("demo") Long demo);

    @Select("SELECT count(*) FROM Elective_course WHERE course_number=#{courseNumber} and teacher_number in (${selectTeachers}) and year=#{year}")
    int isExistsTeacher(@Param("selectTeachers") String selectTeachers,@Param("courseNumber") String courseNumber,@Param("year") int year);

    @Select("SELECT Course.course_name,Course.course_number,Teacher.teacher_number,Teacher.teacher_name,count(Sec.elective_id) as people " +
            "from  Elective_course Ec1,Course,Teacher " +
            "LEFT JOIN slect_elective_course Sec on " +
            "Sec.elective_id =(SELECT Ec2.elective_id FROM Elective_course Ec2 " +
            "WHERE Ec2.course_number=#{courseNumber} and Ec2.year=#{year} and  Ec1.teacher_number=Ec2.teacher_number) " +
            "WHERE Ec1.course_number=Course.course_number and Ec1.teacher_number=Teacher.teacher_number " +
            "and Ec1.course_number=#{courseNumber} and Ec1.year=#{year} " +
            "GROUP BY Course.course_name,Course.course_number,Teacher.teacher_number,Teacher.teacher_name ")
    List<electiveDetailsModel> searchDetails(@Param("courseNumber") String courseNumber,@Param("year") int year);

    @Select("SELECT email FROM Student,slect_elective_course,Elective_course WHERE Student.student_number=slect_elective_course.student_number AND slect_elective_course.elective_id=Elective_course.elective_id " +
            "AND Elective_course.teacher_number=#{teacherNumber} AND Elective_course.course_number=#{courseNumber} " +
            "AND Elective_course.year=#{year}")
    List<String> getStudent(@Param("courseNumber") String courseNumber,@Param("teacherNumber") String teacherNumber,@Param("year") int year);

    @Delete("DELETE FROM slect_elective_course  WHERE slect_elective_course.elective_id=(SELECT elective_id FROM Elective_course " +
            "WHERE teacher_number=#{teacherNumber} AND course_number=#{courseNumber} AND year=#{year}) ")
    boolean deleteSelectDetails(@Param("courseNumber") String courseNumber,@Param("teacherNumber") String teacherNumber,@Param("year") int year);

    @Delete("DELETE FROM Elective_course " +
            "WHERE teacher_number=#{teacherNumber} AND course_number=#{courseNumber} AND year=#{year} ")
    boolean deleteDetails(@Param("courseNumber") String courseNumber,@Param("teacherNumber") String teacherNumber,@Param("year") int year);

}
