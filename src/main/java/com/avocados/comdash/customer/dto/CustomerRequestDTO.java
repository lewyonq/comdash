package com.avocados.comdash.customer.dto;

import com.avocados.comdash.model.embedded.Address;
import com.avocados.comdash.model.enums.ContactPreference;
import lombok.Data;

import java.time.LocalDate;

@Data
public class CustomerRequestDTO {
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
}
