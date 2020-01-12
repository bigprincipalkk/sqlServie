package com.youngc.pipeline.bean.param;

import lombok.Data;

@Data
public class SysRoleModuleBean {
    private Long id;

    private Long roleId;

    private Long moduleId;

    private int checked;

    private int status;

}
