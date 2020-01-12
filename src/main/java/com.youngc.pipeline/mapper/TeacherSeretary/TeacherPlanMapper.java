package com.youngc.pipeline.mapper.TeacherSeretary;

import com.youngc.pipeline.model.CourseManageModel;
import com.youngc.pipeline.sqlProvider.system.SystemSqlProvider;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@Component
public interface TeacherPlanMapper {

    @Select("SELECT OtId,Ot.course_number,course_name,course_nature,course_credit,course_hour FROM Course,Obligatory_teachPlan Ot " +
            "WHERE Ot.course_number=Course.course_number and Ot.College_number=#{collegeNumber} and Ot.major_number=#{majorNumber} " +
            "and Ot.grade=#{grade} and Ot.term=#{term}")
    List<CourseManageModel> search(@Param("collegeNumber") String collegeNumber,@Param("majorNumber") String majorNumber,@Param("grade") int grade,@Param("term") int term);

    @Select("SELECT course_number,course_name,course_nature,course_credit,course_hour FROM Course " +
            "WHERE  College_number=#{collegeNumber} and major_number=#{majorNumber}")
    List<CourseManageModel> searchCourse(@Param("collegeNumber") String collegeNumber,@Param("majorNumber") String majorNumber);


    @InsertProvider(type = SystemSqlProvider.class, method = "addCourse")
    boolean addCourse(@Param("demo")Long demo,@Param("number")List<Map<String, String>> number,@Param("demo1")int[] demo1);

    @Delete("DELETE FROM Obligatory_teachPlan WHERE OtId in (${deleteOtIds})")
    boolean deleteTeachingPlan(@Param("deleteOtIds")String deleteOtIds);
}

