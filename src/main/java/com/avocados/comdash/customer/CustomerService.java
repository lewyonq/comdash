package com.avocados.comdash.customer;

import com.avocados.comdash.customer.dto.CustomerRequestDTO;
import com.avocados.comdash.customer.dto.CustomerResponseDTO;
import com.avocados.comdash.model.entity.Customer;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    public CustomerResponseDTO createCustomer(@NonNull CustomerRequestDTO customerRequestDTO) {
        Customer customer = customerMapper.toEntity(customerRequestDTO);
        Customer savedCustomer = customerRepository.save(customer);

        return customerMapper.toResponseDto(savedCustomer);
    }
}
