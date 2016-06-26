package edu.ynu.service.impl;

import edu.ynu.dao.ItemDao;
import edu.ynu.dao.ProjectDao;
import edu.ynu.entity.ItemEntity;
import edu.ynu.entity.ProjectEntity;
import edu.ynu.message.PurchaseApplySubmit;
import edu.ynu.service.TeacherService;
import edu.ynu.util.TransformUtil;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Transactional
public class TeacherServiceImpl implements TeacherService{
    @Autowired
    private ProjectDao projectDao;
    @Autowired
    private ItemDao itemDao;

    @Override
    public PurchaseApplySubmit findDraftByUID(String userId) {
        DetachedCriteria dc = DetachedCriteria.forClass(ProjectEntity.class);
        dc.add(Restrictions.eq("userId",userId));
        dc.add(Restrictions.eq("status","草稿"));
        return TransformUtil.toMessage(projectDao.findByCriteria(dc));
    }
    @Override
    public void saveDraftByUID(String userId, PurchaseApplySubmit submit) {
        DetachedCriteria dc = DetachedCriteria.forClass(ProjectEntity.class);
        dc.add(Restrictions.eq("userId",userId));
        dc.add(Restrictions.eq("status","草稿"));
        ProjectEntity testEntity = projectDao.findByCriteria(dc);
        if(testEntity!=null) {
            Set<ItemEntity> setItems = testEntity.getItems();
            itemDao.batchDelete(setItems);
            setItems.clear();
            projectDao.delete(testEntity);
        }
        ProjectEntity otherEntity = TransformUtil.toEntity(submit);
        otherEntity.setUserId(userId);
        otherEntity.setStatus("草稿");
        System.out.println(otherEntity.getSum()==null);
        projectDao.save(otherEntity);
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
        String[] status = {"已立项","待采购", "采购完成"};
        dc.add(Restrictions.in("status",status));
        return TransformUtil.transformToMessageList(projectDao.listByCriteria(dc,count,pageNum));
    }

    @Override
    public Integer countHistorySubmit(String teacherId) {
        DetachedCriteria dc = DetachedCriteria.forClass(ProjectEntity.class);
        String[] status = {"已立项","待采购", "采购完成"};
        dc.add(Restrictions.in("status",status));
        return projectDao.countByCriteria(dc);
    }
    private String getCurrentProjectId(){
        DetachedCriteria dc = DetachedCriteria.forClass(ProjectEntity.class);
        DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        dc.add(Restrictions.like("submitTime",dateFormat.format(new Date())+"%"));
        Integer count = projectDao.countByCriteria(dc);
        if(count==0){
            return dateFormat.format(new Date()) + "01";
        }else {
            DetachedCriteria dc1 = DetachedCriteria.forClass(ProjectEntity.class);
            dc1.setProjection(Projections.max("projectId"));
            dc1.add(Restrictions.like("submitTime",dateFormat.format(new Date())+"%"));
            String lastNumStr = String.valueOf(projectDao.listByCriteria(dc1).get(0));
            Integer currentNum = Integer.valueOf(lastNumStr) + 1;
            return String.valueOf(currentNum);
        }
    }
    @Override
    public void submitPurchaseApply(PurchaseApplySubmit submit,String userId) {
        ProjectEntity entity = TransformUtil.toEntity(submit);
        entity.setUserId(userId);
        String now = String.valueOf(new Timestamp(new Date().getTime()));  //当前时间
        entity.setSubmitTime(now);
        entity.setStatus("待审核");
        entity.setProjectId(getCurrentProjectId());
        projectDao.save(entity);
    }

    @Override
    public void updatePurchaseApply(PurchaseApplySubmit submit, String projectId) {
        DetachedCriteria dc = DetachedCriteria.forClass(ProjectEntity.class);
        dc.add(Restrictions.eq("projectId",projectId));
        ProjectEntity entity = projectDao.findByCriteria(dc);
        entity.setStatus("待审核");
        Set<ItemEntity> setItems = entity.getItems();
        itemDao.batchDelete(setItems);
        setItems.clear();
        TransformUtil.updateEntity(submit,entity);
        projectDao.update(entity);
    }
}
