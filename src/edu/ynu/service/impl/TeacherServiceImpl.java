package edu.ynu.service.impl;

import edu.ynu.dao.ProjectDao;
import edu.ynu.entity.ProjectEntity;
import edu.ynu.message.PurchaseApplySubmit;
import edu.ynu.service.TeacherService;
import edu.ynu.util.TransformUtil;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Transactional
public class TeacherServiceImpl implements TeacherService{
    @Autowired
    private ProjectDao projectDao;
    @Override
    public PurchaseApplySubmit findDraftByUID(String userId) {
        DetachedCriteria dc = DetachedCriteria.forClass(ProjectEntity.class);
        dc.add(Restrictions.eq("userId",userId));
        return TransformUtil.toMessage(projectDao.findByCriteria(dc));
    }
    @Override
    public void saveDraftByUID(String userId, PurchaseApplySubmit submit) {
        ProjectEntity entity = TransformUtil.toEntity(submit);
        entity.setStatus("草稿");
        projectDao.save(entity);
    }

    @Override
    public PurchaseApplySubmit findByPID(String pid) {
        DetachedCriteria dc = DetachedCriteria.forClass(ProjectEntity.class);
        dc.add(Restrictions.eq("projectId",pid));
        return TransformUtil.toMessage(projectDao.findByCriteria(dc));
    }

    @Override
    public List<PurchaseApplySubmit> listHandlingProjects(String teacherId, Integer countPerPage, Integer pageNum) {
        DetachedCriteria dc = DetachedCriteria.forClass(ProjectEntity.class);
        String[] status = {"待审核","初审被驳","待立项"};
        dc.add(Restrictions.in("status",status));
        return TransformUtil.transformToMessageList(projectDao.listByCriteria(dc,countPerPage,pageNum));
    }

    @Override
    public Integer countHandingProjects(String teacherId) {
        DetachedCriteria dc = DetachedCriteria.forClass(ProjectEntity.class);
        String[] status = {"待审核","初审被驳","待立项"};
        dc.add(Restrictions.in("status",status));
        return projectDao.countByCriteria(dc);
    }

    @Override
    public List<PurchaseApplySubmit> listHistorySubmit(String teacherId, Integer count, Integer pageNum) {
        DetachedCriteria dc = DetachedCriteria.forClass(ProjectEntity.class);
        String[] status = {"采购完成"};
        dc.add(Restrictions.in("status",status));
        return TransformUtil.transformToMessageList(projectDao.listByCriteria(dc,count,pageNum));
    }

    @Override
    public Integer countHistorySubmit(String teacherId) {
        DetachedCriteria dc = DetachedCriteria.forClass(ProjectEntity.class);
        String[] status = {"采购完成"};
        dc.add(Restrictions.in("status",status));
        return projectDao.countByCriteria(dc);
    }

    @Override
    public void submitPurchaseApply(PurchaseApplySubmit submit,String userId) {
        ProjectEntity entity = TransformUtil.toEntity(submit);
        entity.setUserId(userId);
        String now = String.valueOf(new Timestamp(new Date().getTime()));  //当前时间
        entity.setSubmitTime(now);
        entity.setStatus("待审核");
    }
}
