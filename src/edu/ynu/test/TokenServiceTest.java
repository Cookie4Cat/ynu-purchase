package edu.ynu.test;

import edu.ynu.service.TokenService;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TokenServiceTest{
    private ApplicationContext ctx;
    private TokenService tokenService;

    @Before
    public void setUp(){
        ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
        tokenService= (TokenService)ctx.getBean("tokenService");
    }

    @Test
    public void testGetUserIdFormToken(){
        System.out.println(tokenService.getUserFormToken("79a70f1d04f71e938e1134decca51236"));
    }

}
