package edu.ynu.service;

import edu.ynu.message.PurchaseApplySubmit;
import edu.ynu.message.PurchaseHistoryRecord;

import java.util.List;

public interface TeacherService {

	void saveDraftByUID(String userId, PurchaseApplySubmit submit);

	PurchaseApplySubmit findDraftByUID(String userId);

	PurchaseApplySubmit findByPID(String pid);

	List<PurchaseApplySubmit> listHandlingProjects(String teacherId,Integer countPerPage,Integer pageNum);

	Integer countHandingProjects(String teacherId);

	Integer countHistorySubmit(String teacherId);

	List<PurchaseApplySubmit> listHistorySubmit(String teacherId,Integer count,Integer pageNum);

	void submitPurchaseApply(PurchaseApplySubmit submit,String userId);

	void updatePurchaseApply(PurchaseApplySubmit submit,String projectId);
}
