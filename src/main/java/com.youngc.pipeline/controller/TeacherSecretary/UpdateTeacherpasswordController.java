package com.youngc.pipeline.controller.TeacherSecretary;

import com.youngc.pipeline.result.Result;
import com.youngc.pipeline.result.ResultCode;
import com.youngc.pipeline.result.ResultGenerator;
import com.youngc.pipeline.service.TeacherSeretary.UpdateTeacherpasswordService;
import com.youngc.pipeline.service.login.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/update")
public class UpdateTeacherpasswordController {

    @Autowired
    private UpdateTeacherpasswordService updateTeacherpasswordService;


    @RequestMapping(path = "/updateteacherpassword", method = RequestMethod.POST)
    @ResponseBody
    public Result updateteacherpassword(@RequestParam String email, @RequestParam String newpassword, @RequestParam int change) {

        if (change == 2) {

            if (email == null || email.equals("")) {

                return ResultGenerator.generate(ResultCode.FAIL);
            }
            if (email != null) {

            }

            if (newpassword == null || newpassword.equals("")) {
                return ResultGenerator.generate(ResultCode.FAIL);
            }

            return ResultGenerator.generate(ResultCode.SUCCESS, updateTeacherpasswordService.updateteacherpassword(email, newpassword));
        } else {
            if (email == null || email.equals("")) {

                return ResultGenerator.generate(ResultCode.FAIL);
            }



            if (newpassword == null || newpassword.equals("")) {
                return ResultGenerator.generate(ResultCode.FAIL);
            }

            return ResultGenerator.generate(ResultCode.SUCCESS, updateTeacherpasswordService.updatestudentpassword(email, newpassword));

        }
    }


    @RequestMapping(path = "/sendemail", method = RequestMethod.POST)
    @ResponseBody
    public Result sendemail(@RequestParam String email,@RequestParam int change){

        return  ResultGenerator.generate(ResultCode.SUCCESS, updateTeacherpasswordService.sentEmail(email,change));
        }


    @RequestMapping(path = "/chackemail", method = RequestMethod.POST)
    @ResponseBody
    public Result chackemail(@RequestParam String word,@RequestParam String email){

        return ResultGenerator.generate(ResultCode.SUCCESS,updateTeacherpasswordService.chackEmail(word,email));

    }
}