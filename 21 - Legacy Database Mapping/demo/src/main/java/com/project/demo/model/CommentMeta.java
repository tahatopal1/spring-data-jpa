package com.project.demo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "wp_commentmeta", indexes = {
        @Index(name = "comment_id", columnList = "comment_id"),
        @Index(name = "meta_key", columnList = "meta_key")
})
public class CommentMeta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "meta_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "comment_id")
    private Comment comment;

    @Size(max = 255)
    @Column(name = "meta_key")
    private String metaKey;

    @Lob
    private String metaValue;
}
