package com.youngc.pipeline.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.Date;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SysRoleModel {
    private Long id;

    private Long roleId;

    private String roleName;

    private String roleDesc;

    private Long moduleId;

    private String moduleName;

    private Long pid;

    private int checked;

    private int status;

    private int priority;

    private Long addPerson;

    private Date addTime;

    private Long lastPerson;

    private Date lastTime;
}
