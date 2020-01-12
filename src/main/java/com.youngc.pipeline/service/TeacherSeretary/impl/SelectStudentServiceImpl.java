package com.youngc.pipeline.service.TeacherSeretary.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import com.youngc.pipeline.mapper.TeacherSeretary.SelectStudentMapper;
import com.youngc.pipeline.model.ClassModel;
import com.youngc.pipeline.model.College;
import com.youngc.pipeline.model.StudentManagerModel;
import com.youngc.pipeline.model.Major;
import com.youngc.pipeline.service.TeacherSeretary.SelectStudentService;
import com.youngc.pipeline.utils.BCryptUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

@Service
public class SelectStudentServiceImpl implements SelectStudentService {

    @Autowired
    private SelectStudentMapper selectStudentMapper;

    public List<College> selectCollege(){
        return selectStudentMapper.getCollege();
    }

    public boolean readExcel(MultipartFile file){
        List<StudentManagerModel> data=new ArrayList<StudentManagerModel>();
        FileInputStream input=null;
        try{
            input = (FileInputStream)file.getInputStream();
            Workbook workBook = WorkbookFactory.create(input);
            int numberOfSheets = workBook.getNumberOfSheets();
            for (int s = 0; s < numberOfSheets; s++) { // sheet工作表
                Sheet sheetAt = workBook.getSheetAt(s);
                int rowsOfSheet = sheetAt.getPhysicalNumberOfRows(); // 获取当前Sheet的总列数
                for (int r = 1; r < rowsOfSheet; r++) { // 总行
                    Row row = sheetAt.getRow(r);
                    if (row == null) {
                        continue;
                    } else {
                        if(row.getCell(0) != null) {
                            StudentManagerModel studentManagerModel = new StudentManagerModel();
                            studentManagerModel.setStudentNumber(row.getCell(0) != null ? "'" + row.getCell(0).getStringCellValue() + "'" : null);
                            studentManagerModel.setStudentName(row.getCell(1) != null ? "'" + row.getCell(1).getStringCellValue() + "'" : null);
                            studentManagerModel.setSex(row.getCell(2) != null ? "'" + row.getCell(2).getStringCellValue() + "'" : null);
                            studentManagerModel.setEntranceYear(row.getCell(3) != null ? Long.parseLong(new java.text.DecimalFormat("0").format(row.getCell(3).getNumericCellValue())) : null);
                            studentManagerModel.setEmail(row.getCell(4) != null ? "'" + row.getCell(4).getStringCellValue() + "'" : null);
                            studentManagerModel.setGrade(row.getCell(5) != null ? Long.parseLong(new java.text.DecimalFormat("0").format(row.getCell(5).getNumericCellValue())) : null);
                            String studentNumber=studentManagerModel.getStudentNumber();
                            String password = studentNumber.substring(studentNumber.length()-5,studentNumber.length()-1);
                            studentManagerModel.setPassword(BCryptUtil.hashpw(password, BCryptUtil.gensalt(12)));
                            data.add(studentManagerModel);
                        }
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if (input!= null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        selectStudentMapper.addStudentExcel(data,Long.parseLong("1"),Long.parseLong("2"));
        return true;
    }


    public List<Major> selectMajor(String collegeNumber){
        List<Major> majors = selectStudentMapper.getMajor(collegeNumber);
        return majors;
    }

    public List<ClassModel> selectClass(String collegeNumber,String majorNumber) {
        String majorName=selectStudentMapper.getMajorName(collegeNumber,majorNumber);
        List<ClassModel> classes = selectStudentMapper.getClass(majorName);
        return classes;
    }

    public Page getStudent(String searchNumber, int pageNum, int pageSize){
        PageHelper.startPage(pageNum, pageSize);
        return (Page) selectStudentMapper.getStudent(searchNumber);
    }

    public boolean resetPassword(String studentNumber,String password){
        String pad=BCryptUtil.hashpw(password, BCryptUtil.gensalt(12));
        return selectStudentMapper.resetPassword(studentNumber,pad);
    }

    public int updateStudent(StudentManagerModel studentManagerModel){
        try {
            if (selectStudentMapper.getStudent(studentManagerModel.getStudentNumber()).size() == 0) {
                if (selectStudentMapper.updateStudent(studentManagerModel)){
                    return 1;
                }else{
                    return 2;
                }
            } else {
                return 0;
            }
        }catch (Exception e){
            return 2;
        }
    }

    public int deleteStudent(String deleteStudentNumbers){
        if(selectStudentMapper.isExistStudentInElectiveCourse(deleteStudentNumbers)==0){
            selectStudentMapper.deleteStudent(deleteStudentNumbers);
            return 1;
        }else {
            return 0;
        }
    }

    public boolean addStudentSingal(StudentManagerModel studentManagerModel){
        return selectStudentMapper.addStudentSingal(studentManagerModel);
    }

    public int isStudentNumberExists(String studentNumber){
        return selectStudentMapper.getStudent(studentNumber).size()!=0?1:0;
    }
}
