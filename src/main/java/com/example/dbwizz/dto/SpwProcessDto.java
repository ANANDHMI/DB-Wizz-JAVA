package com.example.dbwizz.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SpwProcessDto {
    private String process;
    private String variable;
    private String value;
    private boolean active;

}
