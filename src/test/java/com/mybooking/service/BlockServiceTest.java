package com.mybooking.service;

import com.mybooking.dto.BlockDTO;
import com.mybooking.exception.BlockException;
import com.mybooking.exception.Constants;
import com.mybooking.model.Block;
import com.mybooking.model.Booking;
import com.mybooking.model.Property;
import com.mybooking.repository.BlockRepository;
import com.mybooking.repository.BookingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;

class BlockServiceTest {

    @InjectMocks
    private BlockService blockService;

    @Mock
    private BlockRepository blockRepository;

    @Mock
    private BookingRepository bookingRepository;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetById() {
        Block block = new Block();
        block.setId(1L);
        block.setStartDate(LocalDate.of(2024, 6, 10));
        block.setEndDate(LocalDate.of(2024, 6, 20));

        when(blockRepository.findById(anyLong())).thenReturn(Optional.of(block));

        Block foundBlock = blockService.getById(1L);

        assertThat(foundBlock).isNotNull();
        assertThat(foundBlock.getId()).isEqualTo(1L);
    }

    @Test
    void testCreateBlock_Success() {
        Property property = new Property();
        property.setId(1L);

        BlockDTO blockDTO = new BlockDTO(
                LocalDate.of(2024, 6, 10),
                LocalDate.of(2024, 6, 20),
                "Maintenance",
                property);

        Block block = new Block();
        block.setId(1L);
        block.setStartDate(blockDTO.getStartDate());
        block.setEndDate(blockDTO.getEndDate());
        block.setReason(blockDTO.getReason());
        block.setProperty(blockDTO.getProperty());

        when(blockRepository.save(any(Block.class))).thenReturn(block);

        Block createdBlock = blockService.createBlock(blockDTO);

        assertThat(createdBlock).isNotNull();
        assertThat(createdBlock.getId()).isEqualTo(1L);
    }

    @Test
    void testCreateBlock_BlockDatesConflict() {
        Property property = new Property();
        property.setId(1L);

        BlockDTO blockDTO = new BlockDTO(
                LocalDate.of(2024, 6, 10),
                LocalDate.of(2024, 6, 20),
        "Maintenance",
                property);

        when(bookingRepository.findOverlappingBookings(any(LocalDate.class), any(LocalDate.class), anyLong()))
                .thenReturn(List.of(new Booking()));

        Throwable thrown = catchThrowable(() -> blockService.createBlock(blockDTO));

        assertThat(thrown)
                .isInstanceOf(BlockException.class)
                .hasMessage(Constants.BLOCK_DATES_CONFLICT)
                .hasFieldOrPropertyWithValue("status", HttpStatus.BAD_REQUEST);
    }

    @Test
    void testUpdateBlock_Success() {
        Block existingBlock = new Block();
        existingBlock.setId(1L);
        existingBlock.setStartDate(LocalDate.of(2024, 6, 10));
        existingBlock.setEndDate(LocalDate.of(2024, 6, 20));
        existingBlock.setReason("Maintenance");
        Property property = new Property();
        property.setId(1L);
        existingBlock.setProperty(property);

        BlockDTO blockDTO = new BlockDTO(
            LocalDate.of(2024, 6, 15),
            LocalDate.of(2024, 6, 25),
        "Updated Maintenance",
            property);

        when(blockRepository.findById(anyLong())).thenReturn(Optional.of(existingBlock));
        when(blockRepository.save(any(Block.class))).thenReturn(existingBlock);

        Block updatedBlock = blockService.updateBlock(1L, blockDTO);

        assertThat(updatedBlock).isNotNull();
        assertThat(updatedBlock.getStartDate()).isEqualTo(LocalDate.of(2024, 6, 15));
        assertThat(updatedBlock.getEndDate()).isEqualTo(LocalDate.of(2024, 6, 25));
        assertThat(updatedBlock.getReason()).isEqualTo("Updated Maintenance");
    }

    @Test
    void testDeleteBlock_Success() {
        Block block = new Block();
        block.setId(1L);
        block.setStartDate(LocalDate.of(2024, 6, 10));
        block.setEndDate(LocalDate.of(2024, 6, 20));
        block.setReason("Maintenance");

        when(blockRepository.findById(anyLong())).thenReturn(Optional.of(block));

        blockService.deleteBlock(1L);

        verify(blockRepository, times(1)).delete(block);
    }
}