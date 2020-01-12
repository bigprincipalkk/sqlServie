package com.youngc.pipeline.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.Date;

/***
 * wanghongli
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DictModel {

    private String dictValue;

    private String value;

    private String name;
}
