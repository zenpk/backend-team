package dal

import (
	"errors"
	"golang.org/x/crypto/bcrypt"
)

// User 用户表
type User struct {
	Id       int64  `gorm:"primaryKey"`
	Username string `gorm:"not null"`
	Password string `gorm:"not null"`
}

// Register 注册，先查数据库是否已存在用户信息，再存入数据库
func Register(username, password string) (user User, err error) {
	if DB.Where("username = ?", username).Find(&User{}).RowsAffected > 0 {
		return User{}, errors.New("用户名已存在")
	} else {
		passwordHash, _ := bCryptPassword(password) // 密码 BCrypt 加密
		// 创建新用户
		newUser := User{
			Username: username,
			Password: passwordHash,
		}
		// 存入数据库
		if err := DB.Create(&newUser).Error; err != nil {
			// 出错则返回错误
			return User{}, err
		}
		return newUser, nil
	}
}

// Login 登录
func Login(username, password string) (User, error) {
	var user User
	// 先检查是否存在此用户
	if DB.Where("username = ?", username).First(&user).RowsAffected > 0 {
		// 再比对密码
		passwordHashByte := []byte(user.Password)
		passwordByte := []byte(password)
		if err := bcrypt.CompareHashAndPassword(passwordHashByte, passwordByte); errors.Is(err, bcrypt.ErrMismatchedHashAndPassword) {
			return User{}, errors.New("密码错误")
		} else {
			return user, nil
		}
	} else {
		return User{}, errors.New("用户不存在")
	}
}

// FindAllUser 提取所有用户信息
func FindAllUser() ([]User, error) {
	var userList []User
	return userList, DB.Find(&userList).Error
}

// bCryptPassword BCrypt 加密器
func bCryptPassword(password string) (string, error) {
	bytes, err := bcrypt.GenerateFromPassword([]byte(password), 10)
	return string(bytes), err
}
