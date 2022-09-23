# 后端组任务：Spring Boot 注册登录接口

## 运行 MySQL

可选择以 Docker 形式运行

```shell
cd ./docker_mysql
docker compose up
```

或直接在宿主机运行，并修改 ``application.properties``的地址和用户

## Spring 依赖

从 start.spring.io 中选择合适版本和以下依赖：

1. Spring Boot DevTools
2. Spring Web
3. MySQL Driver
4. Spring Data JPA
5. Spring Security

## 功能概述

1. Spring Web 提供 Rest 请求支持
2. Spring Security 提供用户认证、权限管理
3. 连接 MySQL 数据库进行读写
4. 端口为 8080

## API

参考：[API 文档](https://www.apifox.cn/apidoc/shared-7a72aec1-4404-41c6-baeb-0e57788e50bb)

## 前端测试

针对本项目使用 Vue 搭建了一个前端测试页面，仓库地址：[frontend-vue](https://github.com/zenpk/frontend-vue)

测试运行（5173 端口）：

```shell
npm run dev -- --host
```

## Todo

- [ ] 更详细的错误提示
- [ ] 更精确的 CORS filter
- [ ] 加入 Remember Me 支持
- [ ] 加入 Spring Security JWT 支持
- [ ] 登出功能
- [ ] 前端登录后返回上一界面
