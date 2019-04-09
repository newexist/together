package cn.newexist.common.factory;

import java.util.UUID;

/**
 * Created by 耿志彬
 * Date 2019/4/9 9:30
 * Description 生成uuid
 **/
public class IdGenerator {
    public static String generate(){
        return UUID.randomUUID().toString().replace("-", "");
    }

    public static void main(String[] args) {
        System.out.println(IdGenerator.generate());
    }
}
