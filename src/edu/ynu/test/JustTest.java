package edu.ynu.test;

import edu.ynu.entity.AssetsEntity;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.sql.DataSource;
import java.sql.SQLException;

public class JustTest {
    public static void main(String[] args) throws SQLException {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
        System.out.println(ctx);
        DataSource dataSource = ctx.getBean(DataSource.class);

        System.out.println(dataSource.getConnection().toString());

        SessionFactory sessionFactory = ctx.getBean(SessionFactory.class);
        System.out.println(sessionFactory);

        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        AssetsEntity assets = new AssetsEntity();
        assets.setDescription("好看逼格高");
        assets.setName("MAC");
        assets.setValue(15000);
        session.save(assets);
        tx.commit();
        session.close();
    }
}
