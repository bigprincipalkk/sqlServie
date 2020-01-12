package com.youngc.pipeline.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.Date;

/**
 * @author liweiqiang
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrgModel {

    private Long orgId;

    private Long userId;

    private Long pid;

    private String orgName;

    private String pOrgName;

    private String orgCode;

    private String orgDesc;

    private Long addPerson;

    private Long lastPerson;
}
