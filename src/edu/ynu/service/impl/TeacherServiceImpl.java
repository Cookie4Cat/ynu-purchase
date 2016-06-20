package edu.ynu.service.impl;

import edu.ynu.dao.ProjectDao;
import edu.ynu.entity.ProjectEntity;
import edu.ynu.message.PurchaseApplySubmit;
import edu.ynu.service.TeacherService;
import edu.ynu.util.TransformUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public class TeacherServiceImpl implements TeacherService{
    @Autowired
    private ProjectDao projectDao;
    @Override
    public Integer findSubmitHistoryByIdCompletedPageCount(String userId) {
        return null;
    }

    @Override
    public PurchaseApplySubmit findDraftByUID(String userId) {
        List<ProjectEntity> draft = projectDao.findProjectsDraftByUID(userId);
        return TransformUtil.toMessage(draft.get(0));
    }

    @Override
    public Integer saveDraftByUID(String userId, PurchaseApplySubmit submit) {
        ProjectEntity entity = TransformUtil.toEntity(submit);
        projectDao.saveProjectDraftByUID(userId,entity);
        return 1;
    }

    @Override
    public Integer findSubmitHistoryByIdUnCompletedPageCount(String userId) {
        return null;
    }
}
