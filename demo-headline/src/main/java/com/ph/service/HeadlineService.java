package com.ph.service;

import com.ph.pojo.Headline;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ph.pojo.dto.PortalDto;
import com.ph.utils.Result;

/**
* @author qwer1
* @description 針對表【news_headline】的數據庫操作Service
* @createDate 2024-01-17 18:54:37
*/
public interface HeadlineService extends IService<Headline> {
    /**
     * 首頁數據查詢
     * @param portalDto
     * @return
     */

    Result findNewsPage(PortalDto portalDto);

    /**
     * 文章內容詳情
     * @param hid
     * @return
     */
    Result showHeadlineDetail(Integer hid);

    /**
     * 發布文章方法
     * @param headline
     * @return
     */
    Result publish(Headline headline,String token);

    /**
     * 修改頭條數據
     * @param headline
     * @return
     */
    Result updateData(Headline headline);
}
