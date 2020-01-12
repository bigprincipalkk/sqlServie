package com.youngc.pipeline.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.Date;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DictionaryValueModel {

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

