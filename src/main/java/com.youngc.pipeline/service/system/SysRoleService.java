package com.youngc.pipeline.service.system;

import com.github.pagehelper.Page;
import com.youngc.pipeline.bean.context.TreeNode;
import com.youngc.pipeline.model.SysDataRoleModel;
import com.youngc.pipeline.model.SysRoleModel;
import com.youngc.pipeline.model.SysRoleModuleModel;

import java.util.List;
import java.util.Map;

public interface SysRoleService {
    Page getRoleList(String dataName, int pageNum, int pageSize);

    SysRoleModel addRole(SysRoleModel sysRoleModel);

    SysRoleModel getRoleInfo(Long id);

    SysRoleModel updateRoleInfo(SysRoleModel sysRoleModel);

    boolean deleteRoleList(String idList);

    List<TreeNode> getRoleTree(Long roleId);

    boolean updateRoleModule(Long roleId,String moudleIds,Long userId);
}
