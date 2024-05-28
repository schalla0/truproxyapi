package com.test.truproxyapi.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Companies implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer total_results;
    private List<Company> items;
}
