package com.vmsbackend.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.vmsbackend.common.QueryPageParam;
import com.vmsbackend.common.Result;
import com.vmsbackend.entity.Goodstype;
import com.vmsbackend.service.GoodstypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author ECNUqhx
 * @since 2024-11-20
 */
@RestController
@RequestMapping("/goodstype")
public class GoodstypeController {
    @Autowired
    private GoodstypeService goodstypeService;

    @PostMapping("/save")
    public Result save(@RequestBody Goodstype goodstype) {
        return goodstypeService.save(goodstype) ? Result.succeed() : Result.fail();
    }

    @PostMapping("/update")
    public Result update(@RequestBody Goodstype goodstype) {
        return goodstypeService.updateById(goodstype) ? Result.succeed() : Result.fail();
    }

    @GetMapping("/remove")
    public Result remove(@RequestParam String id) {
        return goodstypeService.removeById(id) ? Result.succeed():Result.fail();
    }

    @PostMapping("/query")
    public Result listP(@RequestBody QueryPageParam query) {
        HashMap param = query.getParam();

        Page<Goodstype> page = new Page<>();
        page.setCurrent(query.getPageNum());
        page.setSize(query.getPageSize());

        String name = (String) param.get("name");

        LambdaQueryWrapper<Goodstype> wrapper = new LambdaQueryWrapper<>();

        if(StringUtils.isNotBlank(name)){
            wrapper.like(Goodstype::getName, name);
        }


        IPage<Goodstype> storageIPage = goodstypeService.page(page, wrapper);

        return Result.succeed(storageIPage);
    }

    @GetMapping("/list")
    public Result getGoodstypeList(){
        return Result.succeed(goodstypeService.list());
    }
}
