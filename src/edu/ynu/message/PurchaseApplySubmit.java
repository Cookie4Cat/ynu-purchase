package edu.ynu.message;

import java.util.List;

/**
 * description:采购申请信息登记
 * @author cdsnow2017@163.com
 * @since 2016年6月7日 下午8:59:26
 * @version 1.0
 */
public class PurchaseApplySubmit {
	//项目id
	private String projectId;
	// 采购类型
	private String purchaseType;
	// 项目名称
	private String projectName;
	// 项目负责人
	private String leader;
	// 电话
	private String m_tel;
	// 固话
	private String s_tel;
	//代理人电话
	private String angentMobile;
	// 预算总额
	private Double totalMoney_pre;
	// 资金来源
	private String comeFrom;
	// 购置理由
	private String reason;
	//项目状态
	private String status;
	//提交时间
	private String updateTime;
	//文件url
	private String fileUrl;
	//初审意见
	private String suggestion;
	//采购项目清单
	private List<PurchaseItem> table;

	public String getAngentMobile() {
		return angentMobile;
	}

	public void setAngentMobile(String angentMobile) {
		this.angentMobile = angentMobile;
	}

	public String getSuggestion() {
		return suggestion;
	}

	public void setSuggestion(String suggestion) {
		this.suggestion = suggestion;
	}

	public String getFileUrl() {
		return fileUrl;
	}

	public void setFileUrl(String fileUrl) {
		this.fileUrl = fileUrl;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public String getPurchaseType() {
		return purchaseType;
	}

	public void setPurchaseType(String purchaseTyp) {
		this.purchaseType = purchaseTyp;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getLeader() {
		return leader;
	}

	public void setLeader(String leader) {
		this.leader = leader;
	}

	public String getM_tel() {
		return m_tel;
	}

	public void setM_tel(String m_tel) {
		this.m_tel = m_tel;
	}

	public String getS_tel() {
		return s_tel;
	}

	public void setS_tel(String s_tel) {
		this.s_tel = s_tel;
	}

	public Double getTotalMoney_pre() {
		return totalMoney_pre;
	}

	public void setTotalMoney_pre(Double totalMoney_pre) {
		this.totalMoney_pre = totalMoney_pre;
	}

	public String getComeFrom() {
		return comeFrom;
	}

	public void setComeFrom(String comeFrom) {
		this.comeFrom = comeFrom;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public List<PurchaseItem> getTable() {
		return table;
	}

	public void setTable(List<PurchaseItem> table) {
		this.table = table;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
