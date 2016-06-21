package edu.ynu.service;

import edu.ynu.message.PurchaseApplySubmit;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;

public interface AdminService {
    Integer findAllProjectsCount();
    List<PurchaseApplySubmit> findAllProjects(Integer countPerPage,Integer currentPage);
    List<PurchaseApplySubmit> findAllByCondition(String projectId,String type,String status);
    PurchaseApplySubmit findOneProject(String projectId);
    Integer addProjectSuggestion(String projectId,String suggestion,String result);
    Integer setProjectUp(String projectId);
}
