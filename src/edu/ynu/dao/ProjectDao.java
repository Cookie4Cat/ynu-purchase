package edu.ynu.dao;

import edu.ynu.entity.ProjectEntity;
import org.hibernate.criterion.DetachedCriteria;

import java.util.List;

public interface ProjectDao {

    List<ProjectEntity> getProjectsByUID(String userId);

    List<ProjectEntity> findProjectsDraftByUID(String userId);

    List<ProjectEntity> findProjectListByUidAndStatus(String userId,String[] statusList,Integer pageCount,Integer pageNum);

    List<ProjectEntity> findProjectListByStatus(String[] statusList,Integer countPerPage,Integer currentPage);

    ProjectEntity findProjectByPId(String projectId);

    Integer countByCriteria(DetachedCriteria criteria);

    List<ProjectEntity> listByCriteria(DetachedCriteria criteria);

    ProjectEntity findByCriteria(DetachedCriteria criteria);

    void deleteById(Integer id);

    void update(ProjectEntity projectEntity);

    void saveOrUpdate(ProjectEntity projectEntity);

    void delete(ProjectEntity projectEntity);

    void save(ProjectEntity projectEntity);

    ProjectEntity find(Integer id);

    List<ProjectEntity> listProjectsByUidAndStatus(String userId,String[] status);
    List<ProjectEntity> listProjectByStatus(String[] status);
}