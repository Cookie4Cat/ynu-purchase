package edu.ynu.message;

/**
 * description: 采购申请里的采购项目
 * @author cdsnow2017@163.com
 * @since 2016年6月7日 下午9:06:02
 * @version 1.0
 */
public class PurchaseItem {
	//id
	private Integer id;
	// 类型
	private String type;
	// 通用名称
	private String name;
	// 数量
	private Double count;
	// 计量单位
	private String unit;
	// 预算单价
	private Double budget;
	// 合计金额
	private Double totalMoney_real;
	// 交货地点
	private String address;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getCount() {
		return count;
	}

	public void setCount(Double count) {
		this.count = count;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public Double getBudget() {
		return budget;
	}

	public void setBudget(Double budget) {
		this.budget = budget;
	}

	public Double getTotalMoney_real() {
		return totalMoney_real;
	}

	public void setTotalMoney_real(Double totalMoney_real) {
		this.totalMoney_real = totalMoney_real;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

}
