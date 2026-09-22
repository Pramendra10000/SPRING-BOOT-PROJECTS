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

    public AddressServiceImpl(
            AddressRepository addressRepository,
            UserRepository userRepository) {

        this.addressRepository = addressRepository;
        this.userRepository = userRepository;
    }

    // =========================================================
    // ADD ADDRESS
    // =========================================================

    @Override
    public Address addAddress(AddressRequest request) {

        User user = getLoggedInUser();

        /*
         * If this address is marked as default,
         * remove default status from all existing addresses.
         */
        if (Boolean.TRUE.equals(request.getDefaultAddress())) {

            addressRepository.findByUserId(user.getId())
                    .forEach(address -> address.setDefaultAddress(false));
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
        address.setDefaultAddress(
                Boolean.TRUE.equals(request.getDefaultAddress())
        );

        return addressRepository.save(address);
    }

    // =========================================================
    // GET MY ADDRESSES
    // =========================================================

    @Override
    @Transactional(readOnly = true)
    public List<Address> getMyAddresses() {

        User user = getLoggedInUser();

        return addressRepository.findByUserId(user.getId());
    }

    // =========================================================
    // UPDATE ADDRESS
    // =========================================================

    @Override
    public Address updateAddress(Long id, AddressRequest request) {

        User user = getLoggedInUser();

        /*
         * IMPORTANT:
         * Fetch address using BOTH address ID and logged-in user ID.
         *
         * This prevents one customer from modifying
         * another customer's address.
         */
        Address address = addressRepository
                .findByIdAndUserId(id, user.getId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Address not found"
                        ));

        /*
         * If the updated address is being made default,
         * remove default status from the user's other addresses.
         */
        if (Boolean.TRUE.equals(request.getDefaultAddress())) {

            addressRepository.findByUserId(user.getId())
                    .forEach(existingAddress -> {

                        if (!existingAddress.getId().equals(id)) {
                            existingAddress.setDefaultAddress(false);
                        }
                    });
        }

        address.setFullName(request.getFullName());
        address.setMobile(request.getMobile());
        address.setAddressLine1(request.getAddressLine1());
        address.setAddressLine2(request.getAddressLine2());
        address.setCity(request.getCity());
        address.setState(request.getState());
        address.setCountry(request.getCountry());
        address.setPincode(request.getPincode());

        /*
         * Preserve/update the default flag.
         */
        address.setDefaultAddress(
                Boolean.TRUE.equals(request.getDefaultAddress())
        );

        return addressRepository.save(address);
    }

    // =========================================================
    // DELETE ADDRESS
    // =========================================================

    @Override
    public void deleteAddress(Long id) {

        User user = getLoggedInUser();

        /*
         * Again, verify ownership before deleting.
         */
        Address address = addressRepository
                .findByIdAndUserId(id, user.getId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Address not found"
                        ));

        addressRepository.delete(address);
    }

    // =========================================================
    // SET DEFAULT ADDRESS
    // =========================================================

    @Override
    public Address setDefaultAddress(Long id) {

        User user = getLoggedInUser();

        /*
         * First find the address belonging to the logged-in user.
         */
        Address address = addressRepository
                .findByIdAndUserId(id, user.getId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Address not found"
                        ));

        /*
         * Remove default status from all addresses
         * belonging to this user.
         */
        addressRepository.findByUserId(user.getId())
                .forEach(existingAddress ->
                        existingAddress.setDefaultAddress(false)
                );

        /*
         * Make the selected address default.
         */
        address.setDefaultAddress(true);

        return addressRepository.save(address);
    }

    // =========================================================
    // GET LOGGED-IN USER
    // =========================================================

    private User getLoggedInUser() {

        Authentication authentication =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication();

        if (authentication == null ||
                !authentication.isAuthenticated()) {

            throw new ResourceNotFoundException(
                    "User is not authenticated"
            );
        }

        String email = authentication.getName();

        if (email == null || email.isBlank()) {

            throw new ResourceNotFoundException(
                    "Authenticated user email not found"
            );
        }

        return userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "User not found"
                        ));
    }
}