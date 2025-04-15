package com.example.resumeBuilder.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "resumes")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Resume {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private String summary;  // not given by client but generated using generateSummary()
    private String skills;
    private String experience;
}
