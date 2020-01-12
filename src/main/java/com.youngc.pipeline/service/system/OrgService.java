package com.youngc.pipeline.service.system;

import com.youngc.pipeline.bean.context.TreeNode;
import com.youngc.pipeline.model.OrgModel;

import java.util.List;

/**
 * @author liweiqiang
 */
public interface OrgService {
    /**
     * 查询组织树
     */
    List<TreeNode> getTree(String keyword);

    /**
     * 查询组织信息
     */
    OrgModel getOrg(Long userId);

    /**
     * 查询组织编号
     */
    OrgModel getOrgCode(String orgCode);

    /**
     * 更新组织信息
     */
    OrgModel updateOrg(OrgModel orgModel);

    /**
     * 删除组织
     */
    void deleteOrg(Long userId);

    /**
     * 添加组织
     */
    OrgModel addOrg(OrgModel orgModel);
}
