package edu.ynu.service.impl;

import edu.ynu.dao.ContractDao;
import edu.ynu.dao.PlanDao;
import edu.ynu.dao.ProjectDao;
import edu.ynu.entity.ContractEntity;
import edu.ynu.entity.PlanEntity;
import edu.ynu.entity.ProjectEntity;
import edu.ynu.message.PlanMessage;
import edu.ynu.message.PlanSubmit;
import edu.ynu.message.PurchaseApplySubmit;
import edu.ynu.service.RecorderService;
import edu.ynu.util.TransformUtil;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.*;
@Transactional
public class RecorderServiceImpl implements RecorderService {

    @Autowired
    private PlanDao planDao;
    @Autowired
    private ContractDao contractDao;
    @Autowired
    private ProjectDao projectDao;
    private final String[] handlingStatus = {"待审批","待采购"};
    private final String[] historyStatus = {"采购完成"};

    @Override
    public void submitPlan(PlanSubmit submit) {
        PlanEntity plan = new PlanEntity();
        plan.setPlanId(submit.getPlanId());
        plan.setPreOrgType(submit.getPreOrgType());
        plan.setPrePurchaseType(submit.getPrePurchaseType());
        String now = String.valueOf(new Timestamp(new Date().getTime()));//当前时间
        plan.setTime(now);
        Set<ProjectEntity> projects = new HashSet<>();
        for(String pid:submit.getProjectIdList()){
            ProjectEntity project = projectDao.findProjectByPId(pid);
            project.setStatus("待审批");
            project.setPlan(plan);
            projects.add(project);
        }
        plan.setProjects(projects);
        plan.setStatus("待审批");
        planDao.save(plan);
    }

    @Override
    public Integer countHandlingPlan() {
        DetachedCriteria dc = DetachedCriteria.forClass(PlanEntity.class);
        dc.add(Restrictions.in("status",handlingStatus));
        return planDao.countByCriteria(dc);
    }

    @Override
    public List<PlanMessage> listHandlingPlan(Integer countPerPage, Integer pageNum) {
        DetachedCriteria dc = DetachedCriteria.forClass(PlanEntity.class);
        dc.add(Restrictions.in("status",handlingStatus));
        return TransformUtil.transformToPlanMessageList(planDao.listByCriteria(dc,countPerPage,pageNum));
    }

    @Override
    public Integer countHistoryPlan() {
        DetachedCriteria dc = DetachedCriteria.forClass(PlanEntity.class);
        dc.add(Restrictions.in("status",historyStatus));
        return planDao.countByCriteria(dc);
    }

    @Override
    public List<PlanMessage> listHistoryPlan(Integer countPerPage, Integer pageNum) {
        DetachedCriteria dc = DetachedCriteria.forClass(PlanEntity.class);
        dc.add(Restrictions.in("status",historyStatus));
        return TransformUtil.transformToPlanMessageList(planDao.listByCriteria(dc,countPerPage,pageNum));
    }

    @Override
    public void replyPlan(PlanSubmit submit) {
        PlanEntity plan = findByPId(submit.getPlanId());
        plan.setOrgType(submit.getOrgType());
        plan.setPurchaseType(submit.getPurchaseType());
        plan.setStatus("待采购");
        for(ProjectEntity project:plan.getProjects()){
            project.setStatus("待采购");
        }
        planDao.update(plan);
    }

    @Override
    public void dropPlan(String planId) {
        PlanEntity plan = findByPId(planId);
        for(ProjectEntity project:plan.getProjects()){
            project.setStatus("已立项");
        }
        planDao.delete(plan);
    }

    public PlanEntity findByPId(String planId) {
        DetachedCriteria dc = DetachedCriteria.forClass(PlanEntity.class);
        dc.add(Restrictions.eq("planId",planId));
        return planDao.findByCriteria(dc);
    }
    @Override
    public PlanMessage findByPlanId(String planId) {
        DetachedCriteria dc = DetachedCriteria.forClass(PlanEntity.class);
        dc.add(Restrictions.eq("planId",planId));
        return TransformUtil.transformToMessage(planDao.findByCriteria(dc));
    }

    @Override
    public void submitContract(ContractEntity contract) {
        PlanEntity plan = findByPId(contract.getPlanNum());
        contractDao.save(contract);
    }

    @Override
    public List<PurchaseApplySubmit> listProjectsListSetUp() {
        DetachedCriteria dc = DetachedCriteria.forClass(ProjectEntity.class);
        dc.add(Restrictions.eq("status","已立项"));
        List<ProjectEntity> entityList = projectDao.listByCriteria(dc);
        return TransformUtil.transformToMessageList(entityList);
    }
}
