package com.youngc.pipeline.result;

import lombok.Data;

/**
 * @name
 * @description
 * @author: ZhangWenWei
 * @create: 2018/5/9 上午11:26
 **/
@Data
public class PlatformDataResult implements Result {
    private Object data;
    private String code;
    private String msg;

    public PlatformDataResult() {
    }

    public PlatformDataResult(String code, String msg, Object data) {
        this.data = data;
        this.code = code;
        this.msg = msg;
    }
}
