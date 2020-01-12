package com.youngc.pipeline.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.Date;

/**
 * @author liweiqiang
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserManagerModel {

    private Long id;

    private Long userId;

    private Long unitId;

    private String unitName;

    private String roleIds;

    private String droleIds;

    private String userName;

    private String password;

    private String realName;

    private String userAddress;

    private String userEmail;

    private Long userSex;

    private String userPhone;

    private Long status;

    private Long addPerson;

    private Long lastPerson;
}
