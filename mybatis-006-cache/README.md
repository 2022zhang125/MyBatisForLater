该模块主要是用于对mybatis中cache（缓存）机制的探讨
    缓存机制分为三大类
        原理：只要是同一个SQL对象，执行同一条SQL语句的时候，就会走缓存机制。
        1.一级缓存（存储在SqlSession对象中）
            SqlSession的作用域在创建该方法到close中，默认开启
        2.二级缓存（存储在SqlSessionFactory中）
            SqlSessionFactory的作用域是整个数据库的范围。需要手动开启
        3.三级缓存（使用第三方集成的缓存架构）
            使用第三方的缓存：
                如Java语言编写的FhCache技术。

**对于一级缓存：**
    什么时候走缓存?
        1.当SqlSession对象不一致时。
        2.查询条件不一致时。
    什么时候缓存失效呢，只要你在第一个DQL和第二个DQL之间做了以下两件事中的一件事，都会让"一级缓存"清空
        1.执行了SqlSession对象的clearCache()方法,手动清除缓存。
        2.执行了，select,delete,update"不管是哪张表"都会走缓存。
    
    2024-05-13 15:28:49 408 [main] DEBUG c.b.mybatis.mapper.TeacherMapper.selectById - ==>  Preparing: select * from t_teacher where id = ?
    2024-05-13 15:28:49 451 [main] DEBUG c.b.mybatis.mapper.TeacherMapper.selectById - ==> Parameters: 2(Long)
    2024-05-13 15:28:49 516 [main] DEBUG c.b.mybatis.mapper.TeacherMapper.selectById - <==      Total: 1
    Teacher{id=2, name='李四', age=22, height=1.66, birth=Sun Feb 12 00:00:00 GMT+08:00 2023, sex=女}
    Teacher{id=2, name='李四', age=22, height=1.66, birth=Sun Feb 12 00:00:00 GMT+08:00 2023, sex=女}

        SqlSession sqlSession = SqlSessionUtil.openSession();
        TeacherMapper mapper = sqlSession.getMapper(TeacherMapper.class);
        TeacherMapper mapper2 = sqlSession.getMapper(TeacherMapper.class);

        Teacher teacher1 = mapper.selectById(2L);
        System.out.println(teacher1);

        Teacher teacher2 = mapper2.selectById(2L);
        System.out.println(teacher2);

        sqlSession.close();

这里就可以证明，如果我们默认使用的是一级缓存，在这里由于一级缓存是存储在SqlSession中，我们只要是使用的是同一个SqlSession去创建mapper代理的，那么其第二次
调用SQL语句时，就不会查询数据库，直接从缓存中获取。

如果说我们创建多个SqlSession对象的话，不同的SqlSession对象中，就不存在缓存，那么此时的输出结果就是都会查询数据库了。
但是，这里我们使用的是工具类SqlSessionUtil，我们在其中使用到了一个叫ThreadLocal的东西，所以使得我们在同一个线程中SqlSession对象是不变的，所以如果我们想要去
测试这个缓存，就不能去使用这个类.

**对于二级缓存：**
    * 第一点：二级缓存，默认也是自动开启的
        <setting name = "cacheEnabled" value="true"/> 默认就是开启的
    第二点：二级缓存的作用域是SqlSessionFactory
    * 第三点：如果需要使用二级缓存，需要手动配置<cache />标签
    * 第四点：如果使用二级缓存，那么二级缓存的实体类对象，必须要是可序列化的，也就是说必须实现java.io.Serializable接口
    * 第五点：必须等SqlSession对象close或是commit后，这时的一级缓存才会被同步到二级缓存中。那么此时的二级缓存才可以正常使用。

实例：不满足第五点

    public void testSelectById2() throws Exception{
        // 二级缓存的作用域是SqlSessionFactory范围
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(Resources.getResourceAsStream("mybatis-config.xml"));
        // 通过一个SqlSessionFactory对象创建多个SqlSession对象
        SqlSession sqlSession1 = sqlSessionFactory.openSession();
        SqlSession sqlSession2 = sqlSessionFactory.openSession();

        // 创建对应的mapper代理对象
        TeacherMapper mapper1 = sqlSession1.getMapper(TeacherMapper.class);
        TeacherMapper mapper2 = sqlSession2.getMapper(TeacherMapper.class);

        // 调用对应的方法
        Teacher teacher1 = mapper1.selectById2(2L);
        Teacher teacher2 = mapper2.selectById2(2L);
        
        // 输出
        System.out.println(teacher1);
        System.out.println(teacher2);
        
        // 关闭
        sqlSession1.close();
        sqlSession2.close();
        
    }

上述代码中，我们执行的是同一个sql语句，但是没有使用二级缓存，这是因为，二级缓存只在一级缓存close后才被同步到二级缓存中
    但是在上述代码中，我们在最后才close了SqlSession对象，这样就会导致二级缓存虽然被同步，但后序并没有使用到
所以我们应该调整代码

        * 2024-05-13 16:23:19 323 [main] DEBUG com.believesun.mybatis.mapper.TeacherMapper - Cache Hit Ratio [com.believesun.mybatis.mapper.TeacherMapper]: 0.0
        2024-05-13 16:23:20 051 [main] DEBUG c.b.mybatis.mapper.TeacherMapper.selectById2 - ==>  Preparing: select * from t_teacher where id = ?
        2024-05-13 16:23:20 089 [main] DEBUG c.b.mybatis.mapper.TeacherMapper.selectById2 - ==> Parameters: 2(Long)
        2024-05-13 16:23:20 158 [main] DEBUG c.b.mybatis.mapper.TeacherMapper.selectById2 - <==      Total: 1
        Teacher{id=2, name='李四', age=22, height=1.66, birth=Sun Feb 12 00:00:00 GMT+08:00 2023, sex=女}
        * 2024-05-13 16:23:20 175 [main] DEBUG com.believesun.mybatis.mapper.TeacherMapper - Cache Hit Ratio [com.believesun.mybatis.mapper.TeacherMapper]: 0.0
        2024-05-13 16:23:20 175 [main] DEBUG org.apache.ibatis.transaction.jdbc.JdbcTransaction - Opening JDBC Connection
        2024-05-13 16:23:20 210 [main] DEBUG o.apache.ibatis.datasource.pooled.PooledDataSource - Created connection 1101598632.
        2024-05-13 16:23:20 210 [main] DEBUG org.apache.ibatis.transaction.jdbc.JdbcTransaction - Setting autocommit to false on JDBC Connection [com.mysql.cj.jdbc.ConnectionImpl@41a90fa8]
        2024-05-13 16:23:20 211 [main] DEBUG c.b.mybatis.mapper.TeacherMapper.selectById2 - ==>  Preparing: select * from t_teacher where id = ?
        2024-05-13 16:23:20 213 [main] DEBUG c.b.mybatis.mapper.TeacherMapper.selectById2 - ==> Parameters: 2(Long)
        2024-05-13 16:23:20 216 [main] DEBUG c.b.mybatis.mapper.TeacherMapper.selectById2 - <==      Total: 1
        Teacher{id=2, name='李四', age=22, height=1.66, birth=Sun Feb 12 00:00:00 GMT+08:00 2023, sex=女}
此时我们可以看到
    2024-05-13 16:23:19 323 [main] DEBUG com.believesun.mybatis.mapper.TeacherMapper - Cache Hit Ratio [com.believesun.mybatis.mapper.TeacherMapper]: 0.0
这里涉及到了
    Cache Hit Ratio 也就是缓存命中率，这里是0.0也就是没有运用到缓存机制
接下来我们试着对sqlSession.close()进行移位，将其移到上面，使其（一级缓存）早点释放到二级缓存中
    

    2024-05-13 16:29:05 128 [main] DEBUG org.apache.ibatis.transaction.jdbc.JdbcTransaction - Setting autocommit to false on JDBC Connection [com.mysql.cj.jdbc.ConnectionImpl@41813449]
    2024-05-13 16:29:05 143 [main] DEBUG c.b.mybatis.mapper.TeacherMapper.selectById2 - ==>  Preparing: select * from t_teacher where id = ?
    2024-05-13 16:29:05 187 [main] DEBUG c.b.mybatis.mapper.TeacherMapper.selectById2 - ==> Parameters: 2(Long)
    2024-05-13 16:29:05 247 [main] DEBUG c.b.mybatis.mapper.TeacherMapper.selectById2 - <==      Total: 1
    Teacher{id=2, name='李四', age=22, height=1.66, birth=Sun Feb 12 00:00:00 GMT+08:00 2023, sex=女}
    2024-05-13 16:29:05 282 [main] DEBUG org.apache.ibatis.transaction.jdbc.JdbcTransaction - Resetting autocommit to true on JDBC Connection [com.mysql.cj.jdbc.ConnectionImpl@41813449]
    2024-05-13 16:29:05 283 [main] DEBUG org.apache.ibatis.transaction.jdbc.JdbcTransaction - Closing JDBC Connection [com.mysql.cj.jdbc.ConnectionImpl@41813449]
    2024-05-13 16:29:05 283 [main] DEBUG o.apache.ibatis.datasource.pooled.PooledDataSource - Returned connection 1098986569 to pool.
    2024-05-13 16:29:05 286 [main] WARN  org.apache.ibatis.io.SerialFilterChecker - As you are using functionality that deserializes object streams, it is recommended to define the JEP-290 serial filter. Please refer to https://docs.oracle.com/pls/topic/lookup?ctx=javase15&id=GUID-8296D8E8-2B93-4B9A-856E-0A65AF9B8C66
   * 2024-05-13 16:29:05 289 [main] DEBUG com.believesun.mybatis.mapper.TeacherMapper - Cache Hit Ratio [com.believesun.mybatis.mapper.TeacherMapper]: 0.5
    Teacher{id=2, name='李四', age=22, height=1.66, birth=Sun Feb 12 00:00:00 GMT+08:00 2023, sex=女}

这里的
    2024-05-13 16:29:05 289 [main] DEBUG com.believesun.mybatis.mapper.TeacherMapper - Cache Hit Ratio [com.believesun.mybatis.mapper.TeacherMapper]: 0.5
    缓存命中率达到了50%，说明启用了二级缓存

由此我们可以得出二级缓存使用的方法：
    1.该类必须实现Serializable（可序列化）接口
    2.该Mapper映射文件中必须含有<cache />标签
    3.必须等一级缓存（SqlSession）释放后，才能使用二级缓存

二级缓存的相关配置
    <cache 
        eviction(驱逐) = " "
        flushInterVal(刷新时间间隔，默认不刷新) = " "
        size(设置对象数量，默认是1024个对象) = " "
        ......
    />
n
**对于集成的缓存EhCache组件**
第一步：引入依赖
        
    <!--mybatis集成ehcache的组件-->
    <dependency>
      <groupId>org.mybatis.caches</groupId>
      <artifactId>mybatis-ehcache</artifactId>
      <version>1.2.2</version>
    </dependency>

第二步：写配置文件
    
第三步：该<cache />标签
    <cache type="org.mybatis.caches.ehcache.EhcacheCache"/>

第四步：写测试类测试该组件