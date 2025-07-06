package com.benjamin.repository;

import com.benjamin.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IUserRepository extends JpaRepository<UserModel, Long> {
    UserModel findByEmail(String email);
}
