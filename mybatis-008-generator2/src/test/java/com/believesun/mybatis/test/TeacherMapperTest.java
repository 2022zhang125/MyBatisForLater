package com.believesun.mybatis.test;

import com.believesun.mybatis.mapper.TeacherMapper;
import com.believesun.mybatis.pojo.Teacher;
import com.believesun.mybatis.pojo.TeacherExample;
import com.believesun.mybatis.utils.SqlSessionUtil;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

import java.util.List;

public class TeacherMapperTest {
    // CarExample负责封装查询条件的
    @Test
    public void testSelect(){
        SqlSession sqlSession = SqlSessionUtil.openSession();
        TeacherMapper mapper = sqlSession.getMapper(TeacherMapper.class);
        // 查一个
        Teacher teacher = mapper.selectByPrimaryKey(2L);
        System.out.println(teacher);
        // 查所有,采用的是条件查询，没有条件不就是查询所以嘛~
        List<Teacher> teacherList = mapper.selectByExample(null);
        teacherList.forEach(teacher1 -> System.out.println(teacher1));
        // 按照条件查询
        // QBC风格编程（Query By Criteria）面向对象，看不到sql语句。
        // 1.封装条件
        TeacherExample teacherExample = new TeacherExample();
        // 2.添加查询条件
        teacherExample.createCriteria()
                .andNameEqualTo("王五")
                .andSexEqualTo("男");
        // 添加or
        teacherExample.or().andSexEqualTo("女");
        // select * from t_teacher where (name = '王五' and sex = '男') or sex = '女';
        // 进行查询操作
        List<Teacher> teachers = mapper.selectByExample(teacherExample);
        teachers.forEach(teacher1 -> System.out.println(teacher1));
        sqlSession.close();

    }
}
