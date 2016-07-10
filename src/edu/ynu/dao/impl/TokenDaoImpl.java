package edu.ynu.dao.impl;

import edu.ynu.dao.TokenDao;
import edu.ynu.entity.TokenEntity;
import edu.ynu.util.MD5Util;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

public class TokenDaoImpl implements TokenDao {
    private SessionFactory sessionFactory;
    private String salt;                          //加密盐值
    private final Long expireTime = 7200 * 1000L; //token过期时间

    @Autowired
    public TokenDaoImpl(SessionFactory sessionFactory){
        this.sessionFactory = sessionFactory;
        Properties prop = new Properties();
        InputStream in = getClass().getResourceAsStream("/project.properties");
        try{
            prop.load(in);
        }catch (IOException e){
            e.printStackTrace();
        }
        this.salt = prop.getProperty("token.salt");
    }
    private Session currentSession(){
        return this.sessionFactory.getCurrentSession();
    }

    @Override
    public String getUserIdFormToken(String token){
        String hql = "from TokenEntity tokens where tokens.token=:tokenContent";
        Query query = this.currentSession().createQuery(hql);
        query.setString("tokenContent",token);
        List list = query.list();
        Iterator itor = list.iterator();
        if(itor.hasNext()){
            TokenEntity tokenEntity = (TokenEntity)itor.next();
            return String.valueOf(tokenEntity.getUserId());
        }
        return null;
    }
    @Override
    public String getToken(String userId,String userName){
        //清除过期token
        clearToken();
        //查询token是否存在，如果存在返回已经存在的token
        String hql = "from TokenEntity tokens where tokens.userId=:userId";
        Query query = this.currentSession().createQuery(hql);
        query.setString("userId",userId);
        List list = query.list();
        Iterator itor = list.iterator();
        if(itor.hasNext()){
            TokenEntity tokenEntity = (TokenEntity)itor.next();
            return tokenEntity.getToken();
        }
        //否则生成token
        TokenEntity tokenEntity= new TokenEntity();
        Long now=new Date().getTime();
        String sourceStr = userId + this.salt + now.toString();
        String token = MD5Util.GetMD5Code(sourceStr);
        tokenEntity.setToken(token);
        tokenEntity.setUserId(userId);
        tokenEntity.setUserName(userName);
        tokenEntity.setDateline(new Timestamp(now+this.expireTime));
        this.currentSession().save(tokenEntity);
        return tokenEntity.getToken();
    }

    /***
     * 清除过期token
     */
    private void clearToken(){
        String hql = "from TokenEntity";
        Query query = this.currentSession().createQuery(hql);
        Long currentTime=new Date().getTime();
        Timestamp now = new Timestamp(currentTime);
        List<TokenEntity> list = query.list();
        for (TokenEntity tokenEntity:list) {
            if(tokenEntity.getDateline().compareTo(now)<0){
                this.currentSession().delete(tokenEntity);
            }
        }
    }
}
