该模块主要针对 高级映射
    多对一，多的一方加外键
    多的一方为主表
    一的一方为副表

第一种方法：级联属性映射
    
    <resultMap id="studentMap" type="student">
        <id property="sid" column="sid"/>
        <id property="sname" column="sname"/>
        <id property="clazz.cid" column="cid"/>
        <id property="clazz.cname" column="cname"/>
    </resultMap>
    <!--通过sid查询学生所有信息-->
    <select id="selectBySid" resultType="student" resultMap="studentMap">
        select
            s.sid,s.sname,c.cid,c.cname
        from
            t_stu s left join t_clazz c
        on
            s.cid = c.cid
        where
            s.sid = #{sid}
    </select>    

第二种方法：association（关联）,与上一种级联属性映射的区别在于resultMap中的clazz部分，学生关联班级
    
    <resultMap id="studentMapAssociation" type="student">
        <id property="sid" column="sid"/>
        <result property="sname" column="sname"/>

        <association property="clazz" javaType="Clazz">
            <id property="cid" column="cid"/>
            <result property="cname" column="cname"/>
        </association>

    </resultMap>
    
这里在association标签中关联了另一个表的id和元素。

第三种方式：分布查询
    优点：可复用，延迟加载
        可复用：就是，单个的SQL语句可以多次调用
        延迟加载机制：
            在实际的开发过程中，都需要开启延迟加载，所以建议直接开启全局的延迟加载机制！！！
            <settings>
                <!--启用全局延迟加载，默认为false不开启-->
                <setting name="lazyLoadingEnabled" value="true"/>
            </settings>
            当我们只需要查询Student的name时，因为不需要关联第二张表的cid，所以此时的mybatis就不会去查询另一张表
            因此mybatis提供了"懒加载"也叫延迟加载，在标签内加入fetchType = 'lazy' ,去开启mybatis的懒加载，使得在不需要cid的时候，不去调用SQL语句
            已达到优化性能的地步。
        但是我们必然会有不想让某一个进行延迟加载，所以，我们可以先开启全局的加载机制，然后对不想进行分布加载的xml文件中使用 fetchType = "eager" 给他强制变为不开启全部加载。
问题：通过sid查询学生的所有信息
思路：
    先查出student的cid，然后通过cid去查询其他的字段名
调用：selectBySidByStep1()方法 ----> 执行这段XML文件 

    <resultMap id="selectBySidByStepMap" type="student">
        <id property="sid" column="sid"/>
        <result property="sname" column="sname"/>
        <association property="clazz"
                     select="com.believesun.mybatis.mapper.ClazzMapper.selectByCidStep2"
                     column="cid"/>
    </resultMap>
    <select id="selectBySidByStep1" resultMap="selectBySidByStepMap">
        select *  from t_stu where sid = #{sid}
    </select>

 ---->select *  from t_stu where sid = #{sid} 将cid 传入 select="com.believesun.mybatis.mapper.ClazzMapper.selectByCidStep2"这个SQL
语句中----->执行这个方法 selectByCidStep2() ---->执行这段SQL语句

    <select id="selectByCidStep2" resultType="clazz">
        select * from t_clazz where cid = #{cid}
    </select>

----> 最后将Clazz对象返回回去

============================================================================================================================
一对多映射：
    在一对多中，一的一方是CLazz，多的一方是Student
    因此我们需要再一的一方，加入多的一方的List数组。
    private List<Student> studentList;
此时的主表就是：CLazz，副表是Student

第一种方法：collection（了解）
    Clazz{
        cid=1000, cname='高三一班', 
        studentList=[
            Student{sid=1, sname='张三', clazz=null}, 
            Student{sid=2, sname='李四', clazz=null}, 
            Student{sid=3, sname='王五', clazz=null}
        ]
    }
    这里为null很正常，假如有值就递归啦！

    <resultMap id="selectByCollectionResultMap" type="clazz">
        <id property="cid" column="cid" />
        <result property="cname" column="cname" />
        <!--
            一对多，这里是collection，是集合的意思
            ofType:用于指定集合当做元素类型
        -->
        <collection property="studentList" ofType="student">
            <id property="sid" column="sid"/>
            <result property="sname" column="sname" />
        </collection>

    </resultMap>    

第二种方法：分布查询（常用）
    和第一种大差不差
第一步：先通过cid获取到我Clazz对象，在这一步中需要使用collection集合
        
    <resultMap id="selectByStep1Map" type="clazz">
    <id property="cid" column="cid" />
    <result property="cname" column="cname" />
    <collection property="studentList"
                ofType="student"
                column="cid"
                select="com.believesun.mybatis.mapper.StudentMapper.selectByCid" />
    </resultMap>
    <select id="selectByStep1" resultType="clazz" resultMap="selectByStep1Map">
        select * from t_clazz where cid = #{cid}
    </select>

第二步：直接通过第一步传来的cid来查询即可，返回student对象给第一步
    
    <select id="selectByCid" resultType="student">
        select * from t_stu where cid = #{cid}
    </select>