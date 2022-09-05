package com.project.demo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "wp_usermeta")
@Getter
@Setter
public class UserMeta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "umeta_id")
    private Long id;

    @ManyToOne
    private User user;

    @Size(max = 255)
    private String metaKey;

    @Lob
    @Column(columnDefinition = "longtext")
    private String metaValue;

}
