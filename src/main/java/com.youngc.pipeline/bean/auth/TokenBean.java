package com.youngc.pipeline.bean.auth;

import lombok.Data;

@Data
public class TokenBean {
    private Integer id;

    private Integer userId;

    private String token;

    private Integer expireTime;
}
