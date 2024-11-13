package com.vmsbackend.service.impl;

import com.vmsbackend.entity.User;
import com.vmsbackend.mapper.UserMapper;
import com.vmsbackend.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ECNUqhx
 * @since 2024-11-14
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

}
