package com.avocados.comdash.customer.dto;

import com.avocados.comdash.model.embedded.Address;
import com.avocados.comdash.model.entity.Order;
import com.avocados.comdash.model.enums.ContactPreference;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class CustomerResponseDTO {
    private Long id;
    private String firstname;
    private String lastname;
    private String email;
    private String phone;
    private Address address;
    private boolean isBusinessCustomer;
    private String companyName;
    private LocalDate customerSince;
    private ContactPreference preferredContactMethod;
    private String notes;
    private List<Order> orders = new ArrayList<>();
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
