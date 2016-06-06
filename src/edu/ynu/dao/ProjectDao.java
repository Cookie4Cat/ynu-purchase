package edu.ynu.dao;

import edu.ynu.entity.ProjectEntity;

import java.util.List;

public interface ProjectDao {
    /***
     * 根据userId返回project实体列表
     * @param userId 用户id
     * @return project实体
     */
    public List<ProjectEntity> getProjectsByUID(String userId);

    /***
     * 根据id返回project实体对象
     * @param id project的id
     * @return project实体对象
     */
    public ProjectEntity getProjectById(int id);

    /***
     * 根据project示例对象，返回project对象列表
     * @param example 实例对象
     * @return project列表
     */
    public List<ProjectEntity> getProjectsByExample(ProjectEntity example);

}
