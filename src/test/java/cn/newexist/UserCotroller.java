package cn.newexist;

import cn.newexist.admin.entity.User;
import cn.newexist.admin.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserCotroller {

    @Autowired
    private UserService userService;

    @Test
    public void addUser(){
        User user = new User();
        user.setId("123");
        user.setName("张三");
        user.setUsername("admin");
        user.setPassword("admin");
        userService.save(user);
    }
}
