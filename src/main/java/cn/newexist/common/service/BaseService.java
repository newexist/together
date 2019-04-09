package cn.newexist.common.service;

import java.util.List;


/**
 * Created by 耿志彬
 * Date 2019/4/9 9:30
 * Description
 **/
public interface BaseService<T> {

    List<T> selectAll();

    T selectByKey(Object key);

    void save(T entity);

    void delete(Object key);

    void batchDelete(List<String> ids, String property, Class<T> clazz);

    void updateAll(T entity);

    void updateNotNull(T entity);

    List<T> selectByExample(Object example);
}
