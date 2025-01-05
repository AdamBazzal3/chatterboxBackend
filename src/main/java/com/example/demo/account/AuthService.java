package com.example.demo.account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final AccountRepository accountRepository;

    @Autowired
    public AuthService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public String Login(String username, String password){
        //TODO: implement login mechanism
        return "";
    }

    public void SignUp(String username, String fullName, String email, String password){

        accountRepository.saveAndFlush(new Account(
                username,
                email,
                fullName,
                password
        ));
    }
}
