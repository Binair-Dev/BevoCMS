package be.bnair.bevo.services.impl;

import org.springframework.stereotype.Service;

import be.bnair.bevo.models.entities.PaypalOfferEntity;
import be.bnair.bevo.repository.PaypalOfferRepository;
import be.bnair.bevo.services.PaypalOfferService;

import java.util.List;
import java.util.Optional;

@Service
public class PaypalOfferServiceImpl implements PaypalOfferService {
    private final PaypalOfferRepository repository;

    public PaypalOfferServiceImpl(PaypalOfferRepository repository) {
        this.repository = repository;
    }

    @Override
    public PaypalOfferEntity create( PaypalOfferEntity creater) {
        return repository.save(creater);
    }

    @Override
    public List< PaypalOfferEntity> getAll() {
        return repository.findAll();
    }

    @Override
    public Optional< PaypalOfferEntity> getOneById(Long id) {
        return repository.findById(id);
    }

    @Override
    public void remove(Long id) throws Exception {
        repository.deleteById(id);
    }

    @Override
    public PaypalOfferEntity update(Long id, PaypalOfferEntity updater) throws Exception {
        Optional<PaypalOfferEntity> optional = repository.findById(id);
        if(optional.isPresent()) {
            PaypalOfferEntity paypalOfferEntity = optional.get();
            paypalOfferEntity.setTitle(updater.getTitle());
            paypalOfferEntity.setDescription(updater.getDescription());
            paypalOfferEntity.setPrice(updater.getPrice());
            paypalOfferEntity.setCredit(updater.getCredit());
            return repository.save(paypalOfferEntity);
        }
        throw new Exception("Could not find the paypal offer with id: " + id);
    }
}