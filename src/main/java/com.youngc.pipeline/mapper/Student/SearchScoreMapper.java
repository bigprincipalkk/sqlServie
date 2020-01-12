package com.youngc.pipeline.mapper.Student;

import com.youngc.pipeline.model.Major;
import com.youngc.pipeline.model.ScoreModel;
import com.youngc.pipeline.model.TeacherManageModel;
import com.youngc.pipeline.sqlProvider.system.SystemSqlProvider;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;

import java.util.List;

@Component
public interface SearchScoreMapper {

    @Select("select C.course_number,course_name,score from Course C,Score S " +
            "where student_number=#{studentNumber} and C.course_number=S.course_number")
    List<ScoreModel> getSelfScore(@Param("studentNumber") String studentNumber);

    @Select("select avg(Score) as avgScore,C.course_number,course_name from Score a,Course C\n" +
            "where a.course_number in(select course_number from Score b where b.year=a.year and student_number=#{studentNumber}) and a.course_number=C.course_number\n" +
            "group by course_number,year")
    List<ScoreModel> getCourseScore(@Param("studentNumber") String studentNumber);
}
