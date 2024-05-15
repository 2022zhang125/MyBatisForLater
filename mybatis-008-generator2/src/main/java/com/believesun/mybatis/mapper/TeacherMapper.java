package com.believesun.mybatis.mapper;

import com.believesun.mybatis.pojo.Teacher;
import com.believesun.mybatis.pojo.TeacherExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TeacherMapper {
    long countByExample(TeacherExample example);

    int deleteByExample(TeacherExample example);

    int deleteByPrimaryKey(Long id);

    int insert(Teacher row);

    int insertSelective(Teacher row);

    List<Teacher> selectByExample(TeacherExample example);

    Teacher selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("row") Teacher row, @Param("example") TeacherExample example);

    int updateByExample(@Param("row") Teacher row, @Param("example") TeacherExample example);

    int updateByPrimaryKeySelective(Teacher row);

    int updateByPrimaryKey(Teacher row);
}