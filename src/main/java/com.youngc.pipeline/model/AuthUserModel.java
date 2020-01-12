package com.youngc.pipeline.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.Date;

/**
 * @author liweiqiang
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AuthUserModel {

    private Integer id;

    private String username;

    private String password;

    private String realname;

    private String phone;

    private String email;

    private Date addTime;

    private Date lastTime;
}
