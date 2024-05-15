package com.believesun.mybatis.mapper;

import com.believesun.mybatis.pojo.Student;
import org.apache.ibatis.annotations.MapKey;

import java.util.List;
import java.util.Map;

public interface StudentMapper {
    // 为了便于查找，定义一个大Map,大Map的key是key值
    // Map集合的key使每个集合的主键值（id）使用注解@MapKey去指定map集合使用的key值！！！
    @MapKey("id")
    Map<Long,Map<String,Object>> selectAllRtnBigMap();
    List<Map<String,Object>> selectAll();
    // 返回Map集合,查询的结果没有合适的类对应，这时我们用Map
    // 获取学生信息到Map集合中
    Map<String,Object> selectByIdRetMap(Long id);
    // 返回List集合
    List<Student> selectStudentBySex(Character sex);
    // 返回Student类型通过xh
    Student selectStudentByXh(String xh);
}
