package com.youngc.pipeline.bean.param;

import lombok.Data;

import java.util.Date;

@Data
public class SysRoleBean {

    private Long id;

    private Long roleId;

    private String roleName;

    private String roleDesc;

    private int status;

    private int priority;

    private Long moduleId;

    private String moduleName;

    private Long pid;

    private int checked;

}
