package com.test.truproxyapi.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table
public class OfficerInfo implements Serializable {
    @Id
    @GeneratedValue
    private int id;

    @Column(name = "name")
    private String name;
    @Column(name = "officer_role")
    private String officerRole;
    @Column(name = "appointed_on")
    private String appointedOn;
    @Column(name = "company_number")
    private String companyNumber;

    @JsonManagedReference
    @OneToOne(cascade = {
            CascadeType.MERGE,
            CascadeType.PERSIST,
            CascadeType.REMOVE
    })
    @JoinColumn(name="address")
    private AddressInfo address;

    @JsonBackReference
    @ManyToOne(cascade= { CascadeType.ALL})
    @JoinColumn(name="company_info_id")
    private CompanyInfo companyInfo;
}
