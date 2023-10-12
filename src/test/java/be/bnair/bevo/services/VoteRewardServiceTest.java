package be.bnair.bevo.services;

import static org.mockito.Mockito.*;

import be.bnair.bevo.models.entities.ServerEntity;
import be.bnair.bevo.models.enums.EnumRewardType;
import be.bnair.bevo.utils.tests.TestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import be.bnair.bevo.models.entities.VoteRewardEntity;
import be.bnair.bevo.repository.VoteRewardRepository;
import be.bnair.bevo.services.impl.VoteRewardServiceImpl;
import static org.junit.jupiter.api.Assertions.*;

public class VoteRewardServiceTest {

    @InjectMocks
    private VoteRewardServiceImpl voteRewardService;

    @Mock
    private VoteRewardRepository voteRewardRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateVoteReward() {
        ServerEntity server = TestUtils.getServerEntity(1L, "Test");
        VoteRewardEntity voteRewardToCreate = TestUtils.getVoteRewardEntity(1L, "Test", server);

        when(voteRewardRepository.save(any(VoteRewardEntity.class))).thenReturn(voteRewardToCreate);

        VoteRewardEntity createdVoteReward = voteRewardService.create(voteRewardToCreate);

        verify(voteRewardRepository, times(1)).save(voteRewardToCreate);

        assertNotNull(createdVoteReward.getId());
        assertEquals("Test", createdVoteReward.getTitle());
        assertEquals(1, createdVoteReward.getPercent());
        assertEquals(EnumRewardType.CREDIT, createdVoteReward.getRewardType());
        assertEquals("Test", createdVoteReward.getCommand());
        assertEquals(1, createdVoteReward.getCredit());
        assertNotNull(createdVoteReward.getServer());
        assertEquals(server, createdVoteReward.getServer());
    }

    @Test
    public void testGetAllVoteRewards() {
        List<VoteRewardEntity> voteRewardsList = new ArrayList<>();
        ServerEntity server = TestUtils.getServerEntity(1L, "Test");
        VoteRewardEntity v1 = TestUtils.getVoteRewardEntity(1L, "Test1", server);
        VoteRewardEntity v2 = TestUtils.getVoteRewardEntity(2L, "Test2", server);
        voteRewardsList.add(v1);
        voteRewardsList.add(v2);

        when(voteRewardRepository.findAll()).thenReturn(voteRewardsList);

        List<VoteRewardEntity> retrievedRewards = voteRewardService.getAll();

        verify(voteRewardRepository, times(1)).findAll();
        assertEquals(2, retrievedRewards.size());
        assertNotNull(retrievedRewards.get(0).getId());
        assertEquals("Test1", retrievedRewards.get(0).getTitle());
        assertEquals(1, retrievedRewards.get(0).getPercent());
        assertEquals(EnumRewardType.CREDIT, retrievedRewards.get(0).getRewardType());
        assertEquals("Test", retrievedRewards.get(0).getCommand());
        assertEquals(1, retrievedRewards.get(0).getCredit());
        assertNotNull(retrievedRewards.get(0).getServer());
        assertEquals(server, retrievedRewards.get(0).getServer());

        assertNotNull(retrievedRewards.get(1).getId());
        assertEquals("Test2", retrievedRewards.get(1).getTitle());
        assertEquals(1, retrievedRewards.get(1).getPercent());
        assertEquals(EnumRewardType.CREDIT, retrievedRewards.get(1).getRewardType());
        assertEquals("Test", retrievedRewards.get(1).getCommand());
        assertEquals(1, retrievedRewards.get(1).getCredit());
        assertNotNull(retrievedRewards.get(1).getServer());
        assertEquals(server, retrievedRewards.get(1).getServer());
    }

    @Test
    public void testGetOneVoteRewardById() {
        ServerEntity server = TestUtils.getServerEntity(1L, "Test");
        VoteRewardEntity v1 = TestUtils.getVoteRewardEntity(1L, "Test", server);

        when(voteRewardRepository.findById(1L)).thenReturn(Optional.of(v1));

        Optional<VoteRewardEntity> retrievedVoteReward = voteRewardService.getOneById(1L);

        verify(voteRewardRepository, times(1)).findById(1L);
        assertTrue(retrievedVoteReward.isPresent());
        assertNotNull(retrievedVoteReward.get().getId());
        assertEquals("Test", retrievedVoteReward.get().getTitle());
        assertEquals(1, retrievedVoteReward.get().getPercent());
        assertEquals(EnumRewardType.CREDIT, retrievedVoteReward.get().getRewardType());
        assertEquals("Test", retrievedVoteReward.get().getCommand());
        assertEquals(1, retrievedVoteReward.get().getCredit());
        assertNotNull(retrievedVoteReward.get().getServer());
        assertEquals(server, retrievedVoteReward.get().getServer());
    }

    @Test
    public void testRemoveVoteReward() throws Exception {
        Long rewardId = 1L;

        voteRewardService.remove(rewardId);

        verify(voteRewardRepository, times(1)).deleteById(rewardId);
    }

    @Test
    public void testUpdateVoteReward() throws Exception {
        ServerEntity server = TestUtils.getServerEntity(1L, "Test");
        VoteRewardEntity v1 = TestUtils.getVoteRewardEntity(1L, "Test1", server);
        VoteRewardEntity v2 = TestUtils.getVoteRewardEntity(1L, "Test2", server);

        when(voteRewardRepository.findById(1L)).thenReturn(Optional.of(v1));
        when(voteRewardRepository.save(any(VoteRewardEntity.class))).thenReturn(v2);

        VoteRewardEntity updatedReward = voteRewardService.update(1L, v2);

        verify(voteRewardRepository, times(1)).findById(1L);
        verify(voteRewardRepository, times(1)).save(v2);

        assertNotNull(updatedReward.getId());
        assertEquals("Test2", updatedReward.getTitle());
        assertEquals(1, updatedReward.getPercent());
        assertEquals(EnumRewardType.CREDIT, updatedReward.getRewardType());
        assertEquals("Test", updatedReward.getCommand());
        assertEquals(1, updatedReward.getCredit());
        assertNotNull(updatedReward.getServer());
        assertEquals(server, updatedReward.getServer());
    }
}
