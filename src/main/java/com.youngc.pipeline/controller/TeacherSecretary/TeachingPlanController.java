package com.youngc.pipeline.controller.TeacherSecretary;

import com.youngc.pipeline.result.Result;
import com.youngc.pipeline.result.ResultCode;
import com.youngc.pipeline.result.ResultGenerator;
import com.youngc.pipeline.service.TeacherSeretary.TeacherPlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/TeachingPlan")
public class TeachingPlanController {

    @Autowired
    private TeacherPlanService teacherPlanService;

    @RequestMapping(path = "/search", method = RequestMethod.GET)
    @ResponseBody
    public Result search(@RequestParam String collegeNumber, @RequestParam String majorNumber, @RequestParam int grade,@RequestParam int term,@RequestParam int pageNum, @RequestParam int pageSize){
        return ResultGenerator.generate(teacherPlanService.search(collegeNumber,majorNumber,grade,term,pageNum,pageSize));
    }

    @RequestMapping(path = "/searchCourse", method = RequestMethod.GET)
    @ResponseBody
    public Result searchCourse(@RequestParam String collegeNumber, @RequestParam String majorNumber,@RequestParam int pageNum, @RequestParam int pageSize){
        return ResultGenerator.generate(teacherPlanService.searchCourse(collegeNumber,majorNumber,pageNum,pageSize));
    }

    @RequestMapping(path = "/addCourse", method = RequestMethod.GET)
    @ResponseBody
    public Result addCourse(@RequestParam String addCourseIds,@RequestParam String collegeNumber, @RequestParam String majorNumber,@RequestParam int grade, @RequestParam int term){
        return ResultGenerator.generate(ResultCode.SUCCESS,teacherPlanService.addCourse(addCourseIds,collegeNumber,majorNumber,grade,term));
    }

    @RequestMapping(path = "/deleteTeachingPlan", method = RequestMethod.DELETE)
    @ResponseBody
    public Result deleteTeachingPlan(@RequestParam String deleteOtIds){
        return ResultGenerator.generate(ResultCode.SUCCESS,teacherPlanService.deleteTeachingPlan(deleteOtIds));
    }
}
