package com.example.springserver.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {

    @Query("SELECT u FROM User u WHERE u.regNo = ?1 OR u.schoolEmail = ?2")
    Optional<User> getUserByRegNoBeforeOrSchoolEmail(String regNo, String schoolEmail);
}
