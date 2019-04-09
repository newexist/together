package cn.newexist.admin.controller;


import cn.newexist.admin.dto.ResponseCode;
import cn.newexist.admin.exception.GlobalException;
import cn.newexist.admin.redis.CacheKeyFactory;
import cn.newexist.admin.redis.CacheService;
import cn.newexist.admin.utils.HttpContextUtil;
import cn.newexist.admin.utils.IPUtil;
import cn.newexist.common.controller.BaseController;
import eu.bitwalker.useragentutils.Browser;
import eu.bitwalker.useragentutils.OperatingSystem;
import eu.bitwalker.useragentutils.UserAgent;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by 耿志彬
 * Date 2019/4/9 9:30
 * Description 登录控制层
 **/

@RestController
@SuppressWarnings("all")
public class LoginController extends BaseController {

    @Autowired
    private CacheService cacheService;

    /**
     * 跳转到后台首页
     *
     * @param model
     * @return
     */
    @GetMapping("/admin")
    public String admin(Model model) {
        model.addAttribute("user", this.getCurrentUser());
        return "admin/index";
    }

    @GetMapping("/admin/login")
    public ResponseCode login(Model model,
                              @RequestParam(value = "username", required = false) String username,
                              @RequestParam(value = "password", required = false) String password,
                              @RequestParam(value = "remember", required = false) String remember) {
        if (username != null && password != null) {

            //1 是否被禁用
            //2 是否需要提供验证码
            // 登录错误三次需要验证码 五次被禁止登陆

            //获取redis中是否有这个用户被禁止的信息
            boolean isDisable = cacheService.existsKey(CacheKeyFactory.isDisable(username));




            Subject subject = getSubject();
            UsernamePasswordToken token = new UsernamePasswordToken(username, password);
            if (remember != null) {
                if (remember.equals("true")) {
                    //说明选择了记住我
                    token.setRememberMe(true);
                } else {
                    token.setRememberMe(false);
                }
            } else {
                token.setRememberMe(false);
            }
            try {
                subject.login(token);
                //获取HTTP请求
                HttpServletRequest request = HttpContextUtil.getHttpServletRequest();
                String ip = IPUtil.getIpAddr(request);
                System.out.println(ip);
                String header = request.getHeader("User-Agent");
                UserAgent userAgent = UserAgent.parseUserAgentString(header);
                Browser browser = userAgent.getBrowser();
                OperatingSystem operatingSystem = userAgent.getOperatingSystem();
                model.addAttribute("username", getSubject().getPrincipal());
                return ResponseCode.success();
            } catch (Exception e) {
                e.printStackTrace();
                throw new GlobalException(e.getMessage());
            }
        } else {
            throw new GlobalException("用户名或密码错误");
        }
    }

    @GetMapping(value = "/admin/logout")
    public String logout() {
        Subject subject = getSubject();
        subject.logout();
        return "redirect:/admin";
    }
}
