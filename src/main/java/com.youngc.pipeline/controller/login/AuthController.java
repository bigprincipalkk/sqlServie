package com.youngc.pipeline.controller.login;

import com.youngc.pipeline.result.Result;
import com.youngc.pipeline.result.ResultCode;
import com.youngc.pipeline.result.ResultGenerator;
import com.youngc.pipeline.service.login.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @RequestMapping(path = "/demo", method = RequestMethod.POST)
    @ResponseBody
    Result demo() {
    System.out.println(authService.demo());
        return ResultGenerator.generate(ResultCode.SUCCESS, authService.demo());

    }

    @RequestMapping(path = "", method = RequestMethod.POST)
    @ResponseBody
    Result login(String userName, String password) {

        if (userName == null || userName.equals("")) {

            return ResultGenerator.generate(ResultCode.FAIL);
        }

        if (password == null || password.equals("")) {
            return ResultGenerator.generate(ResultCode.FAIL);
        }

        return ResultGenerator.generate(ResultCode.SUCCESS, authService.login(userName, password));
    }


    @RequestMapping(path = "/logout", method = RequestMethod.GET)
    @ResponseBody
    Result logout(String token) {


        if (authService.logout(token)) {
            return ResultGenerator.generate(ResultCode.SUCCESS);
        }

        return ResultGenerator.generate(ResultCode.FAIL);
    }

    @RequestMapping(path = "/oldPasswordValidate", method = RequestMethod.POST)
    @ResponseBody
    Result oldPassword(@RequestParam String userName,@RequestParam String oldPassword,@RequestParam int identity){
        return ResultGenerator.generate(ResultCode.SUCCESS,authService.oldPassword(userName,oldPassword,identity));
    }

    @RequestMapping(path = "/changePassword", method = RequestMethod.POST)
    @ResponseBody
    Result changePassword(@RequestParam String userName,@RequestParam String newPassword,@RequestParam int identity){
        return ResultGenerator.generate(ResultCode.SUCCESS,authService.changePassword(userName,newPassword,identity));
    }
}
