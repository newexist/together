package cn.newexist.admin.controller;


import cn.newexist.admin.dto.ResponseCode;
import cn.newexist.admin.exception.GlobalException;
import cn.newexist.admin.utils.HttpContextUtil;
import cn.newexist.admin.utils.IPUtil;
import cn.newexist.common.controller.BaseController;
import eu.bitwalker.useragentutils.Browser;
import eu.bitwalker.useragentutils.OperatingSystem;
import eu.bitwalker.useragentutils.UserAgent;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletRequest;

@RestController
@SuppressWarnings("all")
public class LoginController extends BaseController {

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
