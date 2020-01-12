package com.youngc.pipeline.bean.context;

import lombok.Data;

import java.util.List;

@Data
public class TreeNode {

    private String id;

    private String name;

    private String path;

    private String type;

    private List<TreeNode> children;

    private String checked;

}
