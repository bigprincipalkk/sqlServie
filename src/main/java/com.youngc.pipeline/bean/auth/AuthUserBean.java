package com.youngc.pipeline.bean.auth;

import lombok.Data;

/**
 * @author liweiqiang
 */
@Data
public class AuthUserBean {
    private Integer id;

    private String username;

    private String password;

    private String realname;

    private String phone;

    private String email;
}
