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
npm install

運行project

npm run dev

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

