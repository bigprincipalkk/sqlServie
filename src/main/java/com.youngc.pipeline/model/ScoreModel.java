package com.youngc.pipeline.model;

import lombok.Data;

@Data
public class ScoreModel {
    String studentNumber;

    String courseNumber;

    String courseName;

    int year;

    String teacherNumber;

    float score;

    float avgScore;
}
