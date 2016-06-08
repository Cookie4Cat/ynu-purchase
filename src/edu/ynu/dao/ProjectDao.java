package edu.ynu.dao;

import edu.ynu.entity.ProjectEntity;

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
     * @param userId 用户ID
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

}
