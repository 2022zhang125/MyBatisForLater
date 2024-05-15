package com.believesun.mybatis.mapper;

import com.believesun.mybatis.pojo.Teacher;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TeacherMapper {

    // ForEach循环插入数据
    int insertByForEach(@Param("teachers") List<Teacher> teachers);
    // Foreach，循环删除记录,使用in关键词
    int deleteByForEach(@Param("ids") long[] ids);
    // 使用Choose标签
    List<Teacher> selectByChoose(@Param("age") Integer age,@Param("sex") Character sex);
    // 正常更新语句
    int updateTeacher(Teacher teacher);
    // 使用trim标签
    List<Teacher> selectByMultiConditionWithTrim(@Param("age") Integer age,@Param("height") Double height,@Param("sex") Character sex);

    // 根据年龄，身高，性别查询(if标签)
    List<Teacher> selectByMultiCondition(@Param("age") Integer age,@Param("height") Double height,@Param("sex") Character sex);

    // 使用Where代替if标签
    List<Teacher> selectByMultiConditionWithWhere(@Param("age") Integer age,@Param("height") Double height,@Param("sex") Character sex);
}
