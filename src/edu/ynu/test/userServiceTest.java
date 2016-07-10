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

	@Test
	public void testGetSubmitHistory() {
		List<PurchaseHistoryRecord> historyList = userService.findSubmitHistoryById("1");
		for (PurchaseHistoryRecord record : historyList) {
			System.out.println(record.toString());
		}
	}
	@Test
	public void testStoreDraft(){
		PurchaseApplySubmit pas = new PurchaseApplySubmit();
		pas.setProjectName("测试项目名称");
		pas.setComeFrom("测试资金来源");
		pas.setPurchaseType("测试资产类型");
		pas.setTotalMoney_pre(25000d);
		List<PurchaseItem> itemList = new ArrayList<>();
		PurchaseItem item = new PurchaseItem();
		item.setName("测试设备名称");
		item.setType("测试设备类型");
		itemList.add(item);
		PurchaseItem item1 = new PurchaseItem();
		item1.setName("测试设备名称1");
		itemList.add(item1);
		pas.setTable(itemList);
		userService.storePurchaseApplyDraft("laohuang",pas);
	}
	@Test
	public void testStoreApply(){
		PurchaseApplySubmit pas = new PurchaseApplySubmit();
		pas.setProjectName("测试项目名称");
		pas.setPurchaseType("测试资产类型");
		List<PurchaseItem> itemList = new ArrayList<>();
		PurchaseItem item = new PurchaseItem();
		item.setName("测试设备名称");
		item.setType("测试设备类型");
		itemList.add(item);
		pas.setTable(itemList);
		userService.submitPurchaseApply("laohuang",pas);
	}
	@Test
	public void testFindAllDraft(){
		List<PurchaseApplySubmit> applyList= userService.findStoredPurchaseApplyDraft("laohuang");
		for(PurchaseApplySubmit pas:applyList){
			System.out.println(pas.getProjectName());
			for(PurchaseItem item : pas.getTable()){
				System.out.println(item.getName());
			}
		}
	}
	@Test
	public void findProjectComplete(){
		List<PurchaseHistoryRecord> records = userService.findSubmitHistoryByIdCompleted("laohuang",1,1);
		for(PurchaseHistoryRecord record:records){
			System.out.println(record.getProName());
		}
	}
	@Test
	public void findProjectUnComplete(){
		List<PurchaseHistoryRecord> records = userService.findSubmitHistoryByIdUnCompleted("laohuang",1,1);
		for(PurchaseHistoryRecord record:records){
			System.out.println(record.getProName());
		}
	}

	@Test
	public void testLogin(){
		LoginMessage message = userService.login("laohuang","pypy");
		System.out.println(message.getType());
		System.out.println(message.getToken());
	}
}
