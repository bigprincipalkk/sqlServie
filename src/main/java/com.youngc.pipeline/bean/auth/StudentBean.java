package com.youngc.pipeline.bean.auth;

import lombok.Data;

@Data
public class StudentBean {
    private String studentNumber;

    private String oldStudentNumber;

    private String password;

    private String email;

    private String studentName;

    private String sex;

    private Long entranceYear;

    private int identity;

    private Long grade;
}
