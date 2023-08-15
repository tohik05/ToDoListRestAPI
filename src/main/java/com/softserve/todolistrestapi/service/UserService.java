package com.softserve.todolistrestapi.service;


import com.softserve.todolistrestapi.dto.user.UserRequest;
import com.softserve.todolistrestapi.model.User;
import org.springframework.validation.BindingResult;

import java.util.List;

public interface UserService {
    User create(UserRequest user, BindingResult bindingResult);
    User readById(long id);
    User update(long id, UserRequest user, BindingResult bindingResult);
    void delete(long id);
    List<User> getAll();

}
