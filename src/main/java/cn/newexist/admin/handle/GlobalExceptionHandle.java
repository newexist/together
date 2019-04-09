package cn.newexist.admin.handle;

import cn.newexist.admin.dto.ResponseCode;
import cn.newexist.admin.enums.StatusEnums;
import cn.newexist.admin.exception.GlobalException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletResponse;


@RestControllerAdvice
@Order(value = Ordered.HIGHEST_PRECEDENCE)
public class GlobalExceptionHandle {

    @ExceptionHandler(value = Exception.class)
    public ResponseCode exception(Exception e) {
        e.printStackTrace();
        return new ResponseCode(StatusEnums.SYSTEM_ERROR);
    }

    @ExceptionHandler(value = GlobalException.class)
    public ResponseCode globalExceptionHandle(GlobalException e, HttpServletResponse response) {
        e.printStackTrace();
        return new ResponseCode(response.getStatus(), e.getMsg());
    }
}