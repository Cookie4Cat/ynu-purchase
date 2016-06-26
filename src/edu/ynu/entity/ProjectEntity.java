package edu.ynu.entity;

import org.hibernate.annotations.*;
import org.hibernate.annotations.CascadeType;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "purchase_project", schema = "purchase")
public class ProjectEntity {
    // 采购项目的主键
    private int id;
    // 采购项目的ID
    private String projectId;
    // 用户的ID
    private String userId;
    // 申请人的名字
    private String proposerName;
    // 申请人的电话
    private String proposerMobile;
    // 申请人的固定电话
    private String proposerTel;
    // 经办人的名字
    private String agentName;
    // 经办人的电话
    private String agentMobile;
    // 经办人的固定电话
    private String agentTel;
    // 采购项目的名称
    private String projectName;
    // 采购类型
    private String purchaseType;
    // 总预算
    private String sum;
    // 资金来源
    private String fundSource;
    // 购置理由
    private String applyReason;
    // 登记日期
    private String submitTime;
    // 初审意见
    private String comment;
    // 采购项目的状态
    private String status;
    //打印文件的url
    private String fileUrl;
    //对应的采购计划
    private PlanEntity plan;
    // 所包含的采购设备
    private Set<ItemEntity> items = new HashSet<ItemEntity>();

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
    @Column(name = "user_id", length = 45)
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Basic
    @Column(name = "agent_name", length = 45)
    public String getAgentName() {
        return agentName;
    }

    public void setAgentName(String agentName) {
        this.agentName = agentName;
    }

    @Basic
    @Column(name = "agent_mobile", length = 45)
    public String getAgentMobile() {
        return agentMobile;
    }

    public void setAgentMobile(String agentMobile) {
        this.agentMobile = agentMobile;
    }

    @Basic
    @Column(name = "agent_tel", length = 45)
    public String getAgentTel() {
        return agentTel;
    }

    public void setAgentTel(String agentTel) {
        this.agentTel = agentTel;
    }

    @Basic
    @Column(name = "apply_reason", length = 500)
    public String getApplyReason() {
        return applyReason;
    }

    public void setApplyReason(String applyReason) {
        this.applyReason = applyReason;
    }

    @Basic
    @Column(name = "comment",  length = 500)
    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Basic
    @Column(name = "fund_source", length = 200)
    public String getFundSource() {
        return fundSource;
    }

    public void setFundSource(String fundSource) {
        this.fundSource = fundSource;
    }

    @Basic
    @Column(name = "project_id", length = 100)
    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    @Basic
    @Column(name = "proposer_name", length = 45)
    public String getProposerName() {
        return proposerName;
    }

    public void setProposerName(String proposerName) {
        this.proposerName = proposerName;
    }

    @Basic
    @Column(name = "project_name", length = 45)
    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    @Basic
    @Column(name = "proposer_mobile", length = 45)
    public String getProposerMobile() {
        return proposerMobile;
    }

    public void setProposerMobile(String proposerMobile) {
        this.proposerMobile = proposerMobile;
    }

    @Basic
    @Column(name = "proposer_tel", length = 45)
    public String getProposerTel() {
        return proposerTel;
    }

    public void setProposerTel(String proposerTel) {
        this.proposerTel = proposerTel;
    }

    @Basic
    @Column(name = "purchase_type", length = 45)
    public String getPurchaseType() {
        return purchaseType;
    }

    public void setPurchaseType(String purchaseType) {
        this.purchaseType = purchaseType;
    }

    @Basic
    @Column(name = "status", length = 45)
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Basic
    @Column(name = "submit_time", length = 100)
    public String getSubmitTime() {
        return submitTime;
    }

    public void setSubmitTime(String submitTime) {
        this.submitTime = submitTime;
    }

    @Basic
    @Column(name = "sum", length = 45)
    public String getSum() {
        return sum;
    }

    public void setSum(String sum) {
        this.sum = sum;
    }

    @Basic
    @Column(name = "file_url", length = 45)
    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    //双向绑定
    //配置project实体与item实体一对多的关系
    @OneToMany(mappedBy = "project", fetch = FetchType.EAGER)
    @Cascade(value = {CascadeType.ALL})
    public Set<ItemEntity> getItems() {
        return items;
    }

    public void setItems(Set<ItemEntity> items) {
        this.items = items;
    }

    //绑定采购计划
    @ManyToOne(fetch = FetchType.LAZY)
    @Cascade(value = {CascadeType.SAVE_UPDATE})
    @JoinColumn(name = "plan_id")

    public PlanEntity getPlan() {
        return plan;
    }

    public void setPlan(PlanEntity plan) {
        this.plan = plan;
    }
}