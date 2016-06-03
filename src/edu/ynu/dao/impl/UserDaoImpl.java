package edu.ynu.dao.impl;

import edu.ynu.dao.UserDao;
import edu.ynu.entity.UserEntity;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UserDaoImpl implements UserDao{
    private SessionFactory sessionFactory;

    @Autowired
    public UserDaoImpl(SessionFactory sessionFactory){
        this.sessionFactory = sessionFactory;
    }
    private Session currentSession(){
        return this.sessionFactory.getCurrentSession();
    }
    @Override
    public UserEntity findUserById(String userId){
        return (UserEntity) this.currentSession().get(UserEntity.class,Integer.valueOf(userId));
    }
}
