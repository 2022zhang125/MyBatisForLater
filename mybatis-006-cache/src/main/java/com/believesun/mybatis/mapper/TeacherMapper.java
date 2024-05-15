package com.believesun.mybatis.mapper;

import com.believesun.mybatis.pojo.Teacher;

public interface TeacherMapper {
    // 测试二级缓存
    Teacher selectById2(Long id);
    // 根据ID查询老师信息
    Teacher selectById(Long id);
}
