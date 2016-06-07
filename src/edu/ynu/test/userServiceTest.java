package edu.ynu.test;

import edu.ynu.message.采购申报记录;
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
    public void setUp(){
        ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
        userService = (UserService)ctx.getBean("userService");
    }
    @Test
    public void testGetSubmitHistory(){
        List<采购申报记录> historyList = userService.findSubmitHistoryById("1");
        for(采购申报记录 record:historyList){
            System.out.println(record.toString());
        }
    }
}
