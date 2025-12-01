package com.example.springserver.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface UserRepository extends JpaRepository<User,Long> {

    @Query("SELECT u FROM User u WHERE u.regNo = ?1 OR u.schoolEmail = ?2")
    Optional<User> getUserByRegNoBeforeOrSchoolEmail(String regNo, String schoolEmail);

    User getUsersBySchoolEmail(String schoolEmail);

    @Query(
            value = "SELECT * FROM User u WHERE JSON_OVERLAPS(u.roles, :roles)",
            nativeQuery = true
    )
    List<User> findByRolesJsonOverlap(@Param("roles") String rolesJson);



}
