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
- 使用者在客戶端輸入帳號密碼並向後端提交
- 後端根據帳號和密碼判斷登錄是否成功
- 帳號有誤或者密碼有誤
- 響應不同的提示信息！

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
- 客戶端發送請求，提交token請求頭
- 後端根據token請求頭獲取登入用戶的訊息
- 並返回給客戶端進行儲存

方式:get

地址:user/getUserInfo

### Request 

header

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
- 使用者在註冊輸入帳號
- 將帳號發送給後端
- 根據帳號查詢是否被占用並做出響應

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
  "code":"200",
  "message":"success",
  "data":{}
}
```
失敗

```JSON
{
"code":"505",
"message":"帳號已被使用",
"data":{}
}
```

註冊功能
---
- 客戶端將新的使用者訊息發送給服務端
- 服務端將新用戶存入數據庫
- 存入前做帳號是否被占用檢查
- 如果通過響應成功提示，失敗響應失敗提示

方式:post

地址:user/regist

### request
```JSON
{
  "username":"abc123456",
  "userPWd":"123456",
  "nickname":"abc"
}
```
### response
成功
```JSON
{
  "code":200,
  "message":"succcess",
  "data":{}
}
```
失敗
```JSON
{
  "code":"505",
  "message":"帳號已被使用",
  "data":{}
}
```

查詢首頁分類
---
進入首頁，查詢所有分類並且動態展示新聞類別欄位

方式: get

地址:portal/findAllTypes

### Requset
參數:無

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

分頁查詢首頁訊息
---
- 客戶端向服務端發送查詢關鍵字，新聞類別，頁碼，頁大小
- 服務端根據搜尋條件搜索分頁訊息，返回含頁碼，頁大小，總點閱數，當前頁數據等訊息，並根據hid降序排序。

方式:post

地址:portal/findNewPage

### Request

```JSON
{
    "keyWords":"今天", // 搜尋標題關鍵字
    "type":0,           // 新聞類型
    "pageNum":1,        // 頁碼
    "pageSize":10     // 頁大小
}
```

### Response

```JSON
{
   "code":"200",
   "message":"success"
   "data":{
      "pageInfo":{
        "pageData":[
          {
            "hid":"21",                      // id 
            "title":"氣溫到達 ... ...",       // 標題
            "type":"1",                      // 所屬類別編號
            "pageViews":"40",                // 瀏覽量
            "pastHours":"3" ,                // 發布時間已過時間
            "publisher":"1"                  // 發布者的ID
        }
        ],
      "pageNum":1,    //頁碼數
      "pageSize":10,  // 頁大小
      "totalPage":20, // 總頁數
      "totalSize":200 // 總記錄數
    }
  }
}
```

閱讀文章詳情
---

- 使用者點查看全部，向服務端發送文章id
- 後端根據id查詢完整文章訊息並返回
- 後端同時要讓新聞的瀏覽量+1

方式:post

地址:portal/showHeadlineDetail

### Request

```JSON
hid=1 param型參數
```
### Response

```JSON
{
    "code":"200",
    "message":"success",
    "data":{
        "headline":{
            "hid":"21",                     // id 
            "title":"今天天氣 ... ...",   //  標題
            "article":"... ..."            //  內文
            "type":"1",                    // 所屬類別編號
            "typeName":"科技",             // 所屬類別
            "pageViews":"40",              // 瀏覽量
            "pastHours":"3" ,              // 發布已過的時間
            "publisher":"1" ,              // 發布者ID
            "author":"abc123456"           // 發布者暱稱
        }
    }
}
```
## 文章CRUD

登入驗證和保護
---
- 在進入增刪改查頁面前，必須先向服務端發送請求攜帶token的請求頭
- 後端接收token驗證使用者是否過期並響應
- 前端根據響應訊息提示使用者進入登入頁面或是增刪改查頁面

請求方式:get

地址:user/checkLogin

### Request
參數:無

header:
```JSON
token:使用者token
```

### Response
未過期:
```JSON
{
  "code":"200",
  "message":"success",
  "data":{}
}
```
過期:
```JSON
{
  "code":"504",
  "message":"loginExpired",
  "data":{}
}
```
新增文章
---
- 用戶在客戶端輸入發布的新聞信息完畢後
- 發布前先請求後端的登錄校驗接口驗證登錄
- 登錄通過則提交新聞信息
- 後端將新聞信息存入數據庫

請求方式:post

地址:headline/publish

### Request

參數:
```JSON
{
	  "title":"明天 ...",       //  標題
    "article":"... ...",          //  內容
    "type":"1"                    //  類別
}
```
header:
```JSON
token ... ... ...
```

### Response
未登入
```JSON
{
  "code":"504",
  "message":"loginExpired",
  "data":{}
}
```
登入
```JSON
{
  "code":"200",
  "message":"success",
  "data":{}
}
```
修改文章回顯
---
- 要修改的時候必須讓在數據庫的文章內容展示給我們看然後修改
- 前端先調用登錄校驗接口,校驗登錄是否過期
- 登錄校驗通過後 ,則根據新聞id查詢新聞的完整信息並響應給前端

請求方式:post

地址:headline/findHeadlineByHid

### Request
```JSON
hid = 1 param參數
```
### Response
```JSON
{
    "code":"200",
    "message":"success",
    "data":{
        "headline":{
            "hid":"1",
            "title":"今天天氣",
            "article":"... ... ",
            "type":"1"
        }
    }
}
```
修改回顯後更新進入數據庫
---
- 用戶將新聞信息修改後,提交前先請求登錄校驗接口校驗登錄狀態
- 登錄校驗通過則提交修改後的新聞信息,後端接收並更新進入數據庫

請求方式:post

地址:headline/update

### Request
```JSON
{

}
