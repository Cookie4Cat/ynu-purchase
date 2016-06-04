package edu.ynu.dao;

import edu.ynu.entity.ProjectEntity;

import java.util.List;

public interface ProjectDao {
    public List<ProjectEntity> getProjectsByUID(String userId);
}
