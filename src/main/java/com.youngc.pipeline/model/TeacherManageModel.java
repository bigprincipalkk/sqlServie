package com.youngc.pipeline.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import net.sf.jsqlparser.expression.DateTimeLiteralExpression;

import java.util.Date;
import java.util.List;


@Data
public class TeacherManageModel {
    private String teacherNumber;

    private String password;

    private String email;

    private String teacherName;

    private String sex;

    private String selectMajor;

    private String prof;

    private String hiredate;

    private String selectCollege;

    private String otherTeacherNumber;

    private List<TeacherManageModel> children;

    private String id;

}
