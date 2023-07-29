package com.example.dbwizz.controller;

import com.example.dbwizz.dto.SpwProcessDto;
import com.example.dbwizz.service.SpwProcessConfigService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/config")
@CrossOrigin(origins = "http://localhost:3000")
public class SpwProcessConfigController {


    private final SpwProcessConfigService spwProcessConfigService;

    public SpwProcessConfigController(SpwProcessConfigService spwProcessConfigService) {
        this.spwProcessConfigService = spwProcessConfigService;
    }

    @GetMapping("/process")
    public List<SpwProcessDto> getAllConfigs() {
        return spwProcessConfigService.getAllConfigs();
    }

    @PostMapping("/process/add")
    public Optional<SpwProcessDto> addConfig(@RequestBody SpwProcessDto config) {
        return spwProcessConfigService.addConfig(config);
    }

    @DeleteMapping("/process/delete/id/{id}")
    public String deleteConfigById(@RequestParam Long id) {
        return spwProcessConfigService.deleteConfigById(id);
    }

    @DeleteMapping("/process/delete/variable/{process}/{variable}")
    @Transactional
    public String deleteConfigByProcessAndVariable(@RequestParam String variable,String process) {
        return spwProcessConfigService.deleteConfigByVariableAndValue(process,variable);
    }


    @PostMapping("/process/update")
    public Optional<SpwProcessDto> updateValueByVariable(@RequestBody SpwProcessDto spwProcessDto) {
        return spwProcessConfigService.updateValueByVariable(spwProcessDto);
    }

}
