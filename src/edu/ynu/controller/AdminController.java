package edu.ynu.controller;

import edu.ynu.message.PurchaseApplySubmit;
import edu.ynu.message.SampleMessage;
import edu.ynu.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private AdminService adminService;
    @RequestMapping("/projects/handling/count")
    public Integer findAllProjectsCount()
            throws Exception {
        return adminService.countHandlingProjects();
    }
    @RequestMapping("/projects/handling")
    public List<PurchaseApplySubmit> findAll(Integer countPerPage,Integer pageNum) throws Exception{
        return adminService.listHandlingProjects(countPerPage, pageNum);
    }

    @RequestMapping(value = "/projects/handling/search",method = RequestMethod.GET)
    public List<PurchaseApplySubmit> findAllByCondition (String projectId,String type,String status,
                                                         Integer countPerPage,Integer pageNum)throws Exception{
        return adminService.listHandlingProjectsByCondition(projectId,type,status,countPerPage,pageNum);
    }

    @RequestMapping(value = "/projects/handling/search/count",method = RequestMethod.GET)
    public Integer countAllByCondition (String projectId,String type,String status)throws Exception{
        return adminService.countHandlingProjectsByCondition(projectId,type,status);
    }

    @RequestMapping("/projects/history/count")
    public Integer findHistoryProjectsCount()
            throws Exception {
        return adminService.countHistoryProjects();
    }
    @RequestMapping("/projects/history")
    public List<PurchaseApplySubmit> listHistory(Integer countPerPage,Integer pageNum) throws Exception{
        return adminService.listHistoryProjects(countPerPage, pageNum);
    }

    @RequestMapping(value = "/projects/history/search",method = RequestMethod.GET)
    public List<PurchaseApplySubmit> findHistoryByCondition (String projectId,String type,String status,
                                                         Integer countPerPage,Integer pageNum)throws Exception{
        return adminService.listHistoryProjectsByCondition(projectId,type,status,countPerPage,pageNum);
    }

    @RequestMapping(value = "/projects/history/search/count",method = RequestMethod.GET)
    public Integer countHistoryByCondition (String projectId,String type,String status)throws Exception{
        return adminService.countHistoryProjectsByCondition(projectId,type,status);
    }

    @RequestMapping(value = "/projects/{id}",method = RequestMethod.GET)
    public PurchaseApplySubmit findOneProject(@PathVariable String id)throws Exception{
        return adminService.findOneProject(id);
    }

    @RequestMapping(value = "/projects/{id}/suggestion",method = RequestMethod.POST)
    public Integer addProjectSuggestion(@PathVariable String id, @RequestBody SampleMessage message)throws Exception{
        adminService.addProjectSuggestion(id,message.getContent(),message.getFlag());
        return 1;
    }
    @RequestMapping(value = "/projects/{id}/setup",method = RequestMethod.POST)
    public Integer setProjectUp(@PathVariable String id) throws Exception{
        adminService.setProjectUp(id);
        return 1;
    }
}
