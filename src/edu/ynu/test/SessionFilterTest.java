package edu.ynu.test;

import edu.ynu.filter.SessionFilter;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SessionFilterTest {
    private ApplicationContext ctx;
    private SessionFilter sessionFilter;
    @Before
    public void setUp(){
        ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
        sessionFilter = (SessionFilter)ctx.getBean("sessionFilter");
    }
}
