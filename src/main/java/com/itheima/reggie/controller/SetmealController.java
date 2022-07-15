package com.itheima.reggie.controller;

/**
 * @author 张起荣
 * @motto 我亦无他 唯手熟尔
 */


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itheima.reggie.common.R;
import com.itheima.reggie.domain.*;
import com.itheima.reggie.dto.DishDto;
import com.itheima.reggie.dto.SetmealDto;
import com.itheima.reggie.service.CategoryService;
import com.itheima.reggie.service.SetmealDishService;
import com.itheima.reggie.service.SetmealService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 套餐管理
 */
@RestController
@RequestMapping("/setmeal")
@Slf4j
public class SetmealController {

    @Autowired
    private SetmealService setmealService;

    @Autowired
    private SetmealDishService setmealDishService;

    @Autowired
    private CategoryService categoryService;

    /**
     * 新增套餐
     * @param setmealDto
     * @return
     */
    @PostMapping
    public R<String> save(@RequestBody SetmealDto setmealDto){
        log.info("套餐信息 :{}", setmealDto);
        setmealService.saveWithDish(setmealDto);
        return R.success("新增套餐成功");
    }

    /**
     * 套餐分页查询
     * @param page
     * @param pageSize
     * @param name
     * @return
     */
    @GetMapping("/page")
    public R<Page> page(int page, int pageSize, String name){
//        log.info("page = {}, pageSize = {}, name = {}",page, pageSize, name);
        //构造分页构造器
        Page<Setmeal> pageInfo = new Page<>(page, pageSize);
        Page<SetmealDto> setmealDtoPage = new Page<>();

        //构造条件构造器
        LambdaQueryWrapper<Setmeal> lqw = new LambdaQueryWrapper<>();
        //增加过滤条件
        lqw.like(name != null, Setmeal::getName, name);
        //添加排序条件
        lqw.orderByDesc(Setmeal::getUpdateTime);
        //执行查询
        setmealService.page(pageInfo,lqw);

        //拷贝数据
        BeanUtils.copyProperties(pageInfo,setmealDtoPage, "records");

        List<Setmeal> records = pageInfo.getRecords();

        List<SetmealDto> list = records.stream().map((item) -> {

            SetmealDto setmealDto = new SetmealDto();
            //拷贝
            BeanUtils.copyProperties(item, setmealDto);

            Long categoryId = item.getCategoryId();//分类Id
            //根据id查分类对象
            Category category = categoryService.getById(categoryId);

            if(category != null){
                //把categoryName属性赋给dishDto
                String categoryName = category.getName();
                setmealDto.setCategoryName(categoryName);
            }

            return setmealDto;
        }).collect(Collectors.toList());

        setmealDtoPage.setRecords(list);
        return R.success(setmealDtoPage);
    }

    /**
     * 删除套餐
     * @param ids
     * @return
     */
    @DeleteMapping
    public R<String> delete(@RequestParam List<Long> ids){
        setmealService.deleteWithDish(ids);
        return R.success("删除套餐成功");
    }

    /**
     * 修改套餐售卖状态
     * @param status
     * @param ids
     * @return
     */
    @PostMapping("/status/{status}")
    public R<String> updateStatus(@PathVariable Integer status,
                                  @RequestParam("ids") List<Long> ids){
        Setmeal setmeal = new Setmeal();
        for(Long dishId : ids){
            setmeal.setId(dishId);
            setmeal.setStatus(status);
            setmealService.updateById(setmeal);
        }
        return R.success("修改售卖状态成功");
    }

    /**
     * 回显套餐数据,根据id查询套餐信息
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public R<SetmealDto> getData(@PathVariable Long id){
        SetmealDto setmealDto = setmealService.getData(id);
        return R.success(setmealDto);
    }

    /**
     * 保存套餐修改信息
     * @param setmealDto
     * @return
     */
    @PutMapping
    public R<String> updateData(@RequestBody SetmealDto setmealDto){
        if(setmealDto == null){
            return R.error("请求异常");
        }
        if(setmealDto.getSetmealDishes() == null){
            return R.error("套餐菜品为空,请添加菜品");
        }
        //删除setmeal_dish表中的对应菜品
        Long setmealId = setmealDto.getId();
        LambdaQueryWrapper<SetmealDish> lqw = new LambdaQueryWrapper<>();
        lqw.eq(SetmealDish::getSetmealId, setmealId);
        setmealDishService.remove(lqw);
        //把修改的菜品数据保存到setmeal_dish表中
        List<SetmealDish> setmealDishes = setmealDto.getSetmealDishes();
        //给setmeal_dish表填充相关属性
        for (SetmealDish setmealDish : setmealDishes) {
            setmealDish.setSetmealId(setmealId);
        }
        //保存
        setmealDishService.saveBatch(setmealDishes);
        //更新setmeal表中的数据
        setmealService.updateById(setmealDto);

        return R.success("修改保存成功");
    }

    /**
     * 根据条件查询套餐数据
     * @param setmeal
     * @return
     */
    @RequestMapping("/list")
    public R<List<Setmeal>> list(Setmeal setmeal){
        //添加查询条件
        LambdaQueryWrapper<Setmeal> lqw = new LambdaQueryWrapper<>();
        lqw.eq(setmeal.getCategoryId() != null,
                Setmeal::getCategoryId, setmeal.getCategoryId());
        lqw.eq(setmeal.getStatus() != null,
                Setmeal::getStatus, setmeal.getStatus());
        List<Setmeal> setmealList = setmealService.list(lqw);

        return R.success(setmealList);

    }

}
