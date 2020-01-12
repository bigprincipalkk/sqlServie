package com.youngc.pipeline.service.TeacherSeretary.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.youngc.pipeline.mapper.TeacherSeretary.TeacherPlanMapper;
import com.youngc.pipeline.service.TeacherSeretary.TeacherPlanService;
import org.apache.xmlbeans.impl.xb.xsdschema.Public;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TeacherPlanImpl implements TeacherPlanService{

    @Autowired
    TeacherPlanMapper teacherPlanMapper;

    public Page search(String collegeNumber, String majorNumber, int grade, int term, int pageNum, int pageSize){
        PageHelper.startPage(pageNum, pageSize);
        return (Page) teacherPlanMapper.search(collegeNumber,majorNumber,grade,term);
    }

    public Page searchCourse(String collegeNumber,String majorNumber,int pageNum,int pageSize){
        PageHelper.startPage(pageNum, pageSize);
        return (Page) teacherPlanMapper.searchCourse(collegeNumber,majorNumber);
    }

    public boolean addCourse(String addCourseIds,String collegeNumber,String majorNumber,int grade,int term){
        List<Map<String, String>> number=new ArrayList<Map<String, String>>();
        Map map=new HashMap();
        map.put("addCourseIds",addCourseIds);
        map.put("collegeNumber",collegeNumber);
        map.put("majorNumber",majorNumber);
        map.put("grade",grade);
        map.put("term",term);
        number.add(map);
        int[] gradeAnd=new int[2];
        gradeAnd[0]=grade;
        gradeAnd[1]=term;
        return teacherPlanMapper.addCourse(Long.parseLong("1"),number,gradeAnd);
    }

    public boolean deleteTeachingPlan(String deleteOtIds){
        return teacherPlanMapper.deleteTeachingPlan(deleteOtIds);
    }
}
