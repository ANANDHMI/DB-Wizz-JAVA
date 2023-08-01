package com.example.dbwizz.controller;

import com.example.dbwizz.dto.SpwInstanceDto;
import com.example.dbwizz.repo.SpwInstanceConfigRepo;
import com.example.dbwizz.service.SpwInstanceConfigService;
import com.example.dbwizz.service.SpwProcessConfigService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/config")
@CrossOrigin(origins = "http://localhost:3000")
public class SpwInstanceConfigController {


    private final SpwInstanceConfigService spwInstanceConfigService;

    public SpwInstanceConfigController(SpwInstanceConfigService spwInstanceConfigService) {
        this.spwInstanceConfigService = spwInstanceConfigService;
    }

    @GetMapping("/instance")
    public List<SpwInstanceDto> getAllConfigs() {
        return spwInstanceConfigService.getAllConfigs();
    }

    @PostMapping("/instance/add")
    public Optional<SpwInstanceDto> addConfig(@RequestBody SpwInstanceDto config) {
        return spwInstanceConfigService.addConfig(config);
    }

    @DeleteMapping("/instance/delete/id/{id}")
    public String deleteConfigById(@RequestParam Long id) {
        return spwInstanceConfigService.deleteConfigById(id);
    }

    @DeleteMapping("/instance/delete/variable/{instance}/{process}/{variable}")
    @Transactional
    public String deleteConfigByProcessAndVariable(@RequestParam String variable,String process,String instance) {
        return spwInstanceConfigService.deleteConfigByInstanceAndProcessAndVariable(instance,process,variable);
    }


    @PostMapping("/instance/update")
    public Optional<SpwInstanceDto> updateValueByVariable(@RequestBody SpwInstanceDto spwProcessDto) {
        return spwInstanceConfigService.updateValueByInstanceAndProcessAndVariable(spwProcessDto);
    }

}
