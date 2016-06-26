package edu.ynu.service;

import edu.ynu.entity.ContractEntity;
import edu.ynu.entity.PlanEntity;
import edu.ynu.message.PlanSubmit;
import edu.ynu.message.PurchaseApplySubmit;
import edu.ynu.message.PurchaseItem;

import java.util.List;

public interface RecorderService {
    void submitPlan(PlanSubmit submit);
    Integer countHandlingPlan();
    List<PlanEntity> listHandlingPlan(Integer countPerPage,Integer pageNum);
    Integer countHistoryPlan();
    List<PlanEntity> listHistoryPlan(Integer countPerPage,Integer pageNum);
    void replyPlan(PlanSubmit submit);
    void dropPlan(String planId);
    PlanEntity findByPlanId(String planId);
    void submitContract(ContractEntity contract);
    List<PurchaseApplySubmit> listProjectsListSetUp();
}
