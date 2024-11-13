package com.vmsbackend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.vmsbackend.entity.User;

import java.util.List;

public interface UserService extends IService<User> {
    List<User> listAll();
}
