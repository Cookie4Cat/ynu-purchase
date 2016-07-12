package edu.ynu.service;

import edu.ynu.message.PurchaseApplySubmit;
import edu.ynu.message.PurchaseHistoryRecord;

import java.util.List;

public interface TeacherService {

	//保存草稿
	void saveDraftByUID(String userId, PurchaseApplySubmit submit);
	//返回草稿
	PurchaseApplySubmit findDraftByUID(String userId);
	//返回项目信息
	PurchaseApplySubmit findByPID(String pid);
	//提交项目信息
	void submitPurchaseApply(PurchaseApplySubmit submit,String userId);
	//更新项目信息
	void updatePurchaseApply(PurchaseApplySubmit submit,String projectId);

	String getCurrentProjectId();
	//返回待处理列表
	List<PurchaseApplySubmit> listAllHandlingProjects(String teacherId);
	//返回历史列表
	List<PurchaseApplySubmit> listAllHistoryProjects(String teacherId);
}
