# 第二周实验报告
## MySQL Docker 部署
从服务器拉取 mysql 镜像
```shell
docker pull mysql:latest
```
创建 MySQL 的 container 时，由于需要进行一些初始设置，因此采用 docker compose 的方式运行
```text
└───docker_mysql
    │   docker-compose.yml
    │
    └───scripts
            init_db.sql
```
docker-compose.yml 包含了 root 密码、端口映射、外部 volume 的设定等，init_db.sql 会创建需要的数据库、用户和表格，并赋予相关权限

执行 compose 命令运行
```shell
cd ./docker_mysql
docker compose up
```
这样一个 MySQL 的 container 就运行在了主机的 12345 端口
## 连接数据库测试是否成功运行
连接 MySQL 数据库，登录到 work2 用户
```sh
mysql -h 127.0.0.1 -P 12345 -u work2 -pwork2 work2
```
```text
mysql> SHOW TABLES;
+-----------------+
| Tables_in_work2 |
+-----------------+
| dorm_infos      |
+-----------------+
```
## Scaffold 生成后端代码
从 start.spring.io 中选择合适版本和以下依赖：
1. Spring Boot DevTools
2. Spring Web
3. MySQL Driver
4. Spring Data JPA

生成初始代码，设定 JPA 连接的数据库地址、库名、用户名、密码，并将 Hibernate 设置为 update 模式

## MVC 框架
创建数据库表格对应的 model: DormInfo 并设置对应的数据结构，创建 Spring 自动识别的 Repository 提供 CRUD 功能，创建 Controller 监听 GET 请求，监听到 "/show" 地址的请求时读取表格 "dorm_infos" 中的全部数据，以 JSON 形式返回给客户端
## 部署
采用容器化部署，首先使用 Maven 编译为 jar 文件
```shell
mvn package
```
在 Dockerfile 中设置对应的 jar 名，build
```shell
docker build -t zenpk/basic:v1 .
```
由于腾讯云访问 GitHub 太慢，因此上述步骤均在本地完成，将 image push 到 docker hub 上，再 pull 到服务器并运行
```shell
docker pull zenpk/basic:v1
docker run --rm -it --name basic_backend -p 8080:8080 zenpk/basic:v1
```
此时后端程序并不能正常访问数据库，原因是 localhost 指向了 container 内部的网络，因此需要修改 JPA 的设置，将数据库地址纸箱 host.docker.internal，并在运行的时候指定该地址为宿主机的地址
```shell
docker run --rm -it --name basic_backend -p 8080:8080 --add-host host.docker.internal:host-gateway zenpk/basic:v1
```
现在即可访问 http://101.43.179.27:8080/show 查看结果 
