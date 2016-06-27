package edu.ynu.entity;

import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;

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
    //实际购买数量
    private String realCount;
    //实际购买单价
    private String realPrice;
    // 交货地址;
    private String deliverySite;
    // 计量单位;
    private String unit;
    // 预算总计;
    private String total;
    // 所属的采购项目;
    private ProjectEntity project;
    // 采购设备的类型
    private String type;

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
    @Column(name = "count", length = 45)
    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    @Basic
    @Column(name = "delivery_site", length = 200)
    public String getDeliverySite() {
        return deliverySite;
    }

    public void setDeliverySite(String deliverySite) {
        this.deliverySite = deliverySite;
    }

    @Basic
    @Column(name = "item_name", length = 45)
    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    @Basic
    @Column(name = "price", length = 45)
    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    @Basic
    @Column(name = "total",  length = 45)
    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    @Basic
    @Column(name = "unit",  length = 45)
    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    @Basic
    @Column(name = "real_count",  length = 45)
    public String getRealCount() {
        return realCount;
    }

    public void setRealCount(String realCount) {
        this.realCount = realCount;
    }

    @Basic
    @Column(name = "real_price",  length = 45)
    public String getRealPrice() {
        return realPrice;
    }

    public void setRealPrice(String realPrice) {
        this.realPrice = realPrice;
    }

    @Basic
    @Column(name = "type", length = 45)
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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
