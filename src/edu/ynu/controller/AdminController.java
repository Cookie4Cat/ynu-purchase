package edu.ynu.controller;

import edu.ynu.message.PurchaseApplySubmit;
import edu.ynu.message.PurchaseHistoryRecord;
import edu.ynu.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {
    private final static int CountPerPage = 8;
    @Autowired
    private AdminService adminService;
    @RequestMapping("/projects/count")
    public Integer findAllProjectsCount()
            throws Exception {
        return adminService.findAllProjectsCount();
    }
    @RequestMapping("/projects")
    public List<PurchaseApplySubmit> findAll(Integer currentPage) throws Exception{
        return adminService.findAllProjects(CountPerPage,currentPage);
    }

    @RequestMapping(value = "/projects/search",method = RequestMethod.GET)
    public List<PurchaseApplySubmit> findAllByCondition (String projectId,String type,String status)throws Exception{
        return adminService.findAllByCondition(projectId,type,status);
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
