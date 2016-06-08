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
    private int id;
    private String projectId;
    private String userId;
    private String proposerName;
    private String proposerMobile;
    private String proposerTel;
    private String agentName;
    private String agentMobile;
    private String agentTel;
    private String projectName;
    private String purchaseType;
    private String sum;
    private String fundSource;
    private String applyReason;
    private String submitTime;
    private String comment;
    private String status;
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
    @Column(name = "user_id", nullable = true, length = 45)
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Basic
    @Column(name = "agent_name", nullable = true, length = 45)
    public String getAgentName() {
        return agentName;
    }

    public void setAgentName(String agentName) {
        this.agentName = agentName;
    }

    @Basic
    @Column(name = "agent_mobile", nullable = true, length = 45)
    public String getAgentMobile() {
        return agentMobile;
    }

    public void setAgentMobile(String agentMobile) {
        this.agentMobile = agentMobile;
    }

    @Basic
    @Column(name = "agent_tel", nullable = true, length = 45)
    public String getAgentTel() {
        return agentTel;
    }

    public void setAgentTel(String agentTel) {
        this.agentTel = agentTel;
    }

    @Basic
    @Column(name = "apply_reason", nullable = true, length = 500)
    public String getApplyReason() {
        return applyReason;
    }

    public void setApplyReason(String applyReason) {
        this.applyReason = applyReason;
    }

    @Basic
    @Column(name = "comment", nullable = true, length = 500)
    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Basic
    @Column(name = "fund_source", nullable = true, length = 200)
    public String getFundSource() {
        return fundSource;
    }

    public void setFundSource(String fundSource) {
        this.fundSource = fundSource;
    }

    @Basic
    @Column(name = "project_id", nullable = true, length = 100)
    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    @Basic
    @Column(name = "proposer_name", nullable = true, length = 45)
    public String getProposerName() {
        return proposerName;
    }

    public void setProposerName(String proposerName) {
        this.proposerName = proposerName;
    }

    @Basic
    @Column(name = "project_name", nullable = true, length = 45)
    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    @Basic
    @Column(name = "proposer_mobile", nullable = true, length = 45)
    public String getProposerMobile() {
        return proposerMobile;
    }

    public void setProposerMobile(String proposerMobile) {
        this.proposerMobile = proposerMobile;
    }

    @Basic
    @Column(name = "proposer_tel", nullable = true, length = 45)
    public String getProposerTel() {
        return proposerTel;
    }

    public void setProposerTel(String proposerTel) {
        this.proposerTel = proposerTel;
    }

    @Basic
    @Column(name = "purchase_type", nullable = true, length = 45)
    public String getPurchaseType() {
        return purchaseType;
    }

    public void setPurchaseType(String purchaseType) {
        this.purchaseType = purchaseType;
    }

    @Basic
    @Column(name = "status", nullable = true, length = 45)
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Basic
    @Column(name = "submit_time", nullable = true, length = 100)
    public String getSubmitTime() {
        return submitTime;
    }

    public void setSubmitTime(String submitTime) {
        this.submitTime = submitTime;
    }

    @Basic
    @Column(name = "sum", nullable = true, length = 45)
    public String getSum() {
        return sum;
    }

    public void setSum(String sum) {
        this.sum = sum;
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
}