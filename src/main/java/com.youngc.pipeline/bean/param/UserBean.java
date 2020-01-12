package com.youngc.pipeline.bean.param;

import lombok.Data;

import java.util.Date;


@Data
public class UserBean {

    private Long userId;

    private Long unitId;

    private String roleIds;

    private String droleIds;

    private String userName;

    private String password;

    private String confirmPassword;

    private String realName;

    private String userAddress;

    private String userEmail;

    private Long userSex;

    private String userPhone;

    private Long status;

}
