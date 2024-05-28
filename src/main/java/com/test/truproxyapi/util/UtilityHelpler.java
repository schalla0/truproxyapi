package com.test.truproxyapi.util;

import com.test.truproxyapi.model.*;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

import static com.test.truproxyapi.util.RequestUtil.httpEntity;

public class UtilityHelpler {

    public static Supplier<List<CompanyInfo>> companyInfoSupplier = () -> {
        CompanyInfo companyInfos = CompanyInfo.builder()
                .id(1)
                .companyNumber("14372657")
                .companyType("ltd")
                .title("COLLEGE OF CONSTRUCTION AND REAL ESTATE MANAGEMENT LTD")
                .companyStatus("active")
                .dateOfCreation("2022-09-23")
                .address(AddressInfo.builder()
                        .locality("London")
                        .postalCode("N1 7G")
                        .premises("N1 7G")
                        .addressLine1("Wenlock Road")
                        .country("England")
                        .build())
                .officerInfos(List.of(OfficerInfo.builder()
                        .name("AHMED, Asmaa Elsayed")
                        .officerRole("director")
                        .appointedOn("2022-09-23")
                        .companyNumber("14372657")
                        .address(AddressInfo
                                .builder()
                                .locality("London")
                                .postalCode("N1 7GU")
                                .premises("20-22")
                                .addressLine1("Wenlock Road")
                                .country("England")
                                .build())
                        .build()))
                .build();

       return Arrays.asList(companyInfos);
    };

    public static List<CompanyInfo> buildCompanyInfo(Companies companies) {
        List<CompanyInfo> companyInfos = new ArrayList<>();
        List<OfficerInfo> officerInfos = new ArrayList<>();
        for (Company company : companies.getItems()) {
            List<Officer> officers = company.getOfficers();
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
        return companyInfos;
    }

    private static List<OfficerInfo> buildOfficerInfo(List<Officer> officers, String companyNumber,
                                                      List<OfficerInfo> officerInfos) {
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
