package com.youngc.pipeline.service.TeacherSeretary.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.youngc.pipeline.mapper.TeacherSeretary.SelectTeacherMapper;
import com.youngc.pipeline.model.Major;
import com.youngc.pipeline.model.TeacherManageModel;
import com.youngc.pipeline.service.TeacherSeretary.SelectTeacherService;
import com.youngc.pipeline.utils.BCryptUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public  class SelectTeacherServicelmpl implements SelectTeacherService {
    @Autowired
    private SelectTeacherMapper SelectTeacherMapper;

    public Page getmajorTeacher(String selectmajor, String selectcollege, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        return (Page) SelectTeacherMapper.getmajorTeacher(selectmajor, selectcollege);
    }
    public Page getcollegeTeacher(String selectcollege, int pageNum, int pageSize){
        PageHelper.startPage(pageNum, pageSize);
        return (Page) SelectTeacherMapper.getcollegeTeacher(selectcollege);
    }

    public boolean addTeacher(TeacherManageModel teachermanagemodel) {
        return SelectTeacherMapper.addTeacher(teachermanagemodel);
    }

    public String getTeacherNumber(String teacherNumber) {
        return SelectTeacherMapper.getTeacherNumber(teacherNumber);
    }

    public int updateTeacher(TeacherManageModel teachermanagemodel) {

        String otherTeacherNumber = teachermanagemodel.getOtherTeacherNumber();
        String selectCollege = teachermanagemodel.getSelectCollege();
        String selectMajor = teachermanagemodel.getSelectMajor();
        if(SelectTeacherMapper.selectObligatoryTeacher(otherTeacherNumber)>0||
                SelectTeacherMapper.selectElective(otherTeacherNumber)>0){
            if(!(SelectTeacherMapper.getTeacherCollegeNumber(otherTeacherNumber).equals(selectCollege))){
                return 2;
            }
            else if(!(SelectTeacherMapper.getTeacherMajorNumber(otherTeacherNumber).equals(selectMajor))) {
                return 2;
            }
            else{
                SelectTeacherMapper.updateTeacher(teachermanagemodel);
                return 1;
            }

        }
        else {
            SelectTeacherMapper.updateTeacher(teachermanagemodel);
            return 1;
        }

    }


    public int deleteTeacherList(String teacherNumbers){
        if(SelectTeacherMapper.selectObligatoryTeachers(teacherNumbers)>0)
            return 1;
        else if(SelectTeacherMapper.selectElectives(teacherNumbers)>0)
            return 1;
        else{
            SelectTeacherMapper.deleteTeacherList(teacherNumbers);
            return 0;
        }
    }

    public boolean resetPassword(String teacherNumber){
        String setPassword =new String(teacherNumber.substring(teacherNumber.length()-2));
        String encrypt= BCryptUtil.hashpw(setPassword, BCryptUtil.gensalt(12));
        return SelectTeacherMapper.resetPassword(encrypt,teacherNumber);
    }

    public Major getTeacherMajor(String otherTeacherNumber, String collegeNumber){
        return SelectTeacherMapper.getTeacherMajor(otherTeacherNumber,collegeNumber);
    }

    public String getEmail(String email){
        return SelectTeacherMapper.getEmail(email);
    }

    public String emailValidateTeacherNumber (String email){
        return SelectTeacherMapper.emailValidateTeacherNumber(email);
    }
}
