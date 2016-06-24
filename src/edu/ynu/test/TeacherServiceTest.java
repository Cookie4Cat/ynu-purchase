package edu.ynu.test;

import edu.ynu.message.PurchaseApplySubmit;
import edu.ynu.service.TeacherService;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

public class TeacherServiceTest {
    private ApplicationContext ctx;
    private TeacherService teacherService;
    private void print(Object str){
        System.out.println(str);
    }
    @Before
    public void setUp() {
        ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
        teacherService = (TeacherService) ctx.getBean("teacherService");
    }
    @Test
    public void testListHandlingProjects(){
        List<PurchaseApplySubmit> list = teacherService.listHandlingProjects("laohuang",8,1);
        for(PurchaseApplySubmit submit:list){
            print(submit.getProjectId());
        }
    }
    @Test
    public void testFindDaft(){
        PurchaseApplySubmit submit = teacherService.findDraftByUID("laohuang");
        print(submit.getPurchaseType());
    }

}
