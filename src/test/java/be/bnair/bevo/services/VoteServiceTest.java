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
import be.bnair.bevo.models.entities.VoteEntity;
import be.bnair.bevo.repository.VoteRepository;
import be.bnair.bevo.services.impl.VoteServiceImpl;
import static org.junit.jupiter.api.Assertions.*;

public class VoteServiceTest {

    @InjectMocks
    private VoteServiceImpl voteService;

    @Mock
    private VoteRepository voteRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateVote() {
        UserEntity user = TestUtils.getUserEntity(1L, "Test", TestUtils.getRankEntity(1L, "Test"));
        VoteEntity voteToCreate = TestUtils.getVoteEntity(1L, user);

        when(voteRepository.save(any(VoteEntity.class))).thenReturn(voteToCreate);

        VoteEntity createdVote = voteService.create(voteToCreate);

        verify(voteRepository, times(1)).save(voteToCreate);

        assertNotNull(createdVote.getId());
        assertEquals(LocalDate.now(), createdVote.getDate());
        assertNotNull(createdVote.getUser());
        assertEquals(user, createdVote.getUser());
    }

    @Test
    public void testGetAllVotes() {
        List<VoteEntity> votesList = new ArrayList<>();
        UserEntity user = TestUtils.getUserEntity(1L, "Test", TestUtils.getRankEntity(1L, "Test"));
        VoteEntity v1 = TestUtils.getVoteEntity(1L, user);
        VoteEntity v2 = TestUtils.getVoteEntity(2L, user);
        votesList.add(v1);
        votesList.add(v2);

        when(voteRepository.findAll()).thenReturn(votesList);

        List<VoteEntity> retrievedVotes = voteService.getAll();

        verify(voteRepository, times(1)).findAll();
        assertEquals(2, retrievedVotes.size());
        assertNotNull(retrievedVotes.get(0).getId());
        assertEquals(LocalDate.now(), retrievedVotes.get(0).getDate());
        assertNotNull(retrievedVotes.get(0).getUser());
        assertEquals(user, retrievedVotes.get(0).getUser());

        assertNotNull(retrievedVotes.get(1).getId());
        assertEquals(LocalDate.now(), retrievedVotes.get(1).getDate());
        assertNotNull(retrievedVotes.get(1).getUser());
        assertEquals(user, retrievedVotes.get(1).getUser());
    }

    @Test
    public void testGetOneVoteById() {
        UserEntity user = TestUtils.getUserEntity(1L, "Test", TestUtils.getRankEntity(1L, "Test"));
        VoteEntity v1 = TestUtils.getVoteEntity(1L, user);

        when(voteRepository.findById(1L)).thenReturn(Optional.of(v1));

        Optional<VoteEntity> retrievedVote = voteService.getOneById(1L);

        verify(voteRepository, times(1)).findById(1L);
        assertTrue(retrievedVote.isPresent());
        assertNotNull(retrievedVote.get().getId());
        assertEquals(1L, retrievedVote.get().getId());
        assertEquals(LocalDate.now(), retrievedVote.get().getDate());
        assertNotNull(retrievedVote.get().getUser());
        assertEquals(user, retrievedVote.get().getUser());
    }

    @Test
    public void testRemoveVote() throws Exception {
        Long voteId = 1L;

        voteService.remove(voteId);

        verify(voteRepository, times(1)).deleteById(voteId);
    }

    @Test
    public void testDeleteAll() {
        voteService.deleteAll();

        verify(voteRepository, times(1)).deleteAll();
    }
}
