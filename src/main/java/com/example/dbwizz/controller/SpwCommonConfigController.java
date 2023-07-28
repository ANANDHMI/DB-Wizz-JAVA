package com.example.dbwizz.controller;


import com.example.dbwizz.entity.SpwCommonConfig;
import com.example.dbwizz.service.SpwCommonConfigService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/repo/")
@CrossOrigin(origins = "http://localhost:3000")
public class SpwCommonConfigController {

    private final SpwCommonConfigService spwCommonConfigService;

    public SpwCommonConfigController(SpwCommonConfigService spwCommonConfigService) {
        this.spwCommonConfigService = spwCommonConfigService;
    }

    @GetMapping("/config")
    public List<SpwCommonConfig> getAllConfigs() {
        return spwCommonConfigService.getAllConfigs();
    }

    @PostMapping("/addConfig")
    public SpwCommonConfig addConfig(@RequestBody SpwCommonConfig config) {
        return spwCommonConfigService.addConfig(config);
    }

    @DeleteMapping("/deleteConfig/{id}")
    public SpwCommonConfig deleteConfigById(@RequestParam Long id){
         SpwCommonConfig beforeDelete=spwCommonConfigService.getConfigById(id);
         spwCommonConfigService.deleteConfigById(id);
         return beforeDelete;
    }
    @GetMapping("/config/{id}")
    public SpwCommonConfig getConfigById(@RequestParam Long id){
        return spwCommonConfigService.getConfigById(id);
    }

    @GetMapping("/config/{variable}")
    public SpwCommonConfig getConfigByVariable(@RequestParam String variable){
            return spwCommonConfigService.getConfigByVariable(variable);
    }
    
    @PostMapping("/updateConfig")
    public SpwCommonConfig updateValueById(@RequestBody SpwCommonConfig spwCommonConfig){
        Long configId=spwCommonConfig.getId();
        if(configId!=null){
            return spwCommonConfigService.updateValueById(configId,spwCommonConfig);
        }else {
            return new SpwCommonConfig();
        }
    }
}