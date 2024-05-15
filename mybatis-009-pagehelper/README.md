MYSql中，limit的使用方法
    limit 开始下标,显示条数
    limit startIndex,pageSize

假如每页显示3条记录
    第一页：limit 0,3
    第二页：limit 3,3

假如每页显示2条记录
    第一页：limit 0,2 （0,1） (1-1)*2
    第二页：limit 2,2 （2,3） (2-1)*2
    第三页：limit 4,2  (4,5) (4-1)*2 
    第四页：limit 6,2  
    第n页：limit (pageNum - 1) * pageSize,2 

前端传入，第3页，每页展示2条记录
pageNum = 3
pageSize = 2
limit (pageNum - 1) * pageSize,2

使用PageHelper插件
    第一步：引入依赖
        <dependency>
            <groupId>com.github.pagehelper</groupId>
            <artifactId>pagehelper</artifactId>
            <version>5.3.1</version>
        </dependency>
    第二步：在核心配置文件中配置插件
        分页拦截器
        <plugins>
            <plugin interceptor="com.github.pagehelper.PageInterceptor"></plugin>
        </plugins>

测试：
    当我们执行DQL语句时，要在之前开启PageHelper插件
        // 一定一定一定要在开启DQL语句之前开启分页功能！！！！！！！！！！
        // 第二页，显示三条记录
        PageHelper.startPage(2,3);
    底层，在执行DQL之前，拦截DQL语句，添加Limit查询条件
    注意：sql语句不要写分号，不然底层拼不上去！！！

使用PageInfo去获取，分页信息
    PageInfo<Teacher> pageInfo = new PageInfo<>(teacherList,2);
    new PageInfo<>(查询的集合对象,百度的那个分页点的那个1,2,3,4...就是第几页)
最后输出的信息：
PageInfo{
    // 第二页，三条记录，Mysql表的第几条记录开始，到哪条记录结束，总记录条数，页数
    pageNum=2, pageSize=3, size=2, startRow=4, endRow=5, total=5, pages=2, 
    list=Page{count=true, pageNum=2, pageSize=3, startRow=3, endRow=6, total=5, pages=2, reasonable=false, pageSizeZero=false }
    [
        Teacher
            {id=8, name='王五', age=20, height=1.65, birth=Sat May 11 00:00:00 GMT+08:00 2024, sex='男'}, 
        Teacher
            {id=9, name='王五', age=20, height=1.65, birth=Sat May 11 00:00:00 GMT+08:00 2024, sex='男'}
    ], 
    // 上一页页码，下一页页码，是第一页吗，是最后一页吗。有没有上一页，有没有下一页，导航页数，导航第一页是几，导航的最后一页是几，导航的数组
    prePage=1, nextPage=0, isFirstPage=false, isLastPage=true, hasPreviousPage=true, hasNextPage=false, navigatePages=2, navigateFirstPage=1, navigateLastPage=2, navigatepageNums=[1, 2]
}

