package com.youngc.pipeline.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.Date;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SysDataRoleModel {

    private Long id;

    private Long droleId;

    private String droleName;

    private String droleDesc;

    private int status;

    private int priority;

    private Long addPerson;

    private Date addTime;

    private Long lastPerson;

    private Date lastTime;
}
