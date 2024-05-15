package com.believesun.mybatis.test;

import com.believesun.mybatis.mapper.TeacherMapper;
import com.believesun.mybatis.pojo.Teacher;
import com.believesun.mybatis.utls.SqlSessionUtil;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

import java.util.List;

public class TeacherMapperTest {
    @Test
    public void testSelectAll(){
        SqlSession sqlSession = SqlSessionUtil.openSession();
        TeacherMapper mapper = sqlSession.getMapper(TeacherMapper.class);
        List<Teacher> teacherList = mapper.selectAll();
        teacherList.forEach(teacher -> System.out.println(teacher));
        sqlSession.close();
    }
}
