package edu.ynu.service;

import edu.ynu.message.*;

import java.util.List;

public interface RecorderService {
    void submitPlan(PlanSubmit submit);
    void replyPlan(PlanSubmit submit);
    void dropPlan(String planId);
    PlanMessage findByPlanId(String planId);
    void submitContract(ContractMessage contract,String planId);
    List<PurchaseApplySubmit> listProjectsListSetUp();
    PlanDetailMessage findPlanDetailByPId(String pid);
    List<PlanMessage> listAllHandlingPlan();
    List<PlanMessage> listAllHistoryPlan();
}
