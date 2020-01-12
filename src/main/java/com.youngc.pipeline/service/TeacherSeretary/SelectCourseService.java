package com.youngc.pipeline.service.TeacherSeretary;

import com.github.pagehelper.Page;
import com.youngc.pipeline.model.Course;
import com.youngc.pipeline.model.Major;

import java.util.List;

public interface SelectCourseService {

    List selectCourse(int year,String teacherNumber);

    Page getScore (int year,String teacherNumber,String courseNumber,int pageNum, int pageSize);

    int updateScore (String studentNumber,String courseNumber,String score);

    Page getCoursebyNature (int year,String courseNature,String teacherNumber,int pageNum, int pageSize);


}
