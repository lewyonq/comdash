package com.avocados.comdash.customer;

import com.avocados.comdash.customer.dto.CustomerRequestDTO;
import com.avocados.comdash.customer.dto.CustomerResponseDTO;
import com.avocados.comdash.model.entity.Customer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private CustomerMapper customerMapper;

    @InjectMocks
    private CustomerService customerService;

    @BeforeEach
    void setUp() {
    }

    @Test
    void createCustomer_Success() {
        CustomerRequestDTO requestDTO = new CustomerRequestDTO();
        Customer testCustomer = new Customer();
        CustomerResponseDTO responseDTO = new CustomerResponseDTO();
        when(customerMapper.toEntity(requestDTO)).thenReturn(testCustomer);
        when(customerRepository.save(any(Customer.class))).thenReturn(testCustomer);
        when(customerMapper.toResponseDto(any(Customer.class))).thenReturn(responseDTO);

        CustomerResponseDTO result = customerService.createCustomer(requestDTO);

        assertNotNull(result);
        assertEquals(responseDTO, result);
        verify(customerMapper).toEntity(requestDTO);
        verify(customerRepository).save(testCustomer);
        verify(customerMapper).toResponseDto(testCustomer);
    }
}