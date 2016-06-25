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
    public void testFindAllProjects(){
        List<PurchaseApplySubmit> list = adminService.listHandlingProjects(2,2);
        for(PurchaseApplySubmit p:list){
            System.out.println(p.getLeader());
        }
    }
    @Test
    public void  testFindAllByCondition(){
        List<PurchaseApplySubmit> list = adminService.listHandlingProjectsByCondition(null,"国内","采购完成",1,1);
        for(PurchaseApplySubmit p:list){
            System.out.println(p.getLeader());
        }
    }
    @Test
    public void testFindOne(){
        adminService.countHandlingProjects();
    }
    @Test
    public void testFindAllCount(){
        Integer count = adminService.countHandlingProjects();
        print(count);
    }
}
