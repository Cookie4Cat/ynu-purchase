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
    public void testGetToken(){
        String token = tokenService.getToken("1122");
        System.out.println(token);
    }

    @Test
    public void testGetUserIdFormToken(){
        System.out.println(tokenService.getUserFormToken("abcd1"));
    }

}
