package cn.newexist.admin.config;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

/**
 * Created by 耿志彬
 * Date 2019/4/9 9:30
 * Description 设置自己的通用mapper
 **/
public interface MyMapper<T> extends Mapper<T>, MySqlMapper<T> {
}
