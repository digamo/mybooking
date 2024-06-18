package com.mybooking.controller;

import com.mybooking.dto.BlockDTO;
import com.mybooking.model.Block;
import com.mybooking.service.BlockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/blocks")
public class BlockController {

    @Autowired
    private BlockService blockService;

    @GetMapping("/{id}")
    public ResponseEntity<Block> getBooking(@PathVariable Long id) {
        return ResponseEntity.ok(blockService.getById(id));
    }

    @PostMapping
    public ResponseEntity<Block> createBlock(@RequestBody BlockDTO blockDTO) {
        return ResponseEntity.ok(blockService.createBlock(blockDTO));
    }
    @PutMapping("/{id}")
    public ResponseEntity<Block> updateBlock(@PathVariable Long id, @RequestBody BlockDTO blockDTO) {
        try {
            return ResponseEntity.ok(blockService.updateBlock(id, blockDTO));
        }catch (IllegalArgumentException e){
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBlock(@PathVariable Long id) {
        blockService.deleteBlock(id);
        return ResponseEntity.ok().build();
    }
}
