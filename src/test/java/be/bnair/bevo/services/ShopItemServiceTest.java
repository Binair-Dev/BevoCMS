package be.bnair.bevo.services;

import static org.mockito.Mockito.*;

import be.bnair.bevo.models.entities.ServerEntity;
import be.bnair.bevo.models.entities.ShopCategoryEntity;
import be.bnair.bevo.utils.tests.TestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import be.bnair.bevo.models.entities.ShopItemEntity;
import be.bnair.bevo.repository.ShopItemRepository;
import be.bnair.bevo.services.impl.ShopItemServiceImpl;
import static org.junit.jupiter.api.Assertions.*;

public class ShopItemServiceTest {

    @InjectMocks
    private ShopItemServiceImpl shopItemService;

    @Mock
    private ShopItemRepository shopItemRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateShopItem() {
        ShopCategoryEntity shopCategory = TestUtils.getShopCategoryEntity(1L, "Test");
        ServerEntity serverEntity = TestUtils.getServerEntity(1L, "Test");
        ShopItemEntity itemToCreate = TestUtils.getShopItemEntity(1L, "Test", shopCategory, serverEntity);

        when(shopItemRepository.save(any(ShopItemEntity.class))).thenReturn(itemToCreate);

        ShopItemEntity createdItem = shopItemService.create(itemToCreate);

        verify(shopItemRepository, times(1)).save(itemToCreate);

        assertNotNull(createdItem.getId());
        assertEquals("Test", createdItem.getTitle());
        assertEquals("Test", createdItem.getDescription());
        assertEquals("Test", createdItem.getImage());
        assertEquals("Test", createdItem.getContentImage());
        assertEquals(1, createdItem.getPrice(), 0.001);
        assertEquals("Test", createdItem.getCommand());
        assertNotNull(createdItem.getShopCategory());
        assertEquals(shopCategory, createdItem.getShopCategory());
        assertNotNull(createdItem.getServer());
        assertEquals(serverEntity, createdItem.getServer());
    }

    @Test
    public void testGetAllShopItems() {
        ShopCategoryEntity shopCategory = TestUtils.getShopCategoryEntity(1L, "Test");
        ServerEntity serverEntity = TestUtils.getServerEntity(1L, "Test");

        List<ShopItemEntity> itemsList = new ArrayList<>();
        ShopItemEntity item1 = TestUtils.getShopItemEntity(1L, "Test1", shopCategory, serverEntity);
        ShopItemEntity item2 = TestUtils.getShopItemEntity(2L, "Test2", shopCategory, serverEntity);
        itemsList.add(item1);
        itemsList.add(item2);

        when(shopItemRepository.findAll()).thenReturn(itemsList);

        List<ShopItemEntity> retrievedItems = shopItemService.getAll();

        verify(shopItemRepository, times(1)).findAll();
        assertEquals(2, retrievedItems.size());
        assertNotNull(retrievedItems.get(0).getId());
        assertEquals("Test1", retrievedItems.get(0).getTitle());
        assertEquals("Test", retrievedItems.get(0).getDescription());
        assertEquals("Test", retrievedItems.get(0).getImage());
        assertEquals("Test", retrievedItems.get(0).getContentImage());
        assertEquals(1, retrievedItems.get(0).getPrice(), 0.001);
        assertEquals("Test", retrievedItems.get(0).getCommand());
        assertNotNull(retrievedItems.get(0).getShopCategory());
        assertEquals(shopCategory, retrievedItems.get(0).getShopCategory());
        assertNotNull(retrievedItems.get(0).getServer());
        assertEquals(serverEntity, retrievedItems.get(0).getServer());

        assertNotNull(retrievedItems.get(1).getId());
        assertEquals("Test2", retrievedItems.get(1).getTitle());
        assertEquals("Test", retrievedItems.get(1).getDescription());
        assertEquals("Test", retrievedItems.get(1).getImage());
        assertEquals("Test", retrievedItems.get(1).getContentImage());
        assertEquals(1, retrievedItems.get(1).getPrice(), 0.001);
        assertEquals("Test", retrievedItems.get(1).getCommand());
        assertNotNull(retrievedItems.get(1).getShopCategory());
        assertEquals(shopCategory, retrievedItems.get(1).getShopCategory());
        assertNotNull(retrievedItems.get(1).getServer());
        assertEquals(serverEntity, retrievedItems.get(1).getServer());
    }

    @Test
    public void testGetOneShopItemById() {
        ShopCategoryEntity shopCategory = TestUtils.getShopCategoryEntity(1L, "Test");
        ServerEntity serverEntity = TestUtils.getServerEntity(1L, "Test");
        ShopItemEntity itemEntity = TestUtils.getShopItemEntity(1L, "Test", shopCategory, serverEntity);

        when(shopItemRepository.findById(1L)).thenReturn(Optional.of(itemEntity));

        Optional<ShopItemEntity> retrievedItem = shopItemService.getOneById(1L);

        verify(shopItemRepository, times(1)).findById(1L);
        assertTrue(retrievedItem.isPresent());
        assertNotNull(retrievedItem.get().getId());
        assertEquals("Test", retrievedItem.get().getTitle());
        assertEquals("Test", retrievedItem.get().getDescription());
        assertEquals("Test", retrievedItem.get().getImage());
        assertEquals("Test", retrievedItem.get().getContentImage());
        assertEquals(1, retrievedItem.get().getPrice(), 0.001);
        assertEquals("Test", retrievedItem.get().getCommand());
        assertNotNull(retrievedItem.get().getShopCategory());
        assertEquals(shopCategory, retrievedItem.get().getShopCategory());
        assertNotNull(retrievedItem.get().getServer());
        assertEquals(serverEntity, retrievedItem.get().getServer());
    }

    @Test
    public void testRemoveShopItem() throws Exception {
        Long itemId = 1L;

        shopItemService.remove(itemId);

        verify(shopItemRepository, times(1)).deleteById(itemId);
    }

    @Test
    public void testUpdateShopItem() throws Exception {
        ShopCategoryEntity shopCategory = TestUtils.getShopCategoryEntity(1L, "Test");
        ServerEntity serverEntity = TestUtils.getServerEntity(1L, "Test");

        ShopItemEntity itemToUpdate = TestUtils.getShopItemEntity(1L, "Test2", shopCategory, serverEntity);
        ShopItemEntity existingItem = TestUtils.getShopItemEntity(1L, "Test1", shopCategory, serverEntity);

        when(shopItemRepository.findById(1L)).thenReturn(Optional.of(existingItem));
        when(shopItemRepository.save(any(ShopItemEntity.class))).thenReturn(itemToUpdate);

        ShopItemEntity updatedItem = shopItemService.update(1L, itemToUpdate);

        verify(shopItemRepository, times(1)).findById(1L);
        verify(shopItemRepository, times(1)).save(itemToUpdate);

        assertNotNull(updatedItem.getId());
        assertEquals("Test2", updatedItem.getTitle());
        assertEquals("Test", updatedItem.getDescription());
        assertEquals("Test", updatedItem.getImage());
        assertEquals("Test", updatedItem.getContentImage());
        assertEquals(1, updatedItem.getPrice(), 0.001);
        assertEquals("Test", updatedItem.getCommand());
        assertNotNull(updatedItem.getShopCategory());
        assertEquals(shopCategory, updatedItem.getShopCategory());
        assertNotNull(updatedItem.getServer());
        assertEquals(serverEntity, updatedItem.getServer());
    }
}
