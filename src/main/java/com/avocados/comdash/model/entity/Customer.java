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
    private String firstname;

    @Column(nullable = false)
    private String lastname;

    @Column(unique = true)
    private String email;

    @Column
    private String phone;

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

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
