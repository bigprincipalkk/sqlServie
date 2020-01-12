package com.youngc.pipeline.controller.TeacherSecretary;

import com.youngc.pipeline.bean.auth.StudentBean;
import com.youngc.pipeline.model.StudentManagerModel;
import com.youngc.pipeline.result.Result;
import com.youngc.pipeline.result.ResultCode;
import com.youngc.pipeline.result.ResultGenerator;
import com.youngc.pipeline.service.TeacherSeretary.SelectStudentService;
import com.youngc.pipeline.utils.BCryptUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/TeacherSecretary")
public class SelectStudentController {

    @Autowired
    private SelectStudentService selectStudentService;

    @RequestMapping(path = "/getCollege", method = RequestMethod.POST)
    @ResponseBody
    public Result getCollege() {
        return ResultGenerator.generate(ResultCode.SUCCESS, selectStudentService.selectCollege());
    }

    @PostMapping(value ="/excelAddStudent")
    public Result excelAddStudent(@RequestParam MultipartFile file) {
        return ResultGenerator.generate(ResultCode.SUCCESS, selectStudentService.readExcel(file));
    }

    @RequestMapping(path = "/getMajor", method = RequestMethod.POST)
    @ResponseBody
    public Result getMajor(String collegeNumber){
        return  ResultGenerator.generate(ResultCode.SUCCESS, selectStudentService.selectMajor(collegeNumber));
    }

    @RequestMapping(path = "/getClass", method = RequestMethod.POST)
    @ResponseBody
    public Result getClass(String collegeNumber,String majorName){
        return ResultGenerator.generate(ResultCode.SUCCESS, selectStudentService.selectClass(collegeNumber,majorName));
    }

    @RequestMapping(path = "/search", method = RequestMethod.GET)
    public Result searchStudent(@RequestParam String searchNumber, @RequestParam int pageNum, @RequestParam int pageSize){
        return ResultGenerator.generate(selectStudentService.getStudent(searchNumber, pageNum, pageSize));
    }

    @RequestMapping(path = "/resetPassword", method = RequestMethod.POST)
    public Result resetPassword(@RequestParam String studentNumber, String password){
        return ResultGenerator.generate(ResultCode.SUCCESS,selectStudentService.resetPassword(studentNumber, password));
    }

    @RequestMapping(path = "/isStudentNumberExists", method = RequestMethod.POST)
    public Result isStudentNumberExists(@RequestParam String studentNumber){
        return ResultGenerator.generate(ResultCode.SUCCESS,selectStudentService.isStudentNumberExists(studentNumber));
    }

    @PutMapping(path = "/resetInformation")
    public Result resetInformation(@RequestBody StudentBean studentBean){
        StudentManagerModel studentManagerModel=new StudentManagerModel();
        studentManagerModel.setStudentNumber(studentBean.getStudentNumber());
        studentManagerModel.setEmail(studentBean.getEmail());
        studentManagerModel.setOldStudentNumber(studentBean.getOldStudentNumber());
        return ResultGenerator.generate(ResultCode.SUCCESS, selectStudentService.updateStudent(studentManagerModel));
    }

    @DeleteMapping("/deleteStudent")
    public Result deleteUser(@RequestParam String deleteStudentNumbers) {
        return ResultGenerator.generate(ResultCode.SUCCESS,selectStudentService.deleteStudent(deleteStudentNumbers));
    }

    @PostMapping(path = "/addStudentSingal")
    public Result addStudentSingal(@RequestBody StudentBean studentBean){
        StudentManagerModel studentManagerModel=new StudentManagerModel();
        studentManagerModel.setStudentNumber(studentBean.getStudentNumber());
        studentManagerModel.setEmail(studentBean.getEmail());
        studentManagerModel.setStudentName(studentBean.getStudentName());
        studentManagerModel.setSex(studentBean.getSex());
        String pas= BCryptUtil.hashpw(studentBean.getPassword(), BCryptUtil.gensalt(12));
        studentManagerModel.setPassword(pas);
        studentManagerModel.setEntranceYear(studentBean.getEntranceYear());
        studentManagerModel.setGrade(studentBean.getGrade());
        return ResultGenerator.generate(ResultCode.SUCCESS, selectStudentService.addStudentSingal(studentManagerModel));
    }
}
