package com.mybooking.repository;

import com.mybooking.model.Block;
import com.mybooking.model.Property;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class BlockRepositoryTest {

    @Autowired
    private BlockRepository blockRepository;

    private Block block;
    private Property property;
/*

    @BeforeEach
    void setUp() {
        property = Property.builder().name("Sample Property").build();
        block = Block.builder()
                .startDate(LocalDate.now().plusDays(1))
                .endDate(LocalDate.now().plusDays(5))
                .status(StatusBlock.ATIVO)
                .reason("Maintenance")
                .property(property)
                .build();
        blockRepository.save(block);
    }

    @Test
    void testFindOverlappingBlocks() {
        List<Block> overlappingBlocks = blockRepository.findOverlappingBlocks(
                LocalDate.now().plusDays(2), LocalDate.now().plusDays(4), property.getId());
        assertFalse(overlappingBlocks.isEmpty());
    }

    @Test
    void testSaveBlock() {
        Block savedBlock = blockRepository.save(block);
        assertNotNull(savedBlock);
        assertNotNull(savedBlock.getId());
    }
*/
}
