package com.youngc.pipeline.service.Student;

import com.github.pagehelper.Page;
import com.youngc.pipeline.model.ScoreModel;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SearchScoreService {
    List<ScoreModel> getSelfScore(String studentNumber,int pageNum,int pageSize);

    List<ScoreModel> getCourseScore(String studentNumber,int pageNum,int pageSize);
}
