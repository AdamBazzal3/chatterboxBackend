package com.example.demo.account;

import com.example.demo.utils.APIResponse;
import com.example.demo.utils.ResponseFactory;
import com.example.demo.utils.SignUpRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "api/v1/auth")
public class AuthController {
    private final AuthService authService;
    private final ResponseFactory responseFactory;

    @Autowired
    public AuthController(AuthService authService, ResponseFactory responseFactory) {
        this.authService = authService;
        this.responseFactory = responseFactory;
    }

    @PostMapping("/signup")
    public ResponseEntity<APIResponse> SignUp(@RequestBody SignUpRequest request){
        APIResponse response;

        try{
            authService.SignUp(
                    request.getUsername(),
                    request.getFullName(),
                    request.getEmail(),
                    request.getPassword()
            );
            response = responseFactory.getResponse(true);

            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
        catch (DataIntegrityViolationException e){
            response = responseFactory.getResponse(false);
            response.setMessage("username already exists");
        }
        catch (Exception e){
            response = responseFactory.getResponse(false);
            response.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }


}
