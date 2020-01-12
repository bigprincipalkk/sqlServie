package com.youngc.pipeline.mapper.system;

import com.youngc.pipeline.model.ModuleModel;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * @author
 */
@Component
public interface ModuleMapper {
    /**
     * 查询模块数据
     * @return
     */
    @Select(" SELECT DISTINCT  sm.module_id, sm.module_name, sm.pid,sm.icon" +
            " FROM sys_module sm LEFT JOIN sys_role_module srm on srm.module_id=sm.module_id"+
            " WHERE srm.role_id in (${roleIds}) ORDER By sm.priority")
    List<Map> getTree(@Param("roleIds")String roleIds);

    /**
     * 查询模块信息
     * @param moduleId
     * @return
     */
    @Select(" SELECT module_id,module_name,pid,(SELECT module_name FROM sys_module " +
            " WHERE module_id=(SELECT pid FROM sys_module where module_id = #{moduleId}))pModule_name,"+
            " control_id,module_desc,type,status,priority,icon" +
            " FROM sys_module where module_id = #{moduleId};")
    ModuleModel getModuleInfo(@Param("moduleId") Long moduleId);

    /**
     * 查询模块标识
     * @param controlId
     * @return
     */
    @Select(" SELECT control_id FROM sys_module where control_id = #{controlId};")
    ModuleModel getControlId(@Param("controlId") String controlId);

    /**
     * 查询模块标识
     * @param controlId
     * @return
     */
    @Select(" SELECT control_id FROM sys_module where control_id = #{controlId} AND module_id != #{moduleId};")
    ModuleModel getUpdateControlId(@Param("moduleId") Long moduleId,@Param("controlId") String controlId);

    /**
     * 更新模块信息
     * @param moduleModel
     * @return
     */
    @Update(" UPDATE sys_module SET pid = #{pid}, module_name = #{moduleName}, control_id = #{controlId}," +
            " module_desc = #{moduleDesc}, type = #{type}, status = #{status}, priority = #{priority}, icon = #{icon}, "+
            " last_person = #{lastPerson}, last_time = now() "+
            " WHERE module_id = #{moduleId};")
    int update(ModuleModel moduleModel);

    /**
     * 添加模块信息
     * @param moduleModel
     * @return
     */
    @Insert(" insert into sys_module(pid,module_name,control_id,module_desc,type,status,priority,icon," +
            " add_person,add_time,last_person,last_time)" +
            " values(#{pid},#{moduleName},#{controlId},#{moduleDesc},#{type},#{status},#{priority},#{icon},#{addPerson},"+
            " now(),#{lastPerson},now());")
    @Options(useGeneratedKeys = true, keyProperty = "moduleId", keyColumn = "module_id")
    int addModule(ModuleModel moduleModel);

    /**
     * 删除模块信息
     * @param moduleId
     * @return
     */
    @Select("SELECT GROUP_CONCAT(module_id separator ',') module_id FROM sys_module " +
            "WHERE FIND_IN_SET(module_id,getModuleChild(#{moduleId}));")
    String deleteModule(@Param("moduleId") Long moduleId);

    @Delete(" DELETE  FROM  sys_module WHERE module_id IN (${moduleIdStr})")
    int deleteModuleInfo(@Param("moduleIdStr") String moduleIdStr);

    /**
     * 查询关联模块id
     * @param roleIds
     * @return
     */
    @Select(" SELECT DISTINCT  sm.module_id, sm.module_name, sm.pid,sm.icon" +
            " FROM sys_module sm LEFT JOIN sys_role_module srm on srm.module_id=sm.module_id"+
            " WHERE srm.role_id in (${roleIds}) AND sm.status=1 ORDER By sm.priority")
    List<Map> getModuleIdsByRoleIds(@Param("roleIds")String roleIds);

    /**
     * 查询关联模块id
     * @param droleIds
     * @return
     */
    @Select("")
    List<Map> getModuleIdsByDroleIds(String droleIds);
}
