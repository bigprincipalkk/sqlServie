package com.youngc.pipeline.controller.Student;

import com.youngc.pipeline.result.Result;
import com.youngc.pipeline.result.ResultCode;
import com.youngc.pipeline.result.ResultGenerator;
import com.youngc.pipeline.service.Student.SearchCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/StudentCourse")
public class SearchCourseController {

    @Autowired
    SearchCourseService searchCourseService;

    @RequestMapping(path = "/search", method = RequestMethod.GET)
    @ResponseBody
    public Result selectCourseByCollege(@RequestParam int year,@RequestParam String studentNumber,@RequestParam int pageNum, @RequestParam int pageSize){
        return ResultGenerator.generate(searchCourseService.search(studentNumber,year,pageNum,pageSize));

    }

    @RequestMapping(path = "/deleteCourse", method = RequestMethod.GET)
    @ResponseBody
    public Result deleteCourse(@RequestParam String courseNumber,@RequestParam String studentNumber,@RequestParam String teacherNumber,@RequestParam int year){
        return ResultGenerator.generate(ResultCode.SUCCESS,searchCourseService.deleteCourse(courseNumber,studentNumber,teacherNumber,year));

    }

    @RequestMapping(path = "/selectCourse", method = RequestMethod.GET)
    @ResponseBody
    public Result selectCourse(@RequestParam String collegeNumber,@RequestParam String majorNumber,@RequestParam int pageNum, @RequestParam int pageSize){
        return ResultGenerator.generate(searchCourseService.selectCourse(collegeNumber,majorNumber,pageNum,pageSize));

    }

    @RequestMapping(path = "/addCourse", method = RequestMethod.GET)
    @ResponseBody
    public Result addCourse(@RequestParam String courseNumber,@RequestParam String teacherNumber,@RequestParam String studentNumber){
        return ResultGenerator.generate(ResultCode.SUCCESS,searchCourseService.addCourse(courseNumber,teacherNumber,studentNumber));

    }
}
