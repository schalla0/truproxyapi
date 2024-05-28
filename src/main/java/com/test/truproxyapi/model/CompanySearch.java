package com.test.truproxyapi.model;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompanySearch {
    private String companyName;

    @Valid
    @NotNull(message = "companyNumber must be present")
    @NotBlank(message = "companyNumber must be present")
    private String companyNumber;
}
