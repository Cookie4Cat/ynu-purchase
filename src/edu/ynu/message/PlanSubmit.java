package edu.ynu.message;

import java.util.List;

public class PlanSubmit {
    //采购计划的编号
    private String planId;
    //拟批复形式
    private String preOrgType;
    //拟采购方式
    private String prePurchaseType;
    //批复形式
    private String purchaseType;
    //采购方式
    private String replyType;
    //提交时间
    private String time;
    //批次勾选的项目id列表
    List<String> projectIdList;

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

    public String getReplyType() {
        return replyType;
    }

    public void setReplyType(String replyType) {
        this.replyType = replyType;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public List<String> getProjectIdList() {
        return projectIdList;
    }

    public void setProjectIdList(List<String> projectIdList) {
        this.projectIdList = projectIdList;
    }
}
