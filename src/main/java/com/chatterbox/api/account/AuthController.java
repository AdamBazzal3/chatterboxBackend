package com.chatterbox.api.account;

import com.chatterbox.api.exceptions.EmailAlreadyInUseException;
import com.chatterbox.api.exceptions.UsernameAlreadyInUseException;
import com.chatterbox.api.utils.APIResponse;
import com.chatterbox.api.utils.ResponseFactory;
import com.chatterbox.api.utils.SignUpRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "api/v1/auth")
public class AuthController {
    private final AuthService authService;
    private final ResponseFactory responseFactory;
    private final BCryptPasswordEncoder encoder;

    @Autowired
    public AuthController(AuthService authService, ResponseFactory responseFactory, BCryptPasswordEncoder encoder) {
        this.authService = authService;
        this.responseFactory = responseFactory;
        this.encoder = encoder;
    }

    @GetMapping("/resource")
    public String resource(){
        return "resource";
    }

    @PostMapping("/signup")
    public ResponseEntity<APIResponse> SignUp(@RequestBody SignUpRequest request){
        APIResponse response;

        try{
            authService.SignUp(
                    request.getUsername(),
                    request.getFullName(),
                    request.getEmail(),
                    encoder.encode(request.getPassword())
            );
            response = responseFactory.getResponse(true);

            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
        catch (UsernameAlreadyInUseException | EmailAlreadyInUseException e ){
            response = responseFactory.getResponse(false);
            response.setMessage(e.getMessage());
        }
        catch (Exception e){
            response = responseFactory.getResponse(false);
            response.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }


}
