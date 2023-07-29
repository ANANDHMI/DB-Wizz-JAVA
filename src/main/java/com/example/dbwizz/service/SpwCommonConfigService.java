package com.example.dbwizz.service;


import com.example.dbwizz.dto.SpwConfigDto;
import com.example.dbwizz.entity.SpwCommonConfig;
import com.example.dbwizz.repo.SpwCommonConfigRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class SpwCommonConfigService {

    private final SpwCommonConfigRepo spwCommonConfigRepo;

    @Autowired
    public SpwCommonConfigService(SpwCommonConfigRepo spwCommonConfigRepo) {
        this.spwCommonConfigRepo = spwCommonConfigRepo;
    }
    public  List<SpwConfigDto> getAllConfigs() {
        List<SpwCommonConfig> spwConfigs=spwCommonConfigRepo.findAll();

        return convertListToDto(spwConfigs);
    }
    public Optional<SpwConfigDto> addConfig(SpwConfigDto spwConfigDto){
         SpwCommonConfig config=convertToEntity(spwConfigDto);

            SpwCommonConfig config1=spwCommonConfigRepo.save(config);
            return Optional.of(spwConfigDto);
        }


        public String deleteConfigById(Long id){
            spwCommonConfigRepo.deleteById(id);
            Optional<SpwCommonConfig> deletedConfig = spwCommonConfigRepo.findById(id);
            if(deletedConfig.isPresent()){
                return "id "+ id + " did not deleted";
            }else {
                return "id "+ id + "  deleted";
            }
        }

    @Transactional
    public String deleteConfigByVariable(String variable){
        spwCommonConfigRepo.deleteByVariable(variable);
        Optional<SpwCommonConfig> deletedConfig = spwCommonConfigRepo.findFirstByVariable(variable);
        if(deletedConfig.isPresent()){
            return "Variable "+ variable + " did not deleted";
        }else {
            return "Variable "+ variable + "  deleted";
        }
    }

        public Optional<SpwConfigDto> updateValueByVariable(SpwConfigDto spwConfigDto){
                Optional<SpwCommonConfig> spwCommonConfig= spwCommonConfigRepo.findFirstByVariable(spwConfigDto.getVariable());
                if(spwCommonConfig.isPresent()){
                    spwCommonConfig.get().setValue(spwConfigDto.getValue());
                    spwCommonConfig.get().setVariable(spwConfigDto.getVariable());
                    spwCommonConfig.get().setActive(spwConfigDto.isActive());
                    spwCommonConfig.get().setVersion(spwCommonConfig.get().getVersion()+1);
                    spwCommonConfig.get().setLastModifiedDate(this.getPresentTime());
                    spwCommonConfigRepo.save(spwCommonConfig.get());
                    return Optional.of(spwConfigDto);
                }else {
                    return Optional.empty();
                }

        }



    public LocalDateTime getPresentTime(){
        return LocalDateTime.now();
    }

    public SpwConfigDto convertToDto(SpwCommonConfig spwCommonConfig){
        SpwConfigDto spwConfigDto=new SpwConfigDto();
        spwConfigDto.setVariable(spwCommonConfig.getVariable());
        spwConfigDto.setValue(spwCommonConfig.getValue());
        return spwConfigDto;
    }

    public SpwCommonConfig convertToEntity(SpwConfigDto spwConfigDto){
        SpwCommonConfig config=SpwCommonConfig.builder()
                .id(this.findMaxIdCheck())
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

    public List<SpwConfigDto> convertListToDto(List<SpwCommonConfig> spwCommonConfig){
        List<SpwConfigDto> spwConfigDtos=new ArrayList<>();
        spwCommonConfig.forEach(spwCommonConfig1 -> {
            spwConfigDtos.add(SpwConfigDto.builder()
                            .variable(spwCommonConfig1.getVariable())
                            .value(spwCommonConfig1.getValue())
                            .active(spwCommonConfig1.isActive())
                    .build());
        });

        return spwConfigDtos;
    }

    public Long findMaxIdCheck(){
        return spwCommonConfigRepo.findMaximumOfId() == null ? 1 :spwCommonConfigRepo.findMaximumOfId()+1;
    }
}
