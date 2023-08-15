package com.softserve.todolistrestapi.service.impl;

import com.softserve.todolistrestapi.exception.NullEntityReferenceException;
import com.softserve.todolistrestapi.model.Role;
import com.softserve.todolistrestapi.repository.RoleRepository;
import com.softserve.todolistrestapi.service.RoleService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    @Autowired
    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public Role create(Role role) {
        if (role == null) {
            throw new NullEntityReferenceException("Role cannot be 'null'");
        }
        return roleRepository.save(role);
    }

    @Override
    public Role readById(long id) {
        return roleRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(String.format("Role with id '%s' not found", id)));
    }

    @Override
    public Role update(Role role) {
        if (role == null) {
            throw new NullEntityReferenceException("Role cannot be 'null'");
        }
        readById(role.getId());
        return roleRepository.save(role);
    }

    @Override
    public void delete(long id) {
        roleRepository.delete(readById(id));
    }

    @Override
    public List<Role> getAll() {
        List<Role> roles = roleRepository.findAll();
        return roles.isEmpty() ? new ArrayList<>() : roles;
    }
}
