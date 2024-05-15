package com.believesun.mybatis.test;

import com.believesun.mybatis.mapper.TeacherMapper;
import com.believesun.mybatis.pojo.Teacher;
import com.believesun.mybatis.utils.SqlSessionUtil;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class TeacherMapperTest {
    @Test
    public void testSelectByNameAndSex2(){
        SqlSession sqlSession = SqlSessionUtil.openSession();
        TeacherMapper mapper = sqlSession.getMapper(TeacherMapper.class);
        List<Teacher> teachers = mapper.selectNameAndSex2("张三",'男');
        teachers.forEach(teacher -> System.out.println(teacher));
        sqlSession.close();
    }
    @Test
    public void testSelectByNameAndSex(){
        SqlSession sqlSession = SqlSessionUtil.openSession();
        TeacherMapper mapper = sqlSession.getMapper(TeacherMapper.class);
        List<Teacher> teachers = mapper.selectByNameAndSex("张三", '男');
        teachers.forEach(teacher -> System.out.println(teacher));
        sqlSession.close();
    }
    @Test
    public void testInsertTeacherByPojo(){
        SqlSession sqlSession = SqlSessionUtil.openSession();
        TeacherMapper mapper = sqlSession.getMapper(TeacherMapper.class);
        Teacher teacher = new Teacher(null, "赵六", 23, 1.98, new Date(), '男');
        mapper.insertTeacherByPojo(teacher);
        sqlSession.commit();
        sqlSession.close();
    }
    @Test
    public void testInsertTeacherByMap() throws ParseException {
        SqlSession sqlSession = SqlSessionUtil.openSession();
        TeacherMapper mapper = sqlSession.getMapper(TeacherMapper.class);
        HashMap<String, Object> maps = new HashMap<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = sdf.parse("2022-12-21");
        maps.put("name","王五");
        maps.put("age",21);
        maps.put("birth",date);
        maps.put("height",1.77);
        maps.put("sex",'女');

        mapper.insertTeacherByMap(maps);
        sqlSession.commit();
        sqlSession.close();
    }
    @Test
    public void testSelectBySex(){
        SqlSession sqlSession = SqlSessionUtil.openSession();
        TeacherMapper mapper = sqlSession.getMapper(TeacherMapper.class);
        List<Teacher> teachers = mapper.selectBySex('男');
        teachers.forEach(teacher -> System.out.println(teacher));
        sqlSession.close();
    }
    @Test
    public void testSelectById(){
        SqlSession sqlSession = SqlSessionUtil.openSession();
        TeacherMapper mapper = sqlSession.getMapper(TeacherMapper.class);
        List<Teacher> teachers = mapper.selectById(1L);
        teachers.forEach(teacher -> System.out.println(teacher));
        sqlSession.close();
    }
    @Test
    public void testSelectByName(){
        SqlSession sqlSession = SqlSessionUtil.openSession();
        TeacherMapper mapper = sqlSession.getMapper(TeacherMapper.class);
        List<Teacher> teachers = mapper.selectByName("李四");
        teachers.forEach(teacher -> System.out.println(teacher));
        sqlSession.close();
    }
    @Test
    public void testSelectByBirth() throws ParseException {
        SqlSession sqlSession = SqlSessionUtil.openSession();
        TeacherMapper mapper = sqlSession.getMapper(TeacherMapper.class);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date birth = sdf.parse("2023-02-12");
        List<Teacher> teachers = mapper.selectByBirth(birth);
        teachers.forEach(teacher -> System.out.println(teacher));
        sqlSession.close();
    }
}
