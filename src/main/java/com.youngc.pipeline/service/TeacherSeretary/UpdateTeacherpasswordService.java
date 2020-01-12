package com.youngc.pipeline.service.TeacherSeretary;

import java.util.Map;

public interface UpdateTeacherpasswordService {
    int selectteacheremail(String email);

    int selectstudentemail(String email);

    boolean updateteacherpassword(String email, String newpassword);

    boolean updatestudentpassword(String email, String newpassword);

    int sentEmail(String email,int change);

    int chackEmail(String word,String email);
}
