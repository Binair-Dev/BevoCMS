package be.bnair.bevo.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import be.bnair.bevo.models.entities.ShopTransactionEntity;
import be.bnair.bevo.repository.ShopTransactionRepository;
import be.bnair.bevo.services.ShopTransactionService;

import java.util.List;
import java.util.Optional;

@Service
public class ShopTransactionServiceImpl implements ShopTransactionService {
    private final ShopTransactionRepository repository;

    public ShopTransactionServiceImpl(ShopTransactionRepository repository) {
        this.repository = repository;
    }

    @Override
    public ShopTransactionEntity create( ShopTransactionEntity creater) {
        return repository.save(creater);
    }

    @Override
    public List< ShopTransactionEntity> getAll() {
        return repository.findAll();
    }

    @Override
    public Optional< ShopTransactionEntity> getOneById(Long id) {
        return repository.findById(id);
    }
}