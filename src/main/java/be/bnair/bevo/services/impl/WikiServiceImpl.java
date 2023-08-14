package be.bnair.bevo.services.impl;

import org.springframework.stereotype.Service;

import be.bnair.bevo.models.entities.WikiEntity;
import be.bnair.bevo.repository.WikiRepository;
import be.bnair.bevo.services.WikiService;

import java.util.List;
import java.util.Optional;

@Service
public class WikiServiceImpl implements WikiService {
    private final WikiRepository repository;

    public WikiServiceImpl(WikiRepository repository) {
        this.repository = repository;
    }

    @Override
    public WikiEntity create( WikiEntity creater) {
        return repository.save(creater);
    }

    @Override
    public List< WikiEntity> getAll() {
        return repository.findAll();
    }

    @Override
    public Optional< WikiEntity> getOneById(Long id) {
        return repository.findById(id);
    }

    @Override
    public void remove(Long id) throws Exception {
        repository.deleteById(id);
    }

    @Override
    public WikiEntity update(Long id, WikiEntity updater) throws Exception {
        Optional<WikiEntity> eOptional = repository.findById(id);
        if (eOptional.isPresent()) {
            WikiEntity wikiEntity = eOptional.get();
            wikiEntity.setTitle(updater.getTitle());
            wikiEntity.setDescription(updater.getDescription());
            wikiEntity.setImage(updater.getImage());
            return repository.save(wikiEntity);
        }
        throw new Exception("Could not find the server with id: " + id);
    }
}