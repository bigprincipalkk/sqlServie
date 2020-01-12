package com.youngc.pipeline.service.TeacherSeretary.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.youngc.pipeline.mapper.TeacherSeretary.ObligatoryManageMapper;
import com.youngc.pipeline.model.CourseManageModel;
import com.youngc.pipeline.model.ObligatoryManageModel;
import com.youngc.pipeline.model.TeacherManageModel;
import com.youngc.pipeline.service.TeacherSeretary.ObligatoryManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ObligatoryManageServiceImpl implements ObligatoryManageService {
    @Autowired
    private ObligatoryManageMapper obligatoryManageMapper;


    public Page selectInfo(String courseNumber,String majorName,int pageNum,int pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        return (Page)obligatoryManageMapper.selectInfo(courseNumber,majorName);
    }

    public List<TeacherManageModel> getTeacher(String collegeNumber, String majorNumber) {
        return obligatoryManageMapper.getTeacher(collegeNumber,majorNumber);
    }

    public String getMajorName(String collegeNumber, String majorNumber) {
        return obligatoryManageMapper.getMajorName(collegeNumber,majorNumber);
    }

    public int addObligatory(String courseNumber, int year, int classId, String teacherNumber) {
        if(obligatoryManageMapper.isExists(courseNumber,classId,teacherNumber)>0){
            return 0;
        }else {
            return obligatoryManageMapper.addObligatory(courseNumber,year,classId,teacherNumber);
        }

    }

    public int deleteObligatory(String obligatoryIds) {
        return obligatoryManageMapper.deleteObligatory(obligatoryIds);
    }

    public CourseManageModel  getCourseInfo(String courseNumber){
        return obligatoryManageMapper.getCourseInfo(courseNumber);
    }
}
