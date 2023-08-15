package com.softserve.todolistrestapi.controller;

import com.softserve.todolistrestapi.dto.user.UserRequest;
import com.softserve.todolistrestapi.dto.user.UserResponse;
import com.softserve.todolistrestapi.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(value = {"", "/"})
    public ResponseEntity<?> create(@RequestBody @Valid UserRequest user, BindingResult bindingResult) {
        userService.create(user, bindingResult);
        return new ResponseEntity<>("User successfully created", HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> read(@PathVariable(name = "id") long id) {
        return new ResponseEntity<>(new UserResponse(userService.readById(id)), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable(name = "id") long id, @RequestBody @Valid UserRequest user, BindingResult bindingResult) {
        userService.update(id, user, bindingResult);
        return new ResponseEntity<>("User successfully updated", HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable(name = "id") long id) {
        userService.delete(id);
        return new ResponseEntity<>("User successfully deleted", HttpStatus.OK);
    }

    @GetMapping(value = {"", "/"})
    public ResponseEntity<List<UserResponse>> getAll() {
        return new ResponseEntity<>(userService.getAll()
                                                .stream()
                                                .map(UserResponse::new)
                                                .collect(Collectors.toList()),
                                    HttpStatus.OK);
    }
}
