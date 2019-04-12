package com.afkl.exercises.spring.matrics;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiStatusSummery {
    private String path;
    private String method;
    private Integer status;
    private Integer count;
}
