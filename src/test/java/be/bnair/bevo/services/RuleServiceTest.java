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
import be.bnair.bevo.models.entities.RuleEntity;
import be.bnair.bevo.repository.RuleRepository;
import be.bnair.bevo.services.impl.RuleServiceImpl;
import static org.junit.jupiter.api.Assertions.*;

public class RuleServiceTest {

    @InjectMocks
    private RuleServiceImpl ruleService;

    @Mock
    private RuleRepository ruleRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateRule() {
        RuleEntity ruleToCreate = TestUtils.getRuleEntity(1L, "Test");

        when(ruleRepository.save(any(RuleEntity.class))).thenReturn(ruleToCreate);

        RuleEntity createdRule = ruleService.create(ruleToCreate);

        verify(ruleRepository, times(1)).save(ruleToCreate);

        assertNotNull(createdRule.getId());
        assertEquals("Test", createdRule.getTitle());
        assertEquals("Test", createdRule.getDescription());
    }

    @Test
    public void testGetAllRules() {
        List<RuleEntity> rulesList = new ArrayList<>();
        RuleEntity rule1 = TestUtils.getRuleEntity(1L, "Test1");
        RuleEntity rule2 = TestUtils.getRuleEntity(2L, "Test2");
        rulesList.add(rule1);
        rulesList.add(rule2);

        when(ruleRepository.findAll()).thenReturn(rulesList);

        List<RuleEntity> retrievedRules = ruleService.getAll();

        verify(ruleRepository, times(1)).findAll();
        assertEquals(2, retrievedRules.size());
        assertEquals("Test1", retrievedRules.get(0).getTitle());
        assertEquals("Test", retrievedRules.get(0).getDescription());

        assertEquals("Test2", retrievedRules.get(1).getTitle());
        assertEquals("Test", retrievedRules.get(1).getDescription());
    }

    @Test
    public void testGetOneRuleById() {
        RuleEntity ruleEntity = TestUtils.getRuleEntity(1L, "Test");

        when(ruleRepository.findById(1L)).thenReturn(Optional.of(ruleEntity));

        Optional<RuleEntity> retrievedRule = ruleService.getOneById(1L);

        verify(ruleRepository, times(1)).findById(1L);
        assertTrue(retrievedRule.isPresent());
        assertEquals(1L, retrievedRule.get().getId());
        assertEquals("Test", retrievedRule.get().getTitle());
        assertEquals("Test", retrievedRule.get().getDescription());
    }

    @Test
    public void testRemoveRule() throws Exception {
        Long ruleId = 1L;

        ruleService.remove(ruleId);

        verify(ruleRepository, times(1)).deleteById(ruleId);
    }

    @Test
    public void testUpdateRule() throws Exception {
        Long ruleId = 1L;
        RuleEntity ruleToUpdate = TestUtils.getRuleEntity(1L, "Test2");

        RuleEntity existingRule = TestUtils.getRuleEntity(1L, "Test1");

        when(ruleRepository.findById(ruleId)).thenReturn(Optional.of(existingRule));
        when(ruleRepository.save(any(RuleEntity.class))).thenReturn(ruleToUpdate);

        RuleEntity updatedRule = ruleService.update(ruleId, ruleToUpdate);

        verify(ruleRepository, times(1)).findById(ruleId);
        verify(ruleRepository, times(1)).save(ruleToUpdate);

        assertEquals(ruleId, updatedRule.getId());
        assertEquals("Test2", updatedRule.getTitle());
        assertEquals("Test", updatedRule.getDescription());
    }
}
