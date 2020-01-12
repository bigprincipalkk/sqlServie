package com.youngc.pipeline.bean.param;

import lombok.Data;

import java.util.Date;

@Data
public class DictionaryValueBean {

    private  Long id;

    private String dataName;

    private int dataValue;

    private String dictValue;

    private String remark;

    private Long addPerson;

    private Date addTime;

    private Long lastPerson;

    private Date lastTime;
}
