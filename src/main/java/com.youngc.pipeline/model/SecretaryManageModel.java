package com.youngc.pipeline.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SecretaryManageModel {
    private String secretaryNumber;

    private String password;

    private String email;
}
