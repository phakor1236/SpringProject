package com.ph.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ph.pojo.Type;
import com.ph.service.TypeService;
import com.ph.mapper.TypeMapper;
import com.ph.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author qwer1
 * @description 針對表【news_type】數據庫操作Service
 * @createDate 2024-01-17 18:54:37
 */
@Service
public class TypeServiceImpl extends ServiceImpl<TypeMapper, Type>
    implements TypeService{

    @Autowired
    private TypeMapper typeMapper;

    @Override
    public Result findAllTypes() {

        List<Type> types = typeMapper.selectList(null);

        return Result.ok(types);
    }
}




