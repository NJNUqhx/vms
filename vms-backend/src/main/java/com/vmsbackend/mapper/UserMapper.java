package com.vmsbackend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.vmsbackend.entity.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper extends BaseMapper<User> {
    List<User> listAll();
}
