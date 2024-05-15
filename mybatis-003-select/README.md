该模块主要是针对多参数返回值
    1.返回POJO
    2.返回List集合
        当我们的查询结果没有对应的POJO类时，我们采用Map集合的方式进行存储，其中Map的泛型是<String,Object>
    3.返回Map
    4.返回List集合中的Map ---> List<Map>
    5.返回大Map，用于便于查找
        2024-05-08 10:55:37 885 [main] DEBUG c.b.m.mapper.StudentMapper.selectAllRtnBigMap - ==>  Preparing: select * from t_student;
        2024-05-08 10:55:37 925 [main] DEBUG c.b.m.mapper.StudentMapper.selectAllRtnBigMap - ==> Parameters:
        2024-05-08 10:55:37 975 [main] DEBUG c.b.m.mapper.StudentMapper.selectAllRtnBigMap - <==      Total: 2
        {1={xh=2022352020143, sex=女, name=张三, id=1}, 33={xh=2022352020144, sex=男, name=李四, id=33}}
        此时的1对应的就是第一个Map的id值，第二个Map对象的 33 对应的就是他的主键id
当返回的条数多于一个POJO类时，我们使用一个POJO类去接收时会出现TooManyResultsException异常

为了解决Mybatis中POJO类的属性名宇数据库字段名不同，导致的查询语句为null，提出了结果映射的方法
    目的：使用resultMap代替起别名的as
    什么是结果映射？
        就是将我们的POJO属性名与数据库字段名进行绑定操作
    怎么绑定？
        在我们的SQL映射文件中，给定标签<resultMap id="" type=""> </resultMap>
        其中的id具有唯一标识作用，type需要制定我们的POJO类
    <resultMap id="" type="">
        // 建议这里配置id主键，可以提高Mybatis的执行效率，最好配上,主键！！！！
        // 一般都有主键，不然不符合数据库设计第一范式
        <id property="id" column="id"/>
        这里的property表示POJO的属性名
        这里的column表示数据库的字段名
        <result property="" column=""/>
    </resultMap>
    在日后需要进行结果映射时，需要在select标签后加上 resultMap="唯一标识" 这里放的就是我们自定义的映射关系，也就是那个具有唯一标识的id

