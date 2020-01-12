package com.youngc.pipeline.service.login.impl;

import com.youngc.pipeline.exception.ServiceException;
import com.youngc.pipeline.mapper.login.AuthTokenMapper;
import com.youngc.pipeline.mapper.login.AuthUserMapper;
import com.youngc.pipeline.model.UserManagerModel;
import com.youngc.pipeline.result.ResultCode;
import com.youngc.pipeline.service.login.AuthService;
import com.youngc.pipeline.utils.BCryptUtil;
import com.youngc.pipeline.utils.EncryptUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 * @author liweiqiang
 */
@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private AuthUserMapper authUserMapper;

    @Autowired
    private AuthTokenMapper authTokenMapper;

    public int demo(){
        return authUserMapper.demo();
    }

    public Map login(String userName, String rawPassword) throws ServiceException {

        UserManagerModel  user = authUserMapper.getUserByUsername(userName);

        // login successfully
        if (BCryptUtil.checkpw(rawPassword, user.getPassword())) {

            // 重新登录 或者 首次登录 都会生成新的 token
            String token = EncryptUtil.encodeMD5(user.getUserId() + Calendar.getInstance().getTime().toString());

            // user already logged in
            if (!authTokenMapper.isTokenExistsById(user.getUserId()).equals(0)) {
                authTokenMapper.updateToken(user.getUserId(), token);
            } else {
                // 用户首次登陆
                authTokenMapper.insertNewToken(user.getUserId(), token);
            }
            Map<String, Object> result = new HashMap<String, Object>();
            result.put("token", token);
            result.put("userName", userName);
            result.put("realName", user.getRealName());
            result.put("roleIds",user.getRoleIds());
            result.put("droleIds",user.getDroleIds());
            return result;
        }

        throw new ServiceException(ResultCode.USER_OR_PASS_ERROR, new Throwable(ResultCode.USER_OR_PASS_ERROR.getMsg()));
    }


    public boolean updatePassword(String token, String oldPassword, String newPassword) {

        Long userId = authTokenMapper.getUserIdByToken(token);

        String hashedPasswd = authUserMapper.getHashedPasswd(userId);

        if (!BCryptUtil.checkpw(oldPassword, hashedPasswd)) {
            return false;
        }

        if (BCryptUtil.checkpw(newPassword, hashedPasswd)) {
            return false;
        }

        if (authUserMapper.updateUserPassword(userId, BCryptUtil.hashpw(newPassword, BCryptUtil.gensalt(12))) != 1) {
            return false;
        }

        return true;
    }


    public boolean logout(String token) {
        if (authTokenMapper.isTokenExists(token).equals(0)) {
            return false;
        }

        authTokenMapper.delete(token);
        return true;
    }

    public boolean isTokenExist(String token) {
        return !authTokenMapper.isTokenExists(token).equals(0);
    }

    public Long getUserIdByToken(String token) {
        return authTokenMapper.getUserIdByToken(token);
    }

    public int oldPassword(String userName,String oldPassword,int identity){
        if(identity==1){
            String studentNumber=userName;
            if (BCryptUtil.checkpw(oldPassword, authUserMapper.getStudentPassword(studentNumber))){
                return 1;
            }
            else
                return 2;
        }
        else if(identity==2){
            String teacherNumber=userName;
            if (BCryptUtil.checkpw(oldPassword, authUserMapper.getTeacherPassword(teacherNumber))){
                return 1;
            }
            else
                return 2;
        }
        else{
            String teacherSecretaryNumber=userName;
            if (BCryptUtil.checkpw(oldPassword, authUserMapper.getTeacherSecretaryPassword(teacherSecretaryNumber))){
                return 1;
            }
            else
                return 2;
        }



    }


    public boolean changePassword(String userName,String newPassword,int identity){
        String password=BCryptUtil.hashpw(newPassword, BCryptUtil.gensalt(12));

        if(identity==1){
            String studentNumber=userName;
            return authUserMapper.changeStudentPassword(studentNumber,password);
        }
        else if(identity==2){
            String teacherNumber=userName;
            return authUserMapper.changeTeacherPassword(teacherNumber,password);
        }
        else{
            String teacherSecretaryNumber=userName;
            return authUserMapper.changeTeacherSecretaryPassword(teacherSecretaryNumber,password);
        }

    }

}
