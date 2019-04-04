package cn.newexist.admin.entity;


import lombok.Data;

@Data
public class User {

    private String id;

    private String name;

    private String username;

    private String password;

    private String salt;
}
