package edu.ynu.dao.impl;

import edu.ynu.dao.ProjectDao;
import edu.ynu.entity.ProjectEntity;
import edu.ynu.message.PurchaseApplySubmit;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
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
        String hql = "select count(*) from ProjectEntity project where project.userId=:userId and project.status in :statusList ";
        Query query = this.currentSession().createQuery(hql);
        query.setString("userId",userId);
        query.setParameterList("statusList",statusList);
        Long count = (Long)query.list().get(0);
        return count.intValue();
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
        Long count = (Long)query.list().get(0);
        return count.intValue();
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
    public void saveProjectDraftByUID(String userId, ProjectEntity entity) {
         List<ProjectEntity> projects = this.findProjectsDraftByUID(userId);
        if (projects.size()==0){
            entity.setUserId(userId);
            entity.setStatus("草稿");
            super.save(entity);
        }else {
            super.delete(projects.get(0));
            entity.setUserId(userId);
            entity.setStatus("草稿");
            super.save(entity);
        }
    }

    @Override
    public void addSuggestion(String projectId, String suggestion,String status) {
        String hlq = "FROM ProjectEntity  project where project.projectId = :pid";
        Query query = currentSession().createQuery(hlq);
        query.setString("pid",projectId);
        List<ProjectEntity> projects = query.list();
        if(projects.size()>0){
            projects.get(0).setComment(suggestion);
            projects.get(0).setStatus(status);
            super.update(projects.get(0));
        }
    }

    @Override
    public Integer countProjectListByStatus(String[] statusList) {
        String hql ="SELECT count(*) from ProjectEntity project where status in :status";
        Query query = currentSession().createQuery(hql);
        query.setParameterList("status",statusList);
        Long count = (Long)query.list().get(0);
        return count.intValue();
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
    public List<ProjectEntity> findProjectsByCondition(String projectId, String type, String status,Integer countPerPage,Integer pageNum, String[] statusList) {
        Criteria criteria = currentSession().createCriteria(ProjectEntity.class);
        criteria.setFirstResult((pageNum-1)*countPerPage);
        criteria.setMaxResults(countPerPage);
        if (projectId!=""){
        criteria.add(Restrictions.eq("projectId",projectId));
        }
        if(type != "") {
            criteria.add(Restrictions.eq("purchaseType", type));
        }
        if (status != "") {
            criteria.add(Restrictions.eq("status", status));
        }
        criteria.add(Restrictions.in("status", statusList));
        criteria.addOrder(Order.desc("submitTime"));
        criteria.setProjection(Projections.groupProperty("id"));

        List<ProjectEntity> projects = criteria.list();
        System.out.println(countPerPage);
        System.out.println(projects.size());
        for(Object o:projects){
            System.out.println(o);
        }
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
    public void setProjectUp(String projectId) {
        String hlq = "FROM ProjectEntity  project where project.projectId = :pid";
        Query query = currentSession().createQuery(hlq);
        query.setString("pid",projectId);
        List<ProjectEntity> projects = query.list();
        if(projects.size()>0){
            projects.get(0).setStatus("已立项");
            super.update(projects.get(0));
        }
    }

    @Override
    public List<ProjectEntity> listProjectByCondition(String pid, String status, String type,
                                                      Integer countPerPage,Integer pageNum) {
        return null;
    }

    @Override
    public List<ProjectEntity> listProjectsByUidAndStatus(String userId, String[] status) {
        String hql = "from ProjectEntity projects where projects.status in :status and projects.userId=:userId";
        Query query = this.currentSession().createQuery(hql);
        query.setString("userId",userId);
        query.setParameter("status",status);
        List<ProjectEntity> list = query.list();
        return list;
    }

    @Override
    public List<ProjectEntity> listProjectByStatus(String[] status) {
        String hql = "from ProjectEntity projects where projects.status in :status";
        Query query = this.currentSession().createQuery(hql);
        query.setParameter("status",status);
        List<ProjectEntity> list = query.list();
        return list;
    }

}
