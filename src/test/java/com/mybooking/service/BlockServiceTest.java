package com.mybooking.service;

import static org.junit.jupiter.api.Assertions.*;

import com.mybooking.model.Block;
import com.mybooking.model.Property;
import com.mybooking.repository.BlockRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.time.LocalDate;
import java.util.Optional;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class BlockServiceTest {

    @Mock
    private BlockRepository blockRepository;

    @InjectMocks
    private BlockService blockService;

    private Block block;
    private Property property;

/*    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        property = Property.builder().id(1L).name("Sample Property").build();
        block = Block.builder()
                .id(1L)
                .startDate(LocalDate.now().plusDays(1))
                .endDate(LocalDate.now().plusDays(5))
                .status(StatusBlock.ATIVO)
                .reason("Maintenance")
                .property(property)
                .build();
    }

    @Test
    void testCreateBlock() {
        when(blockRepository.save(any(Block.class))).thenReturn(block);

        Block createdBlock = blockService.createBlock(block);

        assertNotNull(createdBlock);
        assertEquals(StatusBlock.ATIVO, createdBlock.getStatus());
        verify(blockRepository, times(1)).save(any(Block.class));
    }

    @Test
    void testUpdateBlock() {
        when(blockRepository.findById(anyLong())).thenReturn(Optional.of(block));
        when(blockRepository.save(any(Block.class))).thenReturn(block);

        Block updatedBlock = blockService.updateBlock(1L, block);

        assertNotNull(updatedBlock);
        assertEquals(block.getId(), updatedBlock.getId());
        verify(blockRepository, times(1)).save(any(Block.class));
    }

    @Test
    void testDeleteBlock() {
        blockService.deleteBlock(1L);
        verify(blockRepository, times(1)).deleteById(anyLong());
    }*/
}
