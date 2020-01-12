package com.youngc.pipeline.service.system.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.youngc.pipeline.bean.context.ModuleTreeNode;
import com.youngc.pipeline.bean.context.TreeNode;
import com.youngc.pipeline.mapper.system.SysRoleMapper;
import com.youngc.pipeline.model.SysRoleModel;
import com.youngc.pipeline.model.SysRoleModuleModel;
import com.youngc.pipeline.service.system.SysRoleService;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class SysRoleServiceImpl implements SysRoleService {
    @Autowired
    private SysRoleMapper sysRoleMapper;

    /**
     * 分页获取角色信息
     *
     * @param dataName
     * @param pageNum
     * @param pageSize
     * @return
     */
    public Page getRoleList(String dataName, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        return (Page) sysRoleMapper.getRoleList(dataName);
    }

    /**
     * 添加角色
     *
     * @param sysRoleModel
     * @return
     */
    public SysRoleModel addRole(SysRoleModel sysRoleModel) {
        sysRoleMapper.insertRole(sysRoleModel);
        return sysRoleModel;
    }

    /**
     * 获取角色信息
     *
     * @param id
     * @return
     */
    public SysRoleModel getRoleInfo(Long id) {
        return sysRoleMapper.getRoleInfo(id);
    }

    /**
     * 更新角色信息
     *
     * @param sysRoleModel
     * @return
     */
    public SysRoleModel updateRoleInfo(SysRoleModel sysRoleModel) {
        sysRoleMapper.updateRoleInfo(sysRoleModel);
        return sysRoleModel;
    }

    /**
     * 删除角色
     *
     * @param idList
     * @return
     */
    @Transactional
    public boolean deleteRoleList(String idList) {
        sysRoleMapper.deleteRole(idList);
        sysRoleMapper.deleteRoleMou(idList);
        return true;
    }

    /**
     * 通过权限id获取该权限对应的权限树
     *
     * @param roleId
     * @return
     */
    public List<TreeNode> getRoleTree(Long roleId) {
        List<Map> groups = sysRoleMapper.getRoleTree(roleId);

        List<TreeNode> tree = new ArrayList<TreeNode>();

        for (int i = 0; i < groups.size(); i++) {
            if ((Integer) groups.get(i).get("pid") == 0) {
                TreeNode node = new TreeNode();
                node.setId(groups.get(i).get("module_id").toString());
                node.setName(groups.get(i).get("module_name").toString());
                node.setChecked(groups.get(i).get("checked").toString());
                node.setChildren(getModuleChilds(groups, (Integer) groups.get(i).get("module_id")));
                tree.add(node);
            }
        }

        return tree;
    }

    /**
     * 获取子节点children
     *
     * @param modules
     * @param parentId
     * @return
     */
    List<TreeNode> getModuleChilds(List<Map> modules, Integer parentId) {
        List<TreeNode> children = new ArrayList<TreeNode>();
        for (Map module : modules) {
            if ((module.get("pid")).equals(parentId)) {
                TreeNode node = new TreeNode();
                node.setId(module.get("module_id").toString());
                node.setName((String) (module.get("module_name")));
                node.setChecked(module.get("checked").toString());
                node.setChildren(getModuleChilds(modules, (Integer) (module.get("module_id"))));
                children.add(node);
            }
        }
        return children;
    }

    @Transactional
    public boolean updateRoleModule(Long roleId, String moudleIds, Long userId) {
        sysRoleMapper.deleteRoleMoudle(roleId);
        String[] moduleId = moudleIds.split(",");
        List<String> moduleIds = Arrays.asList(moduleId);
        sysRoleMapper.insertRoleModule(moduleIds,roleId,userId);
        return true;
    }

}
