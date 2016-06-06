package edu.ynu.dao;

import java.util.List;

public interface BaseDao<T> {
    void save(T entity);
    void saveOrUpdate(T entity);
    void delete(T entity);
    void deleteById(Integer id);
    void update(T entity);
    T find(Integer id);
    void batchSave(List<T> entities);
}
