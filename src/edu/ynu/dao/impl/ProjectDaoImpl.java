package edu.ynu.dao.impl;

import edu.ynu.dao.ProjectDao;
import edu.ynu.entity.ProjectEntity;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
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

    @Override
    public Integer countProjectListByUidAndStatus(String userId, String[] statusList) {
        String hql = "select count(*) from ProjectEntity project where project.userId=:userId and project.status in :statusList";
         Query query = this.currentSession().createQuery(hql);
         query.setString("userId",userId);
         query.setParameterList("statusList",statusList);
         Integer count = query.executeUpdate();
        return count;
    }

    @Override
    public Integer countProjectsUnComplete(String userId) {
        String status1 = "采购完成";
        String status2 = "草稿";
        String hql = "select count(*) from ProjectEntity projects where projects.status!=:status1 and projects.status!=:status2 and projects.userId=:userId";
        Query query = this.currentSession().createQuery(hql);
        query.setString("userId",userId);
        query.setString("status1",status1);
        query.setString("status2",status2);
        Integer count = query.executeUpdate();
        return count;
    }

    @Override
    public List<ProjectEntity> findProjectListByUidAndStatus(String userId, String[] statusList, Integer pageCount, Integer pageNum) {
        String hql = "from ProjectEntity project where project.userId=:userId and project.status in :statusList";
        Query query = this.currentSession().createQuery(hql);
        query.setString("userId",userId);
        query.setParameterList("statusList",statusList);
        List projectList = query.list();
        return projectList;
    }
}
