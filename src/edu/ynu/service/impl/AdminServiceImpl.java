package edu.ynu.service.impl;

import edu.ynu.dao.ProjectDao;
import edu.ynu.entity.ProjectEntity;
import edu.ynu.message.PurchaseApplySubmit;
import edu.ynu.service.AdminService;
import edu.ynu.util.TransformUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public class AdminServiceImpl implements AdminService {
    @Autowired
    private ProjectDao projectDao;
    @Override
    public Integer findAllProjectsCount() {
        String[] statusList= {"待立项","待初审"};
        return projectDao.countProjectListByStatus(statusList);
    }

    @Override
    public List<PurchaseApplySubmit> findAllProjects(Integer countPerPage, Integer currentPage) {
        String[] statusList= {"待立项","待初审"};
        List<ProjectEntity> entityList = projectDao.findProjectListByStatus(statusList,countPerPage,currentPage);
        return TransformUtil.transformToMessageList(entityList);
    }

    @Override
    public List<PurchaseApplySubmit> findAllByCondition(String projectId, String type, String status) {
        List<ProjectEntity> entityList = projectDao.findProjectsByCondition(projectId,type,status);
        return TransformUtil.transformToMessageList(entityList);
    }

    @Override
    public PurchaseApplySubmit findOneProject(String projectId) {
        ProjectEntity entity = projectDao.findProjectByPId(projectId);
        return TransformUtil.toMessage(entity);
    }

    @Override
    public Integer addProjectSuggestion(String projectId, String suggestion) {
        projectDao.addSuggestion(projectId,suggestion);
        return 1;//success
    }

    @Override
    public Integer setProjectUp(String projectId) {
        projectDao.setProjectUp(projectId);
        return 1;//success
    }
}
