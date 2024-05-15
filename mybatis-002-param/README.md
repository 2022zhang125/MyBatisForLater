这一模块主要是对Mybatis中不同属性之间的传值进行分析。
    简单类型参数：
        byte short int long float double char
        Byte Short Integer Long Float Double Character
        String
        java.util.Date
        java.sql.Date
1.将数据库的自增主键绑定到对应元素上。
    useGeneratedKeys = "true" 
    keyProperty = "id"
  
2.怎么将特定字符串转为Date格式。
    Date date = SimpleDateFormat.parse(String date);

3.对于简单类型参数，Mybatis可以自动推断功能。

4.TMD傻逼配置文件，不能写注释。傻逼SQL配置文件，一写注释就找不着，大傻逼！！！！别用/**/这个b玩意，用<!---->这玩意

5.对于多参数传值
    Mybatis的原理是，创建一个Map集合
    所以在sql映射中，理应应该写arg0(param1)，arg1(param2),不然就会报错
    map.put("arg0",name)
    map.put("arg1",sex)
    map.put("param1",name)
    map.put("param2",sex)