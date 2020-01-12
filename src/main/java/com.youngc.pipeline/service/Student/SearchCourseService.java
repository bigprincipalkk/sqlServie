package com.youngc.pipeline.service.Student;

import com.github.pagehelper.Page;

public interface SearchCourseService {

    Page search(String studentNumber,int year,int pageNum,int pageSize);

    int deleteCourse(String courseNumber,String studentNumber,String teacherNumber,int year);

    Page selectCourse(String collegeNumber,String majorNumber,int pageNum,int pageSize);

    int addCourse(String courseNumber,String teacherNumber,String studentNumber);
}
