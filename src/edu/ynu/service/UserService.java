package edu.ynu.service;

public interface UserService {
	
	/**
	 * description: 
	 * @param userId 用户帐号
	 * @return 用户实体，若无此用户返回空
	 */
	User finUserById(String userId);
	
	
	

}
