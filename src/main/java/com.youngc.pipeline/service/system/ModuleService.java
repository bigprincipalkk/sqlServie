package com.youngc.pipeline.service.system;

import com.youngc.pipeline.bean.context.ModuleTreeNode;
import com.youngc.pipeline.bean.context.TreeNode;
import com.youngc.pipeline.model.ModuleModel;

import java.util.List;


public interface ModuleService {
    /**
     * 查询模块树
     */
    List<TreeNode> getTree(String  keyword,String roleIds,String droleIds);

    /**
     * 查询菜单树
     */
    List<ModuleTreeNode> getModuleTree(String  keyword,String roleIds,String droleIds);

    /**
     * 查询模块信息
     */
    ModuleModel getModuleDetails(Long userId);

    /**
     * 查询模块标识
     */
    ModuleModel getControlId(String controlId,Long moduleId);

    /**
     * 更新模块信息
     */
    ModuleModel updateModuleDetails(ModuleModel moduleModel);

    /**
     * 删除模块
     */
    void deleteModule(Long userId);

    /**
     * 添加模块
     */
    ModuleModel addModule(ModuleModel moduleModel);
}
