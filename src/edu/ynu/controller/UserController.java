package edu.ynu.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import edu.ynu.entity.AssetsEntity;
import edu.ynu.service.UserService;
import edu.ynu.util.BeanFactory;
import edu.ynu.util.State;

@RestController
@RequestMapping("/user")
public class UserController {
	@Autowired
	private UserService userService;

	@RequestMapping("/login")
	public @ResponseBody State login(@RequestBody Map map) throws Exception {
		String username = (String) map.get("username");
		String password = (String) map.get("password");
		User user = userService.finUserById(username);
		if(user == null ){
			return BeanFactory.getState("0");
		}
		if(user.getPassword().equals(password) ){
			return BeanFactory.getState(User.get);
		}
		return null;

	} 

}
