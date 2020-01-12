package com.youngc.pipeline.result;

public enum ResultCode {

    SUCCESS("200", "操作成功！"),
    FAIL("400", "操作失败！"),
    SYSTEM_ERROR("500", "系统错误"),
    USER_OR_PASS_ERROR("10002", "用户名或密码错误"),
    PARA_ERROR("10001", "参数异常！");
    private String code;
    private String msg;

    ResultCode(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return this.code;
    }

    public String getMsg() {
        return this.msg;
    }
}
