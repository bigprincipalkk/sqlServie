package com.youngc.pipeline.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.Date;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SysRoleModuleModel {

    private Long id;

    private Long roleId;

    private Long moduleId;

    private int checked;

    private int status;

    private Long addPerson;

    private Date addTime;

    private Long lastPerson;

    private Date lastTime;
}
