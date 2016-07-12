package edu.ynu.service;

import edu.ynu.entity.ContractEntity;
import edu.ynu.entity.PlanEntity;
import edu.ynu.message.*;

import java.util.List;

public interface RecorderService {
    void submitPlan(PlanSubmit submit);
    Integer countHandlingPlan();
    List<PlanMessage> listHandlingPlan(Integer countPerPage, Integer pageNum);
    Integer countHistoryPlan();
    List<PlanMessage> listHistoryPlan(Integer countPerPage,Integer pageNum);
    void replyPlan(PlanSubmit submit);
    void dropPlan(String planId);
    PlanMessage findByPlanId(String planId);
    void submitContract(ContractMessage contract,String planId);
    List<PurchaseApplySubmit> listProjectsListSetUp();
    PlanDetailMessage findPlanDetailByPId(String pid);
    //----
    List<PlanMessage> listAllHandlingPlan();
    List<PlanMessage> listAllHistoryPlan();
}
