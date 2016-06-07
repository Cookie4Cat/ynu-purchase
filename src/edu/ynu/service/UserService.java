package edu.ynu.service;

import java.util.List;
import java.util.Map;

import edu.ynu.entity.UserEntity;
import edu.ynu.message.PurchaseApplySubmit;
import edu.ynu.message.purchaseHistoryRecord;

public interface UserService {

	/**
	 * description: 
	 * @param userId 用户帐号
	 * @return 用户实体，若无此用户返回空
	 */
	UserEntity finUserById(String userId);

	/**
	 * description:查询教师申报项目的概况
	 * @param UserId 教师ID
	 * @return 记录实体列表
	 */
	List<purchaseHistoryRecord> findSubmitHistoryById(String userId);

	/**
	 * description:采购申请信息登记草稿存储
	 * @param userId 用户id
	 * @param pas 采购申请实体
	 * @return 1：成功 0：失败
	 */
	Integer storePurchaseApplyDraft(String userId, PurchaseApplySubmit pas);

	/**
	 * description:采购申请信息登记提交（更新至：待初审 状态）
	 * @param userId 用户id
	 * @param pas 采购申请实体
	 * @return 1：成功 0：失败
	 */
	Integer submitPurchaseApply(String userId, PurchaseApplySubmit pas);

	/**
	 * description: 采购申请信息登记草稿详细内容加载
	 * @param userId
	 * @return 
	 */
	List<PurchaseApplySubmit> findStoredPurchaseApplyDraft(String userId);

	/**
	 * description: 下载申请表
	 * @param projectId 项目id
	 * @return "filename":String,"file":byte[] 
	 */
	Map<String, Object> downloadPurchaseApplySheet(String projectId);

}
