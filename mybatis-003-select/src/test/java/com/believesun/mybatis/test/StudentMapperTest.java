package com.believesun.mybatis.test;

import com.believesun.mybatis.mapper.StudentMapper;
import com.believesun.mybatis.pojo.Student;
import com.believesun.mybatis.utils.SqlSessionUtil;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class StudentMapperTest {
    @Test
    public void testSelectAllRtnBigMap(){
        SqlSession sqlSession = SqlSessionUtil.openSession();
        StudentMapper mapper = sqlSession.getMapper(StudentMapper.class);
        Map<Long, Map<String, Object>> longMapMap = mapper.selectAllRtnBigMap();
        System.out.println(longMapMap);
        sqlSession.close();
    }
    @Test
    public void testSelectAll(){
        SqlSession sqlSession = SqlSessionUtil.openSession();
        StudentMapper mapper = sqlSession.getMapper(StudentMapper.class);
        List<Map<String, Object>> maps = mapper.selectAll();
        maps.forEach(student -> System.out.println(student));
        sqlSession.close();
    }
    @Test
    public void testSelectByIdRetMap(){
        SqlSession sqlSession = SqlSessionUtil.openSession();
        StudentMapper mapper = sqlSession.getMapper(StudentMapper.class);
        Map<String, Object> stringObjectMap = mapper.selectByIdRetMap(1L);
        System.out.println(stringObjectMap);
        sqlSession.close();
    }
    @Test
    public void testSelectStudentBySex(){
        SqlSession sqlSession = SqlSessionUtil.openSession();
        StudentMapper mapper = sqlSession.getMapper(StudentMapper.class);
        List<Student> students = mapper.selectStudentBySex('女');
        students.forEach(student -> System.out.println(student));
        sqlSession.close();
    }
    @Test
    public void testSelectStudentByXh(){
        SqlSession sqlSession = SqlSessionUtil.openSession();
        StudentMapper mapper = sqlSession.getMapper(StudentMapper.class);
        Student student = mapper.selectStudentByXh("2022352020144");
        System.out.println(student);
        sqlSession.close();
    }
}
