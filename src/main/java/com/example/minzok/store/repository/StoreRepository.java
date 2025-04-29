package com.example.minzok.store.repository;

import com.example.minzok.member.entity.Member;
import com.example.minzok.store.entity.Store;
import com.example.minzok.store.entity.StoreStatus;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

public interface StoreRepository extends JpaRepository<Store, Long>, CustomStoreRepository {

    @Query("select s " +
            "from Store s " +
            "Where s.storeName LIKE CONCAT('%', :keyword, '%')" +
            "AND s.storeStatus = 'OPEN'")
    Slice<Store> storeNameFindByKeyword(String keyword, Pageable pageable);

    @Query("select s " +
            "from Store s " +
            "Where s.storeStatus IN :status")
    List<Store> findStatusIn(List<StoreStatus> status);

    int countByMemberEmail(String email);

    @Query("SELECT s FROM Store s WHERE s.member.email = :email AND s.storeStatus <> :status")
    List<Store> findStoreByMemberEmailAndStoreStatus(@Param("email") String email, @Param("status") StoreStatus status);
}
