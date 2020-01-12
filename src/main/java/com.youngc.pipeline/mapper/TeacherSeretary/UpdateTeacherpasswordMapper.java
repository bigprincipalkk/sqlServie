package com.youngc.pipeline.mapper.TeacherSeretary;

import com.youngc.pipeline.model.StudentManagerModel;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface UpdateTeacherpasswordMapper {

    @Select("SELECT count(*) from Teacher WHERE e_mail = #{email}")
    int selectteacheremail(@Param("email") String email);
    @Select("SELECT count(*) from Student WHERE email = #{email}")
    int selectstudentemail(@Param("email") String email);

    @Update(" UPDATE Teacher SET password = #{newpassword}" +
            " WHERE e_mail = #{email}")
    boolean updateteacherpassword(@Param("email") String email, @Param("newpassword") String newpassword);


    @Update(" UPDATE Student SET password = #{newpassword}" +
            " WHERE email = #{email}")
    boolean updatestudentpassword(@Param("email") String email, @Param("newpassword") String newpassword);
}
