package edu.ynu.service;

import edu.ynu.entity.AssetsEntity;

import java.util.List;

public interface AssetsService {
    void addAssets(AssetsEntity assets);
    void deleteAssets(int id);
    void updateAssets(AssetsEntity assets);
    AssetsEntity getAssets(int id);
    List<AssetsEntity> findAll();
}
