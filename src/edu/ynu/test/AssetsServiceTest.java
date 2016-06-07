package edu.ynu.test;

import edu.ynu.entity.AssetsEntity;
import edu.ynu.service.AssetsService;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.applet.AppletContext;
import java.util.List;

public class AssetsServiceTest {
    private ApplicationContext ctx;
    private AssetsService assetsService;
    @Before
    public void setUp(){
        ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
        assetsService = (AssetsService)ctx.getBean("assetsService");
    }
    @Test
    public void testFindAll(){
        List<AssetsEntity> list = assetsService.findAll();
        for(AssetsEntity assetsEntity: list){
            System.out.println(assetsEntity.getDescription());
        }
    }
}
