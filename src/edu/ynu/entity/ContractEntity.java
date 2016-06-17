package edu.ynu.entity;

import javax.persistence.*;


@Entity
@Table(name = "contract",schema = "purchase")
public class ContractEntity {
    private int id;
    private String contractId;

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
}
