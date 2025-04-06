package com.avocados.comdash.model.entity;

import com.avocados.comdash.model.embedded.Address;
import com.avocados.comdash.model.enums.ContactPreference;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(unique = true)
    private String email;

    @Column
    private String phoneNumber;

    @Embedded
    private Address primaryAddress;

    @Column
    private boolean isBusinessCustomer;

    @Column
    private String companyName;

    @Column
    private LocalDate customerSince;

    @Enumerated(EnumType.STRING)
    private ContactPreference preferredContactMethod;

    @Column(length = 1000)
    private String notes;

    @OneToMany(mappedBy = "customer")
    private List<Order> orders = new ArrayList<>();

    @Column(updatable = false)
    private LocalDateTime createdAt;

    @Column
    private LocalDateTime updatedAt;
}
