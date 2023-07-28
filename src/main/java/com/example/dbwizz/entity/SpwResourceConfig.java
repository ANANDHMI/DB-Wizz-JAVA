package com.example.dbwizz.entity;


import jdk.jfr.DataAmount;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Data
@Entity
public class SpwResourceConfig {

    @Id
    private Long id;
    private String configName;
    private String driverClass;
    private String userName;
    private String password;
    private String resourceUrl;
    private String host;
    private String port;
    private String databaseName;

    private boolean active;
    private Integer version;
    private LocalDateTime createdDate ;
    private LocalDateTime lastModifiedDate;
    private Long createdBy = -1L;
    private Long lastModifiedBy = -1L;


}
