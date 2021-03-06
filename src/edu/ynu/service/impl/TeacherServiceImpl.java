package edu.ynu.service.impl;

import edu.ynu.dao.ItemDao;
import edu.ynu.dao.ProjectDao;
import edu.ynu.dao.TokenDao;
import edu.ynu.entity.ItemEntity;
import edu.ynu.entity.ProjectEntity;
import edu.ynu.message.PurchaseApplySubmit;
import edu.ynu.message.PurchaseItem;
import edu.ynu.service.TeacherService;
import edu.ynu.util.DateUtil;
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
    @Autowired
    private TokenDao tokenDao;

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
    public String getCurrentProjectId(){
        final String PRESTR = "XN";
        DetachedCriteria dc = DetachedCriteria.forClass(ProjectEntity.class);
        String nowStr = DateUtil.getNow("yyyyMMdd");
        String nowLike = PRESTR + nowStr + "%";
        dc.add(Restrictions.like("projectId",nowLike));
        Integer count = projectDao.countByCriteria(dc);
        if(count==0){
            return PRESTR + nowStr+ "001";
        }else {
            DetachedCriteria dc1 = DetachedCriteria.forClass(ProjectEntity.class);
            dc1.setProjection(Projections.max("projectId"));
            dc1.add(Restrictions.like("projectId",nowLike));
            String lastProjectId = String.valueOf(projectDao.listByCriteria(dc1).get(0));
            Long currentNum = Long.valueOf(lastProjectId.substring(2)) + 1;
            return PRESTR + currentNum;
        }
    }
    @Override
    public void submitPurchaseApply(PurchaseApplySubmit submit,String userId) {
        ProjectEntity entity = TransformUtil.toEntity(submit);
        if(userId != null){
        entity.setUserId(userId);
        }
        String agentName = tokenDao.getUserNameByUID(userId);
        entity.setAgentName(agentName);
        String now = String.valueOf(new Timestamp(new Date().getTime()));  //当前时间
        entity.setSubmitTime(now);
        entity.setStatus("待审核");
        entity.setProjectId(getCurrentProjectId());
        System.out.println(getCurrentProjectId());
        projectDao.save(entity);
    }

    @Override
    public void updatePurchaseApply(PurchaseApplySubmit submit, String projectId) {
        DetachedCriteria dc = DetachedCriteria.forClass(ProjectEntity.class);
        dc.add(Restrictions.eq("projectId",projectId));
        ProjectEntity entity = projectDao.findByCriteria(dc);
        String userId = entity.getUserId();
        entity.setStatus("被驳历史");
        projectDao.update(entity);
        for(PurchaseItem item:submit.getTable()){
            item.setId(null);
        }
        submitPurchaseApply(submit,userId);
    }

    private final String[] handlingStatus = {"待审核","待立项","初审被驳"};
    private final String[] historyStatus = {"已立项","待采购", "采购完成"};
    @Override
    public List<PurchaseApplySubmit> listAllHandlingProjects(String teacherId) {
        List<ProjectEntity> list = projectDao.listProjectsByUidAndStatus(teacherId,handlingStatus);
        return TransformUtil.transformToMessageList(list);
    }
    @Override
    public List<PurchaseApplySubmit> listAllHistoryProjects(String teacherId) {
        List<ProjectEntity> list = projectDao.listProjectsByUidAndStatus(teacherId,historyStatus);
        return TransformUtil.transformToMessageList(list);
    }

}
