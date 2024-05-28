package com.test.truproxyapi.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "companyInfos")
public class CompanyInfo implements Serializable {

    @Id
    @GeneratedValue
    private int id;
    @Column(name = "company_number")
    private String companyNumber;
    @Column(name = "company_type")
    private String companyType;
    @Column(name = "title")
    private String title;
    @Column(name = "company_status")
    private String companyStatus;
    @Column(name = "date_of_creation")
    private String dateOfCreation;
    @JsonManagedReference
    @OneToOne(cascade = {
            CascadeType.MERGE,
            CascadeType.PERSIST,
            CascadeType.REMOVE
    })
    @JoinColumn(name="address")
    private AddressInfo address;

    @JsonManagedReference
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "companyInfo",
            cascade = {
                    CascadeType.MERGE,
                    CascadeType.PERSIST,
                    CascadeType.REMOVE
            })
    private List<OfficerInfo> officerInfos;
}
