package com.youngc.pipeline.mapper.system;

import com.youngc.pipeline.model.SysRoleModel;
import com.youngc.pipeline.model.SysRoleModuleModel;
import com.youngc.pipeline.sqlProvider.system.SystemSqlProvider;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;


@Component
public interface SysRoleMapper {

    /**
     * 批量获取角色信息
     * @param keyword
     * @return
     */
    @Select(" SELECT role_id,role_name,role_desc,status FROM sys_role " +
            " WHERE role_name LIKE CONCAT('%', #{keyword}, '%')")
    List<SysRoleModel> getRoleList(String keyword);

    /**
     * 添加角色
     * @param sysRoleModel
     * @return
     */
    @Insert(" INSERT INTO sys_role (role_name, role_desc, status,add_person, add_time, last_person, last_time)" +
            " VALUES(#{roleName}, #{roleDesc}, #{status}, #{addPerson}, now(), #{lastPerson}, now())")
    @Options(useGeneratedKeys = true, keyColumn = "role_id")
    int insertRole(SysRoleModel sysRoleModel);

    /**
     * 删除角色
     * @param idList
     * @return
     */
    @Delete(" DELETE FROM sys_role WHERE role_id IN (${idList});")
    int deleteRole(@Param("idList") String  idList);

    /**
     * 删除角色之后，删除关联表sys_role_module数据
     * @param idList
     * @return
     */
    @Delete(" DELETE FROM sys_role_module WHERE role_id IN (${idList});")
    int deleteRoleMou(@Param("idList") String  idList);
    /**
     * 获取角色信息
     * @param id
     * @return
     */
    @Select(" SELECT role_id,role_name,role_desc,status " +
            " FROM sys_role WHERE role_id = #{id}")
    SysRoleModel getRoleInfo(@Param("id") Long id);

    /**
     * 更新角色信息
     * @param sysRoleModel
     * @return
     */
    @Update(" UPDATE sys_role SET role_name = #{roleName}, role_desc = #{roleDesc},status=#{status}," +
            " last_person = #{lastPerson}, last_time = now() WHERE role_id = #{roleId}")
    int updateRoleInfo(SysRoleModel sysRoleModel);

    /**
     * 查询权限树
     */
    @Select("SELECT m.module_id,m.module_name,m.pid,(case when r.id is null then 0 else 1 end) checked from  sys_module m " +
            "LEFT JOIN (select module_id,id,role_id from sys_role_module where role_id = #{roleId} ) r ON m.module_id=r.module_id "+
            " WHERE m.status=1")
    List<Map> getRoleTree(@Param("roleId") Long roleId);

    /**
     * 查询权限树
     */
    @Select("SELECT m.module_id,m.module_name,m.pid,(case when r.id is null then 0 else 1 end) checked from  sys_module m " +
            "LEFT JOIN (select module_id,id,role_id from sys_role_module where role_id = #{roleId} ) r ON m.module_id=r.module_id ")
    List<Map> getRoleTreeNoStatus(@Param("roleId") Long roleId);

    /**
     * 修改权限树=>先删除，后插入
     */
    @Delete("DELETE FROM sys_role_module WHERE role_id=#{roleId}")
    int deleteRoleMoudle(@Param("roleId") Long  roleId);

    /**
     * 使用provider拼接插入信息
     * @param moduleIds
     * @param roleId
     * @param userId
     * @return
     */
    @InsertProvider(type = SystemSqlProvider.class, method = "insertRoleModule")
    int insertRoleModule(List<String> moduleIds,  Long roleId,  Long userId);


}
