package edu.ynu.service.impl;

import edu.ynu.dao.ContractDao;
import edu.ynu.dao.ItemDao;
import edu.ynu.dao.PlanDao;
import edu.ynu.dao.ProjectDao;
import edu.ynu.entity.ContractEntity;
import edu.ynu.entity.ItemEntity;
import edu.ynu.entity.PlanEntity;
import edu.ynu.entity.ProjectEntity;
import edu.ynu.message.*;
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
    @Autowired
    private ItemDao itemDao;

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
    public void replyPlan(PlanSubmit submit) {
        PlanEntity plan = findByPId(submit.getPlanId());
        plan.setOrgType(submit.getOrgType());
        plan.setPurchaseType(submit.getPurchaseType());
        plan.setPreFinishTime(submit.getPreFinishTime());
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
        plan.setStatus("省审驳回");
        planDao.update(plan);
    }

    private PlanEntity findByPId(String planId) {
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
    public void submitContract(ContractMessage message,String planId) {
        ContractEntity contract = new ContractEntity();
        contract.setPlanNum(planId);
        contract.setBiddingCompany(message.getBiddingCompany());
        contract.setBidTime(message.getBidTime());
        contract.setNegotiateTime(message.getNegotiateTime());
        contract.setContractId(message.getContractId());
        contract.setContractName(message.getContractName());
        contract.setSum(message.getSum());
        PlanEntity plan = planDao.findByPId(planId);
        plan.setContractId(message.getContractId());
        plan.setStatus("采购完成");
        for(ProjectEntity project:plan.getProjects()){
            project.setStatus("采购完成");
        }
        updateAllItems(message.getProjectList());
        planDao.update(plan);
        contractDao.save(contract);
    }
    //更新设备信息
    private void updateAllItems(List<PurchaseApplySubmit> projectList){
        for(PurchaseApplySubmit submit:projectList){
            for(PurchaseItem item:submit.getTable()){
                ItemEntity itemEntity = itemDao.find(item.getId());
                itemEntity.setRealPrice(item.getRealBuget());
                itemEntity.setRealCount(item.getRealCount());
                itemDao.update(itemEntity);
            }
        }
    }
    @Override
    public List<PurchaseApplySubmit> listProjectsListSetUp() {
        DetachedCriteria dc = DetachedCriteria.forClass(ProjectEntity.class);
        dc.add(Restrictions.eq("status","已立项"));
        List<ProjectEntity> entityList = projectDao.listByCriteria(dc);
        return TransformUtil.transformToMessageList(entityList);
    }
    private ContractEntity findContractByPId(String pid){
        DetachedCriteria dc = DetachedCriteria.forClass(ContractEntity.class);
        dc.add(Restrictions.eq("planNum",pid));
        return contractDao.findByCriteria(dc);
    }
    @Override
    public PlanDetailMessage findPlanDetailByPId(String pid) {
        PlanMessage planMessage = findByPlanId(pid);
        ContractEntity contract = findContractByPId(pid);
        PlanDetailMessage detailMessage = new PlanDetailMessage();
        detailMessage.setPlan(planMessage);
        detailMessage.setContractName(contract.getContractName());
        detailMessage.setContractId(contract.getContractId());
        detailMessage.setNegotiateTime(contract.getNegotiateTime());
        detailMessage.setBiddingCompany(contract.getBiddingCompany());
        detailMessage.setBidTime(contract.getBidTime());
        return detailMessage;
    }

    @Override
    public List<PlanMessage> listAllHandlingPlan() {
        List<PlanEntity> list = planDao.listPlansByStatus(handlingStatus);
        return TransformUtil.transformToPlanMessageList(list);
    }

    @Override
    public List<PlanMessage> listAllHistoryPlan() {
        List<PlanEntity> list = planDao.listPlansByStatus(historyStatus);
        return TransformUtil.transformToPlanMessageList(list);
    }
}
