package com.ph.Interceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ph.utils.JwtHelper;
import com.ph.utils.Result;
import com.ph.utils.ResultCodeEnum;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * 登入保護攔截器
 * 檢查請求頭是否包含token
 * 有，放行
 * 沒有，返回504
 */
@Component
public class LoginProtectInterceptor implements HandlerInterceptor {
    @Autowired
    private JwtHelper jwtHelper;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //從請求頭中獲取token
        String token = request.getHeader("token");
        //檢查是否有效
        boolean expiration = jwtHelper.isExpiration(token);
        //有效放行
        if (!expiration) {
            //放行 沒過期
            return  true;
        }
        //無效返回504狀態json
        Result result= Result.build(null, ResultCodeEnum.NOTLOGIN);
        //先轉成json字串
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(result);
        response.getWriter().print(json);
        return  false;
    }
}
