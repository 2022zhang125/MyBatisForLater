该模块是使用，增强版
    <context id="DB2Tables" targetRuntime="MyBatis3">

QBC风格编程（Query By Criteria）面向对象，看不到sql语句。
    增强版不同于基本版多了一个TeacherExample类,该类封装了多种查询条件

查询一个时：
    Teacher teacher = mapper.selectByPrimaryKey(2L);
查询所有时：
    // 查所有,采用的是条件查询，没有条件不就是查询所以嘛~
    List<Teacher> teacherList = mapper.selectByExample(null);

最重要的是，按条件查询时：
and条件

    // 1.封装条件
    TeacherExample teacherExample = new TeacherExample();
    // 2.添加查询条件
    teacherExample.createCriteria()
    .andNameEqualTo("王五")
    .andSexEqualTo("男");

or条件

    // 2.添加查询条件
    teacherExample.or().andSexEqualTo("女");

这种风格，就是看不到sql语句的风格我们称之为QBC风格（Query By Criteria）
添加完条件后，执行查询语句

    // 3.进行查询操作
    List<Teacher> teachers = mapper.selectByExample(teacherExample);
    select id, name, age, height, birth, sex from t_teacher WHERE ( name = ? and sex = ? ) or( sex = ? )
