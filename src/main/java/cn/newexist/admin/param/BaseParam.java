package cn.newexist.admin.param;

public class BaseParam {
    public String currentTenantId;
    public String currentAccountId;

    public String wrap(String keyword) {
        if(keyword == null){
            return null;
        }
        return "%" + keyword + "%";
    }
}
