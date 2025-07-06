package com.benjamin.model;

import com.benjamin.domain.EUserRole;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;


import java.util.HashSet;
import java.util.Set;

import static com.benjamin.domain.EUserRole.ROLE_CUSTOMER;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class UserModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    private String email;

    private String fullName;

    private String phoneNumber;

    private EUserRole role = ROLE_CUSTOMER;

    @OneToMany
    private Set<AddressModel> addresses = new HashSet<>();

    @ManyToMany
    @JsonIgnore
    private Set<CouponModel> usedCoupons = new HashSet<>();
}
