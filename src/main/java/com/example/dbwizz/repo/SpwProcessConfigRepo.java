package com.example.dbwizz.repo;

import com.example.dbwizz.entity.SpwProcessConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;


public interface SpwProcessConfigRepo extends JpaRepository<SpwProcessConfig, Long> {


    Optional<SpwProcessConfig> findFirstByProcessAndVariable(String process, String variable);

    void deleteByProcessAndVariable(String variable, String value);

    @Query("SELECT MAX(e.id) FROM SpwProcessConfig e")
    Long findMaximumOfId();


}
