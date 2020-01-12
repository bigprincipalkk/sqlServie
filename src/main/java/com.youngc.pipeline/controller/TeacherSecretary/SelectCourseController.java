package com.youngc.pipeline.controller.TeacherSecretary;

import com.youngc.pipeline.bean.auth.CourseBean;
import com.youngc.pipeline.bean.auth.MajorBean;
import com.youngc.pipeline.model.Major;
import com.youngc.pipeline.result.Result;
import com.youngc.pipeline.result.ResultCode;
import com.youngc.pipeline.result.ResultGenerator;
import com.youngc.pipeline.service.TeacherSeretary.SelectCourseService;
import com.youngc.pipeline.service.TeacherSeretary.SelectMajorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/TeacherSelect")
public class SelectCourseController {
    @Autowired
    private SelectCourseService selectCourseService;

    @RequestMapping(path = "/getCourseByyear", method = RequestMethod.POST)
    @ResponseBody
    public Result getCourse(@RequestParam int year,@RequestParam String teacherNumber){

        return ResultGenerator.generate(ResultCode.SUCCESS, selectCourseService.selectCourse(year,teacherNumber));

    }

    @RequestMapping(path = "/getScore", method = RequestMethod.GET)
    @ResponseBody
    public Result getScore(@RequestParam int year,@RequestParam String teacherNumber,
                           @RequestParam String courseNumber, @RequestParam int pageNum, @RequestParam int pageSize){

        return ResultGenerator.generate(ResultCode.SUCCESS, selectCourseService.getScore(year,teacherNumber,courseNumber, pageNum, pageSize));

    }

    @RequestMapping(path = "/updateScore", method = RequestMethod.POST)
    @ResponseBody
    public Result updateScore(@RequestParam String studentNumber,@RequestParam String courseNumber,
                           @RequestParam String score){

        return ResultGenerator.generate(ResultCode.SUCCESS, selectCourseService.updateScore(studentNumber,courseNumber,score));

    }


    @RequestMapping(path = "/getCoursebyNature", method = RequestMethod.GET)
    @ResponseBody
    public Result getCoursebyNature(@RequestParam int year,@RequestParam String courseNature,
                           @RequestParam String teacherNumber, @RequestParam int pageNum, @RequestParam int pageSize){

        return ResultGenerator.generate(ResultCode.SUCCESS, selectCourseService.getCoursebyNature(year,courseNature,teacherNumber, pageNum, pageSize));

    }
}
