package edu.ynu.dao;

import edu.ynu.entity.ProjectEntity;
import org.hibernate.criterion.DetachedCriteria;

import java.util.List;

public interface ProjectDao {
    /***
     * 根据userId返回project实体列表
     * @param userId 用户id
     * @return project实体
     */
    List<ProjectEntity> getProjectsByUID(String userId);

    /***
     * 根据id返回project实体对象
     * @param id project的id
     * @return project实体对象
     */
    ProjectEntity getProjectById(int id);

    /***
     * 根据project示例对象，返回project对象列表
     * @param example 实例对象
     * @return project列表
     */
    List<ProjectEntity> getProjectsByExample(ProjectEntity example);

    /***
     * 保存项目申请表
     * @param projectEntity 项目实体
     * @return 1：成功，0：失败
     */
    void savePurchaseApply(ProjectEntity projectEntity);

    /***
     * 根据用户ID返回项目申请书草稿
     * @param userId 用户ID
     * @return 项目草稿List
     */
    List<ProjectEntity> findProjectsDraftByUID(String userId);

    /***
     * 保存草稿
     * @param userId 用户id
     * @param entity 草稿实体
     */
    void saveProjectDraftByUID(String userId,ProjectEntity entity);

    /***
     * 根据用户ID,返回未完成采购（status!=采购完成）的project实体列表
     * @param userId 用户ID
     * @param pageCount 每页个数
     * @param pageNum 页码
     * @return project实体列表
     */
    List<ProjectEntity> findProjectsUnComplete(String userId,Integer pageCount,Integer pageNum);

    /***
     * 根据userId返回未完成project个数
     * @param userId 用户ID
     * @return projects个数
     */
    Integer countProjectsUnComplete(String userId);
    /***
     * 根据用户id和项目的状态列表返回项目实体列表
     * @param userId 用户Id
     * @param statusList 状态列表
     * @param pageCount 每页个数
     * @param pageNum 页码
     * @return project实体列表
     */
    List<ProjectEntity> findProjectListByUidAndStatus(String userId,String[] statusList,Integer pageCount,Integer pageNum);

    /***
     * 根据用户id,和project状态信息列表返回个数
     * @param userId 用户id
     * @param statusList 状态列表
     * @return project个数
     */
    Integer countProjectListByUidAndStatus(String userId,String[] statusList);

    /***
     * 根据状态返回project列表个数
     * @param statusList 状态列表
     * @return 个数
     */
    Integer countProjectListByStatus(String[] statusList);

    /***
     * 根据状态分页返回project列表
     * @param statusList 状态
     * @param countPerPage 每页个数
     * @param currentPage 页码
     * @return 列表
     */
    List<ProjectEntity> findProjectListByStatus(String[] statusList,Integer countPerPage,Integer currentPage);

    /***
     * 根据条件返回project列表
     * @param projectId 项目id 例如K2016050401
     * @param type      类型
     * @param status    状态
     * @return project列表
     */
    List<ProjectEntity> findProjectsByCondition(String projectId,String type,String status);

    /***
     * 根据projectId返回entity
     * @param projectId pid
     * @return project实体
     */
    ProjectEntity findProjectByPId(String projectId);

    /***
     * 添加初审意见
     * @param projectId pid
     * @param suggestion 意见
     */
    void addSuggestion(String projectId,String suggestion,String status);

    /***
     * 立项
     * @param projectId pid
     */
    void setProjectUp(String projectId);

    List<ProjectEntity> listByCriteria(DetachedCriteria criteria,Integer countPerPage,Integer pageNum);
    Integer countByCriteria(DetachedCriteria criteria);
    List<ProjectEntity> listByCriteria(DetachedCriteria criteria);
    ProjectEntity findByCriteria(DetachedCriteria criteria);
    void deleteById(Integer id);
    void update(ProjectEntity projectEntity);
    void saveOrUpdate(ProjectEntity projectEntity);
    void delete(ProjectEntity projectEntity);
    void save(ProjectEntity projectEntity);
}