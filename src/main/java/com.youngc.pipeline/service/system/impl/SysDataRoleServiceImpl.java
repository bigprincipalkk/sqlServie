package com.youngc.pipeline.service.system.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.youngc.pipeline.bean.context.TreeNode;
import com.youngc.pipeline.mapper.system.OrgMapper;
import com.youngc.pipeline.mapper.system.SysDataRoleMapper;
import com.youngc.pipeline.model.SysDataRoleModel;
import com.youngc.pipeline.service.system.SysDataRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class SysDataRoleServiceImpl implements SysDataRoleService {

    @Autowired
    private SysDataRoleMapper sysDataRoleMapper;
    @Autowired
    private OrgMapper orgMapper;


    /**
     * 获取数据角色信息
     *
     * @param dataName
     * @param pageNum
     * @param pageSize
     * @return
     */
    public Page getDataRoleList(String dataName, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        return (Page) sysDataRoleMapper.getDataRoleList(dataName);
    }

    /**
     * 添加数据角色信息
     *
     * @param sysDataRoleModel
     * @return
     */
    public SysDataRoleModel addDataRole(SysDataRoleModel sysDataRoleModel) {
        sysDataRoleMapper.insertDataRole(sysDataRoleModel);
        return sysDataRoleModel;
    }

    /**
     * 获取数据角色信息
     *
     * @param id
     * @return
     */
    public SysDataRoleModel getDataRoleInfo(Long id) {
        return sysDataRoleMapper.getDataRoleInfo(id);
    }

    /**
     * 更新数据角色信息
     *
     * @param sysDataRoleModel
     * @return
     */
    public SysDataRoleModel updateDataRoleInfo(SysDataRoleModel sysDataRoleModel) {
        sysDataRoleMapper.updateDataRoleInfo(sysDataRoleModel);
        return sysDataRoleModel;
    }

    /**
     * 删除数据角色
     *
     * @param idList
     * @return
     */
    public boolean deleteDataRoleList(String idList) {
        sysDataRoleMapper.deleteDataUnits(idList);
        sysDataRoleMapper.deleteDataRole(idList);
        return true;
    }

    /**
     * 查询组织单位树
     */
    public List<TreeNode> getOrgUnitTree() {

        List<Map> org = orgMapper.getTree();
        List<Map> unit = sysDataRoleMapper.getOrgUnitTree();

        List<TreeNode> tree = new ArrayList<TreeNode>();

        for (int i = 0; i < org.size(); i++) {
            if (((Integer) (org.get(i).get("pid"))) == 0) {
                TreeNode root = new TreeNode();
                root.setId("org_" + ((Integer) (org.get(i).get("org_id"))).toString());
                root.setName((String) (org.get(i).get("org_name")));
                root.setChildren(getChilds(unit, org, ((Integer) (org.get(i).get("org_id")))));
                tree.add(root);
            }
        }
        return tree;
    }

    List<TreeNode> getChilds(List<Map> units, List<Map> orgs, Integer parentId) {
        List<TreeNode> children = new ArrayList<TreeNode>();
        for (int j = 0; j < orgs.size(); j++) {

            Iterator<Map> unit = units.iterator();

            while (unit.hasNext()) {
                Map unitValue = unit.next();
                TreeNode node = new TreeNode();
                if (unitValue.get("org_id").equals(parentId)) {
                    node.setId(((Integer) (unitValue.get("unit_id"))).toString());
                    node.setName((String) (unitValue.get("unit_name")));
                    children.add(node);
                    unit.remove();
                }
            }
            if ((orgs.get(j).get("pid")).equals(parentId)) {
                TreeNode node = new TreeNode();
                node.setId("org_" + ((Integer) (orgs.get(j).get("org_id"))).toString());
                node.setName((String) (orgs.get(j).get("org_name")));
                node.setChildren(getChilds(units, orgs, (Integer) (orgs.get(j).get("org_id"))));
                children.add(node);
            }

        }
        return children;
    }



    /**
     * 修改关联单位
     * @param UnitIds
     * @param userId
     * @param droleId
     * @return
     */
    public boolean putDataUnit(String UnitIds, Long userId, Long droleId) {
        String[] IDS = UnitIds.split(",");
        List<String> DataUnitId = Arrays.asList(IDS);
        sysDataRoleMapper.deleteDataUnit(droleId);

        sysDataRoleMapper.putDataUnit(DataUnitId, userId, droleId);
        return true;
    }
}
