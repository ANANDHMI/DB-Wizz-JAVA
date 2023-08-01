package com.example.dbwizz.repo;

import com.example.dbwizz.entity.SpwInstanceConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface SpwInstanceConfigRepo extends JpaRepository<SpwInstanceConfig, Long> {

    Optional<SpwInstanceConfig> findFirstByInstanceAndProcessAndVariable(String instance ,String process,String variable);

    void deleteByInstanceAndProcessAndVariable(String instance ,String process,String variable);

    @Query("SELECT MAX(e.id) FROM SpwInstanceConfig e")
    Long findMaximumOfId();


}
