package edu.ynu.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import edu.ynu.entity.UserEntity;
import edu.ynu.entity.采购申报记录;
import edu.ynu.service.UserService;
import edu.ynu.util.TokenUtil;

@RestController
@RequestMapping("/user")
public class UserController {
	@Autowired
	private UserService userService;

	@RequestMapping("/login")
	public @ResponseBody Map<Object, Object> login(@RequestBody Map map) throws Exception {
		String username = (String) map.get("username");
		String password = (String) map.get("password");
		UserEntity user = userService.finUserById(username);
		Map<Object, Object> resultMap = new HashMap<>();
		if (user == null) {
			resultMap.put("userType", 0);
		} else if (user.getPassword().equals(password)) {
			TokenUtil tokenUtil = new TokenUtil();
			String token = tokenUtil.getToken(username);
			resultMap.put("userType", user.getType());
			resultMap.put("token", token);
		} else {
			resultMap.put("userType", 0);
		}
		return resultMap;
	}

	@RequestMapping("/user/history/ing")
	public @ResponseBody List<采购申报记录> ing(HttpServletRequest request) throws Exception {
		String userId = (String) request.getAttribute("userId");
		return userService.findSubmitHistoryById(userId);
	}

}
