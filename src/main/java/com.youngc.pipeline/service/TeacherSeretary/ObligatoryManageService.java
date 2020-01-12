package com.youngc.pipeline.service.TeacherSeretary;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.youngc.pipeline.mapper.TeacherSeretary.ObligatoryManageMapper;
import com.youngc.pipeline.model.CourseManageModel;
import com.youngc.pipeline.model.ObligatoryManageModel;
import com.youngc.pipeline.model.TeacherManageModel;
import org.springframework.stereotype.Service;

import java.util.List;

public interface ObligatoryManageService {
    Page selectInfo(String courseNumber,String majorName,int pageNum,int pageSize);

    List<TeacherManageModel> getTeacher(String collegeNumber,String majorNumber);

    String getMajorName(String collegeNumber,String majorNumber);

    int addObligatory(String courseNumber,int year,int classId,String teacherNumber);

    int deleteObligatory(String obligatoryIds);

    CourseManageModel getCourseInfo(String courseNumber);
}
