package edu.ynu.dao;

import edu.ynu.entity.AssetsEntity;

public interface AssetsDao {
    // 增删改查
    public void addAssets(AssetsEntity assets);
    public void deleteAssets(int id);
    public void updateAssets(AssetsEntity assets);
    public AssetsEntity getAssets(int id);
}
