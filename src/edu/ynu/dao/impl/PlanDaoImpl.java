package edu.ynu.dao.impl;

import edu.ynu.dao.PlanDao;
import edu.ynu.entity.PlanEntity;
import edu.ynu.entity.ProjectEntity;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

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

    @Override
    public List<PlanEntity> listPlansByStatus(String[] status) {
        String hql = "from PlanEntity plan where plan.status in :status";
        Query query = this.currentSession().createQuery(hql);
        query.setParameter("status",status);
        List<PlanEntity> list = query.list();
        return list;
    }
}
