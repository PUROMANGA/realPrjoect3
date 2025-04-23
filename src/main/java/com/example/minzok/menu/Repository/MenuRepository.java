package com.example.minzok.menu.Repository;

import com.example.minzok.menu.Entity.Menu;
import com.example.minzok.store.entity.Store;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository

public interface MenuRepository extends JpaRepository<Menu, Long> {

    List<Menu> findByStoreId(Long storeId, Pageable pageable);
}
