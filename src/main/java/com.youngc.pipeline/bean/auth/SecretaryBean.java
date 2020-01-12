package com.youngc.pipeline.bean.auth;

import lombok.Data;

@Data
public class SecretaryBean {
    private String secretaryNumber;

    private String password;

    private String email;

    private int identity;
}
