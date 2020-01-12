package com.youngc.pipeline.mapper.login;

import com.youngc.pipeline.model.SecretaryManageModel;
import com.youngc.pipeline.model.StudentManagerModel;
import com.youngc.pipeline.model.TeacherManageModel;
import com.youngc.pipeline.model.UserManagerModel;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

@Component
public interface LoginMapper {

    @Select("SELECT student_number,password,student_name FROM Student WHERE student_number = #{username}")
    StudentManagerModel getStudentByNumber(@Param("username") String username);


    @Select("SELECT teacher_number,password,teacher_name FROM Teacher WHERE teacher_number = #{username}")
    TeacherManageModel getTeacherByNumber(@Param("username") String username);

    @Select("SELECT secretary_number,password FROM Teacher_secretary WHERE secretary_number = #{username}")
    SecretaryManageModel getSecretaryByNumber(@Param("username") String username);

    @Select(" SELECT su.user_id, su.user_name, su.password, su.real_name,"+
            " GROUP_CONCAT(DISTINCT sur.role_id SEPARATOR ',')role_ids,GROUP_CONCAT(DISTINCT sudr.drole_id SEPARATOR ',')drole_ids"+
            " FROM sys_user su LEFT JOIN sys_user_role sur on sur.user_id=su.user_id " +
            " LEFT JOIN sys_user_data_role sudr on sudr.user_id=su.user_id"+
            " WHERE su.user_name = #{username}")
    UserManagerModel getUserByUsername(@Param("username") String username);

}
