package com.youngc.pipeline.service.system.impl;

import com.youngc.pipeline.bean.context.ModuleTreeNode;
import com.youngc.pipeline.bean.context.TreeNode;
import com.youngc.pipeline.mapper.system.ModuleMapper;
import com.youngc.pipeline.model.ModuleModel;
import com.youngc.pipeline.service.system.ModuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class ModuleServiceImpl implements ModuleService {
    @Autowired
    private ModuleMapper moduleMapper;

    /**
     * 查询模块树
     */
    public List<TreeNode> getTree(String keyword,String roleIds,String droleIds) {

        List<Map> module;

        List<TreeNode> tree = new ArrayList<TreeNode>();

        module = moduleMapper.getTree(roleIds);

        for (int i = 0; i < module.size(); i++) {
            if (((Integer) (module.get(i).get("pid"))) == 0) {
                TreeNode root = new TreeNode();
                root.setId(((Integer) (module.get(i).get("module_id"))).toString());
                root.setName((String) (module.get(i).get("module_name")));
                root.setChildren(getChilds(module, ((Integer) (module.get(i).get("module_id")))));
                tree.add(root);
            }
        }
        return tree;
    }

    List<TreeNode> getChilds(List<Map> module, Integer parentId) {
        List<TreeNode> children = new ArrayList<TreeNode>();
        for (Map mod : module) {
            if ((mod.get("pid")).equals(parentId)) {
                TreeNode node = new TreeNode();
                node.setId(((Integer) (mod.get("module_id"))).toString());
                node.setName((String) (mod.get("module_name")));
                node.setChildren(getChilds(module, (Integer) (mod.get("module_id"))));
                children.add(node);
            }
        }
        return children;
    }

    /**
     * 查询菜单树
     */
    public List<ModuleTreeNode> getModuleTree(String keyword,String roleIds,String droleIds) {

        List<ModuleTreeNode> tree = new ArrayList<ModuleTreeNode>();
        System.out.println(roleIds);
        List<Map> modules=moduleMapper.getModuleIdsByRoleIds(roleIds);

        for (int i = 0; i < modules.size(); i++) {
            if (((Integer) (modules.get(i).get("pid"))) == 0) {
                ModuleTreeNode root = new ModuleTreeNode();
                root.setIcon(modules.get(i).get("icon").toString());
                root.setName((String) (modules.get(i).get("module_name")));
                root.setChildren(getModuleChilds(modules, (Integer) (modules.get(i).get("module_id"))));
                tree.add(root);
            }
        }
        return tree;
    }

    List<ModuleTreeNode> getModuleChilds(List<Map> modules, Integer parentId) {
        List<ModuleTreeNode> children = new ArrayList<ModuleTreeNode>();
        for (Map module : modules) {
            if ((module.get("pid")).equals(parentId)) {
                ModuleTreeNode node = new ModuleTreeNode();
                node.setIcon(((module.get("icon"))).toString());
                node.setName((String) (module.get("module_name")));
                node.setChildren(getModuleChilds(modules, (Integer) (module.get("module_id"))));
                children.add(node);
            }
        }
        return children;
    }

    /**
     * 查询模块信息
     */
    public ModuleModel getModuleDetails(Long moduleId) {
        return moduleMapper.getModuleInfo(moduleId);
    }

    /**
     * 查询模块标识
     */
    public ModuleModel getControlId(String controlId, Long moduleId) {
        if (moduleId == null) {
            return moduleMapper.getControlId(controlId);
        } else {
            return moduleMapper.getUpdateControlId(moduleId, controlId);
        }
    }

    /**
     * 更新模块信息
     */
    public ModuleModel updateModuleDetails(ModuleModel moduleModel) {
        moduleMapper.update(moduleModel);
        return moduleModel;
    }

    /**
     * 添加模块信息
     */
    public ModuleModel addModule(ModuleModel moduleModel) {
        moduleMapper.addModule(moduleModel);
        return moduleModel;
    }

    /**
     * 删除模块
     */
    public void deleteModule(Long moduleId) {
        String moduleIdStr = moduleMapper.deleteModule(moduleId);
        moduleMapper.deleteModuleInfo(moduleIdStr);
    }

}
