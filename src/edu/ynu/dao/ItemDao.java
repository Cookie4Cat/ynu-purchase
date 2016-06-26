package edu.ynu.dao;

import edu.ynu.entity.ItemEntity;

import java.util.Set;

public interface ItemDao {
    void batchDelete(Set<ItemEntity> itemSet);
    ItemEntity find(Integer id);
}
