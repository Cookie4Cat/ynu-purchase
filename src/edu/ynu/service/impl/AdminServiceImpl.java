package edu.ynu.service.impl;

import edu.ynu.dao.ProjectDao;
import edu.ynu.entity.ProjectEntity;
import edu.ynu.message.PurchaseApplySubmit;
import edu.ynu.service.AdminService;
import edu.ynu.util.PdfUtil;
import edu.ynu.util.TransformUtil;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import java.io.IOException;
import java.util.List;

@Transactional
public class AdminServiceImpl implements AdminService {
    @Autowired
    private ProjectDao projectDao;
    private final String[] historyStatusList = {"被驳历史","已立项","待采购","采购完成"};
    private final String[] handlingStatusList = {"待审核","待立项"};

    private ProjectEntity findOneProjectEntity(String projectId){
        DetachedCriteria dc = DetachedCriteria.forClass(ProjectEntity.class);
        dc.add(Restrictions.eq("projectId",projectId));
        return projectDao.findByCriteria(dc);
    }
    @Override
    public PurchaseApplySubmit findOneProject(String projectId) {
        return TransformUtil.toMessage(findOneProjectEntity(projectId));
    }

    @Override
    public void addProjectSuggestion(String projectId, String suggestion,String result) {

        ProjectEntity entity = findOneProjectEntity(projectId);
        entity.setComment(suggestion);
        if(result.equals("approve")){
            entity.setStatus("待立项");
            try{
                PdfUtil pdfUtil = new PdfUtil();
                pdfUtil.createProjectPdfFile(entity);
            }catch (IOException e){
                e.printStackTrace();
            }
            entity.setFileUrl("/public/generate/" + entity.getProjectId() + ".pdf");
        }else {
            entity.setStatus("初审被驳");
        }
        projectDao.update(entity);
    }

    @Override
    public void setProjectUp(String projectId) {
        ProjectEntity entity = findOneProjectEntity(projectId);
        entity.setStatus("已立项");
        projectDao.update(entity);
    }

    @Override
    public List<PurchaseApplySubmit> listAllHandlingProjects() {
        List<ProjectEntity> list = projectDao.listProjectByStatus(handlingStatusList);
        return TransformUtil.transformToMessageList(list);
    }

    @Override
    public List<PurchaseApplySubmit> listAllHistoryProjects() {
        List<ProjectEntity> list = projectDao.listProjectByStatus(historyStatusList);
        return TransformUtil.transformToMessageList(list);
    }
}
