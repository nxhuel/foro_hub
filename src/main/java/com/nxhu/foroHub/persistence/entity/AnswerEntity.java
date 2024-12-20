package com.nxhu.foroHub.persistence.entity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "answer")
public class AnswerEntity
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String message;
    @ManyToOne
    @JoinColumn(name = "topic_id", nullable = false)
    private TopicEntity topic;
    @Column(nullable = false)
    private LocalDateTime creation_date;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity author;
    @Column(nullable = false)
    private String solution;
}
