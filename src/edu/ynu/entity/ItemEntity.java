package edu.ynu.entity;

import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by Administrator on 2016/6/4.
 */
@Entity
@Table(name = "item", schema = "purchase")
public class ItemEntity {
    // 采购设备的主键;
    private int id;
    // 采购设备的名称;
    private String itemName;
    // 采购该设备的数量;
    private String count;
    // 设备的单价;
    private String price;
    // 交货地址;
    private String deliverySite;
    // 计量单位;
    private String unit;
    // 预算总计;
    private String total;
    // 所属的采购项目;
    private ProjectEntity project;

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
    @Column(name = "count", nullable = false, length = 45)
    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    @Basic
    @Column(name = "delivery_site", nullable = false, length = 200)
    public String getDeliverySite() {
        return deliverySite;
    }

    public void setDeliverySite(String deliverySite) {
        this.deliverySite = deliverySite;
    }

    @Basic
    @Column(name = "item_name", nullable = false, length = 45)
    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    @Basic
    @Column(name = "price", nullable = false, length = 45)
    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    @Basic
    @Column(name = "total", nullable = false, length = 45)
    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    @Basic
    @Column(name = "unit", nullable = false, length = 45)
    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    //双向绑定
    //配置item实体和project实体多对一的关系
    @ManyToOne(fetch = FetchType.EAGER)
    @Cascade(value = {org.hibernate.annotations.CascadeType.SAVE_UPDATE})
    @JoinColumn(name = "project_id")
    public ProjectEntity getProject() {
        return project;
    }

    public void setProject(ProjectEntity project) {
        this.project = project;
    }
}
