package edu.ynu.service.impl;

import edu.ynu.dao.impl.UserDaoImpl;
import edu.ynu.entity.UserEntity;
import edu.ynu.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDaoImpl userDao;

    @Override
    public UserEntity finUserById(String userId) {
        return  userDao.findUserById(userId);
    }
}
