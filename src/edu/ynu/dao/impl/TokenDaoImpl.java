package edu.ynu.dao.impl;

import edu.ynu.entity.TokenEntity;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Iterator;
import java.util.List;

public class TokenDaoImpl {
    private SessionFactory sessionFactory;

    @Autowired
    public TokenDaoImpl(SessionFactory sessionFactory){
        this.sessionFactory = sessionFactory;
    }
    private Session currentSession(){
        return this.sessionFactory.getCurrentSession();
    }

    public String getUserIdFormToken(String token){
        String hql = "from TokenEntity tokens where tokens.token=:tokenContent";
        Query query = this.currentSession().createQuery(hql);
        query.setString("tokenContent",token);
        List list = query.list();
        Iterator itor = list.iterator();
        if(itor.hasNext()){
            TokenEntity tokenEntity = (TokenEntity)itor.next();
            return tokenEntity.getToken();
        }
        return "null";
    }
}
