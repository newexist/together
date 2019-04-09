package cn.newexist.admin.exception;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by 耿志彬
 * Date 2019/4/9 9:30
 * Description
 **/
public class GlobalException extends RuntimeException {

    @Getter
    @Setter
    private String msg;

    public GlobalException(String message) {
        this.msg = message;
    }
}
