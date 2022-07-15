package com.itheima.reggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itheima.reggie.common.CustomException;
import com.itheima.reggie.domain.Category;
import com.itheima.reggie.domain.Dish;
import com.itheima.reggie.domain.Setmeal;
import com.itheima.reggie.mapper.CategoryMapper;
import com.itheima.reggie.service.CategoryService;
import com.itheima.reggie.service.DishService;
import com.itheima.reggie.service.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author 张起荣
 * @motto 我亦无他 唯手熟尔
 */

@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService{

    @Autowired
    private DishService dishService;

    @Autowired
    private SetmealService setmealService;
    /**
     * 根据id删除分类,删除之前需要进行判断
     * @param id
     */
    @Override
    public void remove(Long id) {

        LambdaQueryWrapper<Dish> lqw1 = new LambdaQueryWrapper<>();
        //增加查询条件
        lqw1.eq(Dish::getCategoryId, id);
        int count1 = dishService.count(lqw1);
        //查询当前分类是否关联了菜品
        if(count1 > 0){
            //关联了菜品,抛出业务异常
            throw new CustomException("当前分类关联了菜品,不能删除");
        }

        LambdaQueryWrapper<Setmeal> lqw2 = new LambdaQueryWrapper<>();
        //增加查询条件
        lqw2.eq(Setmeal::getCategoryId, id);
        int count2 = setmealService.count(lqw2);
        //查询当前分类是否关联了套餐
        if(count2 > 0){
            //关联了套餐,抛出业务异常
            throw new CustomException("当前套餐关联了菜品,不能删除");
        }

        //正常删除
        super.removeById(id);
    }
}
