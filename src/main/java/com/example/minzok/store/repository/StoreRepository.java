package com.example.minzok.store.repository;

import com.example.minzok.member.entity.Member;
import com.example.minzok.store.entity.Store;
import com.example.minzok.store.entity.StoreStatus;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

public interface StoreRepository extends JpaRepository<Store, Long>, CustomStoreRepository {

    @Query("select s " +
            "from Store s " +
            "Join s.menus m " +
            "Where m.name LIKE CONCAT('%', :keyword, '%')" +
            "AND s.storeStatus = 'OPEN'")
    Slice<Store> storeNameFindByKeyword(String keyword, Pageable pageable);

    List<Store> findByStoreStatusNot(StoreStatus storeStatus);

    int countByMemberEmail(String email);
}
