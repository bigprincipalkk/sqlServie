package com.youngc.pipeline.mapper.Student;


import com.youngc.pipeline.model.CourseTeacherModel;
import com.youngc.pipeline.model.StudentCourseModel;
import com.youngc.pipeline.model.electiveDetailsModel;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

@Component
public interface SearchCourseMapper {

    @Select("SELECT Course.course_number,Course.course_name,Course.course_credit,Course.course_nature," +
            "Teacher.teacher_number,Teacher.teacher_name " +
            "FROM Course,Teacher,slect_elective_course,Elective_course WHERE " +
            "slect_elective_course.student_number=#{studentNumber} AND slect_elective_course.elective_id=Elective_course.elective_id " +
            "AND Elective_course.year=#{year} AND  Elective_course.teacher_number=Teacher.teacher_number " +
            "AND Elective_course.course_number=Course.course_number UNION " +
            "SELECT Course.course_number,Course.course_name,Course.course_credit,Course.course_nature," +
            "Teacher.teacher_number,Teacher.teacher_name " +
            "FROM Course,Teacher,obligatory_teacher,Class WHERE " +
            "Class.class_name=#{className} AND Class.class_id=obligatory_teacher.class_id AND obligatory_teacher.year=#{year} " +
            "AND obligatory_teacher.course_number=Course.course_number AND obligatory_teacher.teacher_number=Teacher.teacher_number")
    List<CourseTeacherModel> searchElective(@Param("className") String className, @Param("studentNumber") String studentNumber, @Param("year") int year);

    @Select("SELECT Course.course_number,Course.course_name,Course.course_credit,Course.course_nature," +
            "Teacher.teacher_number,Teacher.teacher_name " +
            "FROM Course,Teacher,obligatory_teacher,Class WHERE " +
            "Class.class_name=#{className} AND Class.class_id=obligatory_teacher.class_id AND obligatory_teacher.year=#{year}")
    List<CourseTeacherModel> searchObligatory(@Param("className") String className,@Param("year") int year);

    @Select("SELECT major_name FROM Major WHERE major_number=#{majorNumber} AND college_number=#{collegeNumber}")
    String getMajorName(@Param("collegeNumber") String collegeNumber,@Param("majorNumber") String majorNumber);

    @Select("SELECT count(*) from slect_elective_course,Elective_course WHERE slect_elective_course.student_number=#{studentNumber} and " +
            "slect_elective_course.elective_id=Elective_course.elective_id and Elective_course.course_number=#{courseNumber} and Elective_course.teacher_number=#{teacherNumber}")
    int searchCourse(@Param("courseNumber") String courseNumber,@Param("studentNumber") String studentNumber,@Param("teacherNumber") String teacherNumber);

    @Delete("DELETE from slect_elective_course WHERE elective_id=#{courseId} and student_number=#{studentNumber}")
    boolean deleteCourse(@Param("courseNumber") String courseNumber,@Param("studentNumber") String studentNumber,@Param("courseId") String courseId);

    @Select("SELECT slect_elective_course.elective_id from slect_elective_course,Elective_course WHERE slect_elective_course.student_number=#{studentNumber} and " +
            " slect_elective_course.elective_id=Elective_course.elective_id and Elective_course.course_number=#{courseNumber} and Elective_course.teacher_number=#{teacherNumber} " +
            "and Elective_course.year=#{year}")
    String getCourseID(@Param("courseNumber") String courseNumber,@Param("studentNumber") String studentNumber,@Param("teacherNumber") String teacherNumber,@Param("year") int year);

    @Select("SELECT Course.course_name,Course.course_number,T1.teacher_number,T1.teacher_name,count(Sec.elective_id) as people " +
            "from  Course,Teacher T1,Elective_course Ec1 " +
            "left join slect_elective_course Sec on " +
            "Sec.elective_id=Ec1.elective_id "+
            "where Ec1.teacher_number in (select teacher_number from Teacher T2 where T2.major_number=#{majorNumber} and T2.College_number=#{collegeNumber}) and " +
            "Ec1.year=#{year} and Ec1.teacher_number=T1.teacher_number and Ec1.course_number=Course.course_number " +
            "GROUP BY Course.course_name,Course.course_number,T1.teacher_number,T1.teacher_name ")
    List<electiveDetailsModel> selectCourse(@Param("collegeNumber") String collegeNumber, @Param("majorNumber") String majorNumber, @Param("year") int year);

    @Select("SELECT Course.course_name,Course.course_number,T1.teacher_number,T1.teacher_name,count(Sec.elective_id) as people " +
            "from  Course,Teacher T1,Elective_course Ec1 " +
            "left join slect_elective_course Sec on " +
            "Sec.elective_id=Ec1.elective_id "+
            "where Ec1.teacher_number in (select teacher_number from Teacher T2 where T2.College_number=#{collegeNumber}) and " +
            "Ec1.year=#{year} and Ec1.teacher_number=T1.teacher_number and Ec1.course_number=Course.course_number " +
            "GROUP BY Course.course_name,Course.course_number,T1.teacher_number,T1.teacher_name ")
    List<electiveDetailsModel> selectCourseByCollege(@Param("collegeNumber") String collegeNumber, @Param("year") int year);

    @Select("SELECT student_number,sum(course_credit) as sumCredit FROM slect_elective_course Sec1,Elective_course Ec1,Course WHERE Sec1.student_number in " +
            "(SELECT student_number FROM slect_elective_course Sec,Elective_course Ec " +
            "WHERE Ec.teacher_number=#{teacherNumber} and Ec.course_number=#{courseNumber} and Ec.year=#{year} " +
            "and Ec.elective_id=Sec.elective_id) and Sec1.elective_id=Ec1.elective_id and Ec1.course_number=Course.course_number " +
            "GROUP BY Sec1.student_number ")
    List<StudentCourseModel> getStudent(@Param("courseNumber") String courseNumber, @Param("teacherNumber") String teacherNumber, @Param("year") int year);

    @Insert("INSERT into slect_elective_course values (#{studentNumber},#{electiveId})")
    boolean insertStudent(@Param("electiveId") int electiveId,@Param("studentNumber") String studentNumber);

    @Select("select elective_id from Elective_course Ec where Ec.teacher_number=#{teacherNumber} and Ec.course_number=#{courseNumber} and Ec.year=#{year}")
    int getID(@Param("courseNumber") String courseNumber, @Param("teacherNumber") String teacherNumber, @Param("year") int year);

    @Select("select sum(course_credit) from slect_elective_course Sec,Elective_course Ec,Course where " +
            "Sec.student_number=#{studentNumber} and Sec.elective_id=Ec.elective_id and Ec.course_number=Course.course_number")
    String getCredit(@Param("studentNumber") String studentNumber);

    @Delete("delete from slect_elective_course where slect_elective_course.student_number=#{studentNumber} and " +
            "slect_elective_course.elective_id=#{electiveId}")
    boolean deleteStudent(@Param("electiveId") int electiveId,@Param("studentNumber") String studentNumber);

    @Select("select email from Student where student_number=#{student}")
    String getStudentEmail(@Param("student") String student);

    @Select("select count(*) from slect_elective_course where slect_elective_course.student_number=#{studentNumber} and slect_elective_course.elective_id=#{electiveId}")
    int searchIsExist(@Param("studentNumber") String studentNumber,@Param("electiveId") int electiveId);

}
