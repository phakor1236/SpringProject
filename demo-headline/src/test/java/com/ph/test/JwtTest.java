package com.ph.test;

import com.ph.utils.JwtHelper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class JwtTest {

    @Autowired
    private JwtHelper jwtHelper;

    @Test
    public void test(){
        //生成 傳入用戶標示 id
        String token = jwtHelper.createToken(1L);
        System.out.println("token = " + token);

        //解析用户標示
        int userId = jwtHelper.getUserId(token).intValue();
        System.out.println("userId = " + userId);

        //檢查是否到期! false 未到期 true到期
        boolean expiration = jwtHelper.isExpiration(token);
        System.out.println("expiration = " + expiration);
    }

    @Test
    public void test1(){

    }

}
