package com.youngc.pipeline.bean.param;

import lombok.Data;

@Data
public class DevUnitBean {

    private Long id ;

    private Long deviceId;

    private String unitName;

    private String unitVersion;

    private Long unitNumber;

    private String unitMaterial;

    private String remark;
}
