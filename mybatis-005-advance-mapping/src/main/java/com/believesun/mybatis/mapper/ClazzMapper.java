package com.believesun.mybatis.mapper;

import com.believesun.mybatis.pojo.Clazz;

public interface ClazzMapper {
    // 分布查询的第二步
    Clazz selectByCidStep2(Integer cid);

    // 一对多映射,根据班级编号查询班级信息
    Clazz selectByCollection(Integer cid);
    // 一对多映射之分布查询
    // 分布查询第一步，先获取CLazz的cid，将其传入第二步查询中
    Clazz selectByStep1(Integer cid);
}
