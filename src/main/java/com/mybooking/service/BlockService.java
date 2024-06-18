package com.mybooking.service;

import com.mybooking.dto.BlockDTO;
import com.mybooking.exception.BlockException;
import com.mybooking.exception.Constants;
import com.mybooking.model.Block;
import com.mybooking.repository.BlockRepository;
import com.mybooking.repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class BlockService {

    @Autowired
    private BlockRepository blockRepository;

    @Autowired
    private BookingRepository bookingRepository;

    public Block createBlock(BlockDTO blockDTO) {
        validBlock(blockDTO);

        Block newBlock = new Block();
        newBlock.setStartDate(blockDTO.getStartDate());
        newBlock.setEndDate(blockDTO.getEndDate());
        newBlock.setReason(blockDTO.getReason());
        newBlock.setProperty(blockDTO.getProperty());

        return blockRepository.save(newBlock);
    }

    public Block updateBlock(Long id, BlockDTO blockDTO) {
        Block updatedBlock = this.findById(id);
        validBlock(blockDTO) ;
        updatedBlock.setStartDate(blockDTO.getStartDate());
        updatedBlock.setEndDate(blockDTO.getEndDate());
        updatedBlock.setReason(blockDTO.getReason());
        return blockRepository.save(updatedBlock);
    }

    public void deleteBlock(Long id) {
        Block existingBlock = this.findById(id);
        blockRepository.delete(existingBlock);
    }

    private Block findById(Long id){
        return blockRepository.findById(id).orElseThrow(() -> new BlockException(Constants.BLOCK_NOT_FOUND, HttpStatus.NOT_FOUND));
    }

    private boolean isBooked(BlockDTO blockDTO) {
        return !bookingRepository.findOverlappingBookings(
                blockDTO.getStartDate(), blockDTO.getEndDate(), blockDTO.getProperty().getId()).isEmpty();
    }

    private void validBlock(BlockDTO blockDTO) {
        if (isBooked(blockDTO))
            throw new BlockException(Constants.BLOCK_DATES_CONFLICT, HttpStatus.BAD_REQUEST);
    }

}
