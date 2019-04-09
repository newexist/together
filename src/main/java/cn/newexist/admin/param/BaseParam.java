package cn.newexist.admin.param;


/**
 * Created by 耿志彬
 * Date 2019/4/9 9:30
 * Description 通用parm
 **/
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
