package com.ecommerce.userservice.mapper;

import com.ecommerce.userservice.dto.UserResponse;
import com.ecommerce.userservice.model.User;

public final class UserMapper {

    private UserMapper() {
    }

    public static UserResponse toResponse(User user) {
        return new UserResponse(
                user.getId(),
                user.getEmail(),
                user.getFullName(),
                user.getCreatedAt()
        );
    }
}