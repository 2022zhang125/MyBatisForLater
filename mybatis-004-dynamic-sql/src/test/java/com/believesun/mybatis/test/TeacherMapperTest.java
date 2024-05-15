package com.believesun.mybatis.test;

import com.believesun.mybatis.mapper.TeacherMapper;
import com.believesun.mybatis.pojo.Teacher;
import com.believesun.mybatis.utils.SqlSessionUtil;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TeacherMapperTest {

    @Test
    public void testInsertByForEach(){
        SqlSession sqlSession = SqlSessionUtil.openSession();
        TeacherMapper mapper = sqlSession.getMapper(TeacherMapper.class);
        Teacher t1 = new Teacher(null, "王五", 20, 1.65, new Date(), '男');
        Teacher t2 = new Teacher(null, "王五", 20, 1.65, new Date(), '男');
        Teacher t3 = new Teacher(null, "王五", 20, 1.65, new Date(), '男');
        List<Teacher> teachers = new ArrayList<>();
        teachers.add(t1);
        teachers.add(t2);
        teachers.add(t3);
        int count = mapper.insertByForEach(teachers);
        System.out.println(count);
        sqlSession.commit();
        sqlSession.close();
    }

    @Test
    public void testDeleteWithForEach(){
        SqlSession sqlSession = SqlSessionUtil.openSession();
        TeacherMapper mapper = sqlSession.getMapper(TeacherMapper.class);
        long[] ids = new long[]{4L, 5L, 6L};
        mapper.deleteByForEach(ids);
        sqlSession.commit();
        sqlSession.close();
    }
    @Test
    public void testSelectByChoose(){
        SqlSession sqlSession = SqlSessionUtil.openSession();
        TeacherMapper mapper = sqlSession.getMapper(TeacherMapper.class);
        List<Teacher> teacherList = mapper.selectByChoose(22,'男');
        teacherList.forEach(teacher -> System.out.println(teacher));
        sqlSession.close();
    }

    @Test
    public void testUpdateTeacher(){
        SqlSession sqlSession = SqlSessionUtil.openSession();
        TeacherMapper mapper = sqlSession.getMapper(TeacherMapper.class);
        // SET标签，可以将null视为不更新，而不是更新为null
        Teacher teacher = new Teacher(6L,"李逵",45,null,new Date(),'男');
        int count = mapper.updateTeacher(teacher);
        sqlSession.commit();
        sqlSession.close();
    }

    @Test
    public void testSelectByMultiConditionWithTrim(){
        SqlSession sqlSession = SqlSessionUtil.openSession();
        TeacherMapper mapper = sqlSession.getMapper(TeacherMapper.class);
        List<Teacher> teacherList = mapper.selectByMultiConditionWithTrim(23,1.98, '男');
        teacherList.forEach(teacher -> System.out.println(teacher));
        sqlSession.close();
    }

    @Test
    public void testSelectByMultiConditionWithWhere(){
        SqlSession sqlSession = SqlSessionUtil.openSession();
        TeacherMapper mapper = sqlSession.getMapper(TeacherMapper.class);
//        List<Teacher> teacherList = mapper.selectByMultiConditionWithWhere(23,1.98,'男');
        // 前两个为空
        List<Teacher> teacherList = mapper.selectByMultiConditionWithWhere(23,1.98, '男');
        teacherList.forEach(teacher -> System.out.println(teacher));
        sqlSession.close();
    }
    @Test
    public void testSelectByMultiCondition(){
        SqlSession sqlSession = SqlSessionUtil.openSession();
        TeacherMapper mapper = sqlSession.getMapper(TeacherMapper.class);
        List<Teacher> teacherList = mapper.selectByMultiCondition(23,1.98,'男');
        teacherList.forEach(teacher -> System.out.println(teacher));
        sqlSession.close();
    }
}
