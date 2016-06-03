package edu.ynu.dao.impl;

import edu.ynu.dao.AssetsDao;
import edu.ynu.entity.AssetsEntity;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

public class AssetsDaoImpl implements AssetsDao {
    private SessionFactory sessionFactory;

    @Autowired
    public AssetsDaoImpl(SessionFactory sessionFactory){
        this.sessionFactory = sessionFactory;
    }
    private Session currentSession(){
        return this.sessionFactory.getCurrentSession();
    }

    @Override
    public void addAssets(AssetsEntity assets) {
        this.currentSession().save(assets);
    }

    @Override
    public void deleteAssets(int id) {
        AssetsEntity assets = (AssetsEntity)this.currentSession().get(AssetsEntity.class,id);
        this.currentSession().delete(assets);
    }

    @Override
    public void updateAssets(AssetsEntity assets) {
        this.currentSession().update(assets);
    }

    @Override
    public AssetsEntity getAssets(int id) {
        return (AssetsEntity) this.currentSession().get(AssetsEntity.class,id);
    }
}
