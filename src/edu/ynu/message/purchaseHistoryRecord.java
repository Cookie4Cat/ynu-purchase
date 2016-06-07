package edu.ynu.message;

/**
 * description: 用作教师登录后显示已申报的申报记录
 * @author cdsnow2017@163.com
 * @since 2016年6月3日 下午11:00:10
 * @version 1.0
 */
public class purchaseHistoryRecord {

	private String projectId;

	private String purchaseType;

	private String totalMoney;

	private String time;

	private String proType;

	private String suggestion;

	private String proName;

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public String getPurchaseType() {
		return purchaseType;
	}

	public void setPurchaseType(String purchaseType) {
		this.purchaseType = purchaseType;
	}

	public String getTotalMoney() {
		return totalMoney;
	}

	public void setTotalMoney(String totalMoney) {
		this.totalMoney = totalMoney;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getProType() {
		return proType;
	}

	public void setProType(String proType) {
		this.proType = proType;
	}

	public String getSuggestion() {
		return suggestion;
	}

	public void setSuggestion(String suggestion) {
		this.suggestion = suggestion;
	}

	public String getProName() {
		return proName;
	}

	public void setProName(String proName) {
		this.proName = proName;
	}

	
}
