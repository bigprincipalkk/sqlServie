package com.youngc.pipeline.controller.TeacherSecretary;

import com.github.pagehelper.Page;
import com.youngc.pipeline.bean.auth.CourseBean;
import com.youngc.pipeline.bean.auth.MajorBean;
import com.youngc.pipeline.model.CourseManageModel;
import com.youngc.pipeline.model.Major;
import com.youngc.pipeline.result.Result;
import com.youngc.pipeline.result.ResultCode;
import com.youngc.pipeline.result.ResultGenerator;
import com.youngc.pipeline.service.TeacherSeretary.CourseManageService;
import com.youngc.pipeline.service.TeacherSeretary.SelectMajorService;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/TeacherSecretary")
public class CourseManageController {
    @Autowired
    CourseManageService courseManageService;


    @RequestMapping(path = "/selectCourseByCollege", method = RequestMethod.GET)
    @ResponseBody
    public Result selectCourseByCollege(@RequestParam String collegeNumber,int pageNum,int pageSize){
        return ResultGenerator.generate(ResultCode.SUCCESS, courseManageService.getCouseByCollegeNumber(collegeNumber,pageNum,pageSize));

    }

    @RequestMapping(path = "/selectCourseByMajorNumber", method = RequestMethod.GET)
    @ResponseBody
    public Result selectCourseByMajorNumber(@RequestParam String collegeNumber,@RequestParam String majorNumber,int pageNum,int pageSize){
        return ResultGenerator.generate(ResultCode.SUCCESS,courseManageService.getCourseByMajorNumber(collegeNumber,majorNumber,pageNum,pageSize));

    }

    @RequestMapping(path = "/addCourse", method = RequestMethod.PUT)
    @ResponseBody
    public Result addCourse(@RequestBody CourseBean courseBean){
        CourseManageModel courseManageModel = new CourseManageModel();
        courseManageModel.setCourseNumber(courseBean.getCourseNumber());
        courseManageModel.setCourseName(courseBean.getCourseName());
        courseManageModel.setCourseNature(courseBean.getCourseNature());
        courseManageModel.setCourseCredit(courseBean.getCourseCredit());
        courseManageModel.setCourseHour(courseBean.getCourseHour());
        courseManageModel.setCollegeNumber(courseBean.getCollegeNumber());
        courseManageModel.setMajorNumber(courseBean.getMajorNumber());
        return ResultGenerator.generate(ResultCode.SUCCESS, courseManageService.addCourse(courseManageModel));
    }

    @RequestMapping(path = "/isExistsCourse", method = RequestMethod.POST)
    @ResponseBody
    public Result isExistsCourse(@RequestParam String courseNumber){
        return ResultGenerator.generate(ResultCode.SUCCESS, courseManageService.isExistsCourse(courseNumber));
    }

    @RequestMapping(path = "/deleteCourse", method = RequestMethod.DELETE)
    @ResponseBody
    public Result deleteCourse(@RequestParam String courseNumbers){
        return ResultGenerator.generate(ResultCode.SUCCESS, courseManageService.deleteCourse(courseNumbers));
    }

    @RequestMapping(path = "/updateCourse", method = RequestMethod.PUT)
    @ResponseBody
    public Result updateCourse(@RequestBody CourseBean courseBean){
        CourseManageModel courseManageModel = new CourseManageModel();
        courseManageModel.setCourseNumber(courseBean.getCourseNumber());
        courseManageModel.setCourseName(courseBean.getCourseName());
        courseManageModel.setCourseNature(courseBean.getCourseNature());
        courseManageModel.setCourseCredit(courseBean.getCourseCredit());
        courseManageModel.setCourseHour(courseBean.getCourseHour());
        courseManageModel.setCollegeNumber(courseBean.getCollegeNumber());
        courseManageModel.setMajorNumber(courseBean.getMajorNumber());
        courseManageModel.setOldCourseNumber(courseBean.getOldCourseNumber());

        return ResultGenerator.generate(ResultCode.SUCCESS, courseManageService.updateCourse(courseManageModel));
    }

    @RequestMapping(path = "/getMajorNumber", method = RequestMethod.POST)
    @ResponseBody
    public Result getMajorNumber(@RequestParam String courseNumber){
        return ResultGenerator.generate(ResultCode.SUCCESS, courseManageService.getMajorNumber(courseNumber));
    }
}
