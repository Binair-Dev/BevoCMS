package be.bnair.bevo.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import be.bnair.bevo.models.entities.VoteRewardEntity;
import be.bnair.bevo.repository.VoteRewardRepository;
import be.bnair.bevo.services.VoteRewardService;

import java.util.List;
import java.util.Optional;

@Service
public class VoteRewardServiceImpl implements VoteRewardService {
    private final VoteRewardRepository repository;

    public VoteRewardServiceImpl(VoteRewardRepository repository) {
        this.repository = repository;
    }

    @Override
    public VoteRewardEntity create(VoteRewardEntity creater) {
        return repository.save(creater);
    }

    @Override
    public List< VoteRewardEntity> getAll() {
        return repository.findAll();
    }

    @Override
    public Optional< VoteRewardEntity> getOneById(Long id) {
        return repository.findById(id);
    }

    @Override
    public void remove(Long id) throws Exception {
        repository.deleteById(id);
    }

    @Override
    public VoteRewardEntity update(Long id, VoteRewardEntity updater) throws Exception {
        Optional<VoteRewardEntity> optionalVote = repository.findById(id);
        if (optionalVote.isPresent()) {
            VoteRewardEntity voteEntity = optionalVote.get();
            voteEntity.setTitle(updater.getTitle());
            voteEntity.setPercent(updater.getPercent());
            voteEntity.setRewardType(updater.getRewardType());
            voteEntity.setCommand(updater.getCommand());
            voteEntity.setCredit(updater.getCredit());
            voteEntity.setServer(updater.getServer());
            return repository.save(voteEntity);
        }
        throw new Exception("Could not find the shop item with id: " + id);
    }
}