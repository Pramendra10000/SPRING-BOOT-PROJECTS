package com.parammart.service.impl;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.parammart.dto.request.AddressRequest;
import com.parammart.entity.Address;
import com.parammart.entity.User;
import com.parammart.exception.ResourceNotFoundException;
import com.parammart.repository.AddressRepository;
import com.parammart.repository.UserRepository;
import com.parammart.service.AddressService;

@Service
@Transactional
public class AddressServiceImpl implements AddressService {

    private final AddressRepository addressRepository;
    private final UserRepository userRepository;

    public AddressServiceImpl(AddressRepository addressRepository,
                              UserRepository userRepository) {

        this.addressRepository = addressRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Address addAddress(AddressRequest request) {

        User user = getLoggedInUser();

        if (Boolean.TRUE.equals(request.getDefaultAddress())) {

            addressRepository.findByUserId(user.getId())
                    .forEach(a -> a.setDefaultAddress(false));
        }

        Address address = new Address();

        address.setUser(user);
        address.setFullName(request.getFullName());
        address.setMobile(request.getMobile());
        address.setAddressLine1(request.getAddressLine1());
        address.setAddressLine2(request.getAddressLine2());
        address.setCity(request.getCity());
        address.setState(request.getState());
        address.setCountry(request.getCountry());
        address.setPincode(request.getPincode());
        address.setDefaultAddress(request.getDefaultAddress());

        return addressRepository.save(address);
    }

    @Override
    public List<Address> getMyAddresses() {

        User user = getLoggedInUser();

        return addressRepository.findByUserId(user.getId());
    }

    @Override
    public Address updateAddress(Long id, AddressRequest request) {

        Address address = addressRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Address not found"));

        address.setFullName(request.getFullName());
        address.setMobile(request.getMobile());
        address.setAddressLine1(request.getAddressLine1());
        address.setAddressLine2(request.getAddressLine2());
        address.setCity(request.getCity());
        address.setState(request.getState());
        address.setCountry(request.getCountry());
        address.setPincode(request.getPincode());

        return addressRepository.save(address);
    }

    @Override
    public void deleteAddress(Long id) {

        Address address = addressRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Address not found"));

        addressRepository.delete(address);
    }

    @Override
    public Address setDefaultAddress(Long id) {

        User user = getLoggedInUser();

        addressRepository.findByUserId(user.getId())
                .forEach(a -> a.setDefaultAddress(false));

        Address address = addressRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Address not found"));

        address.setDefaultAddress(true);

        return addressRepository.save(address);
    }

    // ==========================

    private User getLoggedInUser() {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        String email = authentication.getName();

        return userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));
    }
}