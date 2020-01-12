package com.youngc.pipeline.mapper.system;

import com.youngc.pipeline.model.SysDataRoleModel;
import com.youngc.pipeline.model.SysRoleModel;
import com.youngc.pipeline.model.UnitModel;
import com.youngc.pipeline.model.UserManagerModel;
import com.youngc.pipeline.sqlProvider.system.SystemSqlProvider;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface UserManagerMapper {
    /**
     * 获取用户信息
     * @param userId
     * @return
     */
    @Select(" SELECT su.user_id,su.unit_id, su.user_name, su.real_name, su.user_sex, su.user_phone, su.user_email, su.user_address, su.status," +
            " GROUP_CONCAT(DISTINCT sur.role_id SEPARATOR ',')role_ids,GROUP_CONCAT(DISTINCT sudr.drole_id SEPARATOR ',')drole_ids "+
            " FROM sys_user su LEFT JOIN sys_user_role sur on sur.user_id=su.user_id "+
            " LEFT JOIN sys_user_data_role sudr on sudr.user_id=su.user_id "+
            " WHERE su.user_id = #{userId}")
    UserManagerModel getUserInfo(@Param("userId") Long userId);

    /**
     * 修改用户信息
     * @param userManagerModel
     * @return
     */
    @Update(" UPDATE sys_user SET user_name = #{userName},unit_id=#{unitId}, real_name = #{realName},user_phone=#{userPhone},user_email=#{userEmail},user_address=#{userAddress}," +
            " user_sex=#{userSex},last_person = #{lastPerson}, last_time = now() WHERE user_id = #{userId}")
    int updateUserInfo(UserManagerModel userManagerModel);

    /**
     * 修改密码
     * @param userId
     * @param newPassword
     * @param lastPerson
     * @return
     */
    @Update(" UPDATE sys_user SET password = #{newPassword}," +
            " last_person = #{lastPerson}, last_time = now() WHERE user_id = #{userId}")
    int updatePassword(@Param("userId") Long userId, @Param("newPassword") String newPassword, @Param("lastPerson") Long lastPerson);

    /**
     * 添加用户
     * @param userManagerModel
     * @return
     */
    @Insert(" INSERT INTO sys_user (user_name, password, real_name,unit_id,user_sex,user_phone,user_email,user_address, add_person, add_time, last_person, last_time)" +
            " VALUES(#{userName}, #{password}, #{realName},#{unitId},#{userSex},#{userPhone},#{userEmail},#{userAddress}, #{addPerson}, now(), #{lastPerson}, now())")
    @Options(useGeneratedKeys = true,keyProperty = "userId", keyColumn = "user_id")
    Long insertNewUser(UserManagerModel userManagerModel);

    /**
     * 删除用户
     * @param userId
     * @return
     */
    @Delete(" DELETE FROM sys_user WHERE user_id = #{userId}")
    int deleteUser(@Param("userId") Long userId);

    /**
     * 分页获取用户信息
     * @param keyword
     * @return
     */
    @Select(" SELECT user_id, user_name, real_name, user_sex, user_phone, user_email, user_address, status,n.unit_name  FROM sys_user s LEFT JOIN sys_unit n on  s.unit_id=n.unit_id" +
            " WHERE ((user_name LIKE CONCAT('%', #{keyword}, '%')) OR (real_name LIKE CONCAT('%', #{keyword}, '%'))) ORDER BY s.add_time")
    List<UserManagerModel> getList(String keyword);

    /**
     * 批量删除用户
     * @param userList
     * @return
     */
    @Delete(" DELETE FROM sys_user WHERE user_id IN (${userList})")
    int deleteUserList(@Param("userList") String userList);

    /**
     * 查询单位表的内容
     */
    @Select("SELECT unit_id,unit_name FROM sys_unit")
    List<UnitModel> getUnitList();

    /**
     *查询权限表的内容
     * @return
     */
    @Select("SELECT role_id,role_name  FROM sys_role")
    List<SysRoleModel> getRoleList();

    /**
     * 查询数据角色表的内容
     */

    @Select("SELECT drole_id,drole_name FROM sys_data_role")
    List<SysDataRoleModel> getDataRoleList();

    /**
     * 删除用户权限
     * @param userId
     * @return
     */

    @Delete(" DELETE FROM sys_user_role WHERE user_id = #{userId}")
    int deleteUserRole(@Param("userId") Long userId);

    /**
     * 批量删除用户权限
     * @param userIds
     * @return
     */
    @Delete(" DELETE FROM sys_user_role WHERE user_id in ( ${userIds} )")
    int deleteUsersRole(@Param("userIds") String userIds);

    /**
     * 给用户分配权限
     * @param roleIds
     * @param userId
     * @param personId
     * @return
     */
    @InsertProvider(type = SystemSqlProvider.class, method = "insertUserRole")
    int insertUserRole(List<String> roleIds,  Long userId,  Long personId);

    /**
     * 删除用户下的数据角色
     * @param userId
     * @return
     */
    @Delete(" DELETE FROM sys_user_data_role WHERE user_id = #{userId}")
    int deleteUserDataRole(@Param("userId") Long userId);

    /**
     * 批量删除用户下的数据角色
     * @param userIds
     * @return
     */
    @Delete(" DELETE FROM sys_user_data_role WHERE user_id in ( ${userIds} )")
    int deleteUsersDataRole(@Param("userIds") String userIds);

    /**
     * 给用户添加数据角色的权限
     * @param roleIds
     * @param userId
     * @param personId
     * @return
     */
    @InsertProvider(type = SystemSqlProvider.class, method = "insertUserDataRole")
    int insertUserDataRole(List<String> roleIds,  Long userId,  Long personId);

    /**
     * 根据用户名查询是否唯一
     * @param userName
     * @return
     */
    @Select("SELECT user_id from sys_user WHERE user_name=#{userName}")
    List<UserManagerModel> getInfoByUserName(@Param("userName") String userName);
}
