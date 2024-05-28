package com.test.truproxyapi.service;

import com.test.truproxyapi.model.*;
import com.test.truproxyapi.repository.CompanyRepository;
import com.test.truproxyapi.util.DataLoader;
import com.test.truproxyapi.util.RequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

import static com.test.truproxyapi.util.RequestUtil.httpEntity;

@Service
public class OfficeSearchService {

    private RestTemplate resetTemplate;

    @Value("${officers.search.url}")
    public String officersSearchUrl;

    @Autowired
    public OfficeSearchService(RestTemplate resetTemplate) {
        this.resetTemplate = resetTemplate;
    }

    public void searchOfficers(String apiKey, Companies companies) {
            for (Company company : companies.getItems()) {
                String searchUrl = RequestUtil.appendUriQueryParam(officersSearchUrl, "CompanyNumber", company.getCompany_number());
                ResponseEntity<String> officeSerachResults = resetTemplate.exchange(searchUrl, HttpMethod.GET, httpEntity(apiKey), String.class);
                String officeJsonResults = officeSerachResults.getBody();
                List<Officer> officers = DataLoader.buildOfficer(officeJsonResults);
                company.setOfficers(officers);
            }
        }

    public void searchOfficers1(String apiKey, Companies companies) {
        List<CompanyInfo> companyInfos = new ArrayList<>();
        List<OfficerInfo> officerInfos = new ArrayList<>();
        for (Company company : companies.getItems()) {
            String searchUrl = RequestUtil.appendUriQueryParam(officersSearchUrl, "CompanyNumber", company.getCompany_number());
            ResponseEntity<String> officeSerachResults = resetTemplate.exchange(searchUrl, HttpMethod.GET, httpEntity(apiKey), String.class);
            String officeJsonResults = officeSerachResults.getBody();
            List<Officer> officers = DataLoader.buildOfficer(officeJsonResults);
            company.setOfficers(officers);
            Address address = company.getAddress();
            CompanyInfo companyInfo = CompanyInfo.builder()
                   // .id(1)
                    .companyNumber(company.getCompany_number())
                    .companyType(company.getCompany_type())
                    .title(company.getTitle())
                    .companyStatus(company.getCompany_status())
                    .dateOfCreation("2022-09-23")
                    .address(AddressInfo.builder()
                            .locality(address.getLocality())
                            .postalCode(address.getPostal_code())
                            .premises(address.getPremises())
                            .addressLine1(address.getAddress_line1())
                            .country(address.getCountry())
                            .build())
                    .officerInfos(buildOfficerInfo(officers,company.getCompany_number(), officerInfos))
                    .build();
            companyInfos.add(companyInfo);
        }
    }

    private List<OfficerInfo> buildOfficerInfo(List<Officer> officers, String companyNumber, List<OfficerInfo> officerInfos) {
        for (Officer officer : officers) {
            Address address = officer.getAddress();
            OfficerInfo officerInfo = OfficerInfo.builder()
                    .name(officer.getName())
                    .officerRole(officer.getOfficer_role())
                    .appointedOn(officer.getAppointed_on())
                    .companyNumber(companyNumber)
                    .address(AddressInfo
                            .builder()
                            .locality(address.getLocality())
                            .postalCode(address.getPostal_code())
                            .premises(address.getPremises())
                            .addressLine1(address.getAddress_line1())
                            .country(address.getCountry())
                            .build()).build();
            officerInfos.add(officerInfo);
        }
        return officerInfos;
    }
}