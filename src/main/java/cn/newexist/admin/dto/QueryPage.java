package cn.newexist.admin.dto;

import lombok.Data;
import java.io.Serializable;

/**
 * Created by 耿志彬
 * Date 2019/4/9 9:30
 * Description 分页参数封装
 **/

@Data
public class QueryPage implements Serializable {

    private int pageCode;
    private int pageSize;
}
