package dal

import (
	"github.com/zenpk/backend-team-go/config"
	"gorm.io/driver/mysql"
	"gorm.io/gorm"
)

var DB *gorm.DB

func DBInit() error {
	// 连接数据库
	dsn := config.DBUserPass + "@tcp(" + config.DBAddr + ")/" + config.DBName + "?charset=utf8mb4&parseTime=True&loc=Local"
	var err error
	DB, err = gorm.Open(mysql.Open(dsn), &gorm.Config{})
	if err != nil {
		return err
	}
	// 映射用户表
	if err := DB.AutoMigrate(&User{}); err != nil {
		return err
	}
	return nil
}
