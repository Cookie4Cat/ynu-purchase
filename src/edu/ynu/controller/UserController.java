package edu.ynu.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

import javax.servlet.http.HttpServletRequest;
import edu.ynu.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import edu.ynu.entity.UserEntity;
import edu.ynu.message.PurchaseHistoryRecord;
import edu.ynu.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {
	@Autowired
	private UserService userService;
	@Autowired
	private TokenService tokenService;

	@RequestMapping("/login")
	public @ResponseBody Map<Object, Object> login(@RequestBody Map map) throws Exception {
		String username = (String) map.get("userName");
		String password = (String) map.get("password");
		UserEntity user = userService.finUserById(username);
		Map<Object, Object> resultMap = new HashMap<>();
		if (user == null) {
			resultMap.put("userType", 0);
		} else if (user.getPassword().equals(password)) {
			String token = tokenService.getToken(username);
			resultMap.put("userType", user.getType());
			resultMap.put("token", token);
		} else {
			resultMap.put("userType", 0);
		}
		return resultMap;
	}

	@RequestMapping("/history/ing")
	public @ResponseBody List<PurchaseHistoryRecord> ing(HttpServletRequest request) throws Exception {
		String userId = (String) request.getAttribute("userId");
		List<PurchaseHistoryRecord> findSubmitHistoryById = userService.findSubmitHistoryById(userId);
		Predicate<PurchaseHistoryRecord> p = (n) -> n.getProType().equals("采购完成");
		findSubmitHistoryById.removeIf(p);
		return findSubmitHistoryById;
	}

	@RequestMapping("/history/completed")
	public @ResponseBody List<PurchaseHistoryRecord> completed(HttpServletRequest request) throws Exception {
		String userId = (String) request.getAttribute("userId");
		List<PurchaseHistoryRecord> findSubmitHistoryById = userService.findSubmitHistoryById(userId);
		Predicate<PurchaseHistoryRecord> p = (n) -> !n.getProType().equals("采购完成");
		findSubmitHistoryById.removeIf(p);
		return findSubmitHistoryById;
	}
	
	

}
