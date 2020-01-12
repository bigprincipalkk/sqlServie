package com.youngc.pipeline.service.TeacherSeretary.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.youngc.pipeline.mapper.TeacherSeretary.SelectCourseMapper;
import com.youngc.pipeline.mapper.TeacherSeretary.SelectStudentMapper;
import com.youngc.pipeline.model.Course;
import com.youngc.pipeline.model.Major;
import com.youngc.pipeline.service.TeacherSeretary.SelectCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class SelectCourseServiceImpl implements SelectCourseService {
    @Autowired
    private SelectCourseMapper selectCourseMapper;

    public List<Course> selectCourse(int year,String teacherNumber){

        List<Course> courseElective = selectCourseMapper.getCourseElective(year,teacherNumber);
        List<Course> courseObligatory = selectCourseMapper.getCourseObligatory(year,teacherNumber);
        ArrayList <Course> course=(ArrayList<Course>)courseElective;
        course.addAll(courseObligatory);

        return course;
    }
    public Page getScore(int year,String teacherNumber,String courseNumber,int pageNum, int pageSize){
        PageHelper.startPage(pageNum, pageSize);
        return (Page) selectCourseMapper.getScore(year,teacherNumber,courseNumber);
    }

    public int updateScore(String studentNumber,String courseNumber,String score){
         if(selectCourseMapper.updateScore(studentNumber,courseNumber,score)){
             return 1;
        }
        else return 0;
    }

    public Page getCoursebyNature(int year,String courseNature,String teacherNumber,int pageNum, int pageSize){
        PageHelper.startPage(pageNum, pageSize);
        if(courseNature .equals("必修")){
        return (Page) selectCourseMapper.getCoursebyNatureB(year,courseNature,teacherNumber);}
        else return (Page) selectCourseMapper.getCoursebyNatureX(year,courseNature,teacherNumber);
    }
}
