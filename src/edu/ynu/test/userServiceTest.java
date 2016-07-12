package edu.ynu.test;

import edu.ynu.message.LoginMessage;
import edu.ynu.message.PurchaseApplySubmit;
import edu.ynu.message.PurchaseHistoryRecord;
import edu.ynu.message.PurchaseItem;
import edu.ynu.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.ArrayList;
import java.util.List;

public class userServiceTest {
	private ApplicationContext ctx;
	private UserService userService;

	@Before
	public void setUp() {
		ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
		userService = (UserService) ctx.getBean("userService");
	}
}
