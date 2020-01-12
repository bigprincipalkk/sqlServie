package com.youngc.pipeline.result;

import lombok.Data;

/**
 * @name
 * @description
 * @author: ZhangWenWei
 * @create: 2018/5/9 上午11:22
 **/
@Data
public class PlatformPageResult implements Result {
    private Page page;
    private Object data;
    private String code;
    private String msg;

    public PlatformPageResult() {
    }

    public PlatformPageResult(String code, String msg, Page page, Object data) {
        this.page = page;
        this.data = data;
        this.code = code;
        this.msg = msg;
    }
}
