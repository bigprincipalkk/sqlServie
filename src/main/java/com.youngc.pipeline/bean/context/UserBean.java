package com.youngc.pipeline.bean.context;

import lombok.Data;

import java.util.Date;

@Data
public class UserBean {
    private long userId;

    private String userName;

    private String password;

    private String realName;

    private String userAddress;

    private String userBirth;

    private String userEmail;

    private Long userSex;

    private String userPhone;

    private Long status;

}
