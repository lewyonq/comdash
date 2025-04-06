package com.avocados.comdash.model.entity;

import com.avocados.comdash.model.embedded.Address;
import com.avocados.comdash.model.enums.InstallationStatus;
import com.avocados.comdash.model.enums.OrderStatus;
import com.avocados.comdash.model.enums.PaymentStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @Column(nullable = false)
    private LocalDateTime orderDate;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

//    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<OrderItem> orderItems = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;

    @Embedded
    private Address installationAddress;

    @Column
    private LocalDate scheduledInstallationDate;

    @Enumerated(EnumType.STRING)
    private InstallationStatus installationStatus;

    @Column
    private String specialInstructions;

    @Column
    private boolean warrantyApplied;

    @Column
    private int warrantyPeriodMonths;

    @Column(updatable = false)
    private LocalDateTime createdAt;

    @Column
    private LocalDateTime updatedAt;
}
