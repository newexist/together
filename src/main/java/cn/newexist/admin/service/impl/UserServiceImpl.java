package cn.newexist.admin.service.impl;

import cn.newexist.admin.entity.User;
import cn.newexist.admin.exception.GlobalException;
import cn.newexist.admin.mapper.UserMapper;
import cn.newexist.admin.service.UserService;
import cn.newexist.admin.utils.PasswordHelper;
import cn.newexist.common.service.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by 耿志彬
 * Date 2019/4/9 9:30
 * Description
 **/
@Service
@SuppressWarnings("all")
public class UserServiceImpl extends BaseServiceImpl<User> implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private PasswordHelper passwordHelper;

    @Override
    public User findByName(String username) {
        if (!username.isEmpty()){
            User user = new User();
            user.setUsername(username);
            return userMapper.select(user).get(0);
        }
        return new User();
    }

    @Override
    @Transactional
    public void save(User user) {
        try {
            passwordHelper.encryptPassword(user);
            userMapper.insert(user);
        } catch (Exception e) {
            e.printStackTrace();
            throw new GlobalException(e.getMessage());
        }
    }

    @Override
    public User findById(String id) {
        return userMapper.selectByPrimaryKey(id);
    }

    @Override
    public void update(User user) {
        if (user.getId() != ""){
            try {
                if (user.getPassword()!=null && !"".equals(user.getPassword())){
                    passwordHelper.encryptPassword(user);//加密
                }
                this.updateNotNull(user);
            }catch (Exception e){
                e.printStackTrace();
                throw new GlobalException(e.getMessage());
            }
        }
    }

    @Override
    public void delete(List<String> ids) {
        if (!ids.isEmpty()) {
            try {
                this.batchDelete(ids, "id", User.class);
            } catch (Exception e) {
                e.printStackTrace();
                throw new GlobalException(e.getMessage());
            }
        }
    }
}
