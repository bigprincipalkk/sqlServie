package com.youngc.pipeline.bean.param;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

/**
 * @author liweiqiang
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DataRoleUnitBean {

    private String unitId;

    private Long droleId;

}
