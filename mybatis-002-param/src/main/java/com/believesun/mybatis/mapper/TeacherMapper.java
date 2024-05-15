package com.believesun.mybatis.mapper;

import com.believesun.mybatis.pojo.Teacher;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface TeacherMapper {
    /**
     * 通过@Param手动指定Map集合中的key使在sql映射文件中可以直接使用
     * 那么此时的Map集合发生了变化
     * map.put("name",name)
     * map.put("sex",sex)
     * 但是下面的这个没有发生改变。所以param1和param2可以使用，但是arg0和arg1都被替换掉了。
     * map.put("param1",name)
     * map.put("param2",sex)
     * @param name
     * @param sex
     * @return
     */
    List<Teacher> selectNameAndSex2(@Param("name")String name, @Param("sex") Character sex);
    /**
     * 多参数传参，通过Name和Sex去查找对象
     * @param name
     * @param sex
     * @return
     */
    List<Teacher> selectByNameAndSex(String name,Character sex);
    /**
     * 保存教师信息，通过POJO参数
     * @param teacher
     * @return 成功条数
     */
    int insertTeacherByPojo(Teacher teacher);
    /**
     * 通过Map集合插入学生信息,单个参数，不是简单类型，是Map集合
     * @return count
     */
    int insertTeacherByMap(Map<String,Object> map);
    List<Teacher> selectById(Long id);
    List<Teacher> selectByName(String name);
    List<Teacher> selectByBirth(Date birth);
    List<Teacher> selectBySex(Character sex);
}
