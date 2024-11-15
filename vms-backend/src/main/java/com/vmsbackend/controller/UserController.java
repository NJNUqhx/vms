package com.vmsbackend.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.vmsbackend.common.QueryPageParam;
import com.vmsbackend.common.Result;
import com.vmsbackend.entity.User;
import com.vmsbackend.mapper.UserMapper;
import com.vmsbackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author ECNUqhx
 * @since 2024-11-14
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/list")
    public List<User> list(){
        return userService.list();
    }

    // 新增
    @PostMapping("/save")
    public boolean save(@RequestBody User user){
        return userService.save(user);
    }

    // 修改
    @PostMapping("/update")
    public boolean update(@RequestBody User user){
        return userService.updateById(user);
    }

    // 新增或修改
    @PostMapping("/saveOrUpdate")
    public boolean saveOrUpdate(@RequestBody User user){
        return userService.saveOrUpdate(user);
    }

    // 删除
    @GetMapping("/remove")
    public boolean remove(Integer id){
        return userService.removeById(id);
    }

    // 查询（模糊、匹配）
    @PostMapping("/listP")
    public Result listP(@RequestBody User user){
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        if(StringUtils.isNotBlank(user.getName())){
            wrapper.like(User::getName, user.getName());
        }

        return Result.succeed(userService.list(wrapper));
    }

    @PostMapping("/query")
    public Result listP(@RequestBody QueryPageParam query){
        System.out.println(query);

        HashMap param = query.getParam();
        String name = (String)param.get("name");

        Page<User> page = new Page();
        page.setCurrent(query.getPageNum());
        page.setSize(query.getPageSize());

        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(User::getName, name);

        IPage<User> userIPage =  userService.page(page, wrapper);
        System.out.println(userIPage.getTotal());

        return Result.succeed(userIPage);
    }
}
