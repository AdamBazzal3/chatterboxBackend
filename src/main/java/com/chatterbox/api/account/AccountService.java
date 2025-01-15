package com.chatterbox.api.account;

import com.chatterbox.api.exceptions.EmailAlreadyInUseException;
import com.chatterbox.api.exceptions.UsernameAlreadyInUseException;
import com.chatterbox.api.utils.LoginRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AccountService implements UserDetailsService {
    private final AccountRepository accountRepository;
    private final static String USER_NOT_FOUND = "username %s is not found.";

    @Autowired
    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public void SignUp(String username, String fullName, String email, String password) throws Exception {
        Optional<Account> accountOptional =
                accountRepository.findStudentByUsernameOrEmail(username, email);

        if (accountOptional.isPresent()) {
            Account existingAccount = accountOptional.get();

            if (existingAccount.getUsername().equals(username)) {
                throw new UsernameAlreadyInUseException("Username is already taken.");
            } else if (existingAccount.getEmail().equals(email)) {
                throw new EmailAlreadyInUseException("Email is already taken.");
            }
        }

        accountRepository.saveAndFlush(new Account(
                username,
                email,
                fullName,
                password
        ));
    }

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {
        Account user = accountRepository.findStudentByUsername(username)
                .orElseThrow(()-> new UsernameNotFoundException(
                                String.format(
                                        USER_NOT_FOUND
                                        , username
                                )
                        )
                );

        if(user == null){
            System.out.println("User not found...");
            throw new UsernameNotFoundException("user not found");
        }
        return user;
    }


}
