package com.youngc.pipeline.bean.auth;

import lombok.Data;

@Data
public class ObligatoryBean {
    private int obligatoryId;

    private String courseNumber;

    private int year;

    private int classId;

    private String teacherNumber;

    private String collegeNumber;

    private String majorNumber;
}
