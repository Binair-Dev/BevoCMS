package be.bnair.bevo.services;

import static org.mockito.Mockito.*;

import be.bnair.bevo.models.entities.RankEntity;
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
import org.springframework.security.core.userdetails.UserDetails;
import be.bnair.bevo.repository.UserRepository;
import be.bnair.bevo.services.impl.UserServiceImpl;
import static org.junit.jupiter.api.Assertions.*;

public class UserServiceTest {

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateUser() {
        RankEntity rank = TestUtils.getRankEntity(1L, "Test");
        UserEntity userToCreate = TestUtils.getUserEntity(1L, "Test", rank);

        when(userRepository.save(any(UserEntity.class))).thenReturn(userToCreate);

        UserDetails createdUser = userService.create(userToCreate);
        UserEntity user = (UserEntity)createdUser;

        verify(userRepository, times(1)).save(userToCreate);

        assertNotNull(userToCreate.getId());
        assertEquals("Test", createdUser.getUsername());
        assertEquals("Test", user.getEmail());
        assertNotNull(user.getPassword());
        assertNotNull(user.isConfirmed());
        assertNotNull(user.isEnabled());
        assertEquals(100, user.getCredit(), 0.1);
        assertNotNull(user.getRank());
        assertEquals(rank, user.getRank());
        assertNotNull(user.getCreatedAt());
        assertEquals(LocalDate.now(), user.getCreatedAt());
        assertNotNull(user.getUpdatedAt());
        assertEquals(LocalDate.now(), user.getUpdatedAt());
    }

    @Test
    public void testGetAllUsers() {
        List<UserEntity> usersList = new ArrayList<>();
        RankEntity rank = TestUtils.getRankEntity(1L, "Test");
        UserEntity u1 = TestUtils.getUserEntity(1L, "Test1", rank);
        UserEntity u2 = TestUtils.getUserEntity(2L, "Test2", rank);
        usersList.add(u1);
        usersList.add(u2);

        when(userRepository.findAll()).thenReturn(usersList);

        List<UserEntity> retrievedUsers = userService.getAll();

        verify(userRepository, times(1)).findAll();
        assertEquals(2, retrievedUsers.size());
        assertNotNull(retrievedUsers.get(0).getId());
        assertEquals(1L, retrievedUsers.get(0).getId());
        assertEquals("Test1", retrievedUsers.get(0).getUsername());
        assertEquals("Test", retrievedUsers.get(0).getEmail());
        assertNotNull(retrievedUsers.get(0).getPassword());
        assertNotNull(retrievedUsers.get(0).isConfirmed());
        assertNotNull(retrievedUsers.get(0).isEnabled());
        assertEquals(100, retrievedUsers.get(0).getCredit(), 0.1);
        assertNotNull(retrievedUsers.get(0).getRank());
        assertEquals(rank, retrievedUsers.get(0).getRank());
        assertNotNull(retrievedUsers.get(0).getCreatedAt());
        assertEquals(LocalDate.now(), retrievedUsers.get(0).getCreatedAt());
        assertNotNull(retrievedUsers.get(0).getUpdatedAt());
        assertEquals(LocalDate.now(), retrievedUsers.get(0).getUpdatedAt());

        assertNotNull(retrievedUsers.get(1).getId());
        assertEquals(2L, retrievedUsers.get(1).getId());
        assertEquals("Test2", retrievedUsers.get(1).getUsername());
        assertEquals("Test", retrievedUsers.get(1).getEmail());
        assertNotNull(retrievedUsers.get(1).getPassword());
        assertNotNull(retrievedUsers.get(1).isConfirmed());
        assertNotNull(retrievedUsers.get(1).isEnabled());
        assertEquals(100, retrievedUsers.get(1).getCredit(), 0.1);
        assertNotNull(retrievedUsers.get(1).getRank());
        assertEquals(rank, retrievedUsers.get(1).getRank());
        assertNotNull(retrievedUsers.get(1).getCreatedAt());
        assertEquals(LocalDate.now(), retrievedUsers.get(1).getCreatedAt());
        assertNotNull(retrievedUsers.get(1).getUpdatedAt());
        assertEquals(LocalDate.now(), retrievedUsers.get(1).getUpdatedAt());
    }

    @Test
    public void testGetOneUserById() {
        RankEntity rank = TestUtils.getRankEntity(1L, "Test");
        UserEntity u1 = TestUtils.getUserEntity(1L, "Test", rank);

        when(userRepository.findById(1L)).thenReturn(Optional.of(u1));

        Optional<UserEntity> retrievedUser = userService.getOneById(1L);

        verify(userRepository, times(1)).findById(1L);
        assertTrue(retrievedUser.isPresent());
        assertNotNull(retrievedUser.get().getId());
        assertEquals(1L, retrievedUser.get().getId());
        assertEquals("Test", retrievedUser.get().getUsername());
        assertEquals("Test", retrievedUser.get().getEmail());
        assertNotNull(retrievedUser.get().getPassword());
        assertNotNull(retrievedUser.get().isConfirmed());
        assertNotNull(retrievedUser.get().isEnabled());
        assertEquals(100, retrievedUser.get().getCredit(), 0.1);
        assertNotNull(retrievedUser.get().getRank());
        assertEquals(rank, retrievedUser.get().getRank());
        assertNotNull(retrievedUser.get().getCreatedAt());
        assertEquals(LocalDate.now(), retrievedUser.get().getCreatedAt());
        assertNotNull(retrievedUser.get().getUpdatedAt());
        assertEquals(LocalDate.now(), retrievedUser.get().getUpdatedAt());
    }

    @Test
    public void testGetOneUserByUsername() {
        String username = "Test";
        RankEntity rank = TestUtils.getRankEntity(1L, "Test");
        UserEntity u1 = TestUtils.getUserEntity(1L, "Test", rank);

        when(userRepository.findByUsername(username)).thenReturn(Optional.of(u1));

        Optional<UserEntity> retrievedUser = userService.getOneByUsername(username);

        verify(userRepository, times(1)).findByUsername(username);
        assertTrue(retrievedUser.isPresent());
        assertNotNull(retrievedUser.get().getId());
        assertEquals(1L, retrievedUser.get().getId());
        assertEquals("Test", retrievedUser.get().getUsername());
        assertEquals("Test", retrievedUser.get().getEmail());
        assertNotNull(retrievedUser.get().getPassword());
        assertNotNull(retrievedUser.get().isConfirmed());
        assertNotNull(retrievedUser.get().isEnabled());
        assertEquals(100, retrievedUser.get().getCredit(), 0.1);
        assertNotNull(retrievedUser.get().getRank());
        assertEquals(rank, retrievedUser.get().getRank());
        assertNotNull(retrievedUser.get().getCreatedAt());
        assertEquals(LocalDate.now(), retrievedUser.get().getCreatedAt());
        assertNotNull(retrievedUser.get().getUpdatedAt());
        assertEquals(LocalDate.now(), retrievedUser.get().getUpdatedAt());
    }

    @Test
    public void testRemoveUser() throws Exception {
        RankEntity rank = TestUtils.getRankEntity(1L, "Test");
        UserEntity u1 = TestUtils.getUserEntity(1L, "Test", rank);
        UserEntity u2 = TestUtils.getDisabledUserEntity(1L, "Test", rank);

        when(userRepository.findById(1L)).thenReturn(Optional.of(u1));
        when(userRepository.save(any(UserEntity.class))).thenReturn(u2);

        UserEntity updatedUser = userService.update(1L, u2);

        verify(userRepository, times(1)).findById(1L);
        verify(userRepository, times(1)).save(u2);

        assertNotNull(updatedUser.getId());
        assertEquals(1L, updatedUser.getId());
        assertEquals("Test", updatedUser.getUsername());
        assertEquals("Test", updatedUser.getEmail());
        assertNotNull(updatedUser.getPassword());
        assertNotNull(updatedUser.isConfirmed());
        assertFalse(updatedUser.isEnabled());
        assertEquals(100, updatedUser.getCredit(), 0.1);
        assertNotNull(updatedUser.getRank());
        assertEquals(rank, updatedUser.getRank());
        assertNotNull(updatedUser.getCreatedAt());
        assertEquals(LocalDate.now(), updatedUser.getCreatedAt());
        assertNotNull(updatedUser.getUpdatedAt());
        assertEquals(LocalDate.now(), updatedUser.getUpdatedAt());
    }

    @Test
    public void testUpdateUser() throws Exception {
        RankEntity rank = TestUtils.getRankEntity(1L, "Test");
        UserEntity u1 = TestUtils.getUserEntity(1L, "Test1", rank);
        UserEntity u2 = TestUtils.getUserEntity(1L, "Test2", rank);

        when(userRepository.findById(1L)).thenReturn(Optional.of(u1));
        when(userRepository.save(any(UserEntity.class))).thenReturn(u2);

        UserEntity updatedUser = userService.update(1L, u2);

        verify(userRepository, times(1)).findById(1L);
        verify(userRepository, times(1)).save(u2);

        assertNotNull(updatedUser.getId());
        assertEquals(1L, updatedUser.getId());
        assertEquals("Test2", updatedUser.getUsername());
        assertEquals("Test", updatedUser.getEmail());
        assertNotNull(updatedUser.getPassword());
        assertNotNull(updatedUser.isConfirmed());
        assertNotNull(updatedUser.isEnabled());
        assertEquals(100, updatedUser.getCredit(), 0.1);
        assertNotNull(updatedUser.getRank());
        assertEquals(rank, updatedUser.getRank());
        assertNotNull(updatedUser.getCreatedAt());
        assertEquals(LocalDate.now(), updatedUser.getCreatedAt());
        assertNotNull(updatedUser.getUpdatedAt());
        assertEquals(LocalDate.now(), updatedUser.getUpdatedAt());
    }
}
