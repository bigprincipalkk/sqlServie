package com.youngc.pipeline.service.TeacherSeretary.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.youngc.pipeline.mapper.TeacherSeretary.CourseManageMapper;
import com.youngc.pipeline.model.CourseManageModel;
import com.youngc.pipeline.service.TeacherSeretary.CourseManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseManageServiceImpl implements CourseManageService{
    @Autowired
    private CourseManageMapper courseManageMapper;

    public Page getCouseByCollegeNumber(String collegeNumber, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        return (Page)courseManageMapper.getCourseByCollegeNumber(collegeNumber);
    }

    public Page getCourseByMajorNumber(String collegeNumber,String majorNumber, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        return (Page)courseManageMapper.getCourseByMajorNumber(collegeNumber,majorNumber);
    }

    public int addCourse(CourseManageModel courseManageModel) {
        if(courseManageMapper.isExistsCourse(courseManageModel.getCourseNumber())>0){
            return 0;
        }else {
            return courseManageMapper.addCourse(courseManageModel);
        }
    }

    public int isExistsCourse(String courseNumber) {
        if(courseManageMapper.isExistsCourse(courseNumber) >0){
            return 1;
        }else {
            return 0;
        }
    }

    public int deleteCourse(String courseNumbers) {
        if(courseManageMapper.isExistsCourseInStudent(courseNumbers)>0){
            return 0;
        }else {
            return courseManageMapper.deleteCourse(courseNumbers);
        }
    }

    public int updateCourse(CourseManageModel courseManageModel) {
        if(courseManageModel.getCourseNumber().equals(courseManageModel.getCourseNumber())){
            return courseManageMapper.updateCourse(courseManageModel);
        }else {
            if(courseManageMapper.isExistsCourse(courseManageModel.getCourseNumber())>0){
                return -1;
            }else {
                return courseManageMapper.updateCourse(courseManageModel);
            }
        }
    }

    public String getMajorNumber(String courseNumber) {
        return courseManageMapper.getMajorNumber(courseNumber);
    }
}
