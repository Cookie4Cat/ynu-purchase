package edu.ynu.util;

import edu.ynu.entity.ItemEntity;
import edu.ynu.entity.PlanEntity;
import edu.ynu.entity.ProjectEntity;
import edu.ynu.message.PlanMessage;
import edu.ynu.message.PurchaseApplySubmit;
import edu.ynu.message.PurchaseHistoryRecord;
import edu.ynu.message.PurchaseItem;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TransformUtil {
    private static Double toDouble(String real){
        if(real == null){
            return 0d;
        }else {
            return Double.valueOf(real);
        }
    }
    private static String doubleToString(Double real){
        if(real == null){
            return null;
        }else {
            return String.valueOf(real);
        }
    }
    private static void mapItem2Entity(final List<PurchaseItem> itemList, Set<ItemEntity> itemSet, final ProjectEntity pe){
        for(PurchaseItem pi:itemList){
            ItemEntity item = new ItemEntity();
            if(pi.getId()!=null){
                item.setId(pi.getId());
            }
            item.setItemName(pi.getName());
            item.setCount(doubleToString(pi.getCount()));
            item.setTotal(doubleToString(pi.getTotalMoney_real()));
            item.setDeliverySite(pi.getAddress());
            item.setUnit(pi.getUnit());
            item.setPrice(doubleToString(pi.getBudget()));
            item.setProject(pe);
            item.setType(pi.getType());
            item.setRealCount(pi.getRealCount());
            item.setRealPrice(pi.getRealBuget());
            itemSet.add(item);
        }
    }
    public static void updateEntity(final PurchaseApplySubmit message,ProjectEntity entity){
        entity.setProposerName(message.getLeader());
        entity.setProposerMobile(message.getM_tel());
        entity.setProposerTel(message.getS_tel());
        entity.setProjectName(message.getProjectName());
        entity.setPurchaseType(message.getPurchaseType());
        entity.setSum(doubleToString(message.getTotalMoney_pre()));
        entity.setFundSource(message.getComeFrom());
        entity.setApplyReason(message.getReason());
        entity.setAgentTel(message.getS_tel());
        //转换设备列表
        List<PurchaseItem> itemList = message.getTable();
        //System.out.println(itemList);
        Set<ItemEntity> itemSet = entity.getItems();
        mapItem2Entity(itemList,itemSet,entity);
        //System.out.println(itemSet);
        entity.setItems(itemSet);
        entity.setAgentName("代理人名字");
        entity.setAgentMobile(message.getAgentMobile());
        entity.setAgentTel("代理人固话");
    }
    private static void mapItem2Message(final Set<ItemEntity> itemSet,List<PurchaseItem> itemList){
        for(ItemEntity ie:itemSet){
            PurchaseItem pi = new PurchaseItem();
            pi.setId(ie.getId());
            pi.setName(ie.getItemName());
            pi.setCount(toDouble(ie.getCount()));
            pi.setTotalMoney_real(toDouble(ie.getTotal()));
            pi.setAddress(ie.getDeliverySite());
            pi.setUnit(ie.getUnit());
            pi.setBudget(toDouble(ie.getPrice()));
            pi.setType(ie.getType());
            pi.setRealBuget(ie.getRealPrice());
            pi.setRealCount(ie.getRealCount());
            itemList.add(pi);
        }
    }
    public static ProjectEntity toEntity(final PurchaseApplySubmit message){
        ProjectEntity entity = new ProjectEntity();
        updateEntity(message,entity);
        return entity;
    }
    public static PurchaseApplySubmit toMessage(final ProjectEntity entity){
        PurchaseApplySubmit message = new PurchaseApplySubmit();
        message.setProjectId(entity.getProjectId());
        message.setLeader(entity.getProposerName());
        message.setM_tel(entity.getProposerMobile());
        message.setS_tel(entity.getAgentTel());
        message.setProjectName(entity.getProjectName());
        message.setPurchaseType(entity.getPurchaseType());
        message.setTotalMoney_pre(toDouble(entity.getSum()));
        message.setComeFrom(entity.getFundSource());
        message.setReason(entity.getApplyReason());
        message.setStatus(entity.getStatus());
        message.setUpdateTime(entity.getSubmitTime());
        message.setFileUrl(entity.getFileUrl());
        message.setSuggestion(entity.getComment());
        message.setAgentMobile(entity.getAgentMobile());
        //转换设备列表
        Set<ItemEntity> itemSet = entity.getItems();
        List<PurchaseItem> itemList = new ArrayList<>();
        mapItem2Message(itemSet,itemList);
        message.setTable(itemList);
        return message;
    }
    public static List<PurchaseApplySubmit> transformToMessageList(List<ProjectEntity> entityList){
        List<PurchaseApplySubmit> messageList = new ArrayList<>();
        for(ProjectEntity entity:entityList){
            messageList.add(toMessage(entity));
        }
        return messageList;
    }
    public static List<PurchaseHistoryRecord> transformToRecordMessage(List<ProjectEntity> entityList){
        List<PurchaseHistoryRecord> historyList = new ArrayList<>();
        for(ProjectEntity project : entityList){
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
    public static PlanMessage transformToMessage(PlanEntity entity){
        PlanMessage message = new PlanMessage();
        message.setStatus(entity.getStatus());
        message.setPlanId(entity.getPlanId());
        message.setOrgType(entity.getOrgType());
        message.setPreOrgType(entity.getPreOrgType());
        message.setPrePurchaseType(entity.getPrePurchaseType());
        message.setPurchaseType(entity.getPurchaseType());
        message.setTime(entity.getTime());
        List<PurchaseApplySubmit> projectsList = new ArrayList<>();
        for(ProjectEntity project:entity.getProjects()){
            projectsList.add(toMessage(project));
        }
        message.setProjectsList(projectsList);
        return message;
    }
    public static  List<PlanMessage> transformToPlanMessageList(List<PlanEntity> entityList){
        List<PlanMessage> messageList = new ArrayList<>();
        for(PlanEntity plan:entityList){
            messageList.add(transformToMessage(plan));
        }
        return messageList;
    }
}
