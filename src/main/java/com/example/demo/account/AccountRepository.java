package com.example.demo.account;

import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Meta;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountRepository
        extends JpaRepository<Account, Long> {
    boolean existsAccountByUsername(String username);
}
