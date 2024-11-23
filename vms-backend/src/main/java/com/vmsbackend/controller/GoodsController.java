package com.vmsbackend.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.vmsbackend.common.QueryPageParam;
import com.vmsbackend.common.Result;
import com.vmsbackend.entity.Goods;
import com.vmsbackend.service.GoodsService;
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
@RequestMapping("/goods")
public class GoodsController {

    @Autowired
    private GoodsService goodstypeService;

    @PostMapping("/save")
    public Result save(@RequestBody Goods goods) {
        return goodstypeService.save(goods) ? Result.succeed() : Result.fail();
    }

    @PostMapping("/update")
    public Result update(@RequestBody Goods goods) {
        return goodstypeService.updateById(goods) ? Result.succeed() : Result.fail();
    }

    @GetMapping("/remove")
    public Result remove(@RequestParam String id) {
        return goodstypeService.removeById(id) ? Result.succeed():Result.fail();
    }

    @PostMapping("/query")
    public Result listP(@RequestBody QueryPageParam query) {
        HashMap param = query.getParam();

        Page<Goods> page = new Page<>();
        page.setCurrent(query.getPageNum());
        page.setSize(query.getPageSize());

        String name = (String) param.get("name");
        String goodstype = (String) param.get("goodstype");
        String storage = (String) param.get("storage");

        LambdaQueryWrapper<Goods> wrapper = new LambdaQueryWrapper<>();

        if(StringUtils.isNotBlank(name)){
            wrapper.like(Goods::getName, name);
        }

        if(StringUtils.isNotBlank(goodstype)){
            wrapper.eq(Goods::getGoodstype, goodstype);
        }

        if(StringUtils.isNotBlank(storage)){
            wrapper.eq(Goods::getStorage, storage);
        }

        IPage<Goods> storageIPage = goodstypeService.page(page, wrapper);

        return Result.succeed(storageIPage);
    }

}
