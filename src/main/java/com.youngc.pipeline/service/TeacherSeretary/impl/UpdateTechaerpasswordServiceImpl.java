package com.youngc.pipeline.service.TeacherSeretary.impl;

import com.youngc.pipeline.exception.ServiceException;
import com.youngc.pipeline.mapper.TeacherSeretary.UpdateTeacherpasswordMapper;
import com.youngc.pipeline.mapper.login.AuthUserMapper;
import com.youngc.pipeline.service.TeacherSeretary.UpdateTeacherpasswordService;
import com.youngc.pipeline.utils.BCryptUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class UpdateTechaerpasswordServiceImpl implements UpdateTeacherpasswordService {

    Map nowwordMap =new HashMap();

    @Autowired
    private UpdateTeacherpasswordMapper updateTeacherpasswordMapper;

    @Autowired
     private JavaMailSender javaMailSender;
    @Value("996568235@qq.com")
    private String Sender;

    public boolean updateteacherpassword(String email, String newpassword) {
        String pwd = BCryptUtil.hashpw(newpassword, BCryptUtil.gensalt(12));
        return updateTeacherpasswordMapper.updateteacherpassword(email, pwd);
    }

    public boolean updatestudentpassword(String email, String newpassword) {
        String pwd = BCryptUtil.hashpw(newpassword, BCryptUtil.gensalt(12));
        return updateTeacherpasswordMapper.updatestudentpassword(email, pwd);
    }

    public int sentEmail(String email, int change) {
        if (change == 1 && updateTeacherpasswordMapper.selectstudentemail(email) == 0) {

            return -2;
        }
        if (change == 2 && updateTeacherpasswordMapper.selectteacheremail(email) == 0) {

            return -2;
        } else {
            String nowword =(((int) ((Math.random()*9+1)*100000))+"");
            nowwordMap.put(email,nowword);
             SimpleMailMessage message = new SimpleMailMessage();
             message.setFrom(Sender);
             message.setTo(email);
             message.setSubject("【学了么选课系统】找回密码");
             message.setText("尊敬的学了么用户，您好：\n 您正在更改您的客户端密码，请在24小时内完成重置，24小时后邮件失效，您将需要重新提交密码找回请求，请勿将验证码告知他人" +
                     "\n"+"验证码:"+ nowword);
             javaMailSender.send(message);

            return 1;
        }
    }
    public int chackEmail(String word,String email){
        if (word.equals(nowwordMap.get(email)) )
        {
            nowwordMap.remove(email);
            return 6;
        }
        else  {
            nowwordMap.remove(email);
            return 7;
        }
    }

        public int selectteacheremail (String email){
            return updateTeacherpasswordMapper.selectteacheremail(email);
        }
        public int selectstudentemail (String email){
            return updateTeacherpasswordMapper.selectstudentemail(email);

        }

    }

