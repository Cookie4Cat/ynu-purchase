package edu.ynu.dao;

import edu.ynu.entity.PlanEntity;
import org.hibernate.criterion.DetachedCriteria;

import java.util.List;

public interface PlanDao {
    void save(PlanEntity plan);
    void saveOrUpdate(PlanEntity plan);
    List<PlanEntity> listByCriteria(DetachedCriteria criteria,Integer countPerPage,Integer pageNum);
    Integer countByCriteria(DetachedCriteria criteria);
    void update(PlanEntity planEntity);
    void delete(PlanEntity planEntity);
    PlanEntity findByCriteria(DetachedCriteria criteria);
    PlanEntity findByPId(String planID);
    //====重构后的接口
    List<PlanEntity> listPlansByStatus(String[] status);
}
