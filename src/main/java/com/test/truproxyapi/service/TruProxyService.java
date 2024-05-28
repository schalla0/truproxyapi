package com.test.truproxyapi.service;

import com.test.truproxyapi.model.Companies;
import com.test.truproxyapi.model.Company;
import com.test.truproxyapi.model.CompanySearch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
public class TruProxyService {
    private CompanySearchService companySearchService;

    @Autowired
    public TruProxyService(CompanySearchService companySearchService) {
        this.companySearchService = companySearchService;
    }

    public Companies searchCompanies(String apiKey, CompanySearch companySearch, boolean active) {
        Companies companies = companySearchService.searchCompanies(apiKey, active);

       Companies byCompaniesById = companySearchService.findByCompaniesById(true, companySearch.getCompanyNumber());

        Predicate<Company> checkCompanySearchPredicate = getCompanyPredicate(companySearch);

        List<Company> items = companies.getItems().stream()
                .filter(checkCompanySearchPredicate)
                .collect(Collectors.toList());
       return Companies.builder().items(items).total_results(items.size()).build();
    }

    private Predicate<Company> getCompanyPredicate(CompanySearch companySearch) {
        Predicate<Company> companyNumberPredicate = company -> company.getCompany_number().equals(companySearch.getCompanyNumber());
        Predicate<Company> companyNameAndNumberPredicate = company -> company.getCompany_number().equals(companySearch.getCompanyNumber()) &&
                                                               companySearch.getCompanyName().equalsIgnoreCase(company.getTitle());
        return companySearch.getCompanyName() != null ? companyNameAndNumberPredicate : companyNumberPredicate;
    }

}
