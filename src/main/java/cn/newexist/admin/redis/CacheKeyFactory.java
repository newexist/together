package cn.newexist.admin.redis;


/**
 * Created by 耿志彬
 * Date 2019/4/9 9:30
 * Description cache中的key值 根据用户信息生成
 **/
public class CacheKeyFactory {

    //登录错误次数
    public static String loginErrorCountKey(String username){
        return "LOGIN-ERROR-COUNT-" + username;
    }

    //是否被禁止登录
    public static String isDisable(String username){
        return "IS-DISABLE-" + username;
    }
}
