package com.test.truproxyapi.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Address {
    private String locality;
    private String postal_code;
    private String premises;
    private String address_line1;
    private String country;
}
