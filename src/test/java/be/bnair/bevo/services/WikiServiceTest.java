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
import be.bnair.bevo.models.entities.WikiEntity;
import be.bnair.bevo.repository.WikiRepository;
import be.bnair.bevo.services.impl.WikiServiceImpl;
import static org.junit.jupiter.api.Assertions.*;

public class WikiServiceTest {

    @InjectMocks
    private WikiServiceImpl wikiService;

    @Mock
    private WikiRepository wikiRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateWiki() {
        WikiEntity wikiToCreate = TestUtils.getWikiEntity(1L, "Test");

        when(wikiRepository.save(any(WikiEntity.class))).thenReturn(wikiToCreate);

        WikiEntity createdWiki = wikiService.create(wikiToCreate);

        verify(wikiRepository, times(1)).save(wikiToCreate);

        assertNotNull(createdWiki.getId());
        assertEquals("Test", createdWiki.getTitle());
        assertEquals("Test", createdWiki.getDescription());
        assertEquals("Test", createdWiki.getImage());
    }

    @Test
    public void testGetAllWikis() {
        List<WikiEntity> wikisList = new ArrayList<>();
        WikiEntity w1 = TestUtils.getWikiEntity(1L, "Test1");
        WikiEntity w2 = TestUtils.getWikiEntity(2L, "Test2");
        wikisList.add(w1);
        wikisList.add(w2);

        when(wikiRepository.findAll()).thenReturn(wikisList);

        List<WikiEntity> retrievedWikis = wikiService.getAll();

        verify(wikiRepository, times(1)).findAll();
        assertEquals(2, retrievedWikis.size());
        assertNotNull(retrievedWikis.get(0).getId());
        assertEquals("Test1", retrievedWikis.get(0).getTitle());
        assertEquals("Test", retrievedWikis.get(0).getDescription());
        assertEquals("Test", retrievedWikis.get(0).getImage());

        assertNotNull(retrievedWikis.get(1).getId());
        assertEquals("Test2", retrievedWikis.get(1).getTitle());
        assertEquals("Test", retrievedWikis.get(1).getDescription());
        assertEquals("Test", retrievedWikis.get(1).getImage());
    }

    @Test
    public void testGetOneWikiById() {
        WikiEntity w1 = TestUtils.getWikiEntity(1L, "Test");

        when(wikiRepository.findById(1L)).thenReturn(Optional.of(w1));

        Optional<WikiEntity> retrievedWiki = wikiService.getOneById(1L);

        verify(wikiRepository, times(1)).findById(1L);
        assertTrue(retrievedWiki.isPresent());
        assertNotNull(retrievedWiki.get().getId());
        assertEquals(1L, retrievedWiki.get().getId());
        assertEquals("Test", retrievedWiki.get().getTitle());
        assertEquals("Test", retrievedWiki.get().getDescription());
        assertEquals("Test", retrievedWiki.get().getImage());
    }

    @Test
    public void testRemoveWiki() throws Exception {
        Long wikiId = 1L;

        wikiService.remove(wikiId);

        verify(wikiRepository, times(1)).deleteById(wikiId);
    }

    @Test
    public void testUpdateWiki() throws Exception {
        Long wikiId = 1L;

        // Crée un WikiEntity existant
        WikiEntity w1 = TestUtils.getWikiEntity(1L, "Test1");
        WikiEntity w2 = TestUtils.getWikiEntity(1L, "Test2");

        when(wikiRepository.findById(wikiId)).thenReturn(Optional.of(w1));
        when(wikiRepository.save(w2)).thenReturn(w2);

        WikiEntity result = wikiService.update(wikiId, w2);

        verify(wikiRepository, times(1)).findById(wikiId);
        verify(wikiRepository, times(1)).save(w2);

        assertNotNull(result);
        assertNotNull(result.getId());
        assertEquals(1L, result.getId());
        assertEquals("Test2", result.getTitle());
        assertEquals("Test", result.getDescription());
        assertEquals("Test", result.getImage());
    }
}
