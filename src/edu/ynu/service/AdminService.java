package edu.ynu.service;

import edu.ynu.message.PurchaseApplySubmit;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;

public interface AdminService {

    Integer countHandlingProjects();
    List<PurchaseApplySubmit> listHandlingProjects(Integer countPerPage, Integer currentPage);
    Integer countHandlingProjectsByCondition(String pid,String type,String status);
    List<PurchaseApplySubmit> listHandlingProjectsByCondition(String pid, String type, String status,
                                                              Integer countPerPage,Integer pageNum);

    Integer countHistoryProjects();
    List<PurchaseApplySubmit> listHistoryProjects(Integer countPerPage, Integer currentPage);
    Integer countHistoryProjectsByCondition(String pid,String type,String status);
    List<PurchaseApplySubmit> listHistoryProjectsByCondition(String pid, String type, String status,
                                                             Integer countPerPage,Integer pageNum);

    PurchaseApplySubmit findOneProject(String projectId);
    void addProjectSuggestion(String projectId,String suggestion,String result);
    void setProjectUp(String projectId);
    //=============重构后的方法
    List<PurchaseApplySubmit> listAllHandlingProjects();
    List<PurchaseApplySubmit> listAllHistoryProjects();

}
