package edu.ynu.dao.impl;

import edu.ynu.dao.ItemDao;
import edu.ynu.entity.ItemEntity;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Set;

public class ItemDaoImpl extends BaseDao<ItemEntity> implements ItemDao{
    @Autowired
    public ItemDaoImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }
    @Override
    public void batchDelete(Set<ItemEntity> itemSet) {
        for(ItemEntity item:itemSet){
            super.delete(item);
        }
    }
}
