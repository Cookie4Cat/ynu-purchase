package edu.ynu.entity;

import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Set;


@Entity
@Table(name = "contract",schema = "purchase")
public class ContractEntity {
    private int id;
    private String contractId;
    private Set<PlanEntity> planEntities = new HashSet<PlanEntity>();

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

    @OneToMany(mappedBy = "contractEntity", fetch = FetchType.EAGER)
    @Cascade(value = {org.hibernate.annotations.CascadeType.ALL})
    public Set<PlanEntity> getPlanEntities() {
        return planEntities;
    }

    public void setPlanEntities(Set<PlanEntity> planEntities) {
        this.planEntities = planEntities;
    }
}
