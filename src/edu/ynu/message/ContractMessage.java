package edu.ynu.message;

import java.util.List;

public class ContractMessage {
    //采购计划的编号
    private String planId;
    //批复形式
    private String orgType;
    //采购方式
    private String purchaseType;
    //合同编号
    private String contractId;
    //合同名字
    private String contractName;
    //中标单位
    private String biddingCompany;
    //中标时间
    private String bidTime;
    //谈判时间
    private String negotiateTime;
    //项目列表
    List<PurchaseApplySubmit> projectList;

    public String getPlanId() {
        return planId;
    }

    public void setPlanId(String planId) {
        this.planId = planId;
    }

    public String getOrgType() {
        return orgType;
    }

    public void setOrgType(String orgType) {
        this.orgType = orgType;
    }

    public String getPurchaseType() {
        return purchaseType;
    }

    public void setPurchaseType(String purchaseType) {
        this.purchaseType = purchaseType;
    }

    public String getContractId() {
        return contractId;
    }

    public void setContractId(String contractId) {
        this.contractId = contractId;
    }

    public String getContractName() {
        return contractName;
    }

    public void setContractName(String contractName) {
        this.contractName = contractName;
    }

    public String getBiddingCompany() {
        return biddingCompany;
    }

    public void setBiddingCompany(String biddingCompany) {
        this.biddingCompany = biddingCompany;
    }

    public String getBidTime() {
        return bidTime;
    }

    public void setBidTime(String bidTime) {
        this.bidTime = bidTime;
    }

    public String getNegotiateTime() {
        return negotiateTime;
    }

    public void setNegotiateTime(String negotiateTime) {
        this.negotiateTime = negotiateTime;
    }

    public List<PurchaseApplySubmit> getProjectList() {
        return projectList;
    }

    public void setProjectList(List<PurchaseApplySubmit> projectList) {
        this.projectList = projectList;
    }
}
