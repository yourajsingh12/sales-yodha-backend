package com.salesyodha.salesyodha_backend.Entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "access_codes")
public class AccessCodeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String code;

    private boolean isUsed;

    private LocalDateTime createdAt;

    private LocalDateTime expiryDate;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
        this.isUsed = false;
    }
}
