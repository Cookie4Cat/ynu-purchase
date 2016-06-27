package edu.ynu.test;

import edu.ynu.entity.ContractEntity;
import edu.ynu.entity.PlanEntity;
import edu.ynu.message.PlanMessage;
import edu.ynu.message.PlanSubmit;
import edu.ynu.service.RecorderService;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.ArrayList;
import java.util.List;

public class RecorderServiceTest {
    private ApplicationContext ctx;
    private RecorderService recorderService;
    private void print(Object str){
        System.out.println(str);
    }
    @Before
    public void setUp(){
        ctx  = new ClassPathXmlApplicationContext("applicationContext.xml");
        recorderService = (RecorderService)ctx.getBean("recorderService");
    }
    @Test
    public void testSubmitPlan(){
        PlanSubmit submit = new PlanSubmit();
        submit.setPlanId("PC20160626");
        submit.setPrePurchaseType("拟批复采购形式");
        submit.setPreOrgType("拟批复组织形式");
        List<String> projectList = new ArrayList<>();
        projectList.add("2016062601");
        submit.setProjectIdList(projectList);
        recorderService.submitPlan(submit);
    }
    @Test
    public void testCountHandlingPlan(){
        print(recorderService.countHandlingPlan());
    }
    @Test
    public void testListHandlingPlan(){
        List<PlanMessage> planList = recorderService.listHandlingPlan(1,1);
        for(PlanMessage plan:planList){
            print(plan.getPlanId());
        }
    }
    @Test
    public void testCountHistoryPlan(){
        print(recorderService.countHistoryPlan());
    }
    @Test
    public void testListHistoryPlan(){
        List<PlanMessage> planList = recorderService.listHistoryPlan(1,1);
        for(PlanMessage plan:planList){
            print(plan.getPlanId());
        }
    }
    @Test
    public void testReplyPlan(){
        PlanSubmit submit = new PlanSubmit();
        submit.setPlanId("PC20160626");
        submit.setOrgType("组织形式1");
        submit.setPurchaseType("组织形式2");
        recorderService.replyPlan(submit);
    }
    @Test
    public void testDropPlan(){
        recorderService.dropPlan("PC20160627");
    }
    @Test
    public void testFindByPlanId(){
        PlanMessage plan = recorderService.findByPlanId("PC20160626");
        print(plan.getPlanId());
    }
//    @Test
//    public void testSubmitContract(){
//        ContractEntity contract = new ContractEntity();
//        contract.setPlanNum("PC20160626");
//        contract.setConstractName("合同名字1");
//        recorderService.submitContract(contract);
//    }
}
