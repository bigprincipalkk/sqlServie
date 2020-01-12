package com.youngc.pipeline.mapper.TeacherSeretary;

import com.youngc.pipeline.model.CourseManageModel;
import com.youngc.pipeline.model.TeacherManageModel;
import com.youngc.pipeline.model.ObligatoryManageModel;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface ObligatoryManageMapper {
    @Select("SELECT obligatory_id,obligatory_teacher.course_number,course_name,class_name,teacher_name,year FROM Class,obligatory_teacher,Teacher,Course " +
            "WHERE  obligatory_teacher.class_id = Class.class_id AND obligatory_teacher.teacher_number = Teacher.teacher_number AND obligatory_teacher.course_number = Course.course_number" +
            " AND obligatory_teacher.course_number = #{courseNumber} AND obligatory_teacher.class_id IN(SELECT class_id FROM Class WHERE class_name LIKE CONCAT('%',#{majorName},'%'))")
    List<ObligatoryManageModel> selectInfo(@Param("courseNumber") String courseNumber, @Param("majorName") String majorName);

    @Select("SELECT teacher_number,teacher_name FROM Teacher WHERE college_number =#{collegeNumber} AND major_number = #{majorNumber}")
    List<TeacherManageModel> getTeacher(@Param("collegeNumber") String collegeNumber,@Param("majorNumber") String majorNumber);

    @Select("SELECT major_name FROM Major WHERE college_number = #{collegeNumber} AND major_number = #{majorNumber}")
    String getMajorName(@Param("collegeNumber") String collegeNumber,@Param("majorNumber") String majorNumber);

    @Insert("INSERT INTO obligatory_teacher(course_number,year,class_id,teacher_number) VALUES(#{courseNumber},#{year},#{classId},#{teacherNumber})")
    int addObligatory(@Param("courseNumber") String courseNumber,@Param("year") int year,
                      @Param("classId") int classId,@Param("teacherNumber") String teacherNumber);

    @Delete("DELETE FROM obligatory_teacher WHERE obligatory_id in (${obligatoryIds})")
    int deleteObligatory(@Param("obligatoryIds") String obligatoryIds);

    @Select("SELECT COUNT(*) FROM obligatory_teacher WHERE course_number=#{courseNumber} AND class_id=#{classId} AND teacher_number=#{teacherNumber}")
    int isExists(@Param("courseNumber") String courseNumber,@Param("classId") int classId,@Param("teacherNumber") String teacherNumber);

    @Select("SELECT major_number,college_number from Course WHERE course_number=#{courseNumber}")
    CourseManageModel getCourseInfo(@Param("courseNumber") String courseNumber);
}
