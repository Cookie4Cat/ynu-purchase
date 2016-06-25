package edu.ynu.controller;

import edu.ynu.message.PurchaseApplySubmit;
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

    @RequestMapping(value = "/projects/search",method = RequestMethod.GET)
    public List<PurchaseApplySubmit> findAllByCondition (String projectId,String type,String status,
                                                         Integer countPerPage,Integer pageNum)throws Exception{
        return adminService.listHandlingProjectsByCondition(projectId,type,status,countPerPage,pageNum);
    }

    @RequestMapping(value = "/projects/search/count",method = RequestMethod.GET)
    public Integer countAllByCondition (String projectId,String type,String status)throws Exception{
        return adminService.countHandlingProjectsByCondition(projectId,type,status);
    }

    @RequestMapping(value = "/projects/{id}",method = RequestMethod.GET)
    public PurchaseApplySubmit findOneProject(@PathVariable String id)throws Exception{
        return adminService.findOneProject(id);
    }

    @RequestMapping(value = "/projects/{id}/suggestion",method = RequestMethod.POST)
    public Integer addProjectSuggestion(@PathVariable String id,String content,String result)throws Exception{
        adminService.addProjectSuggestion(id,content,result);
        return 1;
    }
    @RequestMapping(value = "/projects/{id}/setup",method = RequestMethod.POST)
    public Integer setProjectUp(@PathVariable String id) throws Exception{
        adminService.setProjectUp(id);
        return 1;
    }
}
