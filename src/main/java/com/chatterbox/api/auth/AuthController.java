package com.chatterbox.api.auth;

import com.chatterbox.api.account.AccountService;
import com.chatterbox.api.exceptions.EmailAlreadyInUseException;
import com.chatterbox.api.exceptions.UsernameAlreadyInUseException;
import com.chatterbox.api.response.APIResponse;
import com.chatterbox.api.response.TokenAPIResponse;
import com.chatterbox.api.utils.LoginRequest;
import com.chatterbox.api.utils.SignUpRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(path = "api/v1/auth")
public class AuthController {
    private final AccountService accountService;
    private final BCryptPasswordEncoder encoder;
    private final AuthService authService;

    @Autowired
    public AuthController(AccountService accountService, AuthService authService, BCryptPasswordEncoder encoder) {
        this.accountService = accountService;
        this.encoder = encoder;
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<APIResponse> login(@RequestBody LoginRequest request){
        TokenAPIResponse response = new TokenAPIResponse();
        HttpStatusCode code;

        try{

            response.setSuccess(true);
            response.setToken(authService.verify(request));
            code = HttpStatus.OK;
        }
        catch (AuthenticationException e){
            response.setSuccess(false);
            response.setMessage(e.getMessage());
            code = HttpStatus.UNAUTHORIZED;
        }
        catch (Exception e) {
            response.setSuccess(false);
            response.setMessage(e.getMessage());
            code = HttpStatus.BAD_REQUEST;
        }

        return ResponseEntity.status(code).body(response);
    }

    @PostMapping("/signup")
    public ResponseEntity<APIResponse> signUp(@RequestBody SignUpRequest request){
        APIResponse response = new APIResponse();
        HttpStatusCode code;

        try{
            accountService.SignUp(
                    request.getUsername(),
                    request.getFullName(),
                    request.getEmail(),
                    encoder.encode(request.getPassword())
            );
            response.setSuccess(true);
            response.setMessage("success");
            code = HttpStatus.OK;
        }
        catch (Exception e) {
            response.setSuccess(false);
            response.setMessage(e.getMessage());

            if (e instanceof UsernameAlreadyInUseException || e instanceof EmailAlreadyInUseException) {
                code = HttpStatus.CONFLICT;
            } else {
                code = HttpStatus.BAD_REQUEST;
            }
        }

        return ResponseEntity.status(code).body(response);
    }
}
