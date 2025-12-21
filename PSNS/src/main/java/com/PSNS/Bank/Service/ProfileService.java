package com.PSNS.Bank.Service;


import com.PSNS.Bank.model.ProfileDto;

public interface ProfileService {

    ProfileDto getProfileByUsername(String username);

    void updateProfile(ProfileDto profileDto);
}

