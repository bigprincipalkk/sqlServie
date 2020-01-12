package com.youngc.pipeline.service.TeacherSeretary;

import com.github.pagehelper.Page;
import com.youngc.pipeline.model.CourseManageModel;

import java.util.List;

public interface CourseManageService {
    Page getCouseByCollegeNumber(String collegeNumber, int pageNum, int pageSize);

    Page getCourseByMajorNumber(String collegeNumber,String majorNumber, int pageNum, int pageSize);

    int addCourse(CourseManageModel courseManageModel);

    int isExistsCourse(String courseNumber);

    int deleteCourse(String courseNumbers);

    int updateCourse(CourseManageModel courseManageModel);

    String  getMajorNumber(String courseNumber);
}
