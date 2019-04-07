package cn.newexist.common.factory;

import java.util.UUID;

/**
 * Created by 耿志彬 on 2018/11/6
 *
 * 生成uuid
 **/
public class IdGenerator {
    public static String generate(){
        return UUID.randomUUID().toString().replace("-", "");
    }

    public static void main(String[] args) {
        System.out.println(IdGenerator.generate());
    }
}
