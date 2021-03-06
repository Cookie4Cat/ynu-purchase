package edu.ynu.entity;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "plan", schema = "purchase")
public class PlanEntity {
    //采购计划的主键
    private int id;
    //采购计划的编号
    private String planId;
    //拟组织形式
    private String preOrgType;
    //拟采购方式
    private String prePurchaseType;
    //组织形式
    private String orgType;
    //采购方式
    private String purchaseType;
    //提交时间
    private String time;
    //状态
    private String status;
    //合同号
    private String contractId;
    //预计处理时间
    private String preFinishTime;
    //对应的采购项目
    private Set<ProjectEntity> projects = new HashSet<ProjectEntity>();


    @Id
    @GeneratedValue
    @Column(name = "id",unique = true,nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "plan_id",length = 45)
    public String getPlanId() {
        return planId;
    }

    public void setPlanId(String planId) {
        this.planId = planId;
    }

    @Basic
    @Column(name = "pre_purchase_type",length = 45)
    public String getPrePurchaseType() {
        return prePurchaseType;
    }

    public void setPrePurchaseType(String prePurchaseType) {
        this.prePurchaseType = prePurchaseType;
    }

    @Basic
    @Column(name = "pre_reply_type",length = 45)
    public String getPreOrgType() {
        return preOrgType;
    }

    public void setPreOrgType(String preReplyType) {
        this.preOrgType = preReplyType;
    }

    @Basic
    @Column(name = "purchase_type",length = 45)
    public String getOrgType() {
        return orgType;
    }

    public void setOrgType(String purchaseType) {
        this.orgType = purchaseType;
    }

    @Basic
    @Column(name = "reply_type",length = 45)
    public String getPurchaseType() {
        return purchaseType;
    }

    public void setPurchaseType(String replyType) {
        this.purchaseType = replyType;
    }

    @Basic
    @Column(name = "time",length = 45)
    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Basic
    @Column(name="status",length = 45)
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Basic
    @Column(name = "pre_finish_time", length = 45)
    public String getPreFinishTime() {
        return preFinishTime;
    }

    public void setPreFinishTime(String preFinishTime) {
        this.preFinishTime = preFinishTime;
    }

    @OneToMany(mappedBy = "plan", fetch = FetchType.EAGER)
    @Cascade(value = {CascadeType.SAVE_UPDATE})
    public Set<ProjectEntity> getProjects() {
        return projects;
    }

    public void setProjects(Set<ProjectEntity> projects) {
        this.projects = projects;
    }

    @Basic
    @Column(name="contract_num",length = 45)
    public String getContractId() {
        return contractId;
    }

    public void setContractId(String contractId) {
        this.contractId = contractId;
    }


}
