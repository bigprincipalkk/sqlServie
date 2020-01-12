package com.youngc.pipeline.model;

import lombok.Data;

@Data
public class CourseManageModel {

    private int OtId;

    private String courseNumber;

    private String courseName;

    private String courseNature;

    private float courseCredit;

    private int courseHour;

    private String majorNumber;

    private String collegeNumber;

    private String oldMajorNumber;

    private String oldCollegeNumber;

    private String oldCourseNumber;
}
