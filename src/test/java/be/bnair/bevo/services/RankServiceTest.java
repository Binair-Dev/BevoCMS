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
import be.bnair.bevo.models.entities.RankEntity;
import be.bnair.bevo.repository.RankRepository;
import be.bnair.bevo.services.impl.RankServiceImpl;
import static org.junit.jupiter.api.Assertions.*;

public class RankServiceTest {

    @InjectMocks
    private RankServiceImpl rankService;

    @Mock
    private RankRepository rankRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateRank() {
        RankEntity rankToCreate = TestUtils.getRankEntity(1L, "Test");

        when(rankRepository.save(any(RankEntity.class))).thenReturn(rankToCreate);

        RankEntity createdRank = rankService.create(rankToCreate);

        verify(rankRepository, times(1)).save(rankToCreate);

        assertNotNull(createdRank.getId());
        assertEquals("Test", createdRank.getTitle());
        assertEquals(0L, createdRank.getPower());
    }

    @Test
    public void testGetAllRanks() {
        List<RankEntity> ranksList = new ArrayList<>();
        RankEntity rank1 = TestUtils.getRankEntity(1L, "Test1");
        RankEntity rank2 = TestUtils.getRankEntity(2L, "Test2");

        ranksList.add(rank1);
        ranksList.add(rank2);

        when(rankRepository.findAll()).thenReturn(ranksList);

        List<RankEntity> retrievedRanks = rankService.getAll();

        verify(rankRepository, times(1)).findAll();
        assertEquals(2, retrievedRanks.size());
        assertEquals("Test1", retrievedRanks.get(0).getTitle());
        assertEquals(0L, retrievedRanks.get(0).getPower());

        assertEquals("Test2", retrievedRanks.get(1).getTitle());
        assertEquals(0L, retrievedRanks.get(1).getPower());
    }

    @Test
    public void testGetOneRankById() {
        Long rankId = 1L;
        RankEntity rankEntity = TestUtils.getRankEntity(1L, "Test");

        when(rankRepository.findById(rankId)).thenReturn(Optional.of(rankEntity));

        Optional<RankEntity> retrievedRank = rankService.getOneById(rankId);

        verify(rankRepository, times(1)).findById(rankId);
        assertTrue(retrievedRank.isPresent());
        assertEquals(rankId, retrievedRank.get().getId());
        assertEquals("Test", retrievedRank.get().getTitle());
        assertEquals(0L, retrievedRank.get().getPower());
    }

    @Test
    public void testRemoveRank() throws Exception {
        Long rankId = 1L;

        rankService.remove(rankId);

        verify(rankRepository, times(1)).deleteById(rankId);
    }

    @Test
    public void testUpdateRank() throws Exception {
        RankEntity existingRank = TestUtils.getRankEntity(1L, "Test1");
        RankEntity rankToUpdate = TestUtils.getRankEntity(1L, "Test2");

        when(rankRepository.findById(1L)).thenReturn(Optional.of(existingRank));
        when(rankRepository.save(any(RankEntity.class))).thenReturn(rankToUpdate);

        RankEntity updatedRank = rankService.update(1L, rankToUpdate);

        verify(rankRepository, times(1)).findById(1L);
        verify(rankRepository, times(1)).save(rankToUpdate);

        assertEquals(1L, updatedRank.getId());
        assertEquals("Test2", updatedRank.getTitle());
        assertEquals(0L, updatedRank.getPower());
    }
}
