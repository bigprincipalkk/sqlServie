package com.youngc.pipeline.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.Date;

/***
 * wanghongli
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DictionaryQueryModel {

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
