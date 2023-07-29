package com.example.dbwizz.controller;


import com.example.dbwizz.dto.SpwConfigDto;
import com.example.dbwizz.service.SpwCommonConfigService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/repo/")
@CrossOrigin(origins = "http://localhost:3000")
public class SpwCommonConfigController {

    private final SpwCommonConfigService spwCommonConfigService;

    public SpwCommonConfigController(SpwCommonConfigService spwCommonConfigService) {
        this.spwCommonConfigService = spwCommonConfigService;
    }

    @GetMapping("/config")
    public List<SpwConfigDto> getAllConfigs() {
        return spwCommonConfigService.getAllConfigs();
    }

    @PostMapping("/config/add")
    public Optional<SpwConfigDto> addConfig(@RequestBody SpwConfigDto config) {
        return spwCommonConfigService.addConfig(config);
    }

    @DeleteMapping("/config/delete/id/{id}")
    public String deleteConfigById(@RequestParam Long id) {
        return spwCommonConfigService.deleteConfigById(id);
    }

    @DeleteMapping("/config/delete/variable/{variable}")
    @Transactional
    public String deleteConfigByVariable(@RequestParam String variable) {
        return spwCommonConfigService.deleteConfigByVariable(variable);
    }


    @PostMapping("/config/update")
    public Optional<SpwConfigDto> updateValueByVariable(@RequestBody SpwConfigDto spwConfigDto) {
        return spwCommonConfigService.updateValueByVariable(spwConfigDto);
    }


}