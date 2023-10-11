package be.bnair.bevo.services;

import static org.mockito.Mockito.*;

import be.bnair.bevo.utils.tests.TestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import be.bnair.bevo.models.entities.PaypalOfferEntity;
import be.bnair.bevo.repository.PaypalOfferRepository;
import be.bnair.bevo.services.impl.PaypalOfferServiceImpl;
import static org.junit.jupiter.api.Assertions.*;

public class PaypalOfferServiceTest {

    @InjectMocks
    private PaypalOfferServiceImpl paypalOfferService;

    @Mock
    private PaypalOfferRepository paypalOfferRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreatePaypalOffer() {
        PaypalOfferEntity offerToCreate = TestUtils.getPaypalOfferEntity(1L, "Test");

        when(paypalOfferRepository.save(any(PaypalOfferEntity.class))).thenReturn(offerToCreate);

        PaypalOfferEntity createdOffer = paypalOfferService.create(offerToCreate);

        verify(paypalOfferRepository, times(1)).save(offerToCreate);

        assertNotNull(createdOffer.getId());
        assertEquals("Test", createdOffer.getTitle());
        assertEquals("Test", createdOffer.getDescription());
        assertEquals(1, createdOffer.getPrice(), 0.01);
        assertEquals(10, createdOffer.getCredit(), 0.01);
    }

    @Test
    public void testGetAllPaypalOffers() {
        List<PaypalOfferEntity> offersList = new ArrayList<>();
        PaypalOfferEntity offer1 = TestUtils.getPaypalOfferEntity(1L, "Test1");
        PaypalOfferEntity offer2 = TestUtils.getPaypalOfferEntity(2L, "Test2");
        offersList.add(offer1);
        offersList.add(offer2);

        when(paypalOfferRepository.findAll()).thenReturn(offersList);

        List<PaypalOfferEntity> retrievedOffers = paypalOfferService.getAll();

        verify(paypalOfferRepository, times(1)).findAll();

        assertEquals(2, retrievedOffers.size());

        assertEquals("Test1", retrievedOffers.get(0).getTitle());
        assertEquals("Test", retrievedOffers.get(0).getDescription());
        assertEquals(1, retrievedOffers.get(0).getPrice(), 0.01);
        assertEquals(10, retrievedOffers.get(0).getCredit(), 0.01);

        assertEquals("Test2", retrievedOffers.get(1).getTitle());
        assertEquals("Test", retrievedOffers.get(1).getDescription());
        assertEquals(1, retrievedOffers.get(1).getPrice(), 0.01);
        assertEquals(10, retrievedOffers.get(1).getCredit(), 0.01);
    }

    @Test
    public void testGetOnePaypalOfferById() {
        Long offerId = 1L;
        PaypalOfferEntity offerEntity = TestUtils.getPaypalOfferEntity(1L, "Test");

        when(paypalOfferRepository.findById(offerId)).thenReturn(Optional.of(offerEntity));

        Optional<PaypalOfferEntity> retrievedOffer = paypalOfferService.getOneById(offerId);

        verify(paypalOfferRepository, times(1)).findById(offerId);
        assertTrue(retrievedOffer.isPresent());
        assertEquals(offerId, retrievedOffer.get().getId());
        assertEquals("Test", retrievedOffer.get().getTitle());
        assertEquals("Test", retrievedOffer.get().getDescription());
        assertEquals(1, retrievedOffer.get().getPrice(), 0.01);
        assertEquals(10, retrievedOffer.get().getCredit(), 0.01);
    }

    @Test
    public void testRemovePaypalOffer() throws Exception {
        Long offerId = 1L;

        paypalOfferService.remove(offerId);

        verify(paypalOfferRepository, times(1)).deleteById(offerId);
    }

    @Test
    public void testUpdatePaypalOffer() throws Exception {
        PaypalOfferEntity existingOffer = TestUtils.getPaypalOfferEntity(1L, "Test1");
        PaypalOfferEntity offerToUpdate = TestUtils.getPaypalOfferEntity(1L, "Test2");

        when(paypalOfferRepository.findById(1L)).thenReturn(Optional.of(existingOffer));
        when(paypalOfferRepository.save(any(PaypalOfferEntity.class))).thenReturn(offerToUpdate);

        PaypalOfferEntity updatedOffer = paypalOfferService.update(1L, offerToUpdate);

        verify(paypalOfferRepository, times(1)).findById(1L);
        verify(paypalOfferRepository, times(1)).save(offerToUpdate);

        assertEquals(1L, updatedOffer.getId());
        assertEquals("Test2", updatedOffer.getTitle());
        assertEquals("Test", updatedOffer.getDescription());
        assertEquals(1, updatedOffer.getPrice(), 0.01);
        assertEquals(10, updatedOffer.getCredit(), 0.01);
    }
}
