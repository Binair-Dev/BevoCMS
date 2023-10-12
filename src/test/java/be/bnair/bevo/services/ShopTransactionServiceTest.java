package be.bnair.bevo.services;

import static org.mockito.Mockito.*;

import be.bnair.bevo.models.entities.ShopCategoryEntity;
import be.bnair.bevo.models.entities.ShopItemEntity;
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
        ShopItemEntity item = TestUtils.getShopItemEntity(1L, "Test", TestUtils.getShopCategoryEntity(1L, "Test"), TestUtils.getServerEntity(1L, "Test"));
        ShopTransactionEntity transactionToCreate = TestUtils.getShopTransactionEntity(1L, item, 1, user);

        when(shopTransactionRepository.save(any(ShopTransactionEntity.class))).thenReturn(transactionToCreate);

        ShopTransactionEntity createdTransaction = shopTransactionService.create(transactionToCreate);

        verify(shopTransactionRepository, times(1)).save(transactionToCreate);

        assertNotNull(createdTransaction.getId());
        assertNotNull(createdTransaction.getItem());
        assertEquals(item, createdTransaction.getItem());
        assertEquals(1, createdTransaction.getCredit(), 0.001);
        assertNotNull(createdTransaction.getUser());
        assertEquals(user, createdTransaction.getUser());
    }

    @Test
    public void testGetAllShopTransactions() {
        UserEntity user = TestUtils.getUserEntity(1L, "Test", TestUtils.getRankEntity(1L, "Test"));
        ShopItemEntity item = TestUtils.getShopItemEntity(1L, "Test", TestUtils.getShopCategoryEntity(1L, "Test"), TestUtils.getServerEntity(1L, "Test"));
        ShopTransactionEntity t1 = TestUtils.getShopTransactionEntity(1L, item, 1, user);
        ShopTransactionEntity t2 = TestUtils.getShopTransactionEntity(2L, item, 2, user);

        List<ShopTransactionEntity> transactionsList = new ArrayList<>();
        transactionsList.add(t1);
        transactionsList.add(t2);

        when(shopTransactionRepository.findAll()).thenReturn(transactionsList);

        List<ShopTransactionEntity> retrievedTransactions = shopTransactionService.getAll();

        verify(shopTransactionRepository, times(1)).findAll();
        assertEquals(2, retrievedTransactions.size());

        assertNotNull(retrievedTransactions.get(0).getId());
        assertNotNull(retrievedTransactions.get(0).getItem());
        assertEquals(item, retrievedTransactions.get(0).getItem());
        assertEquals(1, retrievedTransactions.get(0).getCredit(), 0.001);
        assertNotNull(retrievedTransactions.get(0).getUser());
        assertEquals(user, retrievedTransactions.get(0).getUser());

        assertNotNull(retrievedTransactions.get(1).getId());
        assertNotNull(retrievedTransactions.get(1).getItem());
        assertEquals(item, retrievedTransactions.get(1).getItem());
        assertEquals(2, retrievedTransactions.get(1).getCredit(), 0.001);
        assertNotNull(retrievedTransactions.get(1).getUser());
        assertEquals(user, retrievedTransactions.get(1).getUser());
    }

    @Test
    public void testGetOneShopTransactionById() {
        UserEntity user = TestUtils.getUserEntity(1L, "Test", TestUtils.getRankEntity(1L, "Test"));
        ShopItemEntity item = TestUtils.getShopItemEntity(1L, "Test", TestUtils.getShopCategoryEntity(1L, "Test"), TestUtils.getServerEntity(1L, "Test"));
        ShopTransactionEntity t1 = TestUtils.getShopTransactionEntity(1L, item, 1, user);

        when(shopTransactionRepository.findById(1L)).thenReturn(Optional.of(t1));

        Optional<ShopTransactionEntity> retrievedTransaction = shopTransactionService.getOneById(1L);

        verify(shopTransactionRepository, times(1)).findById(1L);
        assertTrue(retrievedTransaction.isPresent());
        assertNotNull(retrievedTransaction.get().getId());
        assertNotNull(retrievedTransaction.get().getItem());
        assertEquals(item, retrievedTransaction.get().getItem());
        assertEquals(1, retrievedTransaction.get().getCredit(), 0.001);
        assertNotNull(retrievedTransaction.get().getUser());
        assertEquals(user, retrievedTransaction.get().getUser());
    }
}
