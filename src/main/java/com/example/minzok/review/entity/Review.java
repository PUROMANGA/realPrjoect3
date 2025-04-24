package com.example.minzok.review.entity;

import com.example.minzok.global.base_entity.BaseEntity;
import com.example.minzok.member.entity.Member;
import com.example.minzok.store.entity.Store;
import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
@Table(name = "reviews")
public class Review extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, columnDefinition = "longtext")
    private String contents;

    @Column(nullable = false)
    private int rating;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "store_id")
    private Store store;

    public Review(String contents, int rating) {
        this.contents = contents;
        this.rating = rating;
    }

    public Review() {

    }

    public void updateReview(Long id, String contents, int rating) {
        this.id = id;
        this.contents = contents;
        this.rating = rating;
    }
}
