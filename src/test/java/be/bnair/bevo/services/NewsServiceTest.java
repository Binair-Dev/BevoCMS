package be.bnair.bevo.services;

import be.bnair.bevo.models.entities.NewsEntity;
import be.bnair.bevo.repository.NewsRepository;
import be.bnair.bevo.services.impl.NewsServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class NewsServiceTest {
    @InjectMocks
    private NewsServiceImpl newsService; // Remplacez NewsServiceImpl par l'implémentation réelle de votre service si nécessaire

    @Mock
    private NewsRepository newsRepository; // Remplacez NewsRepository par votre repository réel si nécessaire

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateNews() {
        // Créez une instance factice d'actualité pour la création
        NewsEntity newsToCreate = new NewsEntity();
        newsToCreate.setTitle("Nouvelle Actualité");
        newsToCreate.setDescription("Description de la nouvelle actualité");

        // Configurez le comportement du repository pour renvoyer l'instance factice créée
        when(newsRepository.save(newsToCreate)).thenReturn(newsToCreate);

        // Appelez la méthode du service que vous souhaitez tester
        NewsEntity createdNews = newsService.create(newsToCreate);

        // Vérifiez si l'actualité créée correspond à celle renvoyée par le service
        assertEquals("Nouvelle Actualité", createdNews.getTitle());
        assertEquals("Description de la nouvelle actualité", createdNews.getDescription());

        // Vérifiez si la méthode du repository save() a été appelée exactement une fois
        verify(newsRepository, times(1)).save(newsToCreate);
    }

    @Test
    public void testGetNewsById() {
        // Créez une instance factice d'actualité pour la recherche
        NewsEntity newsToFind = new NewsEntity();
        newsToFind.setId(1L);
        newsToFind.setTitle("Actualité existante");
        newsToFind.setDescription("Description de l'actualité existante");

        // Configurez le comportement du repository pour renvoyer l'actualité factice
        when(newsRepository.findById(1L)).thenReturn(Optional.of(newsToFind));

        // Appelez la méthode du service que vous souhaitez tester
        Optional<NewsEntity> foundNews = newsService.getOneById(1L);

        // Vérifiez si l'actualité trouvée correspond à celle renvoyée par le service
        assertTrue(foundNews.isPresent());
        assertEquals("Actualité existante", foundNews.get().getTitle());
        assertEquals("Description de l'actualité existante", foundNews.get().getDescription());

        // Vérifiez si la méthode du repository findById() a été appelée exactement une fois
        verify(newsRepository, times(1)).findById(1L);
    }

    @Test
    public void testUpdateNews() throws Exception {
        // Créez une instance factice d'actualité pour la mise à jour
        NewsEntity newsToUpdate = new NewsEntity();
        newsToUpdate.setId(1L);
        newsToUpdate.setTitle("Actualité à mettre à jour");
        newsToUpdate.setDescription("Description de l'actualité à mettre à jour");

        // Configurez le comportement du repository pour renvoyer l'actualité mise à jour
        when(newsRepository.findById(1L)).thenReturn(Optional.of(newsToUpdate));
        when(newsRepository.save(newsToUpdate)).thenReturn(newsToUpdate);

        // Appelez la méthode du service que vous souhaitez tester
        NewsEntity updatedNews = newsService.update(1L, newsToUpdate);

        // Vérifiez si l'actualité mise à jour correspond à celle renvoyée par le service
        assertEquals("Actualité à mettre à jour", updatedNews.getTitle());
        assertEquals("Description de l'actualité à mettre à jour", updatedNews.getDescription());

        // Vérifiez si la méthode du repository findById() a été appelée exactement une fois
        verify(newsRepository, times(1)).findById(1L);

        // Vérifiez si la méthode du repository save() a été appelée exactement une fois
        verify(newsRepository, times(1)).save(newsToUpdate);
    }

    @Test
    public void testDeleteNews() throws Exception {
        // Créez une instance factice d'actualité à supprimer
        NewsEntity newsToDelete = new NewsEntity();
        newsToDelete.setId(1L);

        // Configurez le comportement du repository pour ne rien renvoyer lors de la suppression
        doNothing().when(newsRepository).deleteById(1L);

        // Appelez la méthode du service que vous souhaitez tester
        newsService.remove(1L);

        // Vérifiez si la méthode du repository deleteById() a été appelée exactement une fois
        verify(newsRepository, times(1)).deleteById(1L);
    }

    @Test
    public void testGetAllNews() {
        // Créez une liste factice d'actualités pour simuler la réponse de la base de données
        List<NewsEntity> newsList = new ArrayList<>();
        NewsEntity news1 = new NewsEntity();
        news1.setId(1L);
        news1.setTitle("News 1");
        news1.setDescription("Description de la news 1");
        newsList.add(news1);

        NewsEntity news2 = new NewsEntity();
        news2.setId(2L);
        news2.setTitle("News 2");
        news2.setDescription("Description de la news 2");
        newsList.add(news2);

        // Configurez le comportement du repository pour renvoyer la liste factice d'actualités
        when(newsRepository.findAll()).thenReturn(newsList);

        // Appelez la méthode du service que vous souhaitez tester
        List<NewsEntity> result = newsService.getAll();

        // Vérifiez si le service renvoie la liste d'actualités attendue
        assertEquals(2, result.size());
        assertEquals("News 1", result.get(0).getTitle());
        assertEquals("News 2", result.get(1).getTitle());

        // Vérifiez si la méthode du repository findAll() a été appelée exactement une fois
        verify(newsRepository, times(1)).findAll();
    }
}