package com.avocados.comdash.customer;

import com.avocados.comdash.customer.dto.CustomerRequestDTO;
import com.avocados.comdash.customer.dto.CustomerResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/customer")
public class CustomerController {
    private final CustomerService customerService;

    @PostMapping()
    public ResponseEntity<CustomerResponseDTO> createCustomer(@RequestBody CustomerRequestDTO customerRequestDTO) {
        CustomerResponseDTO responseDTO = customerService.createCustomer(customerRequestDTO);

        return ResponseEntity
                .created(URI.create("api/v1/customer/" + responseDTO.getId()))
                .body(responseDTO);
    }
}
