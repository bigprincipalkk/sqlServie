package com.youngc.pipeline.service.TeacherSeretary.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.youngc.pipeline.mapper.TeacherSeretary.SelectMajorMapper;
import com.youngc.pipeline.model.Major;
import com.youngc.pipeline.service.TeacherSeretary.SelectMajorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class SelectMajorServiceImpl implements SelectMajorService{
    @Autowired
    private SelectMajorMapper selectMajorMapper;


    public Page getMajors(String collegeNumber, int pageNum, int pageSize) {
        System.out.println(collegeNumber);
        PageHelper.startPage(pageNum,pageSize);
        return (Page) selectMajorMapper.getMajors(collegeNumber);
    }

    public int addMajor(Major major) {
        if(selectMajorMapper.isMajorExists(major) > 0){
            return 0;
        }else {
             return  selectMajorMapper.addMajor(major);
        }
    }


    public int updateMajor(Major major) {
        if(!major.getOldMajorNumber().equals(major.getMajorNumber())) {
            if (selectMajorMapper.isMajorExists(major) > 0) {
                return 0;
            } else {
                if(selectMajorMapper.updateMajor(major)>0){
                    return 2;
                }else {
                    return 1;
                }
            }
        }else {
            if(selectMajorMapper.updateMajor2(major)>0){
                return 2;
            }else {
                return 1;
            }
        }
    }

    public int deleteMajor(String collegeNumber, String majorNumbers) {
//        selectMajorMapper.isMajorExistsInCourse(collegeNumber,majorNumbers) >0 ||
//        ||
//        selectMajorMapper.isMajorExistsInTeacher(collegeNumber,majorNumbers)>0){
        if(selectMajorMapper.isMajorExistsInStudent(collegeNumber,majorNumbers,Long.parseLong("1"))>0){
            return  0;
        }else if(selectMajorMapper.isMajorExistsInCourse(collegeNumber,majorNumbers) >0){
            return -1;
        }else if (selectMajorMapper.isMajorExistsInTeacher(collegeNumber,majorNumbers)>0){
            return -2;
        }else{
            return selectMajorMapper.deleteMajor(collegeNumber,majorNumbers);
        }
    }


}
