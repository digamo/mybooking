package com.mybooking.repository;


import com.mybooking.model.Block;
import com.mybooking.model.Property;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
class BlockRepositoryTest {

    @Autowired
    private BlockRepository blockRepository;
    @Autowired
    private PropertyRepository propertyRepository;
    private Property property;

    @BeforeEach
    void setup() {
        property = new Property();
        property.setName("Test Property");
        propertyRepository.save(property);

        Block block1 = new Block();
        block1.setStartDate(LocalDate.of(2024, 6, 10));
        block1.setEndDate(LocalDate.of(2024, 6, 20));
        block1.setReason("Maintenance");
        block1.setProperty(property);

        Block block2 = new Block();
        block2.setStartDate(LocalDate.of(2024, 7, 10));
        block2.setEndDate(LocalDate.of(2024, 7, 20));
        block2.setReason("Maintenance");
        block2.setProperty(property);

        blockRepository.save(block1);
        blockRepository.save(block2);
    }

    @Test
    void testFindOverlappingBlocks() {
        List<Block> overlappingBlocks = blockRepository.findOverlappingBlocks(
                LocalDate.of(2024, 6, 15),
                LocalDate.of(2024, 6, 25),
                property.getId());

        assertThat(overlappingBlocks).hasSize(1);
        assertThat(overlappingBlocks.get(0).getStartDate()).isEqualTo(LocalDate.of(2024, 6, 10));
        assertThat(overlappingBlocks.get(0).getEndDate()).isEqualTo(LocalDate.of(2024, 6, 20));
    }

    @Test
    void testFindNonOverlappingBlocks() {
        List<Block> overlappingBlocks = blockRepository.findOverlappingBlocks(
                LocalDate.of(2024, 6, 21),
                LocalDate.of(2024, 6, 30),
                property.getId());

        assertThat(overlappingBlocks).isEmpty();
    }
}