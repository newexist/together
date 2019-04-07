package cn.newexist.admin.param;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class UpdateByIdParam extends BaseParam {
    @NotNull(message = "主键不能为空")
    @NotEmpty(message = "主键不能为空")
    public String id;
}
