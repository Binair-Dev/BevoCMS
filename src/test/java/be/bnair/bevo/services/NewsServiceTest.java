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
import be.bnair.bevo.models.entities.NewsEntity;
import be.bnair.bevo.repository.NewsRepository;
import be.bnair.bevo.services.impl.NewsServiceImpl;
import static org.junit.jupiter.api.Assertions.*;

public class NewsServiceTest {

    @InjectMocks
    private NewsServiceImpl newsService;

    @Mock
    private NewsRepository newsRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateNews() {
        UserEntity author = TestUtils.getUserEntity(1L, "Test", TestUtils.getRankEntity(1L, "Test"));
        NewsEntity newsToCreate = TestUtils.getNewsEntity(1L, "Test", author);

        when(newsRepository.save(any(NewsEntity.class))).thenReturn(newsToCreate);

        NewsEntity createdNews = newsService.create(newsToCreate);

        verify(newsRepository, times(1)).save(newsToCreate);

        assertNotNull(createdNews.getId());
        assertEquals("Test", createdNews.getTitle());
        assertEquals("Test", createdNews.getDescription());
        assertEquals("Test", createdNews.getImage());
        assertEquals(LocalDate.now(), createdNews.getDate());
        assertTrue(createdNews.isPublished());
        assertEquals("Test", createdNews.getAuthor().getNickname());
    }

    @Test
    public void testGetAllNews() {
        UserEntity author = TestUtils.getUserEntity(1L, "Test", TestUtils.getRankEntity(1L, "Test"));
        List<NewsEntity> newsList = new ArrayList<>();
        NewsEntity news1 = TestUtils.getNewsEntity(1L, "Test1", author);
        NewsEntity news2 = TestUtils.getNewsEntity(2L, "Test2", author);
        newsList.add(news1);
        newsList.add(news2);

        when(newsRepository.findAll()).thenReturn(newsList);

        List<NewsEntity> retrievedNews = newsService.getAll();

        verify(newsRepository, times(1)).findAll();
        assertEquals(2, retrievedNews.size());
        assertNotNull(retrievedNews.get(0).getId());
        assertEquals("Test1", retrievedNews.get(0).getTitle());
        assertEquals("Test", retrievedNews.get(0).getDescription());
        assertEquals("Test", retrievedNews.get(0).getImage());
        assertEquals(LocalDate.now(), retrievedNews.get(0).getDate());
        assertTrue(retrievedNews.get(0).isPublished());
        assertEquals("Test", retrievedNews.get(0).getAuthor().getNickname());

        assertNotNull(retrievedNews.get(1).getId());
        assertEquals("Test2", retrievedNews.get(1).getTitle());
        assertEquals("Test", retrievedNews.get(1).getDescription());
        assertEquals("Test", retrievedNews.get(1).getImage());
        assertEquals(LocalDate.now(), retrievedNews.get(1).getDate());
        assertTrue(retrievedNews.get(1).isPublished());
        assertEquals("Test", retrievedNews.get(1).getAuthor().getNickname());
    }

    @Test
    public void testGetOneNewsById() {
        UserEntity author = TestUtils.getUserEntity(1L, "Test", TestUtils.getRankEntity(1L, "Test"));
        NewsEntity newsEntity = TestUtils.getNewsEntity(1L, "Test", author);

        when(newsRepository.findById(newsEntity.getId())).thenReturn(Optional.of(newsEntity));

        Optional<NewsEntity> retrievedNews = newsService.getOneById(newsEntity.getId());

        verify(newsRepository, times(1)).findById(newsEntity.getId());
        assertTrue(retrievedNews.isPresent());
        NewsEntity fromDb = retrievedNews.get();

        assertNotNull(newsEntity.getId());
        assertEquals(newsEntity.getId(), fromDb.getId());
        assertEquals("Test", fromDb.getTitle());
        assertEquals("Test", fromDb.getDescription());
        assertEquals("Test", fromDb.getImage());
        assertEquals(LocalDate.now(), fromDb.getDate());
        assertTrue(fromDb.isPublished());
        assertEquals("Test", fromDb.getAuthor().getNickname());
    }

    @Test
    public void testRemoveNews() throws Exception {
        Long newsId = 1L;

        newsService.remove(newsId);

        verify(newsRepository, times(1)).deleteById(newsId);
    }

    @Test
    public void testUpdateNews() throws Exception {
        UserEntity user1 = TestUtils.getUserEntity(1L, "Test1", TestUtils.getRankEntity(1L, "Admin"));
        UserEntity user2 = TestUtils.getUserEntity(2L, "Test2", TestUtils.getRankEntity(1L, "Admin"));
        NewsEntity news = TestUtils.getNewsEntity(1L, "Test1", user1);
        NewsEntity newsUpdated = TestUtils.getNewsEntity(1L, "Test1", user2);

        when(newsRepository.findById(1L)).thenReturn(Optional.of(news));
        when(newsRepository.save(any(NewsEntity.class))).thenReturn(newsUpdated);

        NewsEntity updatedNews = newsService.update(news.getId(), newsUpdated);

        verify(newsRepository, times(1)).findById(news.getId());
        verify(newsRepository, times(1)).save(newsUpdated);

        assertEquals(news.getId(), updatedNews.getId());
        assertEquals("Test1", updatedNews.getTitle());
        assertEquals("Test", updatedNews.getDescription());
        assertEquals("Test", updatedNews.getImage());
        assertEquals(LocalDate.now(), updatedNews.getDate());
        assertTrue(updatedNews.isPublished());
        assertEquals("Test2", updatedNews.getAuthor().getNickname());
    }
}
