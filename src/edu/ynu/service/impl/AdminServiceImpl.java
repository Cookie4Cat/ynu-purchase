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
    public Integer countHandlingProjects() {
        String[] statusList= {"待立项","待初审"};
        DetachedCriteria dc = DetachedCriteria.forClass(ProjectEntity.class);
        dc.add(Restrictions.in("status",statusList));
        return projectDao.countByCriteria(dc);
    }

    @Override
    public List<PurchaseApplySubmit> listHandlingProjects(Integer countPerPage, Integer currentPage) {
        String[] statusList= {"待立项","待审核"};
        DetachedCriteria dc = DetachedCriteria.forClass(ProjectEntity.class);
        dc.add(Restrictions.in("status",statusList));
        List<ProjectEntity> list = projectDao.listByCriteria(dc,countPerPage,currentPage);
        return TransformUtil.transformToMessageList(list);
    }

    @Override
    public Integer countHandlingProjectsByCondition(String pid, String type, String status) {
        DetachedCriteria dc = DetachedCriteria.forClass(ProjectEntity.class);
        if(pid!=null&&!pid.equals("")){
            dc.add(Restrictions.eq("projectId",pid));
        }
        if(type!=null&&!type.equals("")){
            dc.add(Restrictions.eq("purchaseType",type));
        }
        if(status!=null&&!status.equals("")){
            dc.add(Restrictions.eq("status",status));
        }
        String[] statusList= {"待立项","待审核"};
        dc.add(Restrictions.in("status",statusList));
        return projectDao.countByCriteria(dc);
    }

    @Override
    public List<PurchaseApplySubmit> listHandlingProjectsByCondition(String pid, String type, String status,
                                                                     Integer countPerPage,Integer pageNum) {
        DetachedCriteria dc = DetachedCriteria.forClass(ProjectEntity.class);
        if(pid!=null&&!pid.equals("")){
            dc.add(Restrictions.eq("projectId",pid));
        }
        if(type!=null&&!type.equals("")){
            dc.add(Restrictions.eq("purchaseType",type));
        }
        if(status!=null&&!status.equals("")){
            dc.add(Restrictions.eq("status",status));
        }
        String[] statusList= {"待立项","待审核"};
        dc.add(Restrictions.in("status",statusList));
        List<ProjectEntity> list = projectDao.listByCriteria(dc,countPerPage,pageNum);
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
