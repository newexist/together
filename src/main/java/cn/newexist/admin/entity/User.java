package cn.newexist.admin.entity;


import lombok.Data;

import javax.persistence.Table;

/**
 * Created by 耿志彬
 * Date 2019/4/9 9:30
 * Description 分页参数封装
 **/

@Data
@Table(name = "t_user")
public class User {

    private String id;

    private String name;

    private String username;

    private String password;

    private String salt;
}
