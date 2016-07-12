package edu.ynu.dao.impl;

import edu.ynu.dao.ProjectDao;
import edu.ynu.entity.ProjectEntity;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class ProjectDaoImpl extends BaseDao<ProjectEntity> implements ProjectDao {
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
    public List<ProjectEntity> findProjectListByUidAndStatus(String userId, String[] statusList, Integer pageCount, Integer pageNum) {
        String hql = "from ProjectEntity project where project.userId=:userId and project.status in :statusList  ORDER BY project.submitTime DESC";
        Query query = this.currentSession().createQuery(hql);
        query.setFirstResult((pageNum - 1) * pageCount);
        query.setMaxResults(pageCount);
        query.setString("userId",userId);
        query.setParameterList("statusList",statusList);
        List projectList = query.list();
        return projectList;
    }

    @Override
    public List<ProjectEntity> findProjectListByStatus(String[] statusList, Integer countPerPage, Integer currentPage) {
        String hql = "FROM ProjectEntity project where status in :status order by project.submitTime DESC ";
        Query query = currentSession().createQuery(hql);
        query.setFirstResult((currentPage-1)*countPerPage);
        query.setMaxResults(countPerPage);
        query.setParameterList("status",statusList);
        List projects = query.list();
        System.out.println(projects.size());
        return projects;
    }

    @Override
    public ProjectEntity findProjectByPId(String projectId) {
        String hlq = "FROM ProjectEntity  project where project.projectId = :pid";
        Query query = currentSession().createQuery(hlq);
        query.setString("pid",projectId);
        return (ProjectEntity)query.uniqueResult();
    }

    @Override
    public List<ProjectEntity> listProjectsByUidAndStatus(String userId, String[] status) {
        String hql = "from ProjectEntity projects where projects.status in :status and projects.userId=:userId order by projects.submitTime desc ";
        Query query = this.currentSession().createQuery(hql);
        query.setString("userId",userId);
        query.setParameterList("status",status);
        List<ProjectEntity> list = query.list();
        return list;
    }

    @Override
    public List<ProjectEntity> listProjectByStatus(String[] status) {
        String hql = "from ProjectEntity projects where projects.status in :status order by projects.submitTime desc ";
        Query query = this.currentSession().createQuery(hql);
        query.setParameterList("status",status);
        List<ProjectEntity> list = query.list();
        return list;
    }

}
