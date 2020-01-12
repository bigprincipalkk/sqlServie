package com.youngc.pipeline.mapper.TeacherSeretary;

import com.youngc.pipeline.model.CourseManageModel;
import com.youngc.pipeline.model.Major;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface CourseManageMapper {
    @Select("select course_number,course_name,course_nature,course_credit,course_hour from Course WHERE College_number = #{colNum}")
    List<CourseManageModel> getCourseByCollegeNumber(@Param("colNum") String collegeNumber);

    @Select("select course_number,course_name,course_nature,course_credit,course_hour from Course WHERE College_number = #{colNum} AND major_number = #{majorNum}")
    List<CourseManageModel> getCourseByMajorNumber(@Param("colNum") String collegeNumber,@Param("majorNum") String majorNumber);

    //检验课程号是否已经存在
    @Select("SELECT COUNT(*) FROM  Course WHERE course_number =#{courseNumber}")
    int isExistsCourse(String courseNumber);
    //添加课程
    @Insert("INSERT INTO Course(course_number,course_name,course_nature,course_credit,course_hour,major_number,college_number)" +
            "VALUES(#{courseNumber},#{courseName},#{courseNature},#{courseCredit},#{courseHour},#{majorNumber},#{collegeNumber})")
    int addCourse(CourseManageModel courseManageModel);

    //检验该课程是否已经有学生去选
    @Select("SELECT COUNT(*) FROM  Elective_course,slect_elective_course WHERE Elective_course.elective_id= slect_elective_course.elective_id" +
            " AND course_number IN (${courseNumber})")
    int isExistsCourseInStudent(@Param("courseNumber") String courseNumbers);

    @Delete("DELETE FROM Course WHERE  course_number IN (${courseNumber}) ")
    int deleteCourse(@Param("courseNumber") String courseNumbers);

    //修改课程信息
    @Update("UPDATE Course SET course_number=#{courseNumber},course_name=#{courseName},course_nature=#{courseNature}, " +
            "course_credit=#{courseCredit},course_hour=#{courseHour},major_number=#{majorNumber} WHERE course_number = " +
            "#{oldCourseNumber}")
    int updateCourse(CourseManageModel courseManageModel);

    @Select("SELECT major_number FROM Course WHERE course_number = #{courseNumber}")
    String getMajorNumber(@Param("courseNumber") String courseNumber);
}
