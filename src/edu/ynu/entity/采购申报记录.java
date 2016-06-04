package edu.ynu.entity;

/**
 * description: 用作教师登录后显示已申报的申报记录
 * @author cdsnow2017@163.com
 * @since 2016年6月3日 下午11:00:10
 * @version 1.0
 */
public class 采购申报记录 {

	private String 项目编号;

	private String 采购类型;

	private String 总计金额;

	private String 上次提交日期;

	private String 当前状态;

	private String 初审意见;

	public String get项目编号() {
		return 项目编号;
	}

	public void set项目编号(String 项目编号) {
		this.项目编号 = 项目编号;
	}

	public String get采购类型() {
		return 采购类型;
	}

	public void set采购类型(String 采购类型) {
		this.采购类型 = 采购类型;
	}

	public String get总计金额() {
		return 总计金额;
	}

	public void set总计金额(String 总计金额) {
		this.总计金额 = 总计金额;
	}

	public String get上次提交日期() {
		return 上次提交日期;
	}

	public void set上次提交日期(String 上次提交日期) {
		this.上次提交日期 = 上次提交日期;
	}

	public String get当前状态() {
		return 当前状态;
	}

	public void set当前状态(String 当前状态) {
		this.当前状态 = 当前状态;
	}

	public String get初审意见() {
		return 初审意见;
	}

	public void set初审意见(String 初审意见) {
		this.初审意见 = 初审意见;
	}

}
