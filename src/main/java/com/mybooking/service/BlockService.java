package com.mybooking.service;

import com.mybooking.dto.BlockDTO;
import com.mybooking.exception.BlockException;
import com.mybooking.exception.Constants;
import com.mybooking.model.Block;
import com.mybooking.repository.BlockRepository;
import com.mybooking.repository.BookingRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class BlockService {

    @Autowired
    private BlockRepository blockRepository;

    @Autowired
    private BookingRepository bookingRepository;

    public Block getById(Long id) {
        log.info("Getting Block by ID: {}", id);
        Block block = this.findById(id);
        log.info("Successfully retrieved Block: {}", block);
        return block;
    }

    public Block createBlock(BlockDTO blockDTO) {
        log.info("Creating Block {}", blockDTO);

        validBlock(blockDTO);

        Block newBlock = new Block();
        newBlock.setStartDate(blockDTO.getStartDate());
        newBlock.setEndDate(blockDTO.getEndDate());
        newBlock.setReason(blockDTO.getReason());
        newBlock.setProperty(blockDTO.getProperty());

        Block savedBlock = blockRepository.save(newBlock);
        log.info("Successfully created Block: {}", savedBlock);
        return savedBlock;
    }

    public Block updateBlock(Long id, BlockDTO blockDTO) {
        log.info("Updating Block {} with ID: {}", blockDTO, id);

        Block updatedBlock = this.findById(id);
        validBlock(blockDTO) ;
        updatedBlock.setStartDate(blockDTO.getStartDate());
        updatedBlock.setEndDate(blockDTO.getEndDate());
        updatedBlock.setReason(blockDTO.getReason());

        Block savedBlock = blockRepository.save(updatedBlock);
        log.info("Successfully updated Block: {}", savedBlock);
        return savedBlock;
    }

    public void deleteBlock(Long id) {
        log.info("Deleting Block with ID: {}", id);
        Block existingBlock = this.findById(id);
        blockRepository.delete(existingBlock);
        log.info("Successfully deleted Block with ID: {}", id);
    }

    public Block findById(Long id){
        log.info("Finding Block by ID: {}", id);
        return blockRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Block not found with ID: {}", id);
                    return new BlockException(Constants.BLOCK_NOT_FOUND, HttpStatus.NOT_FOUND);
                });
    }

    private boolean isBooked(BlockDTO blockDTO) {
        boolean booked = !bookingRepository.findOverlappingBookings(
                blockDTO.getStartDate(), blockDTO.getEndDate(), blockDTO.getProperty().getId()).isEmpty();
        log.info("For Block: {} - There is a booked: {}", blockDTO, booked);
        return booked;
    }

    private void validBlock(BlockDTO blockDTO) {
        log.info("Validating Block: {}", blockDTO);
        if (isBooked(blockDTO)) {
            log.warn("Block dates with conflict: {}", blockDTO);
            throw new BlockException(Constants.BLOCK_DATES_CONFLICT, HttpStatus.BAD_REQUEST);
        }
        log.info("Block validated successfully: {}", blockDTO);
    }

}
