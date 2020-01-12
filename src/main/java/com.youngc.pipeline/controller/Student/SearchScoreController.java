package com.youngc.pipeline.controller.Student;

import com.youngc.pipeline.bean.auth.TeacherBean;
import com.youngc.pipeline.bean.param.UserBean;
import com.youngc.pipeline.model.TeacherManageModel;
import com.youngc.pipeline.model.UserManagerModel;
import com.youngc.pipeline.result.Result;
import com.youngc.pipeline.result.ResultCode;
import com.youngc.pipeline.result.ResultGenerator;
import com.youngc.pipeline.service.Student.SearchScoreService;
import com.youngc.pipeline.service.TeacherSeretary.SelectTeacherService;
import com.youngc.pipeline.service.system.UserManagerService;
import com.youngc.pipeline.utils.BCryptUtil;
import com.youngc.pipeline.utils.RequestContextHolderUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/Student")
public class SearchScoreController {

    @Autowired
    private SearchScoreService searchscoreservice;

    @RequestMapping(path = "/SearchSelfScore", method = RequestMethod.POST)
    @ResponseBody
    public Result getSelfScore(@RequestParam String studentNumber,@RequestParam int pageNum, @RequestParam int pageSize) {
        return ResultGenerator.generate(ResultCode.SUCCESS, searchscoreservice.getSelfScore(studentNumber,pageNum,pageSize));
    }

    @RequestMapping(path = "/SearchCourseScore", method = RequestMethod.POST)
    @ResponseBody
    public Result getCourseScore(@RequestParam String studentNumber,@RequestParam int pageNum, @RequestParam int pageSize) {
        return ResultGenerator.generate(ResultCode.SUCCESS, searchscoreservice.getCourseScore(studentNumber,pageNum,pageSize));
    }

}

