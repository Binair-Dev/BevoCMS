package be.bnair.bevo.services;

import static org.mockito.Mockito.*;

import be.bnair.bevo.models.entities.security.UserEntity;
import be.bnair.bevo.utils.tests.TestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import be.bnair.bevo.models.entities.ShopTransactionEntity;
import be.bnair.bevo.repository.ShopTransactionRepository;
import be.bnair.bevo.services.impl.ShopTransactionServiceImpl;
import static org.junit.jupiter.api.Assertions.*;

public class ShopTransactionServiceTest {

    @InjectMocks
    private ShopTransactionServiceImpl shopTransactionService;

    @Mock
    private ShopTransactionRepository shopTransactionRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateShopTransaction() {
        UserEntity user = TestUtils.getUserEntity(1L, "Test", TestUtils.getRankEntity(1L, "Test"));

        ShopTransactionEntity transactionToCreate = new ShopTransactionEntity();
        transactionToCreate.setCredit(10.0);

        when(shopTransactionRepository.save(any(ShopTransactionEntity.class))).thenReturn(transactionToCreate);

        ShopTransactionEntity createdTransaction = shopTransactionService.create(transactionToCreate);

        verify(shopTransactionRepository, times(1)).save(transactionToCreate);

        assertNotNull(createdTransaction.getId());
        assertEquals(10.0, createdTransaction.getCredit(), 0.001);
    }

    @Test
    public void testGetAllShopTransactions() {
        List<ShopTransactionEntity> transactionsList = new ArrayList<>();
        ShopTransactionEntity transaction1 = new ShopTransactionEntity();
        transaction1.setCredit(5.0);
        ShopTransactionEntity transaction2 = new ShopTransactionEntity();
        transaction2.setCredit(7.5);
        transactionsList.add(transaction1);
        transactionsList.add(transaction2);

        when(shopTransactionRepository.findAll()).thenReturn(transactionsList);

        List<ShopTransactionEntity> retrievedTransactions = shopTransactionService.getAll();

        verify(shopTransactionRepository, times(1)).findAll();
        assertEquals(2, retrievedTransactions.size());
        assertEquals(5.0, retrievedTransactions.get(0).getCredit(), 0.001);
        assertEquals(7.5, retrievedTransactions.get(1).getCredit(), 0.001);
    }

    @Test
    public void testGetOneShopTransactionById() {
        Long transactionId = 1L;
        ShopTransactionEntity transactionEntity = new ShopTransactionEntity();
        transactionEntity.setId(transactionId);
        transactionEntity.setCredit(10.0);

        when(shopTransactionRepository.findById(transactionId)).thenReturn(Optional.of(transactionEntity));

        Optional<ShopTransactionEntity> retrievedTransaction = shopTransactionService.getOneById(transactionId);

        verify(shopTransactionRepository, times(1)).findById(transactionId);
        assertTrue(retrievedTransaction.isPresent());
        assertEquals(transactionId, retrievedTransaction.get().getId());
        assertEquals(10.0, retrievedTransaction.get().getCredit(), 0.001);
    }

    @Test
    public void testRemoveShopTransaction() throws Exception {
        Long transactionId = 1L;

        shopTransactionService.remove(transactionId);

        verify(shopTransactionRepository, times(1)).deleteById(transactionId);
    }

    @Test
    public void testUpdateShopTransaction() throws Exception {
        Long transactionId = 1L;
        ShopTransactionEntity transactionToUpdate = new ShopTransactionEntity();
        transactionToUpdate.setCredit(15.0);

        ShopTransactionEntity existingTransaction = new ShopTransactionEntity();
        existingTransaction.setId(transactionId);
        existingTransaction.setCredit(10.0);

        when(shopTransactionRepository.findById(transactionId)).thenReturn(Optional.of(existingTransaction));
        when(shopTransactionRepository.save(any(ShopTransactionEntity.class))).thenReturn(transactionToUpdate);

        ShopTransactionEntity updatedTransaction = shopTransactionService.update(transactionId, transactionToUpdate);

        verify(shopTransactionRepository, times(1)).findById(transactionId);
        verify(shopTransactionRepository, times(1)).save(transactionToUpdate);

        assertEquals(transactionId, updatedTransaction.getId());
        assertEquals(15.0, updatedTransaction.getCredit(), 0.001);
    }
}
