package com.youngc.pipeline.bean.param;

import lombok.Data;

import java.util.Date;

@Data
public class SysDataRoleBean {

    private Long id;

    private Long droleId;

    private String droleName;

    private String droleDesc;

    private int status;

    private int priority;

}
