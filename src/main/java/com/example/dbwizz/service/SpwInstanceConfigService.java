package com.example.dbwizz.service;


import com.example.dbwizz.dto.SpwInstanceDto;
import com.example.dbwizz.entity.SpwInstanceConfig;
import com.example.dbwizz.repo.SpwInstanceConfigRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SpwInstanceConfigService {

    private final SpwInstanceConfigRepo spwInstanceConfigRepo;

    @Autowired
    public SpwInstanceConfigService(SpwInstanceConfigRepo spwInstanceConfigRepo) {
        this.spwInstanceConfigRepo = spwInstanceConfigRepo;
    }

    public List<SpwInstanceDto> getAllConfigs() {
        List<SpwInstanceConfig> spwConfigs = spwInstanceConfigRepo.findAll();

        return convertListToDto(spwConfigs);
    }

    public Optional<SpwInstanceDto> addConfig(SpwInstanceDto spwConfigDto) {
        SpwInstanceConfig config = convertToEntity(spwConfigDto);

        SpwInstanceConfig config1 = spwInstanceConfigRepo.save(config);
        return Optional.of(spwConfigDto);
    }


    public String deleteConfigById(Long id) {
        spwInstanceConfigRepo.deleteById(id);
        Optional<SpwInstanceConfig> deletedConfig = spwInstanceConfigRepo.findById(id);
        if (deletedConfig.isPresent()) {
            return "id " + id + " did not deleted";
        } else {
            return "id " + id + "  deleted";
        }
    }

    @Transactional
    public String deleteConfigByInstanceAndProcessAndVariable(String instance ,String process,String variable) {
        Optional<SpwInstanceConfig> getConfig = spwInstanceConfigRepo.findFirstByInstanceAndProcessAndVariable(instance,process,variable);
        if (getConfig.isPresent()) {
            spwInstanceConfigRepo.deleteByInstanceAndProcessAndVariable(instance,process,variable);
            Optional<SpwInstanceConfig> deletedConfig = spwInstanceConfigRepo.findFirstByInstanceAndProcessAndVariable(instance,process,variable);
            if (deletedConfig.isPresent()) {
                return "Variable " + variable + " did not deleted";
            } else {
                return "Variable " + variable + "  deleted";
            }
        } else {
            return "Variable " + variable + "  is not present";
        }
    }

    public Optional<SpwInstanceDto> updateValueByInstanceAndProcessAndVariable(SpwInstanceDto spwConfigDto) {
        Optional<SpwInstanceConfig> spwInstanceConfig = spwInstanceConfigRepo.findFirstByInstanceAndProcessAndVariable(spwConfigDto.getInstance(),spwConfigDto.getProcess(),spwConfigDto.getVariable());
        if (spwInstanceConfig.isPresent()) {
            spwInstanceConfig.get().setValue(spwConfigDto.getValue());
            spwInstanceConfig.get().setVariable(spwConfigDto.getVariable());
            spwInstanceConfig.get().setActive(spwConfigDto.isActive());
            spwInstanceConfig.get().setInstance(spwConfigDto.getInstance());
            spwInstanceConfig.get().setVersion(spwInstanceConfig.get().getVersion() + 1);
            spwInstanceConfig.get().setLastModifiedDate(this.getPresentTime());
            spwInstanceConfigRepo.save(spwInstanceConfig.get());
            return Optional.of(spwConfigDto);
        } else {
            return Optional.empty();
        }

    }


    public LocalDateTime getPresentTime() {
        return LocalDateTime.now();
    }

    public SpwInstanceDto convertToDto(SpwInstanceConfig spwInstanceConfig) {
        SpwInstanceDto spwConfigDto = new SpwInstanceDto();
        spwConfigDto.setVariable(spwInstanceConfig.getVariable());
        spwConfigDto.setValue(spwInstanceConfig.getValue());
        return spwConfigDto;
    }

    public SpwInstanceConfig convertToEntity(SpwInstanceDto spwConfigDto) {
        SpwInstanceConfig config = SpwInstanceConfig.builder()
                .id(this.findMaxIdCheck())
                .instance(spwConfigDto.getInstance())
                .process(spwConfigDto.getProcess())
                .variable(spwConfigDto.getVariable())
                .value(spwConfigDto.getValue())
                .active(spwConfigDto.isActive())
                .createdBy(1L)
                .lastModifiedBy(1L)
                .createdDate(this.getPresentTime())
                .lastModifiedDate(this.getPresentTime())
                .version(1)
                .build();
        return config;
    }

    public List<SpwInstanceDto> convertListToDto(List<SpwInstanceConfig> spwInstanceConfig) {
        List<SpwInstanceDto> spwConfigDtos = new ArrayList<>();
        spwInstanceConfig.forEach(spwInstanceConfig1 -> {
            spwConfigDtos.add(SpwInstanceDto.builder()
                    .instance(spwInstanceConfig1.getInstance())
                            .process(spwInstanceConfig1.getProcess())
                    .variable(spwInstanceConfig1.getVariable())
                    .value(spwInstanceConfig1.getValue())
                    .active(spwInstanceConfig1.isActive())
                    .build());
        });

        return spwConfigDtos;
    }

    public Long findMaxIdCheck() {
        return spwInstanceConfigRepo.findMaximumOfId() == null ? 1 : spwInstanceConfigRepo.findMaximumOfId() + 1;
    }
}
