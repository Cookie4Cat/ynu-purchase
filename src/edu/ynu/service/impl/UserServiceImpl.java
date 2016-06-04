package edu.ynu.service.impl;

import edu.ynu.dao.ProjectDao;
import edu.ynu.dao.UserDao;
import edu.ynu.entity.ProjectEntity;
import edu.ynu.entity.UserEntity;
import edu.ynu.message.采购申报记录;
import edu.ynu.service.UserService;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;
    @Autowired
    private ProjectDao projectDao;

    @Override
    public UserEntity finUserById(String userId) {
        return  userDao.findUserById(userId);
    }

	@Override
	public List<采购申报记录> findSubmitHistoryById(String userId) {

        List<采购申报记录> historyList = new ArrayList<>();
        List<ProjectEntity> projectList = projectDao.getProjectsByUID(userId);
        for(ProjectEntity project : projectList){
            采购申报记录 record = new 采购申报记录();
            record.set项目编号(project.getProjectId());
            record.set项目名称(project.getProjectName());
            record.set初审意见(project.getComment());
            record.set上次提交日期(project.getSubmitTime());
            record.set总计金额(project.getSum());
            record.set当前状态(project.getStatus());
            record.set采购类型(project.getPurchaseType());
            historyList.add(record);
        }
        return historyList;
	}
}
