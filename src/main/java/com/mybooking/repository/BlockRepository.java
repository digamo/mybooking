package com.mybooking.repository;

import com.mybooking.model.Block;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface BlockRepository extends JpaRepository<Block, Long> {

    @Query("SELECT b FROM Block b WHERE b.property.id = :propertyId AND " +
            "(b.startDate <= :endDate AND b.endDate >= :startDate) ")
    List<Block> findOverlappingBlocks(LocalDate startDate, LocalDate endDate, Long propertyId);
}
