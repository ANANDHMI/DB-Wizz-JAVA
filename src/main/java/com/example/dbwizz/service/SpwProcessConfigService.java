package com.example.dbwizz.service;


import com.example.dbwizz.dto.SpwProcessDto;
import com.example.dbwizz.entity.SpwProcessConfig;
import com.example.dbwizz.repo.SpwProcessConfigRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SpwProcessConfigService {

    private final SpwProcessConfigRepo spwProcessConfigRepo;

    @Autowired
    public SpwProcessConfigService(SpwProcessConfigRepo spwProcessConfigRepo) {
        this.spwProcessConfigRepo = spwProcessConfigRepo;
    }

    public List<SpwProcessDto> getAllConfigs() {
        List<SpwProcessConfig> spwConfigs = spwProcessConfigRepo.findAll();

        return convertListToDto(spwConfigs);
    }

    public Optional<SpwProcessDto> addConfig(SpwProcessDto SpwProcessDto) {
        SpwProcessConfig config = convertToEntity(SpwProcessDto);

        SpwProcessConfig config1 = spwProcessConfigRepo.save(config);
        return Optional.of(SpwProcessDto);
    }


    public String deleteConfigById(Long id) {
        spwProcessConfigRepo.deleteById(id);
        Optional<SpwProcessConfig> deletedConfig = spwProcessConfigRepo.findById(id);
        if (deletedConfig.isPresent()) {
            return "id " + id + " did not deleted";
        } else {
            return "id " + id + "  deleted";
        }
    }

    @Transactional
    public String deleteConfigByVariableAndValue(String process, String variable) {
        Optional<SpwProcessConfig> getConfig = spwProcessConfigRepo.findFirstByProcessAndVariable(process, variable);
        if (getConfig.isPresent()) {
            spwProcessConfigRepo.deleteByProcessAndVariable(process, variable);
            Optional<SpwProcessConfig> deletedConfig = spwProcessConfigRepo.findFirstByProcessAndVariable(process, variable);
            if (deletedConfig.isPresent()) {
                return "variable " + variable + " under this process " + process + " did not deleted";
            } else {
                return "variable " + variable + " under this process " + process + " deleted";
            }
        } else {
            return "variable " + variable + "under this process " + process + " is not found";
        }

    }

    public Optional<SpwProcessDto> updateValueByVariable(SpwProcessDto spwProcessDto) {
        Optional<SpwProcessConfig> spwProcessConfig = spwProcessConfigRepo.findFirstByProcessAndVariable(spwProcessDto.getProcess(), spwProcessDto.getVariable());
        if (spwProcessConfig.isPresent()) {
            spwProcessConfig.get().setValue(spwProcessDto.getValue());
            spwProcessConfig.get().setVariable(spwProcessDto.getVariable());
            spwProcessConfig.get().setProcess(spwProcessDto.getProcess());
            spwProcessConfig.get().setActive(spwProcessDto.isActive());
            spwProcessConfig.get().setVersion(spwProcessConfig.get().getVersion() + 1);
            spwProcessConfig.get().setLastModifiedDate(this.getPresentTime());
            spwProcessConfigRepo.save(spwProcessConfig.get());
            return Optional.of(spwProcessDto);
        } else {
            return Optional.empty();
        }

    }


    public LocalDateTime getPresentTime() {
        return LocalDateTime.now();
    }

    public SpwProcessDto convertToDto(SpwProcessConfig SpwProcessConfig) {
        SpwProcessDto SpwProcessDto = new SpwProcessDto();
        SpwProcessDto.setVariable(SpwProcessConfig.getVariable());
        SpwProcessDto.setValue(SpwProcessConfig.getValue());
        SpwProcessDto.setProcess(SpwProcessConfig.getProcess());
        return SpwProcessDto;
    }

    public SpwProcessConfig convertToEntity(SpwProcessDto spwProcessDto) {
        SpwProcessConfig config = SpwProcessConfig.builder()
                .id(this.findMaxIdCheck())
                .process(spwProcessDto.getProcess())
                .variable(spwProcessDto.getVariable())
                .value(spwProcessDto.getValue())
                .active(spwProcessDto.isActive())
                .createdBy(1L)
                .lastModifiedBy(1L)
                .createdDate(this.getPresentTime())
                .lastModifiedDate(this.getPresentTime())
                .version(1)
                .build();
        return config;
    }

    public List<SpwProcessDto> convertListToDto(List<SpwProcessConfig> SpwProcessConfig) {
        List<SpwProcessDto> SpwProcessDtos = new ArrayList<>();
        SpwProcessConfig.forEach(SpwProcessConfig1 -> {
            SpwProcessDtos.add(SpwProcessDto.builder()
                    .variable(SpwProcessConfig1.getVariable())
                    .value(SpwProcessConfig1.getValue())
                    .active(SpwProcessConfig1.isActive())
                    .process(SpwProcessConfig1.getProcess())
                    .build());
        });

        return SpwProcessDtos;
    }

    public Long findMaxIdCheck() {
        return spwProcessConfigRepo.findMaximumOfId() == null ? 1 : spwProcessConfigRepo.findMaximumOfId() + 1;
    }

}
