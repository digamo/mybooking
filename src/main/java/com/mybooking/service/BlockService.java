package com.mybooking.service;

import com.mybooking.dto.BlockDTO;
import com.mybooking.exception.BlockException;
import com.mybooking.model.Block;
import com.mybooking.repository.BlockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BlockService {

    @Autowired
    private BlockRepository blockRepository;

    public Block createBlock(BlockDTO blockDTO) {
        if (isBlockOverlap(blockDTO)) {
            throw new BlockException("Block dates overlap with existing booking or block.", HttpStatus.BAD_REQUEST);
        }
        Block newBlock = new Block();
        newBlock.setStartDate(blockDTO.getStartDate());
        newBlock.setEndDate(blockDTO.getEndDate());
        newBlock.setReason(blockDTO.getReason());
        newBlock.setProperty(blockDTO.getProperty());

        return blockRepository.save(newBlock);
    }

    public Block updateBlock(Long id, BlockDTO blockDetails) {
        return blockRepository.findById(id).map(existingBlock -> {
            if (isBlockOverlap(blockDetails)) {
                throw new BlockException("Block dates overlap with existing booking or block.", HttpStatus.BAD_REQUEST);
            }
            Block updatedBlock = new Block();
            updatedBlock.setStartDate(blockDetails.getStartDate());
            updatedBlock.setEndDate(blockDetails.getEndDate());
            updatedBlock.setReason(blockDetails.getReason());

            return blockRepository.save(updatedBlock);
        }).orElseThrow(() -> new BlockException("Block not found", HttpStatus.NOT_FOUND));
    }

    public void deleteBlock(Long id) {
        Optional<Block> existingBlock = blockRepository.findById(id);
        if(existingBlock.isPresent()) {
            blockRepository.delete(existingBlock.get());
        }else{
            throw new BlockException("Block not found", HttpStatus.NOT_FOUND);
        }
    }

    private boolean isBlockOverlap(BlockDTO blockDTO) {
        List<Block> overlappingBlocks = blockRepository.findOverlappingBlocks(
                blockDTO.getStartDate(), blockDTO.getEndDate(), blockDTO.getProperty().getId());
        return !overlappingBlocks.isEmpty();
    }


}
