package edu.ynu.dao.impl;

import edu.ynu.dao.PlanDao;
import edu.ynu.entity.PlanEntity;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;

public class PlanDaoImpl extends BaseDao<PlanEntity> implements PlanDao {

    @Autowired
    public PlanDaoImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public PlanEntity findByPId(String planID) {
        DetachedCriteria dc = DetachedCriteria.forClass(PlanEntity.class);
        dc.add(Restrictions.eq("planId",planID));
        return findByCriteria(dc);
    }
}
