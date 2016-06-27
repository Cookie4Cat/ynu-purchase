package edu.ynu.message;

public class PlanDetailMessage {
    //计划
    private PlanMessage plan;
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

    public PlanMessage getPlan() {
        return plan;
    }

    public void setPlan(PlanMessage plan) {
        this.plan = plan;
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
}
