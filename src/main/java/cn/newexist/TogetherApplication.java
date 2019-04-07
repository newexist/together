package cn.newexist;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("cn.newexist.admin.mapper")
public class TogetherApplication {

    public static void main(String[] args) {
        SpringApplication.run(TogetherApplication.class, args);
    }

}
