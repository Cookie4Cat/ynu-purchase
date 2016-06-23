package edu.ynu.service.impl;

import edu.ynu.dao.ProjectDao;
import edu.ynu.entity.ProjectEntity;
import edu.ynu.message.PurchaseApplySubmit;
import edu.ynu.service.AdminService;
import edu.ynu.util.TransformUtil;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public class AdminServiceImpl implements AdminService {
    @Autowired
    private ProjectDao projectDao;
    @Override
    public Integer findAllProjectsCount() {
        String[] statusList= {"待立项","待初审"};
        DetachedCriteria dc = DetachedCriteria.forClass(ProjectEntity.class);
        dc.add(Restrictions.in("status",statusList));
        return projectDao.countByCriteria(dc);
    }

    @Override
    public List<PurchaseApplySubmit> findAllProjects(Integer countPerPage, Integer currentPage) {
        String[] statusList= {"待立项","待审核"};
        DetachedCriteria dc = DetachedCriteria.forClass(ProjectEntity.class);
        dc.add(Restrictions.in("status",statusList));
        List<ProjectEntity> list = projectDao.listByCriteria(dc,countPerPage,currentPage);
        return TransformUtil.transformToMessageList(list);
    }

    @Override
    public List<PurchaseApplySubmit> findAllByCondition(String projectId, String type, String status) {
        DetachedCriteria dc = DetachedCriteria.forClass(ProjectEntity.class);
        if(projectId!=null){
            dc.add(Restrictions.eq("projectId",projectId));
        }
        if(type!=null){
            dc.add(Restrictions.eq("type",type));
        }
        if(status!=null){
            dc.add(Restrictions.eq("status",status));
        }
        List<ProjectEntity> list = projectDao.listByCriteria(dc);
        return TransformUtil.transformToMessageList(list);
    }
    private ProjectEntity findOneProjectEntity(String projectId){
        DetachedCriteria dc = DetachedCriteria.forClass(ProjectEntity.class);
        dc.add(Restrictions.eq("projectId",projectId));
        return projectDao.findByCriteria(dc);
    }
    @Override
    public PurchaseApplySubmit findOneProject(String projectId) {
        return TransformUtil.toMessage(findOneProjectEntity(projectId));
    }

    @Override
    public void addProjectSuggestion(String projectId, String suggestion,String result) {

        ProjectEntity entity = findOneProjectEntity(projectId);
        entity.setComment(suggestion);
        if(result.equals("approve")){
            entity.setStatus("待立项");
        }else {
            entity.setStatus("初审被驳");
        }
        projectDao.update(entity);
    }

    @Override
    public void setProjectUp(String projectId) {
        ProjectEntity entity = findOneProjectEntity(projectId);
        entity.setStatus("已立项");
        projectDao.update(entity);
    }
}
