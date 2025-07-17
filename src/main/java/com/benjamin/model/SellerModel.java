package com.benjamin.model;


import com.benjamin.domain.EAccountStatus;
import com.benjamin.domain.EUserRole;
import jakarta.persistence.*;
import lombok.*;

import static com.benjamin.domain.EAccountStatus.PENDING_VERIFICATION;
import static com.benjamin.domain.EUserRole.ROLE_SELLER;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "sellers")
public class SellerModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String sellerName;

    private String phoneNumber;

    @Column(unique = true, nullable = false)
    private String email;

    private String password;

    @Embedded
    private BusinessDetailsModel businessDetails = new BusinessDetailsModel();

    @Embedded
    private BankDetailsModel bankDetails = new BankDetailsModel();

    @OneToOne(cascade = CascadeType.ALL)
    private AddressModel pickupAddress = new AddressModel();

    private String GTIN;

    private EUserRole role = ROLE_SELLER;

    private boolean isEmailVerified = false;

    private EAccountStatus accountStatus = PENDING_VERIFICATION;
}
