package com.ph.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ph.pojo.Headline;
import com.ph.pojo.dto.PortalDto;
import com.ph.service.HeadlineService;
import com.ph.mapper.HeadlineMapper;
import com.ph.utils.JwtHelper;
import com.ph.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author qwer1
 * @description 針對表【news_headline】的數據庫操作Service
 * @createDate 2024-01-17 18:54:37
 */
@Service
public class HeadlineServiceImpl extends ServiceImpl<HeadlineMapper, Headline>
    implements HeadlineService{

    @Autowired
    private JwtHelper jwtHelper;
    @Autowired
    private HeadlineMapper headlineMapper;
    /**
     * 首頁數據查詢
     *
     * 1.進行分頁數據查詢
     * 2.分頁數據，拼接到Result即可
     *
     * 注意1: 查詢需要自訂語句，自訂mapper的方法，攜帶分頁
     * 注意2:返回的結果應該是list<Map>
     * @param portalDto
     * @return
     */

    @Override
    public Result findNewsPage(PortalDto portalDto) {

        //Ipage ->當前頁數 頁容量
        IPage<Map>page = new Page<>(portalDto.getPageNum(),portalDto.getPageSize());

        headlineMapper.selectMyPage(page,portalDto);

        List<Map> records = page.getRecords();

        Map data = new HashMap<>();
        data.put("pageData",records);
        data.put("pageNum",page.getCurrent());
        data.put("pageSize",page.getSize());
        data.put("totalPage",page.getPages());
        data.put("totalSize",page.getTotal());

        Map pageInfo = new HashMap<>();
        pageInfo.put("pageInfo",data);
        return Result.ok(pageInfo);
    }

    /**
     * 根據id查詢文章內容詳情
     *
     *
     *
     *  1.查詢對應的數據即可(多表查詢，自訂方法)頭條表跟User表 ，返回map即可
     *  2.修改點閱數 +1  [version樂觀鎖，當前數據對應的版本]
     * @param hid
     * @return
     */
    @Override
    public Result showHeadlineDetail(Integer hid) {
        Map data = headlineMapper.queryDetailMap(hid);
        Map headLineMap = new HashMap<>();
        headLineMap.put("headline",data);
        //修改點閱量 +1
        Headline headline = new Headline();
        headline.setHid((Integer) data.get("hid"));
        headline.setVersion((Integer) data.get("version"));
        //+1
        headline.setPageViews((Integer) data.get("pageViews")+1);

        headlineMapper.updateById(headline);
        return Result.ok(headLineMap);
    }

    /**
     * 發布文章方法
     *
     *  1.補全數據
     *
     * @param headline
     * @return
     */
    @Override
    public Result publish(Headline headline, String token) {

        //token查詢用戶id
        int userId = jwtHelper.getUserId(token).intValue();
        //數據補齊
        headline.setPublisher(userId);
        headline.setPageViews(0);
        headline.setCreateTime(new Date());
        headline.setUpdateTime(new Date());

        headlineMapper.insert(headline);
        return Result.ok(null);

    }
    /**
     * 修改頭條數據
     *
     * 1.hid查詢數據的最新version
     * 2.修改數據的修改時間
     *
     * @param headline
     * @return
     */

    @Override
    public Result updateData(Headline headline) {
        Integer version = headlineMapper.selectById(headline.getHid()).getVersion();
        headline.setVersion(version);//樂觀鎖
        headline.setUpdateTime(new Date());

        headlineMapper.updateById(headline);
        return Result.ok(null);
    }
}




