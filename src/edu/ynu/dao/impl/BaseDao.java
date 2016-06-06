package edu.ynu.dao.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

public class BaseDao<T> {
    private SessionFactory sessionFactory;
    private Class<T> poclazz;
    @Autowired
    public BaseDao(SessionFactory sessionFactory){
        this.sessionFactory = sessionFactory;
        Type genericSuperclass = this.getClass().getGenericSuperclass();
        Type[] params = ((ParameterizedType) genericSuperclass).getActualTypeArguments();
        poclazz = null;
        if ((params.length > 0) && (params[0] != null)){
            poclazz = (Class) params[0];
        }
    }
    private Session currentSession(){
        return this.sessionFactory.getCurrentSession();
    }

    public String getClazzName(){
        return poclazz.getSimpleName();
    }
    public T find(Integer id){
        return (T)currentSession().get(poclazz,id);
    }
    public List<T> findAll(){
        String hql = "from " + poclazz.getSimpleName();
        return currentSession().createQuery(hql).list();
    }
    public void saveOrUpdate(T entity){
        currentSession().saveOrUpdate(entity);
    }
    public void delete(T entity){
        currentSession().delete(entity);
    }
    public void update(T entity){
        currentSession().update(entity);
    }
    public void save(T entity){
        currentSession().save(entity);
    }
    public void deleteById(Integer id){
        T entity = (T)currentSession().get(poclazz,id);
        currentSession().delete(entity);
    }
}
