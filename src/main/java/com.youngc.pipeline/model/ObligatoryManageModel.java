package com.youngc.pipeline.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ObligatoryManageModel {

        private int obligatoryId;

        private String courseNumber;

        private int year;

        private int classId;

        private String teacherNumber;

        private String collegeNumber;

        private String majorNumber;

        private String courseName;

        private String className;

        private String teacherName;
}