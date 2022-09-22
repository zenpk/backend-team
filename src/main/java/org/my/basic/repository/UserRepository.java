package org.my.basic.repository;

import org.my.basic.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
    // 根据用户名查找用户
    User findByUsername(String username);
}