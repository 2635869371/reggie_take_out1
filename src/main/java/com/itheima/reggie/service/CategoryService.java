package com.itheima.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.itheima.reggie.domain.Category;

/**
 * @author 张起荣
 * @motto 我亦无他 唯手熟尔
 */


public interface CategoryService extends IService<Category> {
    void remove(Long id);
}
