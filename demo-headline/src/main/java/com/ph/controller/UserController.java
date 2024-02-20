package com.ph.controller;

import com.alibaba.druid.util.StringUtils;
import com.ph.pojo.User;
import com.ph.service.UserService;
import com.ph.utils.JwtHelper;
import com.ph.utils.Result;
import com.ph.utils.ResultCodeEnum;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("user") //根路徑
@CrossOrigin//前端跨域要加入的標籤
public class UserController {

    @Autowired
    private JwtHelper jwtHelper;

    @Autowired
    private UserService userService;



    @PostMapping("login")
    public Result login(@RequestBody User user){
        Result result = userService.login(user);
        return result;

    }
    @GetMapping("getUserInfo")
    public Result getUserInfo(@RequestHeader String token){
        Result result = userService.getUserInfo(token);
        return result;
    }
    @PostMapping("checkUserName")
    //默認就是param 所以不用加註解
    public Result checkUserName(String username){
        Result result = userService.checkUserName(username);
        return  result;
    }
    @PostMapping("regist")
    public Result regist(@RequestBody User user){
        Result result = userService.regist(user);
        return result;
    }
    @GetMapping("checkLogin")   //token要用請求頭
    public Result checkLogin(@RequestHeader String token){
//        if (StringUtils.isEmpty(token)|| jwtHelper.isExpiration(token)){
//            //沒有token或是過期 =未登入
//            return Result.build(null, ResultCodeEnum.NOTLOGIN);
//        }
//        return Result.ok(null);
//    }
        boolean expiration = jwtHelper.isExpiration(token);
        if (expiration){
            //已經過期
            return Result.build(null, ResultCodeEnum.NOTLOGIN);
        }
        return Result.ok(null);
    }
}
