package edu.ynu.service;

import java.util.List;
import java.util.Map;

import edu.ynu.entity.UserEntity;
import edu.ynu.message.LoginMessage;
import edu.ynu.message.PurchaseHistoryRecord;

public interface UserService {

	UserEntity finUserById(String userId);

	List<PurchaseHistoryRecord> findSubmitHistoryById(String userId);

	Map<String, Object> downloadPurchaseApplySheet(String projectId);

	LoginMessage login(String userId, String password);
}
