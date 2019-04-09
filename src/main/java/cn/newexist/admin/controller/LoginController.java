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
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.TimeUnit;

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
            // 登录错误三次禁止登录10分钟

            //获取redis中是否有这个用户被禁止的信息
            boolean isDisable = cacheService.existsKey(CacheKeyFactory.isDisable(username));
            if (isDisable){
                throw new GlobalException("用户禁止登录");
            }
            int loginErrorCounts = 0;
            String loginErrorCountStr = cacheService.get(CacheKeyFactory.loginErrorCountKey(username));
            if(StringUtils.isNoneBlank(loginErrorCountStr)){
                loginErrorCounts = Integer.parseInt(loginErrorCountStr);
            }
            //大于3次将错误提示存入cache
            if (loginErrorCounts > 3){
                cacheService.set(CacheKeyFactory.isDisable(username),1,10, TimeUnit.MINUTES);
                throw new GlobalException("用户禁止登录");
            }
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
                String header = request.getHeader("User-Agent");
                UserAgent userAgent = UserAgent.parseUserAgentString(header);
                Browser browser = userAgent.getBrowser();
                OperatingSystem operatingSystem = userAgent.getOperatingSystem();
                model.addAttribute("username", getSubject().getPrincipal());
                //登录成功 清理禁止登录缓存
                cacheService.delete(CacheKeyFactory.isDisable(username));
                //清理登录错误次数缓存
                cacheService.delete(CacheKeyFactory.loginErrorCountKey(username));
                return ResponseCode.success();
            } catch (Exception e) {
                e.printStackTrace();
                //过期时间
                loginError(username);
                throw new GlobalException(e.getMessage());
            }
        } else {
            loginError(username);
            throw new GlobalException("用户名或密码错误");
        }
    }

    private void loginError(String username){
        String loginErrorCounts = CacheKeyFactory.loginErrorCountKey(username);
        long loginErrorCount = cacheService.increase(loginErrorCounts, 1L);
        cacheService.expire(loginErrorCounts, 30, TimeUnit.MINUTES);
    }

    @GetMapping(value = "/admin/logout")
    public String logout() {
        Subject subject = getSubject();
        subject.logout();
        return "redirect:/admin";
    }
}
