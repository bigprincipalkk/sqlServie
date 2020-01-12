package com.youngc.pipeline.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.Date;

/**
 * @author liweiqiang
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ModuleModel {

    private Long moduleId;

    private Long userId;

    private Long pid;

    private String moduleName;

    private String pModuleName;

    private String controlId;

    private String moduleDesc;

    private Long type;

    private Long status;

    private Long priority;

    private String icon;

    private Long addPerson;

    private Date addTime;

    private Long lastPerson;

    private Date lastTime;
}
