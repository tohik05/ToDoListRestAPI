package com.softserve.todolistrestapi.service.impl;

import com.softserve.todolistrestapi.exception.NullEntityReferenceException;
import com.softserve.todolistrestapi.model.State;
import com.softserve.todolistrestapi.repository.StateRepository;
import com.softserve.todolistrestapi.service.StateService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StateServiceImpl implements StateService {
    private final StateRepository stateRepository;

    @Autowired
    public StateServiceImpl(StateRepository stateRepository) {
        this.stateRepository = stateRepository;
    }

    @Override
    public State readById(long id) {
        return stateRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(String.format("State with id '%s' not found", id)));
    }

    @Override
    public State create(State state) {
        if (state == null) {
            throw new NullEntityReferenceException("State cannot be 'null'");
        }
        return stateRepository.save(state);
    }

    @Override
    public State update(State state) {
        if (state == null) {
            throw new NullEntityReferenceException("State cannot be 'null'");
        }
        readById(state.getId());
        return stateRepository.save(state);
    }

    @Override
    public void delete(long id) {
        stateRepository.delete(readById(id));
    }

    @Override
    public State getByName(String name) {
        return stateRepository.findByName(name).orElseThrow(
                () -> new EntityNotFoundException(String.format("State with name '%s' not found", name)));
    }

    @Override
    public List<State> getAll() {
        List<State> states = stateRepository.getAll();
        return states.isEmpty() ? new ArrayList<>() : states;
    }
}
