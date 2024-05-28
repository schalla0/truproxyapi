package com.test.truproxyapi.service;

import com.test.truproxyapi.model.*;
import com.test.truproxyapi.repository.AddressRepository;
import com.test.truproxyapi.repository.CompanyRepository;
import com.test.truproxyapi.repository.OfficerRepository;
import com.test.truproxyapi.util.DataLoader;
import com.test.truproxyapi.util.UtilityHelpler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

import static com.test.truproxyapi.util.RequestUtil.appendUriQueryParam;
import static com.test.truproxyapi.util.RequestUtil.httpEntity;

@Service
public class CompanySearchService {
    private RestTemplate resetTemplate;
    private OfficeSearchService officeSearchService;

    private CompanyRepository companyRepository;

    @Autowired
    private OfficerRepository officerRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Value("${companies.search.url}")
    public String companiesSearchUrl;

    @Autowired
    public CompanySearchService(RestTemplate resetTemplate, OfficeSearchService officeSearchService, CompanyRepository companyRepository) {
        this.resetTemplate = resetTemplate;
        this.officeSearchService = officeSearchService;
        this.companyRepository = companyRepository;
    }

    public Companies searchCompanies(String apiKey, boolean active) {
        String searchUrl = appendUriQueryParam(companiesSearchUrl, "Query", "companies");
        ResponseEntity<String> response = resetTemplate.exchange(searchUrl, HttpMethod.GET,  httpEntity(apiKey), String.class);
        Companies companies = DataLoader.buildCompanies(response.getBody(), active);
        officeSearchService.searchOfficers1(apiKey, companies);
        //save to DB
        List<CompanyInfo> companyInfos = companyRepository.findAll();
        if (companyInfos.isEmpty()) {
            save(companies);
        }

        return companies;
    }

    private void save(Companies companies) {
        List<CompanyInfo> companyInfos = UtilityHelpler.buildCompanyInfo(companies);
        companyRepository.saveAll(companyInfos);
    }

    public Companies findByCompaniesById(boolean active, String companyNumber) {
        List<Company> items = new ArrayList<>();
        CompanyInfo companyInfo = companyRepository.findByCompanyNumber(companyNumber);
        AddressInfo address = companyInfo.getAddress();

        OfficerInfo officerInfo = officerRepository.findByCompanyNumber(companyNumber);
        List<AddressInfo> all = addressRepository.findAll();
        List<OfficerInfo> officerInfos = companyInfo.getOfficerInfos();
        companyInfo.setOfficerInfos(officerInfos);
        Company company =  Company.builder().company_number(companyNumber).company_status(companyInfo.getCompanyStatus()
        ).address(Address.builder().country(address.getCountry()).build()).build();

        List<Company> company1 = List.of(company);
        return Companies.builder().items(company1).total_results(company1.size()).build();
    }
}
