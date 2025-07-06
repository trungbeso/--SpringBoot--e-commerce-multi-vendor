package com.benjamin.model;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BusinessDetailsModel {

    private String businessName;

    private String businessEmail;

    private String businessPhoneNumber;

    private String businessAddress;

    private String logo;

    private String banner;
}
