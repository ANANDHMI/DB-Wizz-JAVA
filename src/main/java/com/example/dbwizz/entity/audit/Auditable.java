package com.example.dbwizz.entity.audit;

import lombok.Data;

import javax.persistence.Entity;
import java.time.LocalDateTime;

@Data
public class Auditable {

    private LocalDateTime createdDate = LocalDateTime.now();
    private LocalDateTime lastModifiedDate;
    private Long createdBy = -1L;
    private Long lastModifiedBy = -1L;
}
