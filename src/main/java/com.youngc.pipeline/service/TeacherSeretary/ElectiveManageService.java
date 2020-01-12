package com.youngc.pipeline.service.TeacherSeretary;

import com.github.pagehelper.Page;
import com.youngc.pipeline.model.TeacherManageModel;

import java.util.List;

public interface ElectiveManageService {

    Page search(String collegeNumber, String majorNumber, int pageNum, int pageSize);

    List<TeacherManageModel> getTeacher(String collegeNumber,String majorNumber);

    int addTeacher(String selectTeachers,String courseNumber);

    Page searchDetails(String courseNumber,int pageNum, int pageSize);

    boolean deleteDetails(String teacherNumber,String courseNumber);
}
