package com.youngc.pipeline.model;

import lombok.Data;

import java.util.Date;

@Data
public class UnitModel {

    private Long unitId;

    private String unitCode;

    private String unitName;

    private String phoneOne;

    private String phoneTwo;

    private String contactOne;

    private String contactTwo;

    private String email;

    private String address;

    private String remark;

    private Long  addPerson;

    private Date addTime;

    private Long lastPerson;

    private Date lastTime;

    private Long orgId;

    private String orgName;
}
