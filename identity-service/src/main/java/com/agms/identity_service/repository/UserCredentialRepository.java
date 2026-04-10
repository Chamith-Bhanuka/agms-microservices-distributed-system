package com.agms.identity_service.repository;

import com.agms.identity_service.entity.UserCredential;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserCredentialRepository extends JpaRepository<UserCredential, Integer> {

    // This is used by CustomUserDetailsService to load user by username
    Optional<UserCredential> findByName(String name);

}