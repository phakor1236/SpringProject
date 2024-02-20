package com.ph.service;

import com.ph.pojo.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ph.utils.Result;

/**
 * @author qwer1
 * @description 針對【news_user】的數據庫操作Service實現
 * @createDate 2024-01-17 18:54:37
 */
public interface UserService extends IService<User> {
    /**
     * 登入方法
     * @param user
     * @return
     */
    Result login(User user);

    /**
     * 根據token 獲得使用者訊息
     * @param token
     * @return
     */
    Result getUserInfo(String token);

    /**
     * 檢查帳號可不可用
     * @param username 帳號
     * @return
     */
    Result checkUserName(String username);

    /**
     * 註冊功能
     * @param user
     * @return
     */
    Result regist(User user);
}
