package com.example.dbwizz.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "spw_instance_config", schema = "config")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SpwInstanceConfig {


    @Id
    private Long id;
    private String instance;
    private String process;
    private String variable;
    private String value;
    private boolean active;
    private Integer version;
    private LocalDateTime createdDate;
    private LocalDateTime lastModifiedDate;
    private Long createdBy = -1L;
    private Long lastModifiedBy = -1L;
}
