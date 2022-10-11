package service

import (
	"github.com/gin-gonic/gin"
	"github.com/zenpk/backend-team-go/dal"
	"net/http"
)

// Response 返回响应结构体
type Response struct {
	UserList []dal.User `json:"user_list"`
}

func HelloWorld(c *gin.Context) {
	// 向前端返回 hello world
	c.String(http.StatusOK, "Hello World!")
}

// AllUser 返回所有用户信息
func AllUser(c *gin.Context) {
	// 先验证 cookie（即检查用户是否登录）
	// 这里简化处理，只看 cookie 存不存在
	token, err := c.Cookie("token")
	if err != nil || token == "" { // no cookie
		c.String(http.StatusUnauthorized, "No Permission")
		return
	}
	// 请求数据库获得用户信息
	userList, err := dal.FindAllUser()
	if err != nil {
		c.String(http.StatusUnauthorized, err.Error())
		return
	}
	c.JSON(http.StatusOK, Response{userList})
}
