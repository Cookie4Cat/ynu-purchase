package edu.ynu.dao.impl;

import edu.ynu.dao.AssetsDao;
import edu.ynu.entity.AssetsEntity;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class AssetsDaoImpl extends BaseDao<AssetsEntity> implements AssetsDao {

    @Autowired
    public AssetsDaoImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public void addAssets(AssetsEntity assets) {

    }

    @Override
    public void deleteAssets(int id) {

    }

    @Override
    public void updateAssets(AssetsEntity assets) {

    }

    @Override
    public AssetsEntity getAssets(int id) {
        return null;
    }
    @Override
    public List<AssetsEntity> findAll(){
        return super.findAll();
    }
}
