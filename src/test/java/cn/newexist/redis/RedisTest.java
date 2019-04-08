package cn.newexist.redis;

import cn.newexist.admin.redis.RedisUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisTest {

    @Autowired
    private RedisUtil redisTemplate;

    @Test
    public void contextLoads(){
        redisTemplate.sSet("123","1231231");
    }
}
