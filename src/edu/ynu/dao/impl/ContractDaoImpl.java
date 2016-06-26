package edu.ynu.dao.impl;

import edu.ynu.dao.ContractDao;
import edu.ynu.entity.ContractEntity;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class ContractDaoImpl extends BaseDao<ContractEntity> implements ContractDao {

    @Autowired
    public ContractDaoImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }
}
