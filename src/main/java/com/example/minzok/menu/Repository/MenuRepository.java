package com.example.minzok.menu.Repository;

import com.example.minzok.menu.Entity.Menu;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository

public interface MenuRepository extends JpaRepository<Menu, Long> {

    List<Menu> findByStoreId(Long storeId, Pageable pageable);

    /**
     *Store_Id로 메뉴를 찾아줄수있는 메소드이다.
     * @param postId
     * @param pageable
     * @return
     */
}
