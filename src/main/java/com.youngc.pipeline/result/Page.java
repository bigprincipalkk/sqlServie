package com.youngc.pipeline.result;

/**
 * @name
 * @description
 * @author: ZhangWenWei
 * @create: 2018/5/9 上午11:22
 **/
public class Page {
    private long total;
    private int page;
    private int size;

    public Page(long total, int page, int size) {
        this.total = total;
        this.page = page;
        this.size = size;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}
