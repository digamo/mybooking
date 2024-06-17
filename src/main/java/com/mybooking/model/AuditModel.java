package com.mybooking.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import java.time.LocalDateTime;

@MappedSuperclass
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public abstract class AuditModel {

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime insertAt;

    @LastModifiedDate
    private LocalDateTime updateAt;

    private LocalDateTime deleteAt;

}