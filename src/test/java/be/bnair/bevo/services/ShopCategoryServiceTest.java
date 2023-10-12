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
import be.bnair.bevo.models.entities.ShopCategoryEntity;
import be.bnair.bevo.repository.ShopCategoryRepository;
import be.bnair.bevo.services.impl.ShopCategoryServiceImpl;
import static org.junit.jupiter.api.Assertions.*;

public class ShopCategoryServiceTest {

    @InjectMocks
    private ShopCategoryServiceImpl shopCategoryService;

    @Mock
    private ShopCategoryRepository shopCategoryRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateShopCategory() {
        ShopCategoryEntity categoryToCreate = TestUtils.getShopCategoryEntity(1L, "Test");

        when(shopCategoryRepository.save(any(ShopCategoryEntity.class))).thenReturn(categoryToCreate);

        ShopCategoryEntity createdCategory = shopCategoryService.create(categoryToCreate);

        verify(shopCategoryRepository, times(1)).save(categoryToCreate);

        assertNotNull(createdCategory.getId());
        assertEquals("Test", createdCategory.getTitle());
        assertEquals(1, createdCategory.getDisplayOrder());
    }

    @Test
    public void testGetAllShopCategories() {
        List<ShopCategoryEntity> categoriesList = new ArrayList<>();
        ShopCategoryEntity category1 = TestUtils.getShopCategoryEntity(1L, "Test1");
        ShopCategoryEntity category2 = TestUtils.getShopCategoryEntity(2L, "Test2");
        categoriesList.add(category1);
        categoriesList.add(category2);

        when(shopCategoryRepository.findAll()).thenReturn(categoriesList);

        List<ShopCategoryEntity> retrievedCategories = shopCategoryService.getAll();

        verify(shopCategoryRepository, times(1)).findAll();
        assertEquals(2, retrievedCategories.size());
        assertNotNull(retrievedCategories.get(0).getId());
        assertEquals("Test1", retrievedCategories.get(0).getTitle());
        assertEquals(1, retrievedCategories.get(0).getDisplayOrder());

        assertNotNull(retrievedCategories.get(1).getId());
        assertEquals("Test2", retrievedCategories.get(1).getTitle());
        assertEquals(1, retrievedCategories.get(1).getDisplayOrder());
    }

    @Test
    public void testGetOneShopCategoryById() {
        ShopCategoryEntity categoryEntity = TestUtils.getShopCategoryEntity(1L, "Test");

        when(shopCategoryRepository.findById(1L)).thenReturn(Optional.of(categoryEntity));

        Optional<ShopCategoryEntity> retrievedCategory = shopCategoryService.getOneById(1L);

        verify(shopCategoryRepository, times(1)).findById(1L);
        assertTrue(retrievedCategory.isPresent());
        assertEquals(1L, retrievedCategory.get().getId());
        assertEquals("Test", retrievedCategory.get().getTitle());
        assertEquals(1, retrievedCategory.get().getDisplayOrder());
    }

    @Test
    public void testRemoveShopCategory() throws Exception {
        Long categoryId = 1L;

        shopCategoryService.remove(categoryId);

        verify(shopCategoryRepository, times(1)).deleteById(categoryId);
    }

    @Test
    public void testUpdateShopCategory() throws Exception {
        ShopCategoryEntity categoryToUpdate = TestUtils.getShopCategoryEntity(1L, "Test2");
        ShopCategoryEntity existingCategory = TestUtils.getShopCategoryEntity(1L, "Test1");

        when(shopCategoryRepository.findById(1L)).thenReturn(Optional.of(existingCategory));
        when(shopCategoryRepository.save(any(ShopCategoryEntity.class))).thenReturn(categoryToUpdate);

        ShopCategoryEntity updatedCategory = shopCategoryService.update(1L, categoryToUpdate);

        verify(shopCategoryRepository, times(1)).findById(1L);
        verify(shopCategoryRepository, times(1)).save(categoryToUpdate);

        assertEquals(1L, updatedCategory.getId());
        assertEquals("Test2", updatedCategory.getTitle());
        assertEquals(1, updatedCategory.getDisplayOrder());
    }
}
