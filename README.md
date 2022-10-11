# backend-team-go

The Go implementation of https://github.com/zenpk/backend-team

## Packages

1. Gin：HTTP 框架（对标 Spring Web）
2. GORM：用于连接 MySQL 数据库（对标 Spring Data JPA/Hibernate）

## 运行

### Linux

```shell
go build
./backend-team-go
```

### Windows

```shell
go build
```

双击 ``backend-team-go.exe``

默认为 8081 端口，数据库地址与 Spring Boot 版介绍的 Docker 地址一致

API 与 Spring Boot 版基本相同

## 文件结构说明

```text
+---config/
|       config.go -> 预设参数
|
+---controller/
|       router.go -> URL 路由
|
+---dal/ -> 数据持久层
|       db_init.go -> 初始化数据库
|       user.go -> 用户相关
|
+---service/ -> 服务层
|       main.go -> 其他操作
|       user.go -> 登录注册相关操作
|
+---go.mod -> 依赖管理
+---main.go -> 主程序入口
```
