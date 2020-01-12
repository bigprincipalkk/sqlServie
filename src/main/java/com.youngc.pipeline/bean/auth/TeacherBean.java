package com.youngc.pipeline.bean.auth;

import lombok.Data;
import net.sf.jsqlparser.expression.DateTimeLiteralExpression;

import java.util.Date;


@Data
public class TeacherBean {
    private String teacherNumber;

    private String password;

    private String email;

    private String teacherName;

    private String sex;

    private String selectmajor;

    private int identity;

    private String selectcollege;

    private String hiredate;

    private String prof;

    private String otherteacherNumber;

}
