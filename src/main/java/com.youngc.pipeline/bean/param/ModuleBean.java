package com.youngc.pipeline.bean.param;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.Date;


@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ModuleBean {

    private Long moduleId;

    private Long pid;

    private String moduleName;

    private String controlId;

    private String addControlId;

    private String moduleDesc;

    private Long type;

    private Long status;

    private Long priority;

    private String icon;
}
