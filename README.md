# 簡易文章發布平台

[簡介](https://drive.google.com/file/d/1K49msRmPn2CJS_tX1luxYH-Mbe0i9ZmD/view "簡介")

demo-project是後端代碼

demo-fronted是前端代碼

sql是資料數據需要先導入

# 安裝及快速開始
前端
---
需要先下載node.js

然後用代碼編輯器打開前端資料夾

安裝套件
---

```java
npm install
```


運行project
---

```java
npm run dev
```

後端
---
用編輯器打開後端資料夾

設置與數據庫的連接

開啟resources/mapper/application.yaml

修改成與自己檔案名稱、路徑相同

```yaml
server:
  port: 8080
  servlet:
    context-path: /

spring:
  datasource:
    type: com.alibaba.druid.pool.DruidDataSource
    druid:
      url: jdbc:mysql://localhost:3306/sm_db
      username: root
      password: root
      driver-class-name: com.mysql.cj.jdbc.Driver
```

點擊run


# API簡介

登入功能
---

使用者在客戶端輸入帳號密碼並向後端提交,後端根據帳號和密碼判斷登錄是否成功,帳號有誤或者密碼有誤，響應不同的提示信息！

方式:post

地址:user/login

### Request



```JSON
{
    "username":"abc123456", //帳號
    "userPwd":"123456"     //密碼
}
```

### Response  

成功

```JSON
{
  "code":"200",         // 成功狀態碼 
   "message":"success"   // 成功描述
   "data":{
    "token":"... ..." // 使用者id的token
  }
}
```

失敗

```JSON
{
   "code":"501",
   "message":"帳號錯誤"
   "data":{}
}

{
   "code":"503",
   "message":"密碼錯誤"
   "data":{}
}
```

根據token獲取使用者數據
---
客戶端發送請求，提交token請求頭，後端根據token請求頭獲取登入用戶的訊息並返回給客戶端進行儲存

方式:get

地址:user/getUserInfo

### Request Header

```JSON
token:token內容
```

### Response

成功

```JSON
{
    "code": 200,
    "message": "success",
    "data": {
        "loginUser": {
            "uid": 1,
            "username": "abc123456",
            "userPwd": "",
            "nickName": "abc"
        }
    }
}
```

失敗

```JSON
{
    "code": 504,
    "message": "notLogin",
    "data": null
}
```

註冊帳號檢查
---
使用者在註冊輸入帳號時，立刻將帳號發送給後端，根據帳號查詢是否被占用並做出響應。

方式:post

地址:user/checkUserName

### request:param

```JSON
username=帳號
```

### response

成功

```JSON
{
  "code":"200"
  "message":"success"
  "data":{}
}
```
失敗

```JSON
{
"code":"505"
"message":"帳號已被使用"
"data":{}
}
```

註冊功能
---
客戶端將新的使用者訊息發送給服務端，服務端將新用戶存入數據庫，存入前做帳號是否被占用檢查，如果通過響應成功提示，失敗響應失敗提示

方式:post

地址:user/regist

### request
```JSON
{
  "username":"abc123456"
  "userPWd":"123456"
  "nickname":"abc"
}
```
### response
成功
```JSON
{
  "code":200
  "message":"succcess"
  "data":{}
}
```
失敗
```JSON
{
  "code":"505"
  "message":"帳號已被使用"
  "data":{}
}
```

查詢首頁分類
---
進入首頁，查詢所有分類並且動態展示新聞類別欄位

方式: get

地址:portal/findAllTypes

### Requset
無

### Response

```JSON
{
   "code":"200",
   "message":"OK"
   "data":{
            [
                {
                    "tid":"1",
                    "tname":"新聞"
                },
                {
                    "tid":"2",
                    "tname":"體育"
                },
                {
                    "tid":"3",
                    "tname":"娛樂"
                },
                {
                    "tid":"4",
                    "tname":"科技"
                },
                {
                    "tid":"5",
                    "tname":"其他"
                }
            ]
    }
}
```


