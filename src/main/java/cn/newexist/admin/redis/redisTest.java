package cn.newexist.admin.redis;

public class redisTest {

    public static void main(String[] args) {
        RedisUtil redisUtil = new RedisUtil();
        redisUtil.sSet("123","123456");
    }
}
