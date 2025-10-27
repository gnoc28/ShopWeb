package com.example.ShopDt.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "news_detail")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class NewsDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT")
    private String content;

    private int status;
    private int type;
    private int contentIndex;

    @ManyToOne
    @JoinColumn(name = "news_id")
    private News news;
}