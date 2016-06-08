package edu.ynu.dao.impl;

import edu.ynu.dao.ProjectDao;
import edu.ynu.entity.ProjectEntity;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.type.IntegerType;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;

public class ProjectDaoImpl extends BaseDao<ProjectEntity> implements ProjectDao {
    private  SessionFactory sessionFactory;
    @Autowired
    public ProjectDaoImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
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
        return super.find(id);
    }

    @Override
    public void savePurchaseApply(String userId, ProjectEntity projectEntity) {
        super.save(projectEntity);
    }

    @Override
    public List<ProjectEntity> findProjectsDraftByUID(String userId) {
        String status = "草稿";
        String hql = "from ProjectEntity projects where projects.status=:status and projects.userId=:userId";
        Query query = this.currentSession().createQuery(hql);
        query.setString("userId",userId);
        query.setString("status",status);
        List<ProjectEntity> projectDrafts = query.list();
        return projectDrafts;
    }

}
