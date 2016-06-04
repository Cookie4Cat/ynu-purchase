package edu.ynu.service;

import java.util.List;

import edu.ynu.entity.UserEntity;
import edu.ynu.message.采购申报记录;

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
	 * @return 记录实体列表，中文名便于理解字段意义，可更改
	 */
	List<采购申报记录> findSubmitHistoryById(String userId);

}
