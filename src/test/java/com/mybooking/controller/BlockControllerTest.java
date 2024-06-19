package com.mybooking.controller;

import com.mybooking.dto.BlockDTO;
import com.mybooking.model.Block;
import com.mybooking.model.Property;
import com.mybooking.service.BlockService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class BlockControllerTest {

    @Mock
    private BlockService blockService;

    @InjectMocks
    private BlockController blockController;

    private MockMvc mockMvc;
    private Block block;
    private Property property;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(blockController).build();

        property = new Property();
        property.setId(1L);

        block = new Block();
        block.setId(1L);
        block.setStartDate(LocalDate.now().plusDays(1));
        block.setEndDate(LocalDate.now().plusDays(5));
        block.setReason("Maintenance");
        block.setProperty(property);
    }

    @Test
    void testCreateBlock() throws Exception {
        when(blockService.createBlock(any(BlockDTO.class))).thenReturn(block);

        mockMvc.perform(post("/block")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"startDate\": \"2024-06-15\", \"endDate\": \"2024-06-20\", \"reason\": \"Maintenance\", \"property\": {\"id\": 1} }"))
                .andExpect(status().isOk());
    }

    @Test
    void testUpdateBlock() throws Exception {
        when(blockService.updateBlock(anyLong(), any(BlockDTO.class))).thenReturn(block);

        mockMvc.perform(put("/block/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"startDate\": \"2024-06-15\", \"endDate\": \"2024-06-20\", \"reason\": \"Maintenance\", \"property\": {\"id\": 1} }"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void testDeleteBlock() throws Exception {
        mockMvc.perform(delete("/block/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
