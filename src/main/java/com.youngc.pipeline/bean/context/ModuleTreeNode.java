package com.youngc.pipeline.bean.context;

import lombok.Data;

import java.util.List;

@Data
public class ModuleTreeNode {

    private String icon;

    private String name;

    private List<ModuleTreeNode> children;


}
