package com.youngc.pipeline.controller.TeacherSecretary;

import com.youngc.pipeline.bean.auth.MajorBean;
import com.youngc.pipeline.model.Major;
import com.youngc.pipeline.result.Result;
import com.youngc.pipeline.result.ResultCode;
import com.youngc.pipeline.result.ResultGenerator;
import com.youngc.pipeline.service.TeacherSeretary.SelectMajorService;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/TeacherSecretary")
public class SelectMajorController {
    @Autowired
    private SelectMajorService selectMajorService;

    @RequestMapping(path = "/ProfessionalManage", method = RequestMethod.GET)
    @ResponseBody
    public Result getMajors(@RequestParam String collegeNumber,@RequestParam int pageNum,@RequestParam int pageSize){
        return ResultGenerator.generate(selectMajorService.getMajors(collegeNumber,pageNum,pageSize));
    }

    @RequestMapping(path = "/addMajor", method = RequestMethod.PUT)
    @ResponseBody
    public Result addMajor(@RequestBody MajorBean majorBean){
        Major major = new Major();
        major.setCollegeName(majorBean.getCollegeName());
        major.setCollegeNumber(majorBean.getCollegeNumber());
        major.setMajorName(majorBean.getMajorName());
        major.setMajorNumber(majorBean.getMajorNumber());
        major.setOpenYear(majorBean.getOpenYear());
        return ResultGenerator.generate(ResultCode.SUCCESS, selectMajorService.addMajor(major));
    }

    @RequestMapping(path = "/updateMajor", method = RequestMethod.PUT)
    @ResponseBody
    public Result updateMajor(@RequestBody MajorBean majorBean){
        Major major = new Major();
        major.setCollegeName(majorBean.getCollegeName());
        major.setCollegeNumber(majorBean.getCollegeNumber());
        major.setMajorName(majorBean.getMajorName());
        major.setMajorNumber(majorBean.getMajorNumber());
        major.setOpenYear(majorBean.getOpenYear());
        major.setOldMajorNumber(majorBean.getOldMajorNumber());
        System.out.println(major.getMajorNumber()+"                 "+major.getOldMajorNumber());
        return ResultGenerator.generate(ResultCode.SUCCESS, selectMajorService.updateMajor(major));
    }

    @DeleteMapping("/{deleteMajor}")
    public Result deleteMajor(@RequestParam("collegeNumber") String collegeNumber,@RequestParam("majorNumbers") String majorNumbers){
        System.out.println(collegeNumber+"                 "+majorNumbers);
        return ResultGenerator.generate(ResultCode.SUCCESS, selectMajorService.deleteMajor(collegeNumber,majorNumbers));
    }
}
