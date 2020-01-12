package com.youngc.pipeline.result;

import lombok.Data;

@Data
public class PlatformResult implements Result {

    private String code;
    private String msg;

    public PlatformResult() {
    }

    public PlatformResult(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
