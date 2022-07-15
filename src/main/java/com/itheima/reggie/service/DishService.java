package com.itheima.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.itheima.reggie.domain.Dish;
import com.itheima.reggie.dto.DishDto;

import java.util.List;

/**
 * @author 张起荣
 * @motto 我亦无他 唯手熟尔
 */


public interface DishService extends IService<Dish> {
    //新增菜品,同时保存口味数据
    void saveWithFlavor(DishDto dishDto);

    //根据id查询对应的菜品信息和口味信息
    DishDto getByIdWithFlavor(Long id);

    //更新菜品信息和口味信息
    void updateWithFlavor(DishDto dishDto);

    //删除菜品信息
    void deleteDish(List<Long> ids);
}
