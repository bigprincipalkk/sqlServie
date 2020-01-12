package com.youngc.pipeline.result;

public class ResultGenerator {

    public static PlatformResult generate(ResultCode r) {
        return new PlatformResult(r.getCode(), r.getMsg());
    }

    public static PlatformDataResult generate(ResultCode r, Object data) {
        return new PlatformDataResult(r.getCode(), r.getMsg(), data);
    }

    public static PlatformPageResult generate(ResultCode r, Page page, Object data) {
        return new PlatformPageResult(r.getCode(), r.getMsg(), page, data);
    }

    public static PlatformPageResult generate(com.github.pagehelper.Page page) {
        Page page1 = new Page(page.getTotal(), page.getPageNum(), page.getPageSize());
        return new PlatformPageResult(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMsg(), page1, page.getResult());
    }
}
