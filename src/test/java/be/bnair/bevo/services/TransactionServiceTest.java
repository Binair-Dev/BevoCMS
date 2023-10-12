package be.bnair.bevo.services;

import static org.mockito.Mockito.*;

import be.bnair.bevo.models.entities.security.UserEntity;
import be.bnair.bevo.utils.tests.TestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import be.bnair.bevo.models.entities.TransactionEntity;
import be.bnair.bevo.repository.TransactionRepository;
import be.bnair.bevo.services.impl.TransactionServiceImpl;
import static org.junit.jupiter.api.Assertions.*;

public class TransactionServiceTest {

    @InjectMocks
    private TransactionServiceImpl transactionService;

    @Mock
    private TransactionRepository transactionRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateTransaction() {
        UserEntity user = TestUtils.getUserEntity(1L, "Test", TestUtils.getRankEntity(1L, "Test"));
        TransactionEntity transactionToCreate = TestUtils.getTransactionEntity(1L, "Test", user);

        when(transactionRepository.save(any(TransactionEntity.class))).thenReturn(transactionToCreate);

        TransactionEntity createdTransaction = transactionService.create(transactionToCreate);

        verify(transactionRepository, times(1)).save(transactionToCreate);

        assertNotNull(createdTransaction.getId());
        assertEquals(1, createdTransaction.getCredit(), 0.001);
        assertEquals(1, createdTransaction.getPrice(), 0.001);
        assertEquals(LocalDate.now(), createdTransaction.getDate());
        assertNotNull(createdTransaction.getUser());
        assertEquals(user, createdTransaction.getUser());
        assertEquals("Test", createdTransaction.getIdentifier());
    }

    @Test
    public void testGetAllTransactions() {
        List<TransactionEntity> transactionsList = new ArrayList<>();

        UserEntity user = TestUtils.getUserEntity(1L, "Test", TestUtils.getRankEntity(1L, "Test"));
        TransactionEntity t1 = TestUtils.getTransactionEntity(1L, "Test1", user);
        TransactionEntity t2 = TestUtils.getTransactionEntity(2L, "Test2", user);

        transactionsList.add(t1);
        transactionsList.add(t2);

        when(transactionRepository.findAll()).thenReturn(transactionsList);

        List<TransactionEntity> retrievedTransactions = transactionService.getAll();

        verify(transactionRepository, times(1)).findAll();
        assertEquals(2, retrievedTransactions.size());
        assertNotNull(retrievedTransactions.get(0).getId());
        assertEquals(1, retrievedTransactions.get(0).getCredit(), 0.001);
        assertEquals(1, retrievedTransactions.get(0).getPrice(), 0.001);
        assertEquals(LocalDate.now(), retrievedTransactions.get(0).getDate());
        assertNotNull(retrievedTransactions.get(0).getUser());
        assertEquals(user, retrievedTransactions.get(0).getUser());
        assertEquals("Test1", retrievedTransactions.get(0).getIdentifier());

        assertNotNull(retrievedTransactions.get(1).getId());
        assertEquals(1, retrievedTransactions.get(1).getCredit(), 0.001);
        assertEquals(1, retrievedTransactions.get(1).getPrice(), 0.001);
        assertEquals(LocalDate.now(), retrievedTransactions.get(1).getDate());
        assertNotNull(retrievedTransactions.get(1).getUser());
        assertEquals(user, retrievedTransactions.get(1).getUser());
        assertEquals("Test2", retrievedTransactions.get(1).getIdentifier());
    }

    @Test
    public void testGetOneTransactionById() {
        UserEntity user = TestUtils.getUserEntity(1L, "Test", TestUtils.getRankEntity(1L, "Test"));
        TransactionEntity t1 = TestUtils.getTransactionEntity(1L, "Test", user);

        when(transactionRepository.findById(1L)).thenReturn(Optional.of(t1));

        Optional<TransactionEntity> retrievedTransaction = transactionService.getOneById(1L);

        verify(transactionRepository, times(1)).findById(1L);
        assertTrue(retrievedTransaction.isPresent());
        assertNotNull(retrievedTransaction.get().getId());
        assertEquals(1, retrievedTransaction.get().getCredit(), 0.001);
        assertEquals(1, retrievedTransaction.get().getPrice(), 0.001);
        assertEquals(LocalDate.now(), retrievedTransaction.get().getDate());
        assertNotNull(retrievedTransaction.get().getUser());
        assertEquals(user, retrievedTransaction.get().getUser());
        assertEquals("Test", retrievedTransaction.get().getIdentifier());
    }
}
