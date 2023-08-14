package be.bnair.bevo.services.impl;

import org.springframework.stereotype.Service;

import be.bnair.bevo.models.entities.RuleEntity;
import be.bnair.bevo.repository.RuleRepository;
import be.bnair.bevo.services.RuleService;

import java.util.List;
import java.util.Optional;

@Service
public class RuleServiceImpl implements RuleService {
    private final RuleRepository repository;

    public RuleServiceImpl(RuleRepository repository) {
        this.repository = repository;
    }

    @Override
    public RuleEntity create( RuleEntity creater) {
        return repository.save(creater);
    }

    @Override
    public List< RuleEntity> getAll() {
        return repository.findAll();
    }

    @Override
    public Optional< RuleEntity> getOneById(Long id) {
        return repository.findById(id);
    }

    @Override
    public void remove(Long id) throws Exception {
        repository.deleteById(id);
    }

    @Override
    public RuleEntity update(Long id, RuleEntity updater) throws Exception {
        Optional<RuleEntity> entity = repository.findById(id);
        if (entity.isPresent()) {
            RuleEntity ruleEntity = entity.get();
            ruleEntity.setTitle(updater.getTitle());
            ruleEntity.setDescription(updater.getDescription());
            return repository.save(ruleEntity);
        }
        throw new Exception("Could not find the rule with id: " + id);
    }
}