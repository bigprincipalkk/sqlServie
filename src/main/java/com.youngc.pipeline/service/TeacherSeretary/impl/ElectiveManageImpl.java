package com.youngc.pipeline.service.TeacherSeretary.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.youngc.pipeline.mapper.TeacherSeretary.ElectiveManageMapper;
import com.youngc.pipeline.model.TeacherManageModel;
import com.youngc.pipeline.service.TeacherSeretary.ElectiveManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Service
public class ElectiveManageImpl implements ElectiveManageService {

    @Autowired
    private ElectiveManageMapper electiveManageMapper;

    @Autowired
    private JavaMailSender javaMailSender;

    @Value("996568235@qq.com")
    private String Sender;

    public Page search(String collegeNumber, String majorNumber, int pageNum, int pageSize){
        PageHelper.startPage(pageNum, pageSize);
        if(majorNumber==null || (majorNumber.equals(""))) {
            return (Page) electiveManageMapper.searchBycollegeNumber(collegeNumber);
        }else{
            return (Page) electiveManageMapper.search(collegeNumber, majorNumber);
        }

    }

    public List<TeacherManageModel> getTeacher(String collegeNumber, String majorNumber){
        List<TeacherManageModel> teacherManageModels=electiveManageMapper.getTeacher(collegeNumber,majorNumber);
        for(int i=0;i<teacherManageModels.size();i++){
            String teacherNumber=teacherManageModels.get(i).getTeacherNumber();
            teacherManageModels.get(i).setId(teacherNumber);
            teacherManageModels.get(i).setChildren(new ArrayList<TeacherManageModel>());
        }
        return teacherManageModels;
    }

    public int addTeacher(String selectTeachers,String courseNumber){
        Calendar cale =  Calendar.getInstance();
        int year=cale.get(Calendar.YEAR);
        if(electiveManageMapper.isExistsTeacher(selectTeachers,courseNumber,year)==0){
            electiveManageMapper.addTeacher(selectTeachers,courseNumber,Long.parseLong("1"));
            return 1;
        }else {
            return 0;
        }
    }

    public Page searchDetails(String courseNumber,int pageNum, int pageSize){
        Calendar cale =  Calendar.getInstance();
        int year=cale.get(Calendar.YEAR);
        PageHelper.startPage(pageNum, pageSize);
        return (Page) electiveManageMapper.searchDetails(courseNumber,year);
    }

    public boolean deleteDetails(String teacherNumber,String courseNumber){
        Calendar cale =  Calendar.getInstance();
        int year=cale.get(Calendar.YEAR);
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(Sender);
        List<String> students=electiveManageMapper.getStudent(courseNumber,teacherNumber,year);
        for(int i=0;i<students.size();i++){
            message.setTo(students.get(i));
            message.setSubject("【学了么选课系统】选修退课");
            message.setText("尊敬的学了么用户，您好：\n 您选修的课程，因非主观因素造成选课失败。" +
                    "\n");
            javaMailSender.send(message);
        }
        electiveManageMapper.deleteSelectDetails(courseNumber,teacherNumber,year);
        electiveManageMapper.deleteDetails(courseNumber,teacherNumber,year);
        return true;
    }
}
