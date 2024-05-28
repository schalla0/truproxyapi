package com.test.truproxyapi.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Officer {
    private String name;
    private String officer_role;
    private String appointed_on;
    private Address address;
}

