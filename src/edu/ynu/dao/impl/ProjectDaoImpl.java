package edu.ynu.dao.impl;

import com.sun.xml.internal.bind.v2.model.annotation.RuntimeInlineAnnotationReader;
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
    public void savePurchaseApply(ProjectEntity projectEntity) {
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

    @Override
    public List<ProjectEntity> findProjectsByUidAndStatus(String userId, String status, Integer pageCount, Integer pageNum) {
        String hql = "from ProjectEntity projects where projects.status=:status and projects.userId=:userId";
        Query query = this.currentSession().createQuery(hql);
        query.setFirstResult((pageNum - 1) * pageCount);
        query.setMaxResults(pageCount);
        query.setString("userId",userId);
        query.setString("status",status);
        List projects = query.list();
        return projects;
    }

    @Override
    public List<ProjectEntity> findProjectsUnComplete(String userId, Integer pageCount, Integer pageNum) {
        String status1 = "采购完成";
        String status2 = "草稿";
        String hql = "from ProjectEntity projects where projects.status!=:status1 and projects.status!=:status2 and projects.userId=:userId";
        Query query = this.currentSession().createQuery(hql);
        query.setFirstResult((pageNum - 1) * pageCount);
        query.setMaxResults(pageCount);
        query.setString("userId",userId);
        query.setString("status1",status1);
        query.setString("status2",status2);
        List projectList = query.list();
        return projectList;
    }
}
