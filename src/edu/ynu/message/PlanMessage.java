package edu.ynu.message;

import java.util.List;

public class PlanMessage {
    //采购计划的编号
    private String planId;
    //拟批复形式
    private String preOrgType;
    //拟采购方式
    private String prePurchaseType;
    //批复形式
    private String orgType;
    //采购方式
    private String purchaseType;
    //提交时间
    private String time;
    //状态
    private String status;
    //项目列表
    private List<PurchaseApplySubmit> projectsList;

    public String getPlanId() {
        return planId;
    }

    public void setPlanId(String planId) {
        this.planId = planId;
    }

    public String getPreOrgType() {
        return preOrgType;
    }

    public void setPreOrgType(String preOrgType) {
        this.preOrgType = preOrgType;
    }

    public String getOrgType() {
        return orgType;
    }

    public void setOrgType(String orgType) {
        this.orgType = orgType;
    }

    public String getPrePurchaseType() {
        return prePurchaseType;
    }

    public void setPrePurchaseType(String prePurchaseType) {
        this.prePurchaseType = prePurchaseType;
    }

    public String getPurchaseType() {
        return purchaseType;
    }

    public void setPurchaseType(String purchaseType) {
        this.purchaseType = purchaseType;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<PurchaseApplySubmit> getProjectsList() {
        return projectsList;
    }

    public void setProjectsList(List<PurchaseApplySubmit> projectsList) {
        this.projectsList = projectsList;
    }
}
