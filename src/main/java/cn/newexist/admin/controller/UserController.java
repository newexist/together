package cn.newexist.admin.controller;

import cn.newexist.admin.dto.ResponseCode;
import cn.newexist.admin.service.UserService;
import cn.newexist.admin.utils.PasswordHelper;
import cn.newexist.common.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SuppressWarnings("all")
@RequestMapping("/user")
public class UserController extends BaseController {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordHelper passwordHelper;

//    @PostMapping("save")
//    public ResponseCode save(){
//
//    }


}
