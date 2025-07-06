package com.benjamin.model;

import com.benjamin.domain.EOrderStatus;
import com.benjamin.domain.EPaymentStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.benjamin.domain.EPaymentStatus.PENDING;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Table(name = "orders")
public class OrderModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String orderId;

    @ManyToOne
    private UserModel user;

    private Long sellerId;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItemModel> orderItems = new ArrayList<>();

    @ManyToOne
    private AddressModel shippingAddress;

    @Embedded
    private PaymentDetailsModel paymentDetails = new PaymentDetailsModel();

    private double totalMrpPrice;

    private Integer totalSellingPrice;

    private Integer discount;

    private EOrderStatus orderStatus;

    private int totalItem;

    private EPaymentStatus paymentStatus = PENDING;

    private LocalDateTime orderDate = LocalDateTime.now();

    private LocalDateTime deliveryDate = orderDate.plusDays(7);
}
