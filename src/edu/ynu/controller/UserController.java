package edu.ynu.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

import javax.servlet.http.HttpServletRequest;

import edu.ynu.message.LoginMessage;
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
	public LoginMessage login(@RequestBody Map map) throws Exception {
		String username = (String) map.get("userName");
		String password = (String) map.get("password");
		return userService.login(username,password);
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
