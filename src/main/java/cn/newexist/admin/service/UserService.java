package cn.newexist.admin.service;

import cn.newexist.admin.entity.User;
import cn.newexist.common.service.BaseService;

import java.util.List;

public interface UserService extends BaseService<User> {

    /**
     * 根据ID查询
     *
     * @param id
     * @return
     */
    User findById(String id);

    /**
     * 根据Name查询用户数据
     *
     * @param username
     * @return
     */
    User findByName(String username);

    /**
     * 更新
     *
     * @param user
     */
    void update(User user);

    /**
     * 删除
     *
     * @param ids
     */
    void delete(List<String> ids);

}
