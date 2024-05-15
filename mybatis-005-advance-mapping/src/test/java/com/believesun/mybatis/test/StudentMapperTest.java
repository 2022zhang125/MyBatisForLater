package com.believesun.mybatis.test;

import com.believesun.mybatis.mapper.StudentMapper;
import com.believesun.mybatis.pojo.Student;
import com.believesun.mybatis.utils.SqlSessionUtil;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

public class StudentMapperTest {
    // 分布查询
    @Test
    public void testSelectBySidByStep1(){
        SqlSession sqlSession = SqlSessionUtil.openSession();
        StudentMapper mapper = sqlSession.getMapper(StudentMapper.class);
        Student student = mapper.selectBySidByStep1(2);
//        System.out.println(student);
        // 验证懒加载
        System.out.println(student.getSname());
        // 然后再输出cname
        System.out.println(student.getClazz().getCname());
        sqlSession.close();

    }
    @Test
    public void testSelectBySidAssociation(){
        SqlSession sqlSession = SqlSessionUtil.openSession();
        StudentMapper mapper = sqlSession.getMapper(StudentMapper.class);
        Student student = mapper.selectBySidAssociation(2);
        System.out.println(student);
        sqlSession.close();
    }
    // 主类，这是多的一方
    // 通过sid查询学生所有信息
    @Test
    public void testSelectBySid(){
        SqlSession sqlSession = SqlSessionUtil.openSession();
        StudentMapper mapper = sqlSession.getMapper(StudentMapper.class);
        Student student = mapper.selectBySid(1);
        System.out.println(student);
        sqlSession.close();
    }
}
