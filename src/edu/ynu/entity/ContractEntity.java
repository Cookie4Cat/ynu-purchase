package edu.ynu.entity;

import javax.persistence.*;
@Entity
@Table(name = "contract",schema = "purchase")
public class ContractEntity {
    //合同的主键
    private int id;
    //合同编号
    private String contractId;
    //合同名字
    private String contractName;
    //预计总金额
    private String sum;
    //中标单位
    private String biddingCompany;
    //中标时间
    private String bidTime;
    //谈判时间
    private String negotiateTime;
    //采购计划编号
    private String planNum;
    //一对一的采购计划
//    private PlanEntity plan;

    @Id
    @GeneratedValue
    @Column(name = "id", unique = true, nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "contract_id", length = 45)
    public String getContractId() {
        return contractId;
    }

    public void setContractId(String contractId) {
        this.contractId = contractId;
    }

    @Basic
    @Column(name = "bidding_company", length = 45)
    public String getBiddingCompany() {
        return biddingCompany;
    }

    public void setBiddingCompany(String biddingCompany) {
        this.biddingCompany = biddingCompany;
    }

    @Basic
    @Column(name = "bid_time", length = 45)
    public String getBidTime() {
        return bidTime;
    }

    public void setBidTime(String bidTime) {
        this.bidTime = bidTime;
    }

    @Basic
    @Column(name = "constract_name", length = 45)
    public String getContractName() {
        return contractName;
    }

    public void setContractName(String constractName) {
        this.contractName = constractName;
    }

    @Basic
    @Column(name = "negotiata_time",length = 45)
    public String getNegotiateTime() {
        return negotiateTime;
    }

    public void setNegotiateTime(String negotiataTime) {
        this.negotiateTime = negotiataTime;
    }

    @Basic
    @Column(name = "plan_num", length = 45)
    public String getPlanNum() {
        return planNum;
    }

    public void setPlanNum(String planNum) {
        this.planNum = planNum;
    }

    @Basic
    @Column(name = "sum", length = 45)
    public String getSum() {
        return sum;
    }

    public void setSum(String sum) {
        this.sum = sum;
    }

//    @OneToOne(mappedBy = "constract")
//    public PlanEntity getPlan() {
//        return plan;
//    }
//
//    public void setPlan(PlanEntity plan) {
//        this.plan = plan;
//    }
}
