package com.example.resumeBuilder.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "job_recommendations")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class JobRecommendation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String jobTitle;
    private String company;
    private String location;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
