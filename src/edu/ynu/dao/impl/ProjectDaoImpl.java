package edu.ynu.dao.impl;

import edu.ynu.dao.ProjectDao;
import edu.ynu.entity.ProjectEntity;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;

public class ProjectDaoImpl implements ProjectDao {
    private  SessionFactory sessionFactory;
    @Autowired
    public ProjectDaoImpl(SessionFactory sessionFactory){
        this.sessionFactory = sessionFactory;
    }
    private Session currentSession(){
        return this.sessionFactory.getCurrentSession();
    }
    @Override
    public List<ProjectEntity> getProjectsByUID(String userId) {
        String hql = "from ProjectEntity projects where projects.userId=:userIdContent";
        Query query = this.currentSession().createQuery(hql);
        query.setString("userIdContent",userId);
        List projectList = query.list();
        return projectList;
    }

    @Override
    public List<ProjectEntity> getProjectsByExample(ProjectEntity example) {
        return null;
    }

    @Override
    public ProjectEntity getProjectById(int id) {
        return null;
    }
}
