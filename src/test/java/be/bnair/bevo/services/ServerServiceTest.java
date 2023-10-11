package be.bnair.bevo.services;

import static org.mockito.Mockito.*;

import be.bnair.bevo.utils.tests.TestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.platform.engine.TestEngine;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import be.bnair.bevo.models.entities.ServerEntity;
import be.bnair.bevo.repository.ServerRepository;
import be.bnair.bevo.services.impl.ServerServiceImpl;
import static org.junit.jupiter.api.Assertions.*;

public class ServerServiceTest {

    @InjectMocks
    private ServerServiceImpl serverService;

    @Mock
    private ServerRepository serverRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateServer() {
        ServerEntity serverToCreate = TestUtils.getServerEntity(1L, "Test");

        when(serverRepository.save(any(ServerEntity.class))).thenReturn(serverToCreate);

        ServerEntity createdServer = serverService.create(serverToCreate);

        verify(serverRepository, times(1)).save(serverToCreate);

        assertNotNull(createdServer.getId());
        assertEquals("Test", createdServer.getTitle());
        assertEquals("localhost", createdServer.getServerIp());
        assertEquals(25565, createdServer.getServerPort());
        assertEquals(25566, createdServer.getRconPort());
        assertEquals("Test", createdServer.getRconPassword());
    }

    @Test
    public void testGetAllServers() {
        List<ServerEntity> serversList = new ArrayList<>();
        ServerEntity server1 = TestUtils.getServerEntity(1L, "Test1");
        ServerEntity server2 = TestUtils.getServerEntity(2L, "Test2");
        serversList.add(server1);
        serversList.add(server2);

        when(serverRepository.findAll()).thenReturn(serversList);

        List<ServerEntity> retrievedServers = serverService.getAll();

        verify(serverRepository, times(1)).findAll();
        assertEquals(2, retrievedServers.size());

        assertEquals("Test1", retrievedServers.get(0).getTitle());
        assertEquals("localhost", retrievedServers.get(0).getServerIp());
        assertEquals(25565, retrievedServers.get(0).getServerPort());
        assertEquals(25566, retrievedServers.get(0).getRconPort());
        assertEquals("Test", retrievedServers.get(0).getRconPassword());

        assertEquals("Test2", retrievedServers.get(1).getTitle());
        assertEquals("localhost", retrievedServers.get(1).getServerIp());
        assertEquals(25565, retrievedServers.get(1).getServerPort());
        assertEquals(25566, retrievedServers.get(1).getRconPort());
        assertEquals("Test", retrievedServers.get(1).getRconPassword());
    }

    @Test
    public void testGetOneServerById() {
        ServerEntity serverEntity = TestUtils.getServerEntity(1L, "Test");

        when(serverRepository.findById(1L)).thenReturn(Optional.of(serverEntity));

        Optional<ServerEntity> retrievedServer = serverService.getOneById(1L);

        verify(serverRepository, times(1)).findById(1L);
        assertTrue(retrievedServer.isPresent());
        assertEquals(1L, retrievedServer.get().getId());
        assertEquals("Test", retrievedServer.get().getTitle());
        assertEquals("localhost", retrievedServer.get().getServerIp());
        assertEquals(25565, retrievedServer.get().getServerPort());
        assertEquals(25566, retrievedServer.get().getRconPort());
        assertEquals("Test", retrievedServer.get().getRconPassword());
    }

    @Test
    public void testRemoveServer() throws Exception {
        Long serverId = 1L;

        serverService.remove(serverId);

        verify(serverRepository, times(1)).deleteById(serverId);
    }

    @Test
    public void testUpdateServer() throws Exception {
        Long serverId = 1L;
        ServerEntity serverToUpdate = TestUtils.getServerEntity(1L, "Test2");
        ServerEntity existingServer = TestUtils.getServerEntity(1L, "Test1");

        when(serverRepository.findById(serverId)).thenReturn(Optional.of(existingServer));
        when(serverRepository.save(any(ServerEntity.class))).thenReturn(serverToUpdate);

        ServerEntity updatedServer = serverService.update(serverId, serverToUpdate);

        verify(serverRepository, times(1)).findById(serverId);
        verify(serverRepository, times(1)).save(serverToUpdate);

        assertEquals(serverId, updatedServer.getId());
        assertEquals(1L, updatedServer.getId());
        assertEquals("Test2", updatedServer.getTitle());
        assertEquals("localhost", updatedServer.getServerIp());
        assertEquals(25565, updatedServer.getServerPort());
        assertEquals(25566, updatedServer.getRconPort());
        assertEquals("Test", updatedServer.getRconPassword());
    }
}
