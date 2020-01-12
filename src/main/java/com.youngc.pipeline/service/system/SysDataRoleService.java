package com.youngc.pipeline.service.system;

import com.github.pagehelper.Page;
import com.youngc.pipeline.bean.context.TreeNode;
import com.youngc.pipeline.model.SysDataRoleModel;

import java.util.List;

public interface SysDataRoleService {

    Page getDataRoleList(String dataName, int pageNum, int pageSize);

    SysDataRoleModel addDataRole(SysDataRoleModel sysDataRoleModel);

    SysDataRoleModel getDataRoleInfo(Long id);

    SysDataRoleModel updateDataRoleInfo(SysDataRoleModel sysDataRoleModel);

    boolean deleteDataRoleList(String idList);

    List<TreeNode> getOrgUnitTree();

    boolean putDataUnit(String UnitIds, Long userId, Long droleId);

}
