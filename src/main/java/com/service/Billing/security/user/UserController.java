package com.service.Billing.security.user;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.service.Billing.config.SecurityConfig.SECURITY_CONFIG_NAME;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@SecurityRequirement(name = SECURITY_CONFIG_NAME)
public class UserController {
    private final UserService userService;



    @GetMapping("/all")
    public ResponseEntity<List<UserRecord>> getAllUsers(){
        return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.FOUND);
    }

    @PostMapping("/add")
    public ResponseEntity<User> add(@RequestBody User user) {
        return ResponseEntity.ok(userService.add(user));
    }


    @GetMapping("/{email}")
    public User getByEmail(@PathVariable("email") String email){
        return  userService.getUser(email);
    }

    @DeleteMapping("/{email}")
    public void delete(@PathVariable("email") String email){
        userService.delete(email);
    }

    @PutMapping("/update")
    public ResponseEntity<User> update(@RequestBody User user){
        return ResponseEntity.ok(userService.update(user));
    }

}
