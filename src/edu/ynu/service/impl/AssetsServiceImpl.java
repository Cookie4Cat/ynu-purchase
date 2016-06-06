package edu.ynu.service.impl;

import edu.ynu.dao.AssetsDao;
import edu.ynu.entity.AssetsEntity;
import edu.ynu.service.AssetsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public class AssetsServiceImpl implements AssetsService {

    @Autowired
    private AssetsDao assetsDao;

    @Override
    public void addAssets(AssetsEntity assets) {
        assetsDao.addAssets(assets);
    }

    @Override
    public void deleteAssets(int id) {
        assetsDao.deleteAssets(id);
    }

    @Override
    public void updateAssets(AssetsEntity assets) {
        assetsDao.updateAssets(assets);
    }

    @Override
    public AssetsEntity getAssets(int id) {
        return assetsDao.getAssets(id);
    }

    @Override
    public List<AssetsEntity> findAll() {
        return assetsDao.findAll();
    }
}
