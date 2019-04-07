package cn.newexist.admin.param;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class PageParam extends BaseParam {

    @Max(value = 100, message = "每页组大200项")
    @NotNull(message = "每页大小不能为空")
    private Integer pageSize = 10;
    @NotNull(message = "页码不能为空")
    @Min(value = 0, message = "页码不能小于零")
    private Integer pageIndex;

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(Integer pageIndex) {
        this.pageIndex = pageIndex;
    }

    public Integer offset() {
        if (pageIndex == null || pageSize == null) {
            return null;
        }

        return pageSize * pageIndex;
    }

    public Integer limit() {
        if (pageSize == null) {
            return 10;
        }
        return pageSize;
    }

    public boolean needCalcTotal(int firstPageSize) {
        return (!(getPageIndex() == 0 && (firstPageSize == 0 || firstPageSize < getPageSize())));
    }
}
