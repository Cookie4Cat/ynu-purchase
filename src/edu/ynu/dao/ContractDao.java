package edu.ynu.dao;

import edu.ynu.entity.ContractEntity;
import org.hibernate.criterion.DetachedCriteria;

public interface ContractDao {
    void save(ContractEntity contractEntity);
    ContractEntity findByCriteria(DetachedCriteria criteria);
}
