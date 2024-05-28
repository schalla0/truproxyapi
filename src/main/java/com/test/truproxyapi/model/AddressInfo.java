package com.test.truproxyapi.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
public class AddressInfo implements Serializable {

    @Id
    @GeneratedValue
    private int id;
    @Column(name = "locality")
    private String locality;
    @Column(name = "postal_code")
    private String postalCode;
    @Column(name = "premises")
    private String premises;
    @Column(name = "address_line1")
    private String addressLine1;
    @Column(name = "country")
    private String country;

    @JsonBackReference
    @OneToOne(mappedBy="address",
            cascade = {
                    CascadeType.MERGE,
                    CascadeType.PERSIST,
                    CascadeType.REMOVE
            })
    private CompanyInfo companyInfo;


    @JsonBackReference
    @OneToOne(mappedBy="address",
            cascade = {
                    CascadeType.MERGE,
                    CascadeType.PERSIST,
                    CascadeType.REMOVE
            })
    private OfficerInfo officerInfo;
}
