package com.ph.service.impl;

import com.alibaba.druid.util.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ph.pojo.User;
import com.ph.service.UserService;
import com.ph.mapper.UserMapper;
import com.ph.utils.JwtHelper;
import com.ph.utils.MD5Util;
import com.ph.utils.Result;
import com.ph.utils.ResultCodeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
* @author qwer1
* @description 針對【news_user】的數據庫操作Service實現
* @createDate 2024-01-17 18:54:37
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService{

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private JwtHelper jwtHelper;

    /**
     * 登入方法
     *
     *  1.根據帳號查詢使用者對象，-loginUser (數據庫操作)
     *  2.如果帳號查不到，查詢失敗 501結果返回
     *  3.對比，密碼，失敗，返回503密碼
     *  4.根據使用者id生成token，將token裝到result中返回
     * @param user
     * @return
     */
    @Override
    public Result login(User user) {

        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(User::getUsername,user.getUsername());
       User loginUser =  userMapper.selectOne(lambdaQueryWrapper);//返回一個數據

        if (loginUser == null) {    //如果帳號查不到
            return Result.build(null, ResultCodeEnum.USERNAME_ERROR);
        }
        //換驗證密碼
        if (!StringUtils.isEmpty(user.getUserPwd()) &&
                MD5Util.encrypt(user.getUserPwd()).equals(loginUser.getUserPwd())){
            //登入成功
            //根據用戶id生成token
            String token = jwtHelper.createToken(Long.valueOf(loginUser.getUid()));
            //將token封裝到result返回
            Map data = new HashMap();
            data.put("token",token);
            return Result.ok(data);
        }

        //密碼錯誤
        return Result.build(null,ResultCodeEnum.PASSWORD_ERROR);
    }

    /**
     * 根據token獲取使用者數據
     *
     * 1.token 是否在使用期限內(120Min)
     * 2.根據token解析userId
     * 3.根據userid查詢數據
     * 4.去掉密碼，封裝result結果返回即可
     *
     *
     * @param token
     * @return
     */

    @Override
    public Result getUserInfo(String token) {
        //isExpiration Token是否過期的方法
        boolean expiration = jwtHelper.isExpiration(token);

        if (expiration){
            //登入失效
            return Result.build(null,ResultCodeEnum.NOTLOGIN);
        }
        //把userId從integer提取出來
        int userId = jwtHelper.getUserId(token).intValue();

        User user = userMapper.selectById(userId);
        user.setUserPwd("");
        Map map = new HashMap();

        map.put("loginUser",user);  //大小寫要跟前端數據配對到

        return Result.ok(map);
    }
    /**
     * 檢查帳號可不可用
     *
     * 1.根據帳號進行count查詢
     * 2.count ==0 可用 (沒人使用)
     * 3.count >0 不可用   (可用)
     * @param username 帳號
     * @return
     */
    @Override
    public Result checkUserName(String username) {
        LambdaQueryWrapper<User>queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUsername,username);
        Long count = userMapper.selectCount(queryWrapper);

        if (count ==0) {
            return Result.ok(null);
        }
        return Result.build(null,ResultCodeEnum.USERNAME_USED);

    }

    /**
     * 註冊功能
     * 1.檢查帳號是否被註冊
     * 2.密碼加密處理
     * 3.將帳號數據保存到數據庫
     * 4.返回結果
     * @param user
     * @return
     */

    @Override
    public Result regist(User user) {
        LambdaQueryWrapper<User>queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUsername,user.getUsername());
        Long count = userMapper.selectCount(queryWrapper);

        if (count >0) {
            return Result.build(null,ResultCodeEnum.USERNAME_USED);
        }
        user.setUserPwd(MD5Util.encrypt(user.getUserPwd()));

        userMapper.insert(user);
        return Result.ok(null);
    }
}




