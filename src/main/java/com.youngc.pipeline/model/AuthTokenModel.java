package com.youngc.pipeline.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

/**
 * @author liweiqiang
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AuthTokenModel {

    private Integer id;

    private Integer userId;

    private String token;

    private Integer expireTime;
}
