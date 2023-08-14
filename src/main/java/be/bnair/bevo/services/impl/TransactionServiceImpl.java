package be.bnair.bevo.services.impl;

import org.springframework.stereotype.Service;

import be.bnair.bevo.models.entities.TransactionEntity;
import be.bnair.bevo.repository.TransactionRepository;
import be.bnair.bevo.services.TransactionService;

import java.util.List;
import java.util.Optional;

@Service
public class TransactionServiceImpl implements TransactionService {
    private final TransactionRepository repository;

    public TransactionServiceImpl(TransactionRepository repository) {
        this.repository = repository;
    }

    @Override
    public TransactionEntity create(TransactionEntity creater) {
        return repository.save(creater);
    }

    @Override
    public List< TransactionEntity> getAll() {
        return repository.findAll();
    }

    @Override
    public Optional< TransactionEntity> getOneById(Long id) {
        return repository.findById(id);
    }
}