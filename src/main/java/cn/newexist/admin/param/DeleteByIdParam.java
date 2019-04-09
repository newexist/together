package cn.newexist.admin.param;


import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * Created by 耿志彬
 * Date 2019/4/9 9:30
 * Description
 **/

public class DeleteByIdParam extends BaseParam {
    @NotNull(message = "id不能为空")
    @NotEmpty(message = "主键不能为空")
    public String id;
}
