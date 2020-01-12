package com.youngc.pipeline.mapper.system;

import com.youngc.pipeline.model.SysDataRoleModel;
import com.youngc.pipeline.sqlProvider.system.SystemSqlProvider;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public interface SysDataRoleMapper {

    /**
     * 获取数据角色信息
     *
     * @param keyword
     * @return
     */
    @Select(" SELECT drole_id,drole_name,drole_desc,status FROM sys_data_role " +
            " WHERE drole_name LIKE CONCAT('%', #{keyword}, '%')")
    List<SysDataRoleModel> getDataRoleList(String keyword);

    /**
     * 添加数据角色信息
     *
     * @param sysDataRoleModel
     * @return
     */
    @Insert(" INSERT INTO sys_data_role (drole_name, drole_desc, status,add_person, add_time, last_person, last_time)" +
            " VALUES(#{droleName}, #{droleDesc}, #{status}, #{addPerson}, now(), #{lastPerson},now())")
    @Options(useGeneratedKeys = true, keyColumn = "drole_id")
    int insertDataRole(SysDataRoleModel sysDataRoleModel);

    /**
     * 删除数据角色
     *
     * @param idList
     * @return
     */
    @Delete(" DELETE FROM sys_data_role WHERE drole_id IN (${idList});")
    int deleteDataRole(@Param("idList") String idList);

    /**
     * 获取数据角色信息
     *
     * @param id
     * @return
     */
    @Select(" SELECT drole_id,drole_name,drole_desc,status " +
            " FROM sys_data_role WHERE drole_id = #{id}")
    SysDataRoleModel getDataRoleInfo(@Param("id") Long id);

    /**
     * 更新数据角色信息
     *
     * @param sysDataRoleModel
     * @return
     */
    @Update(" UPDATE sys_data_role SET drole_name = #{droleName}, drole_desc = #{droleDesc},status=#{status}," +
            " last_person = #{lastPerson}, last_time = now() WHERE drole_id = #{droleId}")
    int updateDataRoleInfo(SysDataRoleModel sysDataRoleModel);

    /**
     * 查询单位数据
     *
     * @return
     */
    @Select(" SELECT unit_id,org_id,unit_name" +
            " FROM sys_unit;")
    List<Map> getOrgUnitTree();



    /**
     * 删除数据角色关联单位信息
     *
     * @param droleId
     * @return
     */
    @Delete(" DELETE FROM sys_data_role_unit WHERE drole_id = #{droleId};")
    int deleteDataUnit(@Param("droleId") Long droleId);

    /**
     * 批量删除数据角色关联单位信息
     *
     * @param droleIds
     * @return
     */
    @Delete(" DELETE FROM sys_data_role_unit WHERE drole_id in (${droleIds});")
    int deleteDataUnits(@Param("droleIds") String droleIds);

    /**
     * 添加数据角色关联单位信息
     *
     * @param DataUnitId
     * @param userId
     * @param droleId
     * @return
     */
    @InsertProvider(type = SystemSqlProvider.class, method = "putDataUnit")
    int putDataUnit(List<String> DataUnitId,  Long userId,  Long droleId);

    /**
     * 查询单位
     * @param droleIds
     * @return
     */
    @Select("")
    List<Map> getOrgUnitTreeByDroleIds(String droleIds);
}
