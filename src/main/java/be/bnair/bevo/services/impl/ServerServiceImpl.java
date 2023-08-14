package be.bnair.bevo.services.impl;

import org.springframework.stereotype.Service;

import be.bnair.bevo.models.entities.ServerEntity;
import be.bnair.bevo.repository.ServerRepository;
import be.bnair.bevo.services.ServerService;

import java.util.List;
import java.util.Optional;

@Service
public class ServerServiceImpl implements ServerService {
    private final ServerRepository repository;

    public ServerServiceImpl(ServerRepository repository) {
        this.repository = repository;
    }

    @Override
    public ServerEntity create( ServerEntity creater) {
        return repository.save(creater);
    }

    @Override
    public List< ServerEntity> getAll() {

        return repository.findAll();
    }
    @Override
    public Optional< ServerEntity> getOneById(Long id) {
        return repository.findById(id);
    }

    @Override
    public void remove(Long id) throws Exception {
        repository.deleteById(id);
    }

    @Override
    public ServerEntity update(Long id, ServerEntity updater) throws Exception {
        Optional< ServerEntity> server = repository.findById(id);
        if (server.isPresent()) {
            ServerEntity serverEntity = server.get();
            serverEntity.setTitle(updater.getTitle());
            serverEntity.setServerIp(updater.getServerIp());
            serverEntity.setServerPort(updater.getServerPort());
            serverEntity.setRconPort(updater.getRconPort());
            serverEntity.setRconPassword(updater.getRconPassword());
            return repository.save(serverEntity);
        }
        throw new Exception("Could not find the server with id: " + id);
    }
}