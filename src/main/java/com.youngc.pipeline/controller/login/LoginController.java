package com.youngc.pipeline.controller.login;

import com.youngc.pipeline.result.Result;
import com.youngc.pipeline.result.ResultCode;
import com.youngc.pipeline.result.ResultGenerator;
import com.youngc.pipeline.service.login.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class LoginController {
    @Autowired
    private LoginService loginService;

    @RequestMapping(path = "/login", method = RequestMethod.POST)
    @ResponseBody
    Result login(String userName, String password, int identity) {

        if (userName == null || userName.equals("")) {

            return ResultGenerator.generate(ResultCode.FAIL);
        }

        if (password == null || password.equals("")) {
            return ResultGenerator.generate(ResultCode.FAIL);
        }

        return ResultGenerator.generate(ResultCode.SUCCESS, loginService.login(userName, password,identity));
    }
}
