package com.youngc.pipeline.service.TeacherSeretary;

import com.github.pagehelper.Page;

public interface TeacherPlanService {

    Page search(String collegeNumber,String majorNumber,int grade,int term,int pageNum,int pageSize);

    Page searchCourse(String collegeNumber,String majorNumber,int pageNum,int pageSize);

    boolean addCourse(String addCourseIds,String collegeNumber,String majorNumber,int grade,int term);

    boolean deleteTeachingPlan(String deleteOtIds);
}
