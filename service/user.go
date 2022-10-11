package service

import (
	"github.com/gin-gonic/gin"
	"github.com/zenpk/backend-team-go/config"
	"github.com/zenpk/backend-team-go/dal"
	"net/http"
)

func Register(c *gin.Context) {
	// 提取前端传入的数据
	username := c.PostForm("username")
	password := c.PostForm("password")
	// 调用数据层函数存入数据库
	_, err := dal.Register(username, password)
	// 出错则返回错误信息
	if err != nil {
		c.String(http.StatusUnauthorized, err.Error())
		return
	}
	// 否则返回注册成功
	c.String(http.StatusOK, "success")
}
func Login(c *gin.Context) {
	// 提取前端传入的数据
	username := c.PostForm("username")
	password := c.PostForm("password")
	// 调用数据层函数查找数据库
	user, err := dal.Login(username, password)
	// 出错则返回错误信息
	if err != nil {
		c.String(http.StatusUnauthorized, err.Error())
		return
	}
	// 写入 cookie 记录用户登录情况
	c.SetCookie("token", user.Username, 0, "/", config.Domain, false, true)
	c.String(http.StatusOK, "success")
}
