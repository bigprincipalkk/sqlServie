package com.youngc.pipeline.controller.TeacherSecretary;

import com.youngc.pipeline.result.Result;
import com.youngc.pipeline.result.ResultCode;
import com.youngc.pipeline.result.ResultGenerator;
import com.youngc.pipeline.service.TeacherSeretary.ElectiveManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ElectiveManage")
public class ElectiveManageController {

    @Autowired
    private ElectiveManageService electiveManageService;

    @RequestMapping(path = "/search", method = RequestMethod.GET)
    @ResponseBody
    public Result search(@RequestParam String collegeNumber, @RequestParam String majorNumber,@RequestParam int pageNum, @RequestParam int pageSize){
        return ResultGenerator.generate(electiveManageService.search(collegeNumber,majorNumber,pageNum,pageSize));
    }

    @RequestMapping(path = "/getTeacher", method = RequestMethod.POST)
    @ResponseBody
    public Result getTeacher(@RequestParam String collegeNumber, @RequestParam String majorNumber){
        return ResultGenerator.generate(ResultCode.SUCCESS,electiveManageService.getTeacher(collegeNumber,majorNumber));
    }

    @RequestMapping(path = "/addTeacher", method = RequestMethod.GET)
    @ResponseBody
    public Result addTeacher(@RequestParam String selectTeachers, @RequestParam String courseNumber){
        return ResultGenerator.generate(ResultCode.SUCCESS,electiveManageService.addTeacher(selectTeachers,courseNumber));
    }

    @RequestMapping(path = "/searchDetails", method = RequestMethod.GET)
    @ResponseBody
    public Result addTeacher(@RequestParam String courseNumber,@RequestParam int pageNum, @RequestParam int pageSize){
        return ResultGenerator.generate(ResultCode.SUCCESS,electiveManageService.searchDetails(courseNumber,pageNum,pageSize));
    }

    @RequestMapping(path = "/deleteDetails", method = RequestMethod.GET)
    @ResponseBody
    public Result deleteDetails(@RequestParam String teacherNumber,@RequestParam String courseNumber){
        return ResultGenerator.generate(ResultCode.SUCCESS,electiveManageService.deleteDetails(teacherNumber,courseNumber));
    }
}
