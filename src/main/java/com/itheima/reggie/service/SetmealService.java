package com.itheima.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.itheima.reggie.domain.Setmeal;
import com.itheima.reggie.dto.SetmealDto;

import java.util.List;

/**
 * @author 张起荣
 * @motto 我亦无他 唯手熟尔
 */


public interface SetmealService extends IService<Setmeal> {

    //新增套餐,同时保存套餐与产品之间的关系
    void saveWithDish(SetmealDto setmealDto);

    //删除套餐,同时删除菜品和套餐的关联数据
    void deleteWithDish(List<Long> ids);

    //回显套餐数据,根据id查询套餐信息
    SetmealDto getData(Long id);
}
