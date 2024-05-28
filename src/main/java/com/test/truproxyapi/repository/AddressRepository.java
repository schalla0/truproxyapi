package com.test.truproxyapi.repository;

import com.test.truproxyapi.model.AddressInfo;
import com.test.truproxyapi.model.OfficerInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AddressRepository extends JpaRepository<AddressInfo, Integer> {
    @Override
    List<AddressInfo> findAll();
}
