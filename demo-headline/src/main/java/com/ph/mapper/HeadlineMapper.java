package com.ph.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ph.pojo.Headline;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ph.pojo.dto.PortalDto;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
* @author qwer1
* @description 针对表【news_headline】的數據庫操作Mapper
* @createDate 2024-01-17 18:54:37
* @Entity com.ph.pojo.Headline
*/
public interface HeadlineMapper extends BaseMapper<Headline> {
        //自訂分頁查詢方法
        IPage<Map>selectMyPage(IPage page,@Param("portalDto") PortalDto portalDto);


        Map queryDetailMap(Integer hid);
}




