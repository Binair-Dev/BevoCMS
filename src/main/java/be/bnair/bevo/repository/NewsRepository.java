package be.bnair.bevo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import be.bnair.bevo.models.entities.NewsEntity;

public interface NewsRepository extends JpaRepository<NewsEntity, Long> {}