package edu.ynu.dao;

import edu.ynu.entity.UserEntity;

public interface UserDao {
    UserEntity findUserById(String userId);
}
