package edu.ynu.service.impl;

import edu.ynu.dao.ProjectDao;
import edu.ynu.dao.UserDao;
import edu.ynu.entity.ItemEntity;
import edu.ynu.entity.ProjectEntity;
import edu.ynu.entity.UserEntity;
import edu.ynu.message.PurchaseApplySubmit;
import edu.ynu.message.PurchaseHistoryRecord;
import edu.ynu.message.PurchaseItem;
import edu.ynu.service.UserService;

import java.io.File;
import java.sql.Timestamp;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;
    @Autowired
    private ProjectDao projectDao;
    private Double toDouble(String real){
        if(real==null){
            return 0d;
        }else {
            return Double.valueOf(real);
        }
    }
    private void mapItem2Entity(final List<PurchaseItem> itemList, Set<ItemEntity> itemSet,final ProjectEntity pe){
        for(PurchaseItem pi:itemList){
            ItemEntity item = new ItemEntity();
            item.setItemName(pi.getName());
            item.setCount(String.valueOf(pi.getCount()));
            item.setTotal(String.valueOf(pi.getTotalMoney_real()));
            item.setDeliverySite(pi.getAddress());
            item.setUnit(pi.getUnit());
            item.setPrice(String.valueOf(pi.getBudget()));
            item.setProject(pe);
            itemSet.add(item);
        }
    }
    private void mapItem2Message(final Set<ItemEntity> itemSet,List<PurchaseItem> itemList){
        for(ItemEntity ie:itemSet){
            PurchaseItem pi = new PurchaseItem();
            pi.setId(ie.getId());
            pi.setName(ie.getItemName());
            pi.setCount(toDouble(ie.getCount()));
            pi.setTotalMoney_real(toDouble(ie.getTotal()));
            pi.setAddress(ie.getDeliverySite());
            pi.setUnit(ie.getUnit());
            pi.setBudget(toDouble(ie.getPrice()));
            itemList.add(pi);
        }
    }
    private void mapMessage(final PurchaseApplySubmit message, ProjectEntity entity){
        entity.setProposerName(message.getLeader());
        entity.setProposerMobile(message.getM_tel());
        entity.setProposerTel(message.getS_tel());
        entity.setProjectName(message.getProjectName());
        entity.setPurchaseType(message.getPurchaseTyp());
        entity.setSum(String.valueOf(message.getTotalMoney_pre()));
        entity.setFundSource(message.getComeFrom());
        entity.setApplyReason(message.getReason());
        //转换设备列表
        List<PurchaseItem> itemList = message.getTable();
        System.out.println(itemList);
        Set<ItemEntity> itemSet = new HashSet<>();
        mapItem2Entity(itemList,itemSet,entity);
        System.out.println(itemSet);
        entity.setItems(itemSet);
        entity.setAgentName("代理人名字");
        entity.setAgentMobile("代理人电话");
        entity.setAgentTel("代理人固话");
    }
    private void mapEntity(final ProjectEntity entity,PurchaseApplySubmit message){
        message.setProjectId(entity.getProjectId());
        message.setLeader(entity.getProjectName());
        message.setM_tel(entity.getAgentMobile());
        message.setS_tel(entity.getProposerTel());
        message.setProjectName(entity.getProjectName());
        message.setPurchaseTyp(entity.getPurchaseType());
        message.setTotalMoney_pre(toDouble(entity.getSum()));
        message.setComeFrom(entity.getFundSource());
        message.setReason(entity.getApplyReason());
        //转换设备列表
        Set<ItemEntity> itemSet = entity.getItems();
        List<PurchaseItem> itemList = new ArrayList<>();
        mapItem2Message(itemSet,itemList);
        message.setTable(itemList);
    }

    private Integer savePurchaseApply(String userId, PurchaseApplySubmit pas,String status){
        ProjectEntity projectEntity = new ProjectEntity();
        projectEntity.setUserId(userId);
        String now = String.valueOf(new Timestamp(new Date().getTime()));  //当前时间
        projectEntity.setSubmitTime(now);
        projectEntity.setStatus(status);
        projectEntity.setProjectId("项目编号");
        //导入其他属性
        mapMessage(pas,projectEntity);
        try{
            projectDao.savePurchaseApply(projectEntity);
        }catch (Exception e){
            return 0;
        }
        return 1;
    }
    @Override
    public UserEntity finUserById(String userId) {
        return  userDao.findUserById(userId);
    }
    @Override
    public List<PurchaseHistoryRecord> findSubmitHistoryById(String userId) {

        List<PurchaseHistoryRecord> historyList = new ArrayList<>();
        List<ProjectEntity> projectList = projectDao.getProjectsByUID(userId);
        for(ProjectEntity project : projectList){
            PurchaseHistoryRecord record = new PurchaseHistoryRecord();
            record.setProjectId(project.getProjectId());
            record.setProName(project.getProjectName());
            record.setSuggestion(project.getComment());
            record.setTime(project.getSubmitTime());
            record.setTotalMoney(project.getSum());
            record.setProType(project.getStatus());
            record.setPurchaseType(project.getPurchaseType());
            historyList.add(record);
        }
        return historyList;
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
            PurchaseApplySubmit ps = new PurchaseApplySubmit();
            mapEntity(pe,ps);
            applyList.add(ps);
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
}
