package edu.ynu.test;

import edu.ynu.message.PurchaseApplySubmit;
import edu.ynu.service.AdminService;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

public class AdminServiceTest {
    private ApplicationContext ctx;
    private AdminService adminService;

    private void print(Object str){
        System.out.println(str);
    }

    @Before
    public void setUp() {
        ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
        adminService = (AdminService)ctx.getBean("adminService");
    }
    @Test
    public void testSetUp(){
        adminService.setProjectUp("2016062814");
    }

    @Test
    public void testAddSuggestion(){
        adminService.addProjectSuggestion("2016062814","没问题","approve");
    }
}
