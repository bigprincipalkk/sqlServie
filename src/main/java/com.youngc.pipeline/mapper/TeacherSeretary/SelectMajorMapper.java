package com.youngc.pipeline.mapper.TeacherSeretary;

import com.youngc.pipeline.model.College;
import com.youngc.pipeline.model.Major;
import com.youngc.pipeline.sqlProvider.system.SystemSqlProvider;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface SelectMajorMapper {
    @Select("select major_number,major_name,College_name,open_year from Major,College where College.College_number = " +
            "Major.college_number and Major.college_number= #{colNum}")
    List<Major> getMajors(@Param("colNum") String collegeNumber);

    @Insert("INSERT INTO Major(major_number,major_name,college_number,open_year) VALUES" +
            "(#{majorNumber},#{majorName},#{collegeNumber},#{openYear})")
    int addMajor(Major major);

    @Update("UPDATE Major SET major_number=#{majorNumber},major_name=#{majorName},college_number=#{collegeNumber},open_year=#{openYear}" +
            "WHERE major_number=#{oldMajorNumber} ")
    int updateMajor(Major major);
//    @Delete("DELETE FROM Major WHERE major_number IN (${deleteMajors})")
//    boolean deleteMajors()
    @Update("UPDATE Major SET major_name=#{majorName},open_year=#{openYear}" +
        "WHERE major_number=#{oldMajorNumber} AND college_number =#{collegeNumber}")
    int updateMajor2(Major major);

    @Select("SELECT COUNT(major_number) FROM Major WHERE major_number = #{majorNumber} AND college_number=#{collegeNumber}")
    int isMajorExists(Major major);

    //检查该专业是否已经开设课程
    @Select("SELECT COUNT(major_number) FROM Course WHERE college_number = #{collegeNumber} and major_number in (#{majorNumber})")
    int isMajorExistsInCourse(@Param("collegeNumber") String collegeNumber,@Param("majorNumber") String majorNumbers);

    //检查该专业是否已经有老师
    @Select("SELECT COUNT(major_number) FROM Teacher WHERE college_number = #{collegeNumber} and major_number in (#{majorNumber})")
    int  isMajorExistsInTeacher(@Param("collegeNumber") String collegeNumber,@Param("majorNumber") String majorNumbers);


    @SelectProvider(type = SystemSqlProvider.class,method = "batStuDel")
    int isMajorExistsInStudent( String collegeNumber, String majorNumbers,Long ids);

    @Delete("DELETE FROM Major WHERE  major_number IN (${majorNumber}) AND college_number = #{collegeNumber}")
    int deleteMajor(@Param("collegeNumber") String collegeNumber,@Param("majorNumber") String majorNumbers);
}
