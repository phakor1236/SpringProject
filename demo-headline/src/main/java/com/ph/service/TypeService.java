package com.ph.service;

import com.ph.pojo.Type;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ph.utils.Result;

/**
* @author qwer1
* @description 針對表【news_type】數據庫操作Service
* @createDate 2024-01-17 18:54:37
*/
public interface TypeService extends IService<Type> {
    /**
     *
     * @return
     */
    Result findAllTypes();
}
