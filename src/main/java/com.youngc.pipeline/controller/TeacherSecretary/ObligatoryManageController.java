package com.youngc.pipeline.controller.TeacherSecretary;

import com.youngc.pipeline.bean.auth.ObligatoryBean;
import com.youngc.pipeline.model.ObligatoryManageModel;
import com.youngc.pipeline.result.Result;
import com.youngc.pipeline.result.ResultCode;
import com.youngc.pipeline.result.ResultGenerator;
import com.youngc.pipeline.service.TeacherSeretary.ObligatoryManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/TeachingPlan")
public class ObligatoryManageController {
    @Autowired
    private ObligatoryManageService obligatoryManageService;

    //查找排课信息
    @RequestMapping(path = "/selectInfo", method = RequestMethod.GET)
    @ResponseBody
    public Result selectInfo(@RequestParam String collegeNumber,@RequestParam String courseNumber,@RequestParam String majorNumber,int pageNum,int pageSize){
        return ResultGenerator.generate(ResultCode.SUCCESS,obligatoryManageService.selectInfo(courseNumber,
                obligatoryManageService.getMajorName(collegeNumber,majorNumber),pageNum,pageSize));

    }

    //找到学院专业下的老师
    @RequestMapping(path = "/getTeacher", method = RequestMethod.POST)
    @ResponseBody
    public Result getTeacher(@RequestParam String collegeNumber,@RequestParam String majorNumber){
        return ResultGenerator.generate(ResultCode.SUCCESS, obligatoryManageService.getTeacher(collegeNumber,majorNumber));

    }

    @RequestMapping(path = "/addObligatory", method = RequestMethod.POST)
    @ResponseBody
    public Result addObligatory(@RequestParam String courseNumber,@RequestParam int year,@RequestParam int classId,@RequestParam String teacherNumber){
        return ResultGenerator.generate(ResultCode.SUCCESS,obligatoryManageService.addObligatory(courseNumber,year,classId,teacherNumber));
    }

    @RequestMapping(path = "/{deleteObligatory}", method = RequestMethod.DELETE)
    @ResponseBody
    public Result deleteObligatory(@RequestParam String obligatoryIds){
        return ResultGenerator.generate(ResultCode.SUCCESS,obligatoryManageService.deleteObligatory(obligatoryIds));
    }

    @RequestMapping(path = "/getCourseInfo", method = RequestMethod.GET)
    @ResponseBody
    public Result getCourseInfo(@RequestParam String courseNumber){
        return ResultGenerator.generate(ResultCode.SUCCESS,obligatoryManageService.getCourseInfo(courseNumber));

    }
}
