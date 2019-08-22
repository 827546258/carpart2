package com.xiupeilian.carpart.base;


/**
 * @Description:
 * @Author: Tu Xu
 * @CreateDate: 2019/8/21 9:02
 * @Version: 1.0
 **/
public interface BaseMapper<T> {
    int deleteByPrimaryKey(Integer id);

    int insert(T record);

    int insertSelective(T record);

    T selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(T record);

    int updateByPrimaryKey(T record);
}
