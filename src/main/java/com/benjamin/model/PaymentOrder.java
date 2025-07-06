package com.benjamin.model;

import com.benjamin.domain.EPaymentMethod;
import com.benjamin.domain.EPaymentOrderStatus;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

import static com.benjamin.domain.EPaymentOrderStatus.PENDING;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class PaymentOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long amount;

    private EPaymentOrderStatus status = PENDING;

    private EPaymentMethod paymentMethod;

    private String paymentLinkId;

    @ManyToOne
    private UserModel user;

    @OneToMany
    private Set<OrderModel> orders = new HashSet<>();
}
