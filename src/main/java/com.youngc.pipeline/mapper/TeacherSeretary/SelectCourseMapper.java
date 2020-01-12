package com.youngc.pipeline.mapper.TeacherSeretary;

import com.youngc.pipeline.model.College;
import com.youngc.pipeline.model.Course;
import com.youngc.pipeline.model.ScoreManagerModel;
import com.youngc.pipeline.model.StudentManagerModel;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public interface SelectCourseMapper {

    @Select("select distinct course_name,Elective_course.course_number from Elective_course,Course" +
            " where Elective_course.course_number=Course.course_number AND year=#{year} AND teacher_number=#{teacherNumber}")
    List<Course> getCourseElective(@Param("year") int year,@Param("teacherNumber") String teacherNumber);

    @Select("select distinct course_name,obligatory_teacher.course_number from obligatory_teacher,Course" +
            " where obligatory_teacher.course_number=Course.course_number AND year=#{year} AND teacher_number=#{teacherNumber}")
    List<Course> getCourseObligatory(@Param("year") int year,@Param("teacherNumber") String teacherNumber);

    @Select("SELECT Student.student_number,student_name,score from Student,Score WHERE Student.student_number = Score.student_number AND " +
            "year=#{year} AND teacher_number=#{teacherNumber} AND course_number=#{courseNumber}")
    List<ScoreManagerModel> getScore(@Param("year")int year,@Param("teacherNumber")String teacherNumber,@Param("courseNumber")String courseNumber);

    @Update("UPDATE Score SET score=#{score} WHERE student_number=#{studentNumber} AND course_number=#{courseNumber}")
    boolean updateScore(@Param("studentNumber")String studentNumber,@Param("courseNumber")String courseNumber,@Param("score")String score);

    @Select("select distinct course_name, Course.course_number,class_name from obligatory_teacher,Class,Course" +
            " where  course_nature = #{courseNature} AND year=#{year} AND teacher_number=#{teacherNumber} AND obligatory_teacher.class_id = Class.class_id" +
            " AND obligatory_teacher.course_number=Course.course_number")
    List<ScoreManagerModel> getCoursebyNatureB(@Param("year") int year,@Param("courseNature") String courseNature,@Param("teacherNumber") String teacherNumber);

    @Select("select distinct course_name,Course.course_number from Elective_course,Course" +
            " where  course_nature = #{courseNature} AND year=#{year} AND teacher_number=#{teacherNumber} AND Elective_course.course_number=Course.course_number")
    List<ScoreManagerModel> getCoursebyNatureX(@Param("year") int year,@Param("courseNature") String courseNature,@Param("teacherNumber") String teacherNumber);



}
