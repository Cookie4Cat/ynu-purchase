package edu.ynu.service.impl;

import edu.ynu.dao.ProjectDao;
import edu.ynu.entity.ProjectEntity;
import edu.ynu.message.PurchaseApplySubmit;
import edu.ynu.message.PurchaseHistoryRecord;
import edu.ynu.service.TeacherService;
import edu.ynu.util.MapUtil;
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
        PurchaseApplySubmit submit = new PurchaseApplySubmit();
        MapUtil.mapEntity(draft.get(0),submit);
        return submit;
    }

    @Override
    public Integer saveDraftByUID(String userId, PurchaseApplySubmit submit) {
        return null;
    }

    @Override
    public Integer findSubmitHistoryByIdUnCompletedPageCount(String userId) {
        return null;
    }
}
