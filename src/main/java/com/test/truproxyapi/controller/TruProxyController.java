package com.test.truproxyapi.controller;

import com.test.truproxyapi.model.Companies;
import com.test.truproxyapi.model.CompanySearch;
import com.test.truproxyapi.service.TruProxyService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@RestController
@Validated
@RequestMapping
public class TruProxyController {
    private static final Logger LOGGER = LoggerFactory.getLogger(TruProxyController.class);

    private TruProxyService truProxyService;

    @Autowired
    public TruProxyController(TruProxyService truProxyService) {
        this.truProxyService = truProxyService;
    }

    @PostMapping(value = "api/v1/company/search", produces = {"application/json", MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Companies> searchCompany(@RequestParam("active") boolean active, @Valid @NotNull(message = "apiKey must be present")
                                                   @NotBlank(message = "apiKey must be present") @RequestHeader("x-api-key") String apiKey,
                                                   @Valid @RequestBody CompanySearch companySearch) {


        return ResponseEntity
                .ok()
                .contentType(MediaType.parseMediaType("application/json"))
                .body(truProxyService.searchCompanies(apiKey, companySearch, active));
    }
}
