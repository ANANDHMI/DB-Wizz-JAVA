package com.example.dbwizz.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "spw_common_config", schema = "config")
public class SpwCommonConfig {

    @Id
    private Long id;

    private String variable;
    private String value;
    private boolean active;
    private Integer version;
    private LocalDateTime createdDate;
    private LocalDateTime lastModifiedDate;
    private Long createdBy = -1L;
    private Long lastModifiedBy = -1L;
}
