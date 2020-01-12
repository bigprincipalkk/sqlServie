package com.youngc.pipeline.bean.param;

import lombok.Data;

import java.util.Date;

@Data
public class DictionaryQueryBean {
    private Long id;

    private String dictValue;

    private String dictName;

    private int status;

    private String remark;

    private Long addPerson;

    private Date addTime;

    private Long lastPerson;

    private Date lastTime;

}
