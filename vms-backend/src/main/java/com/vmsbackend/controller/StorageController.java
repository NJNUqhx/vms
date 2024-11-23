package com.vmsbackend.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.vmsbackend.common.QueryPageParam;
import com.vmsbackend.common.Result;
import com.vmsbackend.entity.Storage;
import com.vmsbackend.mapper.StorageMapper;
import com.vmsbackend.service.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author ECNUqhx
 * @since 2024-11-19
 */
@RestController
@RequestMapping("/storage")
public class StorageController {
    @Autowired
    private StorageService storageService;
    
    @PostMapping("/save")
    public Result save(@RequestBody Storage storage) {
        return storageService.save(storage) ? Result.succeed() : Result.fail();
    }

    @PostMapping("/update")
    public Result update(@RequestBody Storage storage) {
        return storageService.updateById(storage) ? Result.succeed() : Result.fail();
    }

    @GetMapping("/remove")
    public Result remove(@RequestParam String id) {
        return storageService.removeById(id) ? Result.succeed():Result.fail();
    }

    @PostMapping("/query")
    public Result listP(@RequestBody QueryPageParam query) {
        HashMap param = query.getParam();

        Page<Storage> page = new Page<>();
        page.setCurrent(query.getPageNum());
        page.setSize(query.getPageSize());

        String name = (String) param.get("name");

        LambdaQueryWrapper<Storage> wrapper = new LambdaQueryWrapper<>();

        if(StringUtils.isNotBlank(name)){
            wrapper.like(Storage::getName, name);
        }


        IPage<Storage> storageIPage = storageService.page(page, wrapper);

        return Result.succeed(storageIPage);
    }

    @GetMapping("/list")
    public Result getStorageList(){
        return Result.succeed(storageService.list());
    }
}
