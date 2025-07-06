package com.benjamin.model;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BankDetailsModel {

    private String accountNumber;

    private String accountHolderName;

    private String ifscCode;
}
