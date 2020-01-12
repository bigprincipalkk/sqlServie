package com.youngc.pipeline.mapper.login;

import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

@Component
public interface StudentTokenMapper {
    @Select(" SELECT userId FROM sys_token WHERE token = #{token}")
    Long getStuNumberByToken(@Param("token") String token);

    @Select(" SELECT COUNT(userId) FROM sys_token WHERE token = #{token}")
    Integer isTokenExists(@Param("token") String token);

    @Select(" SELECT COUNT(user_id) FROM sys_token WHERE user_id = #{userId}")
    Integer isTokenExistsById(@Param("userId") String userId);

    @Insert(" INSERT INTO sys_token (user_id, token) VALUES (#{userId}, #{token})")
    int insertNewToken(@Param("userId") String userId, @Param("token") String token);

    /**
     * 根据用户ID更新token
     * @param userId
     * @param newToken
     * @return
     */
    @Update(" UPDATE sys_token SET token = #{newToken}" +
            " WHERE user_id = #{userId}")
    int updateToken(@Param("userId") String userId, @Param("newToken") String newToken);



    @Delete(" DELETE FROM sys_token WHERE token = #{token}")
    int delete(@Param("token") String token);
}
