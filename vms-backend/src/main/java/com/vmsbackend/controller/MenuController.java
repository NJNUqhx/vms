package com.vmsbackend.controller;


import com.vmsbackend.common.Result;
import com.vmsbackend.entity.Menu;
import com.vmsbackend.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author ECNUqhx
 * @since 2024-11-19
 */
@RestController
@RequestMapping("/menu")
public class MenuController {

    @Autowired
    private MenuService menuService;

    @GetMapping("/list")
    public Result list(@RequestParam String roleId){
        List<Menu> menus = menuService.lambdaQuery().like(Menu::getMenuright, roleId).list();
        return Result.succeed(menus);
    }
}
