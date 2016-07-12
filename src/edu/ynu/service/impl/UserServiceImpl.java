package edu.ynu.service.impl;

import edu.ynu.dao.ProjectDao;
import edu.ynu.dao.UserDao;
import edu.ynu.entity.ProjectEntity;
import edu.ynu.entity.TeacherEntity;
import edu.ynu.entity.UserEntity;
import edu.ynu.message.LoginMessage;

import edu.ynu.message.PurchaseHistoryRecord;
import edu.ynu.service.TokenService;
import edu.ynu.service.UserService;

import java.io.File;
import java.util.*;

import edu.ynu.util.DBUtil;
import edu.ynu.util.MD5Util;
import edu.ynu.util.TransformUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;
    @Autowired
    private ProjectDao projectDao;
    @Autowired
    private TokenService tokenService;

    @Override
    public UserEntity finUserById(String userId) {
        return  userDao.findUserById(userId);
    }

    @Override
    public List<PurchaseHistoryRecord> findSubmitHistoryById(String userId) {
        List<ProjectEntity> projectList = projectDao.getProjectsByUID(userId);
        return TransformUtil.transformToRecordMessage(projectList);
    }

    @Override
    public Map<String, Object> downloadPurchaseApplySheet(String projectId) {
        Map<String,Object> map = new HashMap<>();
        File file = new File("test.pdf");
        map.put("paf测试文件",file);
        return map;
    }

    @Override
    public LoginMessage login(String userId, String password) {
        LoginMessage loginMessage = new LoginMessage();
        loginMessage.setType(0);  //初始化
        //先去教师数据库里查询
        DBUtil dbUtil = new DBUtil();
        String md5Pwd = "0x" + MD5Util.GetMD5Code(password);
        TeacherEntity user = dbUtil.findTeacherByUIDAndPwd(userId,md5Pwd);
        //如果在教师表中查到
        if(user!=null){
            loginMessage.setType(1);   //类型为教师
            String token = tokenService.getToken(userId,user.getName()); //生成或者获取token
            loginMessage.setToken(token);
        }else{
            UserEntity otherTypeUser = finUserById(userId);
            if(otherTypeUser!=null&&otherTypeUser.getPassword().equals(password)){
                loginMessage.setType(otherTypeUser.getType());
                loginMessage.setToken(tokenService.getToken(userId,otherTypeUser.getName()));
                return loginMessage;
            }
        }
        return null;
    }
}
