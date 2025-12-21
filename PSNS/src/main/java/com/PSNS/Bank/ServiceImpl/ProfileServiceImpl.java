package com.PSNS.Bank.ServiceImpl;

import org.springframework.stereotype.Service;

import com.PSNS.Bank.model.ProfileDto;
import com.PSNS.Bank.Service.ProfileService;

@Service
public class ProfileServiceImpl implements ProfileService {

    @Override
    public ProfileDto getProfileByUsername(String username) {

        // 🔧 Dummy data (replace with DB call)
        ProfileDto dto = new ProfileDto();
        dto.setUserName(username);
        dto.setFirstName("John");
        dto.setLastName("Doe");
        dto.setEmail("john.doe@email.com");
        dto.setPhone("+1 234 567 8900");
        dto.setAddress("123 Main Street, New York, NY 10001");

        return dto;
    }

    @Override
    public void updateProfile(ProfileDto profileDto) {
        // 🔧 Save to DB
        System.out.println("Profile updated for user: " + profileDto.getUserName());
    }
}

