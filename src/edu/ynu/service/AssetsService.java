package edu.ynu.service;

import edu.ynu.entity.AssetsEntity;

import java.util.List;

public interface AssetsService {
    public void addAssets(AssetsEntity assets);
    public void deleteAssets(int id);
    public void updateAssets(AssetsEntity assets);
    public AssetsEntity getAssets(int id);
    public List<AssetsEntity> findAll();
}
