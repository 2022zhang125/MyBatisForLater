该模块主要是处理动态SQL，使用if,where,trim, set标签对SQL语句进行动态处理

if标签：
    if标签中test属性是必须的
        1.test的值使boolean类型
        2.为true时，就会将标签内的元素插入到sql语句中，反之则不可
        3.test属性中可以使用什么？
            当使用了@Param注解，test中需要出现Param注解指定的参数名。@Param("sex") Character sex这里只能用sex
            如果没用，则用arg0...param1....
            当使用POJO，则填写POJO的属性名。
        4.在Mybatis的动态SQL中，不能用&&，只能用and

            <if test="age != null and age != '' ">
                age = #{age} and
            </if>
            <if test="height != null and age != '' ">
                and height >= #{height}
            </if>
            <if test="sex != null and sex != '' ">
                and sex = #{sex}
            </if>
            <!--为了避免后面的if都不成立，会导致sql语句变成 select * from t_teacher where 会导致sql语句报错，所以我们在where后面加入个1 = 1-->
            select * from t_teacher where 1 = 1

where标签：
    相较于裸露的if标签，where标签更加智能。可以自动去除多余的最前面的or和and，也能自动加上and
    但是不支持去除结尾的and
        <!--where标签专门负责Where字句的-->
        <!--
            使用Where标签时，第一个if就不需要加入and，where标签可以自动补全
            很tm智能
            去不了后面的and
            age = #{age} and  ===> Error
        -->

            <where>
                <if test="age != null and age != '' ">
                    age = #{age} and
                </if>
                <if test="height != null and age != '' ">
                    and height >= #{height}
                </if>
                <if test="sex != null and sex != '' ">
                    and sex = #{sex}
                </if>
            </where>

trim标签：有四个属性
    prefix：加前缀，这个也TM傻逼，只能加在最前面
    prefixOverrides：删除前缀
    suffix：加后缀,傻逼，只能在最后加
    suffixOverrides：删除后缀
        我们的where不是对第一个前缀支持自动补全，但是对全部的前缀不行，而且对于后缀也没有办法吗? 
            所以我们使用prefix在Trim标签"所有内容"的前面加前缀where ,前缀是只针对第一个的。
            使用suffixOverrides把trim"标签内容中"的后缀and或or删除掉
        注意：当我们使用了where标签时，就不需要在最前面加入where了。

        <trim prefix="where" suffixOverrides="and | or">
            <if test="age != null and age != '' ">
                age = #{age} and
            </if>
            <if test="height != null and age != '' ">
                height >= #{height} and
            </if>
            <if test="sex != null and sex != '' ">
                sex = #{sex} or
            </if>
        </trim>

set标签（主要是处理update的SQL语句）：
    智能点：
        1.如果提交的数据中含有NULL，在原先的mybatisSQL语句中会出现直接替换数据库表中的数据为NULL
            但是，我们的本意是，只修改不为空的值，为空的就不进行update了。
            所以，这里的set标签就解决了这一问题。
        2.自动去除后面的逗号,但是tmd不能自己加逗号，要搭配trim
        3.注意：这里的birth ！= ‘’ 需要去掉，傻逼东西
            <if test="birth != null and birth != '' ">birth = #{birth},</if>

                update t_teacher
                    <set>
                        <if test="name != null and name != '' ">name = #{name},</if>
                        <if test="age != null and age != '' ">age = #{age},</if>
                        <if test="height != null and height != '' ">height = #{height},</if>
                        <if test="birth != null">birth = #{birth},</if>
                        <if test="sex != null and sex != '' ">sex = #{sex},</if>
                    </set>
                    <trim prefix="where" suffixOverrides="and | or">
                        id = #{id}
                    </trim>

choose when（当） otherwise（其他）标签 类似与JSTL标签库：
    当我们的when成立的时候，就不会走其他分支。
    <choose>
        <when></when>
        <when></when>
        <when></when>
        <otherwise></otherwise>
    </choose>

if(){
    else if(){}
    else if(){}
    else if(){}
    else(){}
}

    select * from t_teacher
    <where>
        <choose>
            <when test="age != null and age != '' ">
                age = #{age}
            </when>
            <when test="sex != null and sex != '' ">
                sex = #{sex}
            </when>
            <otherwise></otherwise>
        </choose>
    </where>
    
foreach标签：
    该标签主要是处理多循环，多次CRUD而使用的
    格式：
    <delete id="deleteByForEach">
        delete from t_teacher
        where id in
        <foreach collection="ids" item="id" open="(" close=")" separator=",">
            #{id}
        </foreach>
    </delete>
    其中：id:代表接口方法名
        collection后接数组，集合，也就是接口指定的@Param
        item代表该数组中的一个元素
        open表示，在这个循环拼接的SQL语句中，最前面加上什么东西
        close表示在后面加上东西 
        separator：表示动态SQL之间的连接方式，使用什么字符进行连接。

sql标签和include标签
    sql标签和include标签主要解决代码重复性低的问题，使用include标签可以附带sql标签的id值，在sql标签中写入公共代码
    在使用include进行调用即可。
