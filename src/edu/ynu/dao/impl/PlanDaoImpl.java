package edu.ynu.dao.impl;

import edu.ynu.dao.PlanDao;
import edu.ynu.entity.PlanEntity;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class PlanDaoImpl extends BaseDao<PlanEntity> implements PlanDao {

    @Autowired
    public PlanDaoImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }
}
