package com.chatterbox.api.account;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Transactional
@Repository
public interface AccountRepository
        extends JpaRepository<Account, Long> {
    Optional<Account> findStudentByUsernameOrEmail(String username, String email);
    Optional<Account> findStudentByUsername(String username);
}
