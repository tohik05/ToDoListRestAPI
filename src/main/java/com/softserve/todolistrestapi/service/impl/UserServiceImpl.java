package com.softserve.todolistrestapi.service.impl;

import com.softserve.todolistrestapi.dto.user.UserRequest;
import com.softserve.todolistrestapi.exception.DuplicateEmailException;
import com.softserve.todolistrestapi.exception.EntityNotCreatedException;
import com.softserve.todolistrestapi.exception.NullEntityReferenceException;
import com.softserve.todolistrestapi.model.User;
import com.softserve.todolistrestapi.repository.UserRepository;
import com.softserve.todolistrestapi.service.RoleService;
import com.softserve.todolistrestapi.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.softserve.todolistrestapi.util.ErrorMessageBuilder.errorMessage;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleService roleService, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User readById(long id) {
        return userRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(String.format("User with id '%s' not found", id)));
    }

    @Override
    public User create(UserRequest user, BindingResult bindingResult) {
        if (user == null) {
            throw new NullEntityReferenceException("User cannot be 'null'");
        }
        if (bindingResult.hasErrors()){
            throw new EntityNotCreatedException(errorMessage(bindingResult));
        }
        checkDuplicateEmail(user.getEmail());

        User newUser = new User();
        newUser.setFirstName(user.getFirstName());
        newUser.setLastName(user.getLastName());
        newUser.setEmail(user.getEmail());
        newUser.setPassword(passwordEncoder.encode(user.getPassword()));
        newUser.setRole(roleService.readById(2));
        return userRepository.save(newUser);
    }

    @Override
    public User update(long id, UserRequest user, BindingResult bindingResult) {
        if (user == null) {
            throw new NullEntityReferenceException("User cannot be 'null'");
        }
        if (bindingResult.hasErrors()){
            throw new EntityNotCreatedException(errorMessage(bindingResult));
        }
        checkDuplicateEmail(user.getEmail());

        User oldUser = readById(id);
        oldUser.setFirstName(user.getFirstName());
        oldUser.setLastName(user.getLastName());
        oldUser.setEmail(user.getEmail());
        oldUser.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(oldUser);
    }

    @Override
    public void delete(long id) {
        userRepository.delete(readById(id));
    }

    @Override
    public List<User> getAll() {
        List<User> users = userRepository.findAll();
        return users.isEmpty() ? new ArrayList<>() : users;
    }

    private void checkDuplicateEmail(String email){
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isPresent()){
            throw new DuplicateEmailException("User with this email has already exist");
        }
    }
}
