package com.test.truproxyapi.repository;

import com.test.truproxyapi.model.CompanyInfo;
import com.test.truproxyapi.model.OfficerInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OfficerRepository extends JpaRepository<OfficerInfo, Integer> {
    OfficerInfo  findByCompanyNumber(String companyNumber);
}
