package main

import (
	"github.com/gin-gonic/gin"
	"github.com/zenpk/backend-team-go/controller"
	"github.com/zenpk/backend-team-go/dal"
	"log"
)

const (
	IP   = "localhost"
	Port = "8081"
)

func main() {
	r := gin.Default()
	controller.InitRouter(r)             // 初始化 Gin
	if err := dal.DBInit(); err != nil { // 初始化数据库
		log.Fatalln(err)
	}
	if err := r.Run(IP + ":" + Port); err != nil { // 启动服务
		log.Fatalln(err)
	}
	log.Printf("running on %s\n", Port)
}
