package be.bnair.bevo.services.impl;

import org.springframework.stereotype.Service;

import be.bnair.bevo.models.entities.VoteEntity;
import be.bnair.bevo.repository.VoteRepository;
import be.bnair.bevo.services.VoteService;

import java.util.List;
import java.util.Optional;

@Service
public class VoteServiceImpl implements VoteService {
    private final VoteRepository repository;

    public VoteServiceImpl(VoteRepository repository) {
        this.repository = repository;
    }

    @Override
    public VoteEntity create( VoteEntity creater) {
        return repository.save(creater);
    }

    @Override
    public List< VoteEntity> getAll() {
        return repository.findAll();
    }

    @Override
    public Optional< VoteEntity> getOneById(Long id) {
        return repository.findById(id);
    }

    @Override
    public void remove(Long id) throws Exception {
        repository.deleteById(id);
    }

    @Override
    public void deleteAllByUserId(Long userId) {
        List<VoteEntity> votes = repository.findAll();
        for(VoteEntity vote : votes) {
            if(vote.getUser().getId() == userId) {
                repository.delete(vote);
            }
        }
    }

    @Override
    public void deleteAll() {
        repository.deleteAll();
    }
}