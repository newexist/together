package cn.newexist.admin.param;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * Created by 耿志彬 on 2018/11/8
 **/
public class TimeRangerParam extends PageParam {

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date from;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date to;

    public Date getFrom() {
        return from;
    }

    public void setFrom(Date from) {
        this.from = from;
    }

    public Date getTo() {
        return to;
    }

    public void setTo(Date to) {
        this.to = to;
    }

}
