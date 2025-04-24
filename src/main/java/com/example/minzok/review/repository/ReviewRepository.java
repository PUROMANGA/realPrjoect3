package com.example.minzok.review.repository;

import com.example.minzok.review.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    @Query(value = "select id, contents, rating, store_id, creat_time, modified_time from minzok.reviews where rating between ? and ? order by creat_time desc", nativeQuery = true)
    List<Review> searchFindByRating(int min, int max);

    default Review findByIdOrElseThrow(Long id) {
        return findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 아이디를 찾을 수 없습니다." + id));
    }

}
