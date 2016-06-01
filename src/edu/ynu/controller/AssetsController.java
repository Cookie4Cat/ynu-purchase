package edu.ynu.controller;

import edu.ynu.entity.AssetsEntity;
import edu.ynu.service.AssetsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/assets")
public class AssetsController {

    @Autowired
    private AssetsService assetsService;

    @RequestMapping(value="/{id}",method=RequestMethod.GET)
    public AssetsEntity getById(@PathVariable int id){
        return assetsService.getAssets(id);
    }
    @RequestMapping(method=RequestMethod.POST)
    public AssetsEntity save(@RequestBody AssetsEntity assets){
        assetsService.addAssets(assets);
        return assets;
    }
    @RequestMapping(method=RequestMethod.PUT)
    public AssetsEntity update(@RequestBody AssetsEntity assets){
        assetsService.updateAssets(assets);
        return assets;
    }
    @RequestMapping(value="/{id}",method=RequestMethod.DELETE)
    public String delete(@PathVariable int id){
        assetsService.deleteAssets(id);
        return "delete " + id + " success";
    }
}

