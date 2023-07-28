package com.example.dbwizz.service;


import com.example.dbwizz.entity.SpwCommonConfig;
import com.example.dbwizz.repo.SpwCommonConfigRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class SpwCommonConfigService {

    private final SpwCommonConfigRepo spwCommonConfigRepo;

    @Autowired
    public SpwCommonConfigService(SpwCommonConfigRepo spwCommonConfigRepo) {
        this.spwCommonConfigRepo = spwCommonConfigRepo;
    }
    public  List<SpwCommonConfig> getAllConfigs() {
        return spwCommonConfigRepo.findAll();
    }
    public SpwCommonConfig addConfig(SpwCommonConfig spwCommonConfig){
        Optional<SpwCommonConfig> dbCommonConfig =spwCommonConfigRepo.findById(spwCommonConfig.getId());
        if (dbCommonConfig.isEmpty()){
            spwCommonConfig.setLastModifiedDate(this.getPresentTime());
            spwCommonConfig.setCreatedDate(this.getPresentTime());
            return spwCommonConfigRepo.save(spwCommonConfig);
        }else {
            return new SpwCommonConfig();
        }

    }

    public SpwCommonConfig getConfigById(Long id){
        Optional<SpwCommonConfig> config=spwCommonConfigRepo.findById(id);
        return config.orElseGet(SpwCommonConfig::new);
    }

    public String deleteConfigById(Long id){
        boolean isPresent = spwCommonConfigRepo.existsById(id);
        if(isPresent){
            spwCommonConfigRepo.deleteById(id);
            return "id :" +id + " deleted";
        }else{
            return "id : "+id + " not present";
        }

    }


    public SpwCommonConfig updateValueById (Long id,SpwCommonConfig userEntity ){
       SpwCommonConfig dbEntity=this.getConfigById(id);
       if(dbEntity.getId() != null){
           dbEntity.setValue(userEntity.getValue());
           Integer updateVersion=dbEntity.getVersion()+1;
           dbEntity.setVersion(updateVersion);
           dbEntity.setLastModifiedDate(LocalDateTime.now());
            spwCommonConfigRepo.save(dbEntity);
            return dbEntity;
       }else {
           return new SpwCommonConfig();
       }
    }


    public SpwCommonConfig getConfigByVariable(String variable){
        List<SpwCommonConfig> dbConfigs=spwCommonConfigRepo.findAll();

        SpwCommonConfig  filteredConfig=dbConfigs.stream()
                .filter(spwCommonConfig ->
                Objects.equals(spwCommonConfig.getVariable(),variable))
                .findFirst()
                .orElse(new SpwCommonConfig());

        return filteredConfig;
    }

    public LocalDateTime getPresentTime(){
        return LocalDateTime.now();
    }
}
