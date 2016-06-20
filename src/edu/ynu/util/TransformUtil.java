package edu.ynu.util;

import edu.ynu.entity.ItemEntity;
import edu.ynu.entity.ProjectEntity;
import edu.ynu.message.PurchaseApplySubmit;
import edu.ynu.message.PurchaseHistoryRecord;
import edu.ynu.message.PurchaseItem;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TransformUtil {
    private static Double toDouble(String real){
        if(real==null){
            return 0d;
        }else {
            return Double.valueOf(real);
        }
    }
    private static void mapItem2Entity(final List<PurchaseItem> itemList, Set<ItemEntity> itemSet, final ProjectEntity pe){
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
            itemList.add(pi);
        }
    }
    public static ProjectEntity toEntity(final PurchaseApplySubmit message){
        ProjectEntity entity = new ProjectEntity();
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
        return entity;
    }
    public static PurchaseApplySubmit toMessage(final ProjectEntity entity){
        PurchaseApplySubmit message = new PurchaseApplySubmit();
        message.setProjectId(entity.getProjectId());
        message.setLeader(entity.getProposerName());
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
}
