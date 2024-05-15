package com.believesun.mybatis.test;

import com.believesun.mybatis.mapper.TeacherMapper;
import com.believesun.mybatis.pojo.Teacher;
import com.believesun.mybatis.utils.SqlSessionUtil;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

public class TeacherMapperTest {
    @Test
    public void testSelectById2() throws Exception{
        // 二级缓存的作用域是SqlSessionFactory范围
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(Resources.getResourceAsStream("mybatis-config.xml"));
        // 通过一个SqlSessionFactory对象创建多个SqlSession对象
        SqlSession sqlSession1 = sqlSessionFactory.openSession();
        SqlSession sqlSession2 = sqlSessionFactory.openSession();

        // 创建对应的mapper代理对象
        TeacherMapper mapper1 = sqlSession1.getMapper(TeacherMapper.class);
        TeacherMapper mapper2 = sqlSession2.getMapper(TeacherMapper.class);

        // 调用对应的方法
        Teacher teacher1 = mapper1.selectById2(2L);
        System.out.println(teacher1);
        sqlSession1.close(); // 释放二级缓存，将其（一级缓存）同步到二级缓存中。


        Teacher teacher2 = mapper2.selectById2(2L);
        System.out.println(teacher2);

        // 关闭
        //  sqlSession1.close();
        sqlSession2.close();

    }
    @Test
    public void testSelectById(){
        SqlSession sqlSession = SqlSessionUtil.openSession();

        TeacherMapper mapper = sqlSession.getMapper(TeacherMapper.class);
        TeacherMapper mapper2 = sqlSession.getMapper(TeacherMapper.class);

        Teacher teacher1 = mapper.selectById(2L);
        System.out.println(teacher1);

        Teacher teacher2 = mapper2.selectById(2L);
        System.out.println(teacher2);

        sqlSession.close();
    }
}
