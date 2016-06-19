package edu.ynu.entity;

import org.hibernate.annotations.Cascade;

import javax.persistence.*;


@Entity
@Table(name = "plan", schema = "purchase")
public class PlanEntity {
    private int id;
    private String planId;
    private  ContractEntity contractEntity;

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

    @ManyToOne(fetch = FetchType.EAGER)
    @Cascade(value = {org.hibernate.annotations.CascadeType.SAVE_UPDATE})
    @JoinColumn(name = "contract_id")
    public ContractEntity getContractEntity() {
        return contractEntity;
    }

    public void setContractEntity(ContractEntity contractEntity) {
        this.contractEntity = contractEntity;
    }
}
