package edu.ynu.util;

import edu.ynu.entity.ItemEntity;
import edu.ynu.entity.ProjectEntity;
import edu.ynu.message.PurchaseApplySubmit;
import edu.ynu.message.PurchaseItem;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MapUtil {
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
    public static void mapMessage(final PurchaseApplySubmit message, ProjectEntity entity){
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
    public static void mapEntity(final ProjectEntity entity,PurchaseApplySubmit message){
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

}
