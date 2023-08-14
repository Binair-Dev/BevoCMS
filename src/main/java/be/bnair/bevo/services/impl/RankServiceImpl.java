package be.bnair.bevo.services.impl;

import org.springframework.stereotype.Service;

import be.bnair.bevo.models.entities.RankEntity;
import be.bnair.bevo.repository.RankRepository;
import be.bnair.bevo.services.RankService;

import java.util.List;
import java.util.Optional;

@Service
public class RankServiceImpl implements RankService {
    private final RankRepository repository;

    public RankServiceImpl(RankRepository repository) {
        this.repository = repository;
    }

    @Override
    public RankEntity create( RankEntity creater) {
        return repository.save(creater);
    }

    @Override
    public List< RankEntity> getAll() {
        return repository.findAll();
    }

    @Override
    public Optional< RankEntity> getOneById(Long id) {
        return repository.findById(id);
    }

    @Override
    public void remove(Long id) throws Exception {
        repository.deleteById(id);
    }

    @Override
    public RankEntity update(Long id, RankEntity updater) throws Exception {
        Optional<RankEntity> rank = repository.findById(id);
        if (rank.isPresent()) {
            RankEntity rankEntity = rank.get();
            rankEntity.setTitle(updater.getTitle());
            rankEntity.setPower(updater.getPower());
            return repository.save(rankEntity);
        }
        throw new Exception("Could not find the rank with id: " + id);
    }
}