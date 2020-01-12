package com.youngc.pipeline.sqlProvider.system;


import com.youngc.pipeline.model.StudentManagerModel;


import java.text.MessageFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

public class  SystemSqlProvider {

    public String selectStudentName(Map map){
        List<String> xueHao = (List<String>) map.get("xueHao");
        Long demo1 = (Long)map.get("demo1");
        Long demo2 = (Long)map.get("demo2");
        System.out.println("123");

        StringBuffer sql = new StringBuffer();
        for (int i = 0;i< xueHao.size();i++){
            String demo = xueHao.get(i)+"__";
            xueHao.set(i,demo);
        }

        sql.append("SELECT student_name,student_number,grade FROM Student WHERE ");
        for (int i = 0;i<xueHao.size();i++){
            if(i==0){
                sql.append("student_number LIKE ");
            }else if(i <xueHao.size()&&i>0){
                sql.append(" OR student_number LIKE ");
            }
            sql.append("'").append(xueHao.get(i)).append("'");
        }



        return sql.toString();
    }
    public String addTeacher(Map map){
        String selectTeachers=(String) map.get("selectTeachers") ;
        String courseNumber=(String) map.get("courseNumber") ;
        Long demo=(Long) map.get("demo");
        String[] teachers=selectTeachers.split(",");
        Calendar cale =  Calendar.getInstance();
        int year=cale.get(Calendar.YEAR);
        StringBuilder sqlBuilder = new StringBuilder("INSERT INTO Elective_course(teacher_number,year,course_number) VALUES ");
        String template="(%s,%d,%s)";
        for(int i=0;i<teachers.length;i++){
            sqlBuilder.append(String.format(template,"'"+teachers[i]+"'",
                    year,
                    "'"+courseNumber+"'"
                    ));
            if (i < teachers.length - 1) {
                sqlBuilder.append(",");
            }
        }
        return sqlBuilder.toString();
    }

    public String addCourse(Map<String, Object> map){
        Long demo = (Long) map.get("demo") ;
        List<Map<String, String>> number = (List<Map<String, String>>)map.get("number") ;
        int[] demo1 = (int[]) map.get("demo1");
        Map<String,String> numberMap=number.get(0);
        String[] courseIds=numberMap.get("addCourseIds").split(",");
        //int grade=Integer.valueOf(numberMap.get("grade"));
        //int term=Integer.valueOf(numberMap.get("term"));
        int grade=demo1[0];
        int term=demo1[1];
        String majorNumber=numberMap.get("majorNumber");
        String collegeNumber=numberMap.get("collegeNumber");
        StringBuilder sqlBuilder = new StringBuilder("INSERT INTO Obligatory_teachPlan(course_number, major_number, College_number, grade, term) VALUES ");
        String template="(%s,%s,%s,%d,%d)";
        for(int i=0;i<courseIds.length;i++){
            sqlBuilder.append(String.format(template,"'"+courseIds[i]+"'",
                    "'"+majorNumber+"'",
                    "'"+collegeNumber+"'",
                    grade,
                    term));
            if (i < courseIds.length - 1) {
                sqlBuilder.append(",");
            }
        }
        return sqlBuilder.toString();
    }

    //批量删除学生
    public String batStuDel(Map map){
        String collegeNumber = (String)map.get("arg0") ;
        String majorNumber = (String ) map.get("arg1");
        Long droleId = (Long) map.get("arg2");

        StringBuffer sb = new StringBuffer();
        String[] majorNumbers = majorNumber.split(",");
        //SELECT语句的拼成
        sb.append("SELECT COUNT(student_number) FROM Student WHERE student_number REGEXP '[0-9]{2}[");
        sb.append(collegeNumber).append("](");
        for(int i = 0;i< majorNumbers.length;i++) {
            sb.append(majorNumbers[i]);
            if (i !=(majorNumbers.length - 1)) {
                sb.append("|");
            }
        }
        sb.append(")'");
        System.out.println(sb.toString());
        return sb.toString();
    }

    public String putDataUnit(Map<String, Object> para) {

        List<String> DataUnitId = (List<String>) para.get("arg0");
        Long userId = (Long) para.get("arg1");
        Long droleId = (Long) para.get("arg2");

        StringBuilder builder = new StringBuilder("INSERT INTO sys_data_role_unit (drole_id,unit_id,status,add_person,add_time,last_person,last_time) VALUES ");

        MessageFormat messageFormat = new MessageFormat("({0}, {1}, 1, {2}, now(), {3}, now())");

        for (int i = 0; i < DataUnitId.size(); i++) {
            builder.append(messageFormat.format(new Object[]{droleId, DataUnitId.get(i), userId, userId}));
            if (i < DataUnitId.size() - 1) {
                builder.append(",");
            }
        }
        return builder.toString();
    }


    public String insertRoleModule(Map<String, Object> para) {

        List<String> moduleIds = (List<String>) para.get("arg0");
        Long roleId = (Long) para.get("arg1");
        Long userId = (Long) para.get("arg2");

        StringBuilder builder = new StringBuilder("INSERT INTO sys_role_module (role_id, module_id, status, add_person, add_time, last_person, last_time) VALUES ");

        MessageFormat messageFormat = new MessageFormat("({0}, {1}, 1, {2}, now(), {3}, now())");

        for (int i = 0; i < moduleIds.size(); i++) {
            builder.append(messageFormat.format(new Object[]{roleId, moduleIds.get(i), userId, userId}));
            if (i < moduleIds.size() - 1) {
                builder.append(",");
            }
        }
        return builder.toString();
    }


    public String insertUserRole(Map<String, Object> para) {

        List<String> roleIds = (List<String>) para.get("arg0");
        Long userId = (Long) para.get("arg1");//需要修改的用户id
        Long personId = (Long) para.get("arg2");//操作用户的id

        StringBuilder builder = new StringBuilder("INSERT INTO sys_user_role (user_id, role_id, add_person, add_time, last_person, last_time) VALUES ");

        MessageFormat messageFormat = new MessageFormat("({0}, {1},{2}, now(), {3}, now())");

        for (int i = 0; i < roleIds.size(); i++) {
            builder.append(messageFormat.format(new Object[]{userId, roleIds.get(i), personId, personId}));
            if (i < roleIds.size() - 1) {
                builder.append(",");
            }
        }
        return builder.toString();
    }


    public String insertUserDataRole(Map<String, Object> para) {

        List<String> droleIds = (List<String>) para.get("arg0");
        Long userId = (Long) para.get("arg1");//需要修改的用户id
        Long personId = (Long) para.get("arg2");//操作用户的id

        StringBuilder builder = new StringBuilder("INSERT INTO sys_user_data_role (user_id, drole_id, add_person, add_time, last_person, last_time) VALUES ");

        MessageFormat messageFormat = new MessageFormat("({0}, {1},{2}, now(), {3}, now())");

        for (int i = 0; i < droleIds.size(); i++) {
            builder.append(messageFormat.format(new Object[]{userId, droleIds.get(i), personId, personId}));
            if (i < droleIds.size() - 1) {
                builder.append(",");
            }
        }
        return builder.toString();
    }



        public String addStudentExcel(Map<String,Object> para) {
        List<StudentManagerModel> data = (List<StudentManagerModel>) para.get("arg0");

        StringBuilder sqlBuilder = new StringBuilder("INSERT INTO Student(student_number, password, email, student_name, sex, entrance_year, grade) VALUES ");
        String template="(%s,%s,%s,%s,%s,%d,%d)";

        MessageFormat devMessageFormat = new MessageFormat("({0},{1},{2},{3},{4},{5},{6})");
        for(int i=0;i<data.size();i++){
            StudentManagerModel studentManagerModel=data.get(i);
            //devBuilder.append(devMessageFormat.format(new Object[]{studentManagerModel.getStudentNumber(),
            //studentManagerModel.getPassword(),studentManagerModel.getEmail(),studentManagerModel.getStudentName(),
            //studentManagerModel.getSex(),studentManagerModel.getEntranceYear(),studentManagerModel.getGrade()}));
            sqlBuilder.append(String.format(template,studentManagerModel.getStudentNumber(),
                    "'"+studentManagerModel.getPassword()+"'",
                    studentManagerModel.getEmail(),
                    studentManagerModel.getStudentName(),
                    studentManagerModel.getSex(),
                    studentManagerModel.getEntranceYear(),
                    studentManagerModel.getGrade()));
            if (i < data.size() - 1) {
                sqlBuilder.append(",");
            }
        }
        return sqlBuilder.toString();
    }
}

