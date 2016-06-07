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
        super.save(assets);
    }

    @Override
    public void deleteAssets(int id) {
        super.deleteById(id);
    }

    @Override
    public void updateAssets(AssetsEntity assets) {
        super.update(assets);
    }

    @Override
    public AssetsEntity getAssets(int id) {
        return super.find(id);
    }
    @Override
    public List<AssetsEntity> findAll(){
        return super.findAll();
    }
}
