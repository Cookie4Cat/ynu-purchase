package edu.ynu.message;

import java.util.List;

public class PlanSubmit {
    //采购计划的编号
    private String planId;
    //拟批复形式
    private String preReplyType;
    //拟采购方式
    private String prePurchaseType;
    //批复形式
    private String purchaseType;
    //采购方式
    private String replyType;
    //提交时间
    private String time;
    //批次勾选的项目id列表
    List<String> projectIdList;
}
