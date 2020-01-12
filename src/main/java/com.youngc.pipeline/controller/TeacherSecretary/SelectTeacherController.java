package com.youngc.pipeline.controller.TeacherSecretary;

import com.youngc.pipeline.bean.auth.TeacherBean;
import com.youngc.pipeline.bean.param.UserBean;
import com.youngc.pipeline.model.TeacherManageModel;
import com.youngc.pipeline.model.UserManagerModel;
import com.youngc.pipeline.result.Result;
import com.youngc.pipeline.result.ResultCode;
import com.youngc.pipeline.result.ResultGenerator;
import com.youngc.pipeline.service.TeacherSeretary.SelectTeacherService;
import com.youngc.pipeline.service.system.UserManagerService;
import com.youngc.pipeline.utils.BCryptUtil;
import com.youngc.pipeline.utils.RequestContextHolderUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/TeacherSecretary")
public class SelectTeacherController {
    @Autowired
    private SelectTeacherService selectTeacherService;

    // 获得教师信息
    @RequestMapping(path = "/Teacher", method = RequestMethod.GET)
    @ResponseBody
    public Result getTeacher(@RequestParam String selectmajor, @RequestParam String selectcollege, @RequestParam int pageNum, @RequestParam int pageSize) {
        if(selectmajor==""||selectmajor==null)
            return ResultGenerator.generate(selectTeacherService.getcollegeTeacher(selectcollege, pageNum, pageSize));
        else
        return ResultGenerator.generate(selectTeacherService.getmajorTeacher(selectmajor, selectcollege, pageNum, pageSize));
    }

    //添加教师
    @RequestMapping(path = "/TeacherAdd", method = RequestMethod.POST)
    @ResponseBody
    public Result postTeacher(@RequestBody TeacherBean teacherBean) {
        TeacherManageModel teachermanagemodel = new TeacherManageModel();
        teachermanagemodel.setTeacherNumber(teacherBean.getTeacherNumber());
        teachermanagemodel.setTeacherName(teacherBean.getTeacherName());
        teachermanagemodel.setSelectCollege(teacherBean.getSelectcollege());
        teachermanagemodel.setSelectMajor(teacherBean.getSelectmajor());
        String pad= BCryptUtil.hashpw(teacherBean.getPassword(), BCryptUtil.gensalt(12));
        teachermanagemodel.setPassword(pad);
        teachermanagemodel.setHiredate(teacherBean.getHiredate());
        teachermanagemodel.setProf(teacherBean.getProf());
        teachermanagemodel.setEmail(teacherBean.getEmail());


        return ResultGenerator.generate(ResultCode.SUCCESS,
                selectTeacherService.addTeacher(teachermanagemodel));
    }

    //查询教师教工号
    @RequestMapping(path = "/Teacherinfo", method = RequestMethod.POST)
    @ResponseBody
    public Result getTeacherNumber(@RequestParam String teacherNumber) {
        return ResultGenerator.generate(ResultCode.SUCCESS, selectTeacherService.getTeacherNumber(teacherNumber));
    }

    //修改教师信息
    @RequestMapping(path = "/Teacherupdate", method = RequestMethod.PUT)
    @ResponseBody
    public Result putTeacher(@RequestBody TeacherBean teacherBean) {

        TeacherManageModel teachermanagemodel = new TeacherManageModel();
        teachermanagemodel.setOtherTeacherNumber(teacherBean.getOtherteacherNumber());
        teachermanagemodel.setTeacherName(teacherBean.getTeacherName());
        teachermanagemodel.setSelectCollege(teacherBean.getSelectcollege());
        teachermanagemodel.setSelectMajor(teacherBean.getSelectmajor());
        teachermanagemodel.setHiredate(teacherBean.getHiredate());
        teachermanagemodel.setProf(teacherBean.getProf());
        teachermanagemodel.setEmail(teacherBean.getEmail());
        return ResultGenerator.generate(ResultCode.SUCCESS, selectTeacherService.updateTeacher(teachermanagemodel));
    }

    //删除教师
    @DeleteMapping(path = "/Teacherdelete")
    public Result deleteTeacherList(@RequestParam("teacherNumbers") String teacherNumbers) {

        return ResultGenerator.generate(ResultCode.SUCCESS,selectTeacherService.deleteTeacherList(teacherNumbers));
    }

    //重置密码
    @RequestMapping(path = "/resetpassword", method = RequestMethod.POST)
    @ResponseBody
    public Result resetPassword(@RequestParam String teacherNumber){
        return ResultGenerator.generate(ResultCode.SUCCESS,selectTeacherService.resetPassword(teacherNumber));
    }

    //返回专业号
    @RequestMapping(path = "/Teacherupdatecheck", method = RequestMethod.POST)
    @ResponseBody
    public Result getTeacherMajor(@RequestParam String otherTeacherNumber,@RequestParam String collegeNumber){
        return ResultGenerator.generate(ResultCode.SUCCESS,selectTeacherService.getTeacherMajor(otherTeacherNumber,collegeNumber));
    }

    //返回邮箱
    @RequestMapping(path = "/Emailinfo", method = RequestMethod.POST)
    @ResponseBody
    public Result getEmail(@RequestParam String email){
        return ResultGenerator.generate(ResultCode.SUCCESS,selectTeacherService.getEmail(email));
    }

    @RequestMapping(path = "/emailValidateTeacherNumber", method = RequestMethod.POST)
    @ResponseBody
    public Result emailValidateTeacherNumber(@RequestParam String email){
        return ResultGenerator.generate(ResultCode.SUCCESS,selectTeacherService.emailValidateTeacherNumber(email));
    }
}
