package be.bnair.bevo.services;

import be.bnair.bevo.models.entities.VoteEntity;

public interface VoteService extends CrudService<VoteEntity, Long> {
    void deleteAllByUserId(Long userId);
    void deleteAll();
}