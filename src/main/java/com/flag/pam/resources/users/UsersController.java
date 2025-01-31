package com.flag.pam.resources.users;

import com.flag.pam.common.AppResponse;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

/**
 * @author rameshalagumalai
 * @Date 26/01/2025
 * */

@RestController
@RequestMapping("/api/users")
public class UsersController {

    @Autowired
    private UsersRepository usersRepository;

    @GetMapping
    public AppResponse getAllUsers() {
        return new AppResponse(usersRepository.findAll());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AppResponse createNewUser(@Valid @RequestBody UserCreatePayload payload) throws Exception {
        User user = User.builder()
                .authId(payload.getAuthId())
                .name(payload.getName())
                .email(payload.getEmail())
                .upiId(payload.getUpiId())
                .build();
        usersRepository.save(user);

        return new AppResponse("User created successfully", user);
    }

    @GetMapping("/{userId:\\d+}")
    public AppResponse getUserById(@PathVariable long userId) throws Exception {
        Optional<User> userResult = usersRepository.findById(userId);
        if (!userResult.isPresent()) {
            throw new EntityNotFoundException("User doesn't exist");
        }
        return new AppResponse(userResult.get());
    }

    @PutMapping(path = "/{userId:\\d+}")
    public AppResponse editUserById(@PathVariable long userId, @Valid @RequestBody UserEditPayload payload) throws Exception {
        Optional<User> userResult = usersRepository.findById(userId);
        if (!userResult.isPresent()) {
            throw new EntityNotFoundException("User doesn't exist");
        }

        User user = userResult.get();
        user.setName(payload.getName());
        user.setUpiId(payload.getUpiId());
        usersRepository.save(user);

        return new AppResponse("User edited successfully", user);
    }

    @DeleteMapping("/{userId:\\d+}")
    public AppResponse deleteUserById(@PathVariable long userId) throws Exception {
        Optional<User> userResult = usersRepository.findById(userId);
        if (!userResult.isPresent()) {
            throw new EntityNotFoundException("User doesn't exist");
        }
        usersRepository.delete(userResult.get());

        return new AppResponse("User deleted successfully");
    }

}
