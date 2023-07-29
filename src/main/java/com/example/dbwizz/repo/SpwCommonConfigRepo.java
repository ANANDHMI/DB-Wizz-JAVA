package com.example.dbwizz.repo;

import com.example.dbwizz.entity.SpwCommonConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface SpwCommonConfigRepo extends JpaRepository<SpwCommonConfig,Long> {

    Optional<SpwCommonConfig> findFirstByVariable(String variable);

        void deleteByVariable(String variable);

    @Query("SELECT MAX(e.id) FROM SpwCommonConfig e")
    Long findMaximumOfId();

}
