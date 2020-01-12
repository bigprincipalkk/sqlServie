package com.youngc.pipeline.service.Student.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.youngc.pipeline.mapper.Student.SearchCourseMapper;
import com.youngc.pipeline.model.CourseTeacherModel;
import com.youngc.pipeline.model.StudentCourseModel;
import com.youngc.pipeline.service.Student.SearchCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.List;

@Service
public class SearchCourseImpl implements SearchCourseService{

    @Autowired
    SearchCourseMapper searchCourseMapper;

    @Autowired
    private JavaMailSender javaMailSender;

    @Value("996568235@qq.com")
    private String Sender;

    public Page search(String studentNumber,int year, int pageNum, int pageSize){
        //List<CourseTeacherModel> elective=searchCourseMapper.searchElective(studentNumber,year);
        String collegeNumber=studentNumber.substring(2,4);
        String majorNumber=studentNumber.substring(4,6);
        String class1=studentNumber.substring(7,8);
        String yearStudent=studentNumber.substring(0,2);
        String majorName=searchCourseMapper.getMajorName(collegeNumber,majorNumber);
        String className=majorName+yearStudent+class1;
        List<CourseTeacherModel> obligatory=searchCourseMapper.searchElective(className,studentNumber,year);
        //obligatory.addAll(elective);

        PageHelper.startPage(pageNum,pageSize);
        return (Page) searchCourseMapper.searchElective(className,studentNumber,year);
    }

    public int deleteCourse(String courseNumber,String studentNumber,String teacherNumber,int year){
        if(searchCourseMapper.searchCourse(courseNumber,studentNumber,teacherNumber)==0) {
            return 1;
        }else {
            String courseId=searchCourseMapper.getCourseID(courseNumber,studentNumber,teacherNumber,year);
            searchCourseMapper.deleteCourse(courseNumber,studentNumber,courseId);
            return 0;
        }
    }

    public Page selectCourse(String collegeNumber,String majorNumber,int pageNum,int pageSize){
        Calendar cale =  Calendar.getInstance();
        int year=cale.get(Calendar.YEAR);
        if(majorNumber.equals("")||majorNumber==null){
            PageHelper.startPage(pageNum, pageSize);
            return (Page) searchCourseMapper.selectCourseByCollege(collegeNumber, year);
        }else {
            PageHelper.startPage(pageNum, pageSize);
            return (Page) searchCourseMapper.selectCourse(collegeNumber, majorNumber, year);
        }
    }

    public int addCourse(String courseNumber,String teacherNumber,String studentNumber){
        Calendar cale =  Calendar.getInstance();
        int year=cale.get(Calendar.YEAR);
        List<StudentCourseModel> studentCourseModels=searchCourseMapper.getStudent(courseNumber,teacherNumber,year);
        int electiveId=searchCourseMapper.getID(courseNumber,teacherNumber,year);
        if(searchCourseMapper.searchIsExist(studentNumber,electiveId)>0){
            return 2;
        }
        String creditString=searchCourseMapper.getCredit(studentNumber);
        float credit;
        if(creditString==null){
            credit=0;
        }else {
             credit= Float.parseFloat(creditString);
        }
        if(studentCourseModels.size()<2){
            searchCourseMapper.insertStudent(electiveId,studentNumber);
        }else{
            String student=studentNumber;
            for(int i=0;i<studentCourseModels.size();i++){
                if(studentCourseModels.get(i).getStudentNumber().substring(0,2).compareTo(student.substring(0,2)) <0){

                }else if(studentCourseModels.get(i).getStudentNumber().substring(0,2).compareTo(student.substring(0,2))==0){
                    if(studentCourseModels.get(i).getSumCredit()>credit){
                        student=studentCourseModels.get(i).getStudentNumber();
                    }else{

                    }
                }else{
                    student=studentCourseModels.get(i).getStudentNumber();
                }
            }
            if(student.equals(studentNumber)){
                return 0;
            }else{
                String email=searchCourseMapper.getStudentEmail(student);
                SimpleMailMessage message = new SimpleMailMessage();
                message.setFrom(Sender);
                message.setTo(email);
                message.setSubject("【学了么选课系统】选修退课");
                message.setText("尊敬的学了么用户，您好：\n 您选修的课程，因非主观因素造成选课失败。" +
                        "\n");
                javaMailSender.send(message);
                searchCourseMapper.deleteStudent(electiveId,student);
                searchCourseMapper.insertStudent(electiveId,studentNumber);

                return 1;
            }
        }
        return 1;
    }
}
