package controller

import (
	"github.com/gin-gonic/gin"
	"github.com/zenpk/backend-team-go/config"
	"github.com/zenpk/backend-team-go/service"
)

// InitRouter 初始化路由器，用于处理 REST 请求
func InitRouter(r *gin.Engine) {
	// 允许 CORS
	rCors := r.Group("/", CORSMiddleware())
	rCors.GET("/", service.HelloWorld)
	rCors.POST("/login", service.Login)
	rCors.POST("/register", service.Register)
	rCors.GET("/all_user", service.AllUser)
}

// CORSMiddleware 允许 CORS 的中间件
func CORSMiddleware() gin.HandlerFunc {
	return func(c *gin.Context) {
		c.Writer.Header().Set("Access-Control-Allow-Origin", "http://"+config.Domain+":5173") // 允许来自前端 Vue 的请求
		c.Writer.Header().Set("Access-Control-Allow-Credentials", "true")
		c.Writer.Header().Set("Access-Control-Allow-Headers", "Content-Type, Content-Length, Accept-Encoding, X-CSRF-Token, Authorization, accept, origin, Cache-Control, X-Requested-With")
		c.Next()
	}
}
