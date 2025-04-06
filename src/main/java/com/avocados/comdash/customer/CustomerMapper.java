package com.avocados.comdash.customer;

import com.avocados.comdash.customer.dto.CustomerRequestDTO;
import com.avocados.comdash.customer.dto.CustomerResponseDTO;
import com.avocados.comdash.model.entity.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel="spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface CustomerMapper {

    Customer toEntity(CustomerRequestDTO dto);

    CustomerResponseDTO toResponseDto(Customer customer);
}
