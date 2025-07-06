package com.benjamin.model;

import com.benjamin.domain.EPaymentStatus;
import lombok.Data;

@Data
public class PaymentDetailsModel {

    private String paymentId;

    private String razorpayPaymentLinkId;

    private String razorpayPaymentLinkRef;

    private String razorpayPaymentLinkStatus;

    private String razorpayPaymentIdZWSP;

    private EPaymentStatus status;
}
