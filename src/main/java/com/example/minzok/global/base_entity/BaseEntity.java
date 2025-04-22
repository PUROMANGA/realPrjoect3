package com.example.minzok.global.base_entity;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)

public class BaseEntity {

    @Column(nullable = false, columnDefinition = "TINYINT(1) DEFAULT 0" )
    private boolean withdrawn;

    @CreatedDate // 생성시 자동입력
    @Column(updatable = false)
    private LocalDateTime creatTime;

    @LastModifiedDate
    private LocalDateTime modifiedTime;
}
