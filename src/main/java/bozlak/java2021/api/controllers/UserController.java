package bozlak.java2021.api.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import bozlak.java2021.business.abstracts.UserService;
import bozlak.java2021.core.dtos.user.CreateUserRequest;
import bozlak.java2021.core.utilities.results.ErrorDataResult;
import bozlak.java2021.core.utilities.results.Result;

@RestController
@RequestMapping(path = "/api/users")
public class UserController {
    
    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<?> add(@Valid @RequestBody CreateUserRequest createUserRequest) {
        Result result = this.userService.add(createUserRequest);
        return ResponseEntity.ok(result);
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDataResult<Object> handleValidationException(MethodArgumentNotValidException exceptions) {
        Map<String,String> validationErrors = new HashMap<>();
        List<FieldError> fieldErrors = exceptions.getBindingResult().getFieldErrors();
        for (FieldError fieldError : fieldErrors) {
            validationErrors.put(fieldError.getField(), fieldError.getDefaultMessage());
        }
        String message = "Doğrulama hataları!";
        return new ErrorDataResult<Object>(message, validationErrors);
    }

    // @PostMapping
    // public ResponseEntity<?> add(@Valid @RequestBody User user) {
    //     Result result = this.userService.add(user);
    //     return ResponseEntity.ok(result);
    // }
    
}
// 200 - 201(Added code)
// 300
// 400
// 500