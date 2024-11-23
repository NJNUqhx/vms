package com.vmsbackend.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.vmsbackend.common.QueryPageParam;
import com.vmsbackend.common.Result;
import com.vmsbackend.entity.Menu;
import com.vmsbackend.entity.User;
import com.vmsbackend.service.MenuService;
import com.vmsbackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.jws.soap.SOAPBinding;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 前端控制器
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

    @Autowired
    private MenuService menuService;

    @GetMapping("/list")
    public List<User> list() {
        return userService.list();
    }

    @GetMapping("/findByNo")
    public Result findByNo(@RequestParam String no) {
        List<User> userList = userService.lambdaQuery().eq(User::getNo, no).list();
        return userList.size() > 0 ? Result.succeed(userList) : Result.fail();
    }

    // 新增
    @PostMapping("/save")
    public Result save(@RequestBody User user) {
        return userService.save(user) ? Result.succeed() : Result.fail();
    }

    @PostMapping("/update")
    public Result update(@RequestBody User user) {
        return userService.updateById(user) ? Result.succeed() : Result.fail();
    }

    @PostMapping("/login")
    public Result login(@RequestBody User user){
        List<User> userList = userService.lambdaQuery().eq(User::getNo, user.getNo()).eq(User::getPassword, user.getPassword()).list();

        if(userList.size() > 0){
            User user1 = userList.get(0);
            List<Menu> menus = menuService.lambdaQuery().like(Menu::getMenuright, user1.getRoleId()).list();
            HashMap<String, Object> map = new HashMap<>();
            map.put("user", user1);
            map.put("menu", menus);
            return Result.succeed(map);
        }
        return Result.fail();
    }

    // 新增或修改
    @PostMapping("/saveOrUpdate")
    public boolean saveOrUpdate(@RequestBody User user) {
        return userService.saveOrUpdate(user);
    }

    // 删除
    @GetMapping("/remove")
    public Result remove(@RequestParam String id) {
        return userService.removeById(id) ? Result.succeed():Result.fail();
    }

    // 查询（模糊、匹配）
    @PostMapping("/listP")
    public Result listP(@RequestBody User user) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        if (StringUtils.isNotBlank(user.getName())) {
            wrapper.like(User::getName, user.getName());
        }

        return Result.succeed(userService.list(wrapper));
    }

    @PostMapping("/query")
    public Result listP(@RequestBody QueryPageParam query) {
        System.out.println(query);

        HashMap param = query.getParam();
        String name = (String) param.get("name");
        String sex = (String) param.get("sex");
        String roleId = (String) param.get("roleId");

        Page<User> page = new Page<>();
        page.setCurrent(query.getPageNum());
        page.setSize(query.getPageSize());


        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        if (StringUtils.isNotBlank(name)) {
            wrapper.like(User::getName, name);
        }

        if (StringUtils.isNotBlank(sex)) {
            wrapper.eq(User::getSex, sex);
        }

        if (StringUtils.isNotBlank(roleId)) {
            wrapper.eq(User::getRoleId, roleId);
        }


        IPage<User> userIPage = userService.page(page, wrapper);
        System.out.println(userIPage.getTotal());

        return Result.succeed(userIPage);
    }
}
