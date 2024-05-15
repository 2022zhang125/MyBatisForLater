package com.believesun.mybatis.test;

import com.believesun.mybatis.mapper.StudentMapper;
import com.believesun.mybatis.pojo.Student;
import com.believesun.mybatis.utils.SqlSessionUtil;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

import java.util.List;

public class StudentMapperTest {
    @Test
    public void testInsert(){
        SqlSession sqlSession = SqlSessionUtil.openSession();
        StudentMapper mapper = sqlSession.getMapper(StudentMapper.class);
        Student student = new Student(null,"李四","2022352020144",'男');
        mapper.insert(student);
        sqlSession.commit();
        sqlSession.close();
    }
    @Test
    public void testDeleteById(){
        SqlSession sqlSession = SqlSessionUtil.openSession();
        StudentMapper mapper = sqlSession.getMapper(StudentMapper.class);
        int count = mapper.deleteById(4L);
        sqlSession.commit();
        sqlSession.close();
        System.out.println(count);
    }
    @Test
    public void testUpdate(){
        SqlSession sqlSession = SqlSessionUtil.openSession();
        StudentMapper mapper = sqlSession.getMapper(StudentMapper.class);
        Student student = new Student(1L,"张三","2022352020143",'女');
        int update = mapper.update(student);
        sqlSession.commit();
        sqlSession.close();
        System.out.println(update);
    }
    @Test
    public void testSelectAll(){
        SqlSession sqlSession = SqlSessionUtil.openSession();
        StudentMapper mapper = sqlSession.getMapper(StudentMapper.class);
        List<Student> students = mapper.selectAll();
        students.forEach(student -> System.out.println(student));
        sqlSession.close();
    }
    @Test
    public void testSelectByName(){
        SqlSession sqlSession = SqlSessionUtil.openSession();
        StudentMapper mapper = sqlSession.getMapper(StudentMapper.class);
        Student student = mapper.selectByName("张三");
        System.out.println(student);
        sqlSession.close();
    }
}
