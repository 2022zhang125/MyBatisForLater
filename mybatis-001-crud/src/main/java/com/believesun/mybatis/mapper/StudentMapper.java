package com.believesun.mybatis.mapper;

import com.believesun.mybatis.pojo.Student;

import java.util.List;

public interface StudentMapper {
    int insert(Student student);
    int deleteById(Long id);
    int update(Student student);
    List<Student> selectAll();
    Student selectByName(String name);
}
