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


    @Query("select r from Review r where r.rating between ?1 and ?2 order by r.creatTime desc")
    List<Review> searchFindByRating(int min, int max);

    @Query("SELECT r FROM Review r WHERE r.store.id = :storeId ORDER BY r.creatTime DESC")
    List<Review> findAllByStoreId(Long storeId);

    default Review findByIdOrElseThrow(Long id) {
        return findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 아이디를 찾을 수 없습니다." + id));
    }

}
