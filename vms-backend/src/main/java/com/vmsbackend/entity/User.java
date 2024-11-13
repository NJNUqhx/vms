package com.vmsbackend.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

@Data
public class User {
    private int id;
    private String no;
    private String name;
    private String password;
    private int sex;
    private String roleId;
    private String phone;
    @TableField("isvalid")
    private String isValid;
}