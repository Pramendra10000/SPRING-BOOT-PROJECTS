package com.parammart.service;

import java.util.List;

import com.parammart.dto.request.AddressRequest;
import com.parammart.entity.Address;

public interface AddressService {

    Address addAddress(AddressRequest request);

    List<Address> getMyAddresses();

    Address updateAddress(Long id, AddressRequest request);

    void deleteAddress(Long id);

    Address setDefaultAddress(Long id);

}