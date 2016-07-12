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
    @RequestMapping("/projects/handling/all")
    public List<PurchaseApplySubmit> listAllHandling() throws Exception{
        return adminService.listAllHandlingProjects();
    }
    @RequestMapping("/projects/history/all")
    public List<PurchaseApplySubmit> listAllHistory() throws Exception{
        return adminService.listAllHistoryProjects();
    }
}
