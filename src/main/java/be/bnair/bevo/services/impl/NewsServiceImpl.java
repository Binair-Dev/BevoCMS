package be.bnair.bevo.services.impl;

import org.springframework.stereotype.Service;

import be.bnair.bevo.models.entities.NewsEntity;
import be.bnair.bevo.repository.NewsRepository;
import be.bnair.bevo.services.NewsService;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class NewsServiceImpl implements NewsService {
    private final NewsRepository repository;

    public NewsServiceImpl(NewsRepository repository) {
        this.repository = repository;
    }

    @Override
    public NewsEntity create( NewsEntity creater) {
        return repository.save(creater);
    }

    @Override
    public List< NewsEntity> getAll() {
        return repository.findAll();
    }

    @Override
    public Optional< NewsEntity> getOneById(Long id) {
        return repository.findById(id);
    }

    @Override
    public void remove(Long id) throws Exception {
        repository.deleteById(id);
    }

   @Override
    public NewsEntity update(Long id, NewsEntity updater) throws Exception {
        Optional<NewsEntity> entity = this.repository.findById(id);
        if(entity.isPresent()) {
            NewsEntity newsEntity = entity.get();
            newsEntity.setTitle(updater.getTitle());
            newsEntity.setDescription(updater.getDescription());
            newsEntity.setImage(updater.getImage());
            newsEntity.setDate(LocalDate.now());
            newsEntity.setPublished(updater.isPublished());
            newsEntity.setAuthor(updater.getAuthor());
            return this.repository.save(newsEntity);
        }
        throw new Exception("Could not find the news with id: " + id);
    }
}