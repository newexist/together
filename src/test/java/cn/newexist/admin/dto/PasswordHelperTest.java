package cn.newexist.admin.dto;

import cn.newexist.admin.entity.User;
import cn.newexist.admin.utils.PasswordHelper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@SuppressWarnings("all")
public class PasswordHelperTest {

    @Autowired
    private PasswordHelper passwordHelper;

    @Test
    public void encryptPassword(){

        User user = new User();
        user.setId("1");
        user.setUsername("admin");
        user.setPassword("admin");
        user.setSalt("536a51359841754df6bbab57d24d2128");
        passwordHelper.encryptPassword(user);
        System.out.println(user.getPassword());

    }

}
