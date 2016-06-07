package edu.ynu.service.impl;

import edu.ynu.dao.ProjectDao;
import edu.ynu.dao.UserDao;
import edu.ynu.entity.ProjectEntity;
import edu.ynu.entity.UserEntity;
import edu.ynu.message.purchaseHistoryRecord;
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
	public List<purchaseHistoryRecord> findSubmitHistoryById(String userId) {

        List<purchaseHistoryRecord> historyList = new ArrayList<>();
        List<ProjectEntity> projectList = projectDao.getProjectsByUID(userId);
        for(ProjectEntity project : projectList){
            purchaseHistoryRecord record = new purchaseHistoryRecord();
            record.setProjectId(project.getProjectId());
            record.setProName(project.getProjectName());
            record.setSuggestion(project.getComment());
            record.setTime(project.getSubmitTime());
            record.setTotalMoney(project.getSum());
            record.setProType(project.getStatus());
            record.setPurchaseType(project.getPurchaseType());
            historyList.add(record);
        }
        return historyList;
	}
}
