package com.believesun.mybatis.mapper;

import com.believesun.mybatis.pojo.Student;

import java.util.List;

public interface StudentMapper {
    // 通过sid查询学生所有信息(第一种方法：级联属性映射)
    Student selectBySid(Integer sid);
    Student selectBySidAssociation(Integer sid);
    // 分布查询的第一步
    Student selectBySidByStep1(Integer sid);
    // 根据CID查询学生信息,分布查询的第二步
    List<Student> selectByCid(Integer cid);
}
