package com.youngc.pipeline.service.TeacherSeretary;

import com.github.pagehelper.Page;
import com.youngc.pipeline.model.ClassModel;
import com.youngc.pipeline.model.College;
import com.youngc.pipeline.model.StudentManagerModel;
import com.youngc.pipeline.result.Result;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.RequestParam;
import com.youngc.pipeline.model.Major;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface SelectStudentService {
    List<College> selectCollege();

    boolean readExcel(MultipartFile file);

    List<Major> selectMajor(String collegeNumber);

    List<ClassModel> selectClass(String collegeNumber,String majorName);

    Page getStudent(String searchNumber,int pageNum,int pageSize);

    boolean resetPassword(String studentNumber,String password);

    int updateStudent(StudentManagerModel studentManagerModel);

    int deleteStudent(String deleteStudentNumbers);

    boolean addStudentSingal(StudentManagerModel studentManagerModel);

    int isStudentNumberExists(String studentNumber);
}
