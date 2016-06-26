package edu.ynu.controller;

import edu.ynu.entity.ContractEntity;
import edu.ynu.entity.PlanEntity;
import edu.ynu.message.PlanSubmit;
import edu.ynu.service.RecorderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/recorder")
public class RecorderController {
    @Autowired
    private RecorderService recorderService;

    @RequestMapping(value = "/plans/{pid}",method = RequestMethod.GET)
    public PlanEntity findPlanById(@PathVariable String pid)throws Exception{
        return recorderService.findByPlanId(pid);
    }

    @RequestMapping(value = "/plans/handling/count",method = RequestMethod.GET)
    public Integer countHandlingPlans()throws Exception{
        return recorderService.countHandlingPlan();
    }

    @RequestMapping(value = "/plans/handling",method = RequestMethod.GET)
    public List<PlanEntity> listHandlingPlans(Integer countPerPage, Integer pageNum){
        return recorderService.listHandlingPlan(countPerPage,pageNum);
    }

    @RequestMapping(value = "/plans/history/count",method = RequestMethod.GET)
    public Integer countHistoryPlans(){
        return recorderService.countHistoryPlan();
    }

    @RequestMapping(value = "/plans/history",method = RequestMethod.GET)
    public List<PlanEntity> listHistoryPlans(Integer countPerPage, Integer pageNum){
        return recorderService.listHistoryPlan(countPerPage,pageNum);
    }

    @RequestMapping(value = "/plans/{pid}/reply",method = RequestMethod.POST)
    public Integer replyPlan(@RequestBody PlanSubmit submit,@PathVariable String pid){
        submit.setPlanId(pid);
        recorderService.replyPlan(submit);
        return 1;
    }

    @RequestMapping(value = "/plans/{pid}/drop",method = RequestMethod.POST)
    public Integer dropPlan(@PathVariable String pid){
        recorderService.dropPlan(pid);
        return 1;
    }

    @RequestMapping(value = "/contracts",method = RequestMethod.POST)
    public Integer submitContract(@RequestBody ContractEntity contract){
        recorderService.submitContract(contract);
        return 1;
    }
}
