package com.believesun.mybatis.mapper;

import com.believesun.mybatis.pojo.Teacher;
import java.util.List;

public interface TeacherMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Teacher row);

    Teacher selectByPrimaryKey(Long id);

    List<Teacher> selectAll();

    int updateByPrimaryKey(Teacher row);
}