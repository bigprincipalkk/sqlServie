package com.youngc.pipeline.service.Teacher;

import com.youngc.pipeline.model.CourseManageModel;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface SelectStudentSearchService {
    List<CourseManageModel> getCourse(String teacherNumber,int year);

    Page selectStudent(String teacherNumber, String courseNumber, int year,int pageNum,int pageSize);

    public String downloadFileInfo(HttpServletRequest request, HttpServletResponse response,String teacherNumber,String courseNumber, int year);

    Page electiveSearch(String teacherNumber, String courseNumber, int year,int pageNum,int pageSize);

    List<CourseManageModel> getCourseElective(String teacherNumber,int year);

    Page getScores(String teacherNumber, String courseNumber, int year,int pageNum,int pageSize);

    int updateScore(String teacherNumber, String courseNumber, int year,float score);
}
