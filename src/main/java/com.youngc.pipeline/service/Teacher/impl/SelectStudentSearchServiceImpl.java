package com.youngc.pipeline.service.Teacher.impl;

import com.youngc.pipeline.mapper.teacher.SelectStudentSearchMapper;

import com.youngc.pipeline.model.ClassManageModel;
import com.youngc.pipeline.model.CourseManageModel;
import com.youngc.pipeline.model.StudentManagerModel;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.youngc.pipeline.service.Teacher.SelectStudentSearchService;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.*;

@Service
public class SelectStudentSearchServiceImpl implements SelectStudentSearchService{
    @Autowired
    private  SelectStudentSearchMapper selectStudentSearchMapper ;


    public List<CourseManageModel> getCourse(String teacherNumber, int year) {
        return selectStudentSearchMapper.getCourse(teacherNumber,year);
    }



    public List<String> getXuehao(String teacherNumber, String courseNumber,int year){
        IdentityHashMap<String,String> className =new IdentityHashMap<String,String>();
        List<ClassManageModel> classManageModels = selectStudentSearchMapper.getClassName(teacherNumber,courseNumber,year);
        IdentityHashMap<String,String> className2 =new IdentityHashMap<String,String>();
        //将班级名后三位切割出来
        //跟班级名放在可重复map里
        for(int i = 0;i < classManageModels.size();i++){
            String name = classManageModels.get(i).getClassName();
            String newName =  name.substring(0,name.length()-3)+"";
            String nameId = name.substring(name.length()-3,name.length());
            className.put(newName,nameId);
        }

        //取到班级名集合
        Set<String> keys = className.keySet();
        StringBuffer majorName = new StringBuffer();
        for(String value : keys){
            majorName.append("'").append(value).append("'").append(",");
        }
        String newMajorName = majorName.substring(0,majorName.length()-1);

        //拿到学院号和专业号
        List<Map<String,String>> majorAndCollege = selectStudentSearchMapper.getXueHao(newMajorName);
        Map<String,String> newMajorAndCollege = new HashMap<String, String>();
        for (int i = 0;i< majorAndCollege.size();i++){
            newMajorAndCollege.put(majorAndCollege.get(i).get("major_name"),majorAndCollege.get(i).get("CONCAT(college_number,major_number)"));
        }

        //把几个拼起来  除了后两位
        for(int i = 0;i < classManageModels.size();i++){
            String name = classManageModels.get(i).getClassName();
            String newName =  name.substring(0,name.length()-3)+"";
            String nameId = name.substring(name.length()-3,name.length()-1);
            String nameId2 = name.substring(name.length()-1);
            String nameId3 = nameId+newMajorAndCollege.get(newName)+"0"+nameId2;
            className2.put(newName,nameId3);
        }

        List<String> newXueHao = new ArrayList<String>();

        for (String key:className2.keySet()){
            newXueHao.add(className2.get(key));
        }
        return newXueHao;
    }

    public Page selectStudent(String teacherNumber, String courseNumber, int year,int pageNum,int pageSize) {


        List<String> newXueHao =getXuehao(teacherNumber,courseNumber,year);
        Long demo1 =new Long("123");
        Long demo2=new Long("123");

        PageHelper.startPage(pageNum,pageSize);
        return (Page) selectStudentSearchMapper.selectStudentName(newXueHao,demo1,demo2);
    }

    public String downloadFileInfo(HttpServletRequest request, HttpServletResponse response,String teacherNumber,String courseNumber, int year){

        List<String> newXueHao1 =getXuehao(teacherNumber,courseNumber,year);
        Long demo3 =new Long("123");
        Long demo4=new Long("123");
        List<StudentManagerModel> studentManagerModels = selectStudentSearchMapper.selectStudentName(newXueHao1,demo3,demo4);
        Workbook wb=new XSSFWorkbook();
        Sheet sheet=wb.createSheet();
        Row headRow=sheet.createRow(0);
        headRow.createCell(0).setCellValue("学号");
        headRow.createCell(1).setCellValue("姓名");
        headRow.createCell(2).setCellValue("年级");
        for(int i=0;i<studentManagerModels.size();i++){
            Row dataRow=sheet.createRow(i+1);
            dataRow.createCell(0).setCellValue(studentManagerModels.get(i).getStudentNumber());
            dataRow.createCell(1).setCellValue(studentManagerModels.get(i).getStudentName());
            dataRow.createCell(2).setCellValue(studentManagerModels.get(i).getGrade());

        }
        try {
            response.setHeader("Content-type", "application/vnd.ms-excel;charset=utf-8");
            response.setHeader("Content-disposition", "attachment;filename=" + URLEncoder.encode("选课学生.xlsx", "UTF-8"));
            OutputStream out=response.getOutputStream();
            wb.write(out);
        }catch (Exception e){
            return "导出失败";
        }
        return "导出成功";
    }

    public Page electiveSearch(String teacherNumber, String courseNumber, int year, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        return (Page)selectStudentSearchMapper.electiveSearch(teacherNumber,courseNumber,year);
    }

    public List<CourseManageModel> getCourseElective(String teacherNumber, int year) {
        return selectStudentSearchMapper.getCourseElective(teacherNumber,year);
    }

    public List<StudentManagerModel> getStudentNumbers(List stuNum,Long demo1,Long demo2){
        return selectStudentSearchMapper.selectStudentName(stuNum,demo1,demo2);
    }
    public Page getScores(String teacherNumber, String courseNumber, int year, int pageNum, int pageSize) {
        List<String> newXueHao =getXuehao(teacherNumber,courseNumber,year);
        Long demo1 =new Long("123");
        Long demo2=new Long("123");
        List<StudentManagerModel> studentNumbers = new ArrayList<StudentManagerModel>();
        studentNumbers = getStudentNumbers(newXueHao,demo1,demo2);
        List<String> newStudentNumbers = new ArrayList<String>();
        for (int i=0;i<studentNumbers.size();i++){
            newStudentNumbers.add(studentNumbers.get(i).getStudentNumber());
        }
        StringBuffer stuNums = new StringBuffer();
        stuNums.append("'");
        for(int i =0;i<newStudentNumbers.size();i++){
            stuNums.append(newStudentNumbers.get(i));
            if(i!=newStudentNumbers.size()-1){
                stuNums.append("','");
            }
        }
        stuNums.append("'");
        String newStuNums = stuNums.toString();
        System.out.println(newStuNums);
        PageHelper.startPage(pageNum,pageSize);
        return  (Page) selectStudentSearchMapper.getScores(teacherNumber,courseNumber,year,newStuNums);
    }

    public int updateScore(String teacherNumber, String courseNumber, int year, float score) {
        return selectStudentSearchMapper.updateScore(teacherNumber,courseNumber,year,score);
    }
}
