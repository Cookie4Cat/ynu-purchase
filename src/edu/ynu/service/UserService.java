package edu.ynu.service;

import edu.ynu.entity.UserEntity;

public interface UserService {
	
	/**
	 * description: 
	 * @param userId 用户帐号
	 * @return 用户实体，若无此用户返回空
	 */
	UserEntity finUserById(String userId);
	
	
	

}
