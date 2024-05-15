主要作用：
    1.将mybatis-config.xml文件中的dataSource中变成由properties代替
    2.将mappers标签中的直接指向xml，变为了package形式
        在resources下创建'目录'，com/believesun/mybatis/mapper在其下方创建对应的SQL映射文件
    3.提供了自定义命名规范，简化了在select操作时写resultType时的全限定名称将其简化为了简化类名
        <typeAliases><package name="com.believesun.mybatis.pojo"/></typeAliases>
    4.提供了Mybatis核心配置文件和SQL映射文件的模版