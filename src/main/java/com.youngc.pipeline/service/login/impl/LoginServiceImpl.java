package com.youngc.pipeline.service.login.impl;

import com.youngc.pipeline.exception.ServiceException;
import com.youngc.pipeline.mapper.login.AuthTokenMapper;
import com.youngc.pipeline.mapper.login.AuthUserMapper;
import com.youngc.pipeline.mapper.login.LoginMapper;
import com.youngc.pipeline.mapper.login.StudentTokenMapper;
import com.youngc.pipeline.model.SecretaryManageModel;
import com.youngc.pipeline.model.StudentManagerModel;
import com.youngc.pipeline.model.TeacherManageModel;
import com.youngc.pipeline.model.UserManagerModel;
import com.youngc.pipeline.result.ResultCode;
import com.youngc.pipeline.service.login.LoginService;
import com.youngc.pipeline.utils.BCryptUtil;
import com.youngc.pipeline.utils.EncryptUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

@Service
public class LoginServiceImpl implements LoginService {
    @Autowired
    private AuthUserMapper authUserMapper;
    @Autowired
    private LoginMapper loginMapper;

    @Autowired
    private StudentTokenMapper studentTokenMapper;

    @Autowired
    private AuthTokenMapper authTokenMapper;


    public Map login(String username, String password, int identity) throws ServiceException {

        Map<String, Object> result = new HashMap<String, Object>();
        if (identity == 1) {
            StudentManagerModel stu = loginMapper.getStudentByNumber(username);

            // login successfully
            if (BCryptUtil.checkpw(password, stu.getPassword())) {
                // 重新登录 或者 首次登录 都会生成新的 token
                String token = EncryptUtil.encodeMD5(stu.getStudentNumber() + Calendar.getInstance().getTime().toString());

                // user already logged in
                if (!studentTokenMapper.isTokenExistsById(stu.getStudentNumber()).equals(0)) {
                    studentTokenMapper.updateToken(stu.getStudentNumber(), token);
                } else {
                    // 用户首次登陆
                    studentTokenMapper.insertNewToken(stu.getStudentNumber(), token);
                }
                result.put("token", token);
                result.put("userName", username);
                result.put("roleIds", identity);
                result.put("realName",stu.getStudentName());

                return result;
            }
        } else if (identity == 2) {
            TeacherManageModel tea = loginMapper.getTeacherByNumber(username);
            // login successfully
            if (BCryptUtil.checkpw(password, tea.getPassword())) {
                // 重新登录 或者 首次登录 都会生成新的 token
                String token = EncryptUtil.encodeMD5(tea.getTeacherNumber() + Calendar.getInstance().getTime().toString());

                // user already logged in
                if (!studentTokenMapper.isTokenExistsById(tea.getTeacherNumber()).equals(0)) {
                    studentTokenMapper.updateToken(tea.getTeacherNumber(), token);
                } else {
                    // 用户首次登陆
                    studentTokenMapper.insertNewToken(tea.getTeacherNumber(), token);
                }
                result.put("token", token);
                result.put("userName", username);
                result.put("roleIds", identity);
                result.put("realName",tea.getTeacherName());
                return result;
            }

        }
//        else if (identity == 3) {
//            SecretaryManageModel sec = loginMapper.getSecretaryByNumber(username);
//
//            if (BCryptUtil.checkpw(password, sec.getPassword())) {
//                // 重新登录 或者 首次登录 都会生成新的 token
//                String token = EncryptUtil.encodeMD5(sec.getSecretaryNumber() + Calendar.getInstance().getTime().toString());
//
//                // user already logged in
//                if (!studentTokenMapper.isTokenExistsById(sec.getSecretaryNumber()).equals(0)) {
//                    studentTokenMapper.updateToken(sec.getSecretaryNumber(), token);
//                } else {
//                    // 用户首次登陆
//                    studentTokenMapper.insertNewToken(sec.getSecretaryNumber(), token);
//                }
//
//                result.put("token", token);
//                result.put("userName", username);
//               result.put("roleIds", identity);

//            }
//
//            throw new ServiceException(ResultCode.USER_OR_PASS_ERROR, new Throwable(ResultCode.USER_OR_PASS_ERROR.getMsg()));
//
//
//        }
        else{
            UserManagerModel user = authUserMapper.getUserByUsername(username);
            // login successfully
            if (BCryptUtil.checkpw(password, user.getPassword())) {

                // 重新登录 或者 首次登录 都会生成新的 token
                String token = EncryptUtil.encodeMD5(user.getUserId() + Calendar.getInstance().getTime().toString());

                // user already logged in
                if (!authTokenMapper.isTokenExistsById(user.getUserId()).equals(0)) {
                    authTokenMapper.updateToken(user.getUserId(), token);
                } else {
                    // 用户首次登陆
                    authTokenMapper.insertNewToken(user.getUserId(), token);
                }
                result.put("token", token);
                result.put("userName", username);
                result.put("realName", user.getRealName());
                result.put("roleIds",user.getRoleIds());
                result.put("droleIds",user.getDroleIds());
                return result;
            }

        }

        throw new ServiceException(ResultCode.USER_OR_PASS_ERROR, new Throwable(ResultCode.USER_OR_PASS_ERROR.getMsg()));
    }


}