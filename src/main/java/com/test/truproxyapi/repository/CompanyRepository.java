package com.test.truproxyapi.repository;

import com.test.truproxyapi.model.CompanyInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyRepository extends JpaRepository<CompanyInfo, Integer> {
    CompanyInfo  findByCompanyNumber(String companyNumber);
}
