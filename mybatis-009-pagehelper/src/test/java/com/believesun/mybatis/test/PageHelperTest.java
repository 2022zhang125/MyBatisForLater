package com.believesun.mybatis.test;

import com.believesun.mybatis.mapper.TeacherMapper;
import com.believesun.mybatis.pojo.Teacher;
import com.believesun.mybatis.utils.SqlSessionUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

import java.util.List;

public class PageHelperTest {
    // 测试PageHelper插件
    @Test
    public void testPageHelper(){
        SqlSession sqlSession = SqlSessionUtil.openSession();
        TeacherMapper mapper = sqlSession.getMapper(TeacherMapper.class);

        // 一定一定一定要在开启DQL语句之前开启分页功能！！！！！！！！！！1
        // 第二页，显示三条记录
        PageHelper.startPage(2,3);
        List<Teacher> teacherList = mapper.selectByExample(null);
//        teacherList.forEach(teacher -> System.out.println(teacher));
        PageInfo<Teacher> pageInfo = new PageInfo<>(teacherList,2);
        System.out.println(pageInfo);
        sqlSession.close();
    }
}
