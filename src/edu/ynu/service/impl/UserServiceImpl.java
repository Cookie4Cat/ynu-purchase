package edu.ynu.service.impl;

import edu.ynu.dao.ProjectDao;
import edu.ynu.dao.UserDao;
import edu.ynu.entity.ProjectEntity;
import edu.ynu.entity.UserEntity;
import edu.ynu.message.PurchaseApplySubmit;
import edu.ynu.message.PurchaseHistoryRecord;
import edu.ynu.service.UserService;

import java.io.File;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import edu.ynu.util.TransformUtil;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;
    @Autowired
    private ProjectDao projectDao;
    public static void main(String[] args){

    }
    private String getCurrentProjectId(){
        DetachedCriteria dc = DetachedCriteria.forClass(ProjectEntity.class);
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
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
    private Integer savePurchaseApply(String userId, PurchaseApplySubmit pas,String status){
        ProjectEntity projectEntity = TransformUtil.toEntity(pas);
        projectEntity.setUserId(userId);
        String now = String.valueOf(new Timestamp(new Date().getTime()));  //当前时间
        projectEntity.setSubmitTime(now);
        projectEntity.setStatus(status);
        if(!status.equals("草稿")){
            projectEntity.setProjectId(getCurrentProjectId());
        }
        projectDao.save(projectEntity);
        return 1;
    }
    @Override
    public UserEntity finUserById(String userId) {
        return  userDao.findUserById(userId);
    }

    @Override
    public List<PurchaseHistoryRecord> findSubmitHistoryById(String userId) {
        List<ProjectEntity> projectList = projectDao.getProjectsByUID(userId);
        return TransformUtil.transformToRecordMessage(projectList);
    }

    @Override
    public Integer storePurchaseApplyDraft(String userId, PurchaseApplySubmit pas) {
        return savePurchaseApply(userId,pas,"草稿");
    }

    @Override
    public Integer submitPurchaseApply(String userId, PurchaseApplySubmit pas) {
        return savePurchaseApply(userId,pas,"待审核");
    }

    @Override
    public List<PurchaseApplySubmit> findStoredPurchaseApplyDraft(String userId) {
        List<PurchaseApplySubmit> applyList = new ArrayList<>();
        List<ProjectEntity> projectList = projectDao.findProjectsDraftByUID(userId);
        for(ProjectEntity pe:projectList){
            applyList.add(TransformUtil.toMessage(pe));
        }
        return applyList;
    }

    @Override
    public Map<String, Object> downloadPurchaseApplySheet(String projectId) {
        Map<String,Object> map = new HashMap<>();
        File file = new File("test.pdf");
        map.put("paf测试文件",file);
        return map;
    }

    @Override
    public List<PurchaseHistoryRecord> findSubmitHistoryByIdCompleted(String userId, Integer CountPerGet, Integer page) {
        String[] status = {"采购完成"};
        List<ProjectEntity> entityList = projectDao.findProjectListByUidAndStatus(userId,status,CountPerGet,page);
        return TransformUtil.transformToRecordMessage(entityList);
    }

    @Override
    public List<PurchaseHistoryRecord> findSubmitHistoryByIdUnCompleted(String userId, Integer CountPerGet, Integer page) {
        String[] status = {"待审核","初审被驳","待立项"};
        List<ProjectEntity> entityList = projectDao.findProjectListByStatus(status,CountPerGet,page);
        return TransformUtil.transformToRecordMessage(entityList);
    }
}
