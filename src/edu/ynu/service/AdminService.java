package edu.ynu.service;

import edu.ynu.message.PurchaseApplySubmit;

import java.util.List;

public interface AdminService {
    PurchaseApplySubmit findOneProject(String projectId);
    void addProjectSuggestion(String projectId,String suggestion,String result);
    void setProjectUp(String projectId);
    List<PurchaseApplySubmit> listAllHandlingProjects();
    List<PurchaseApplySubmit> listAllHistoryProjects();

}
