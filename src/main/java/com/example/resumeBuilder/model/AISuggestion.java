package com.example.resumeBuilder.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "ai_suggestions")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AISuggestion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "suggestions", columnDefinition = "TEXT")
    private String suggestions;  // AI-generated resume suggestions

    @Column(name = "created_at")
    private LocalDateTime created_at;   // Timestamp of when the suggestion was generated

    @Column(name = "updated_at")
    private LocalDateTime updated_at;  // Timestamp of when the suggestion was last updated

}
